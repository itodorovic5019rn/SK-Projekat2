package com.raf.reservationservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raf.reservationservice.domain.Period;
import com.raf.reservationservice.domain.Reservation;
import com.raf.reservationservice.domain.Room;
import com.raf.reservationservice.domain.RoomLayout;
import com.raf.reservationservice.dto.*;
import com.raf.reservationservice.exception.BadRequestException;
import com.raf.reservationservice.exception.NotFoundException;
import com.raf.reservationservice.exception.ServerErrorException;
import com.raf.reservationservice.mapper.PeriodMapper;
import com.raf.reservationservice.mapper.ReservationMapper;
import com.raf.reservationservice.mapper.RoomLayoutMapper;
import com.raf.reservationservice.messagebroker.MessageHelper;
import com.raf.reservationservice.repository.PeriodRepository;
import com.raf.reservationservice.repository.ReservationRepository;
import com.raf.reservationservice.repository.RoomLayoutRepository;
import com.raf.reservationservice.repository.RoomRepository;
import com.raf.reservationservice.service.ReservationService;
import com.raf.reservationservice.service.RoomLayoutService;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;
    private RoomLayoutRepository roomLayoutRepository;
    private RoomRepository roomRepository;
    private PeriodRepository periodRepository;
    private ReservationMapper reservationMapper;
    private RoomLayoutMapper roomLayoutMapper;
    private PeriodMapper periodMapper;
    private RestTemplate userServiceApiClient;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String incrementReservationCountDestination;
    private String decrementReservationCountDestination;
    private String reservationCompleteDestination;
    private String reservationCancelDestination;
    private Retry userServiceRetry;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ReservationMapper reservationMapper,
                                  @Qualifier("userServiceApiClient") RestTemplate userServiceApiClient,
                                  JmsTemplate jmsTemplate, MessageHelper messageHelper,
                                  @Value("${destination.increment.reservation.count}") String incrementReservationCountDestination,
                                  @Value("${destination.decrement.reservation.count}") String decrementReservationCountDestination,
                                  @Value("${destination.reservationComplete}") String reservationCompleteDestination,
                                  @Value("${destination.reservationCancel}") String reservationCancelDestination,
                                  RoomLayoutRepository roomLayoutRepository, RoomRepository roomRepository, RoomLayoutMapper roomLayoutMapper,
                                  PeriodRepository periodRepository, PeriodMapper periodMapper, Retry userServiceRetry) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.userServiceApiClient = userServiceApiClient;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.incrementReservationCountDestination = incrementReservationCountDestination;
        this.decrementReservationCountDestination = decrementReservationCountDestination;
        this.reservationCompleteDestination = reservationCompleteDestination;
        this.reservationCancelDestination = reservationCancelDestination;
        this.roomLayoutRepository = roomLayoutRepository;
        this.roomLayoutMapper = roomLayoutMapper;
        this.roomRepository = roomRepository;
        this.periodMapper = periodMapper;
        this.periodRepository = periodRepository;
        this.userServiceRetry = userServiceRetry;

    }

    @Override
    public ReservationDto createReservation(ReservationCreateDto reservationCreateDto) {
        Optional<Reservation> reservationCheck = reservationRepository.findByUserIdAndRoomIdAndPeriodId(reservationCreateDto.getUserId(), reservationCreateDto.getRoomId(), reservationCreateDto.getPeriodId());
        if(reservationCheck.isPresent()){
            throw new BadRequestException("Reservation already exists!");
        }


            UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto( reservationCreateDto.getUserId())).get();
            DiscountDto discountDto = Retry.decorateSupplier(userServiceRetry, () -> getDiscountDto( reservationCreateDto.getUserId())).get();


        Reservation reservation = reservationMapper.reservationCreateDtoToReservation(reservationCreateDto, discountDto);

            jmsTemplate.convertAndSend(incrementReservationCountDestination, messageHelper.createTextMessage(new IncrementReservationCountDto(reservation.getUserId())));

            reservationRepository.save(reservation);
            ReservationDto reservationDto = reservationMapper.reservationToReservationDto(reservation, userDto);

            jmsTemplate.convertAndSend(reservationCompleteDestination, messageHelper.createTextMessage(new ReservationCompleteDto(userDto,reservationDto.getRoom().getHotelDto().getManager())));
            return  reservationDto;

    }

    @Override
    public void deleteReservationById(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(!reservation.isPresent()){
            throw new NotFoundException("Reservation with given id doesn't exist!");
        }

        UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto( reservation.get().getUserId())).get();
        ReservationDto reservationDto = reservationMapper.reservationToReservationDto(reservation.get(), userDto);
        jmsTemplate.convertAndSend(decrementReservationCountDestination, messageHelper.createTextMessage(new DecrementReservationCountDto(reservation.get().getUserId())));
        jmsTemplate.convertAndSend(reservationCancelDestination, messageHelper.createTextMessage(new ReservationCompleteDto(userDto, reservationDto.getRoom().getHotelDto().getManager())));

        reservationRepository.delete(reservation.get());

    }

    @Override
    public Page<ReservationDto> findAllReservations(Pageable pageable) {
        List<Reservation> reservationList = reservationRepository.findAll();
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for(Reservation r: reservationList){
            try {
                UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto( r.getUserId())).get();
                reservationDtos.add(reservationMapper.reservationToReservationDto(r, userDto));
            }catch(HttpClientErrorException e){
                if(e.getStatusCode().value() == 404)
                    throw new NotFoundException("User with given id doesn't exist");
                throw e;
            }catch (Exception e){
                throw e;
            }
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), reservationList.size());
        final Page<ReservationDto> page = new PageImpl<>(reservationDtos.subList(start, end), pageable, reservationList.size());
        return page;
    }

    @Override
    public ReservationDto getReservationById(Long id) {

            Optional<Reservation> reservation = reservationRepository.findById(id);
            if (!reservation.isPresent()) {
                throw new NotFoundException("Reservation with given id doesn't exist!");
            }
            UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto( reservation.get().getUserId())).get();

            return reservationMapper.reservationToReservationDto(reservation.get(), userDto);

    }

    @Override
    public Page<FreeTermsDto> getFreeTerms(Pageable pageable) {
        List<RoomLayoutDto> roomLayoutDtos = roomLayoutRepository.findAll().stream().map(roomLayoutMapper::roomLayoutToRoomLayoutDto).collect(Collectors.toList());
        List<Period> periods = periodRepository.findAll();

        List<FreeTermsDto> freeTermsDtos = new ArrayList<>();
        for(Period p: periods) {
            for (RoomLayoutDto roomLayoutDto : roomLayoutDtos) {
                FreeTermsDto freeTermDto = new FreeTermsDto();
                freeTermDto.setRoomLayoutDto(roomLayoutDto);
                freeTermDto.setPeriod(p);
                freeTermDto.setNumberOfFreeRooms(findNumberOfRooms(roomLayoutDto, p));
                freeTermsDtos.add(freeTermDto);
            }
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), freeTermsDtos.size());
        final Page<FreeTermsDto> page = new PageImpl<>(freeTermsDtos.subList(start, end), pageable, freeTermsDtos.size());
        return page;
    }

    private int findNumberOfRooms(RoomLayoutDto roomLayoutDto, Period period){
        List<Room> rooms = roomRepository.getAllByHotelIdAndTypeId(roomLayoutDto.getHotelDto().getId(), roomLayoutDto.getRoomType().getId());
        int cnt = 0;
        for(Room r: rooms){
            Optional<Reservation> reservation = reservationRepository.findReservationByRoomIdAndPeriodId(r.getId(),period.getId());
            if(reservation.isPresent())
                cnt += 1;

        }

        return rooms.size()-cnt;

    }

    private UserDto getUserDto(Long id){
        System.out.println("Getting user...");
        try {
            ResponseEntity<UserDto> userDto = userServiceApiClient.exchange("/" + id, HttpMethod.GET, null, UserDto.class);
            return userDto.getBody();
        }catch(HttpClientErrorException e){
            if(e.getStatusCode().value() == 404)
                throw new NotFoundException("User with given id doesn't exist");
            throw  new RuntimeException("Internal server error!");
        }catch (Exception e){
            throw  new RuntimeException("Internal server error!");
        }

    }

    private DiscountDto getDiscountDto(Long id){
        System.out.println("Getting discount...");
        try {
            ResponseEntity<DiscountDto> discountDto = userServiceApiClient.exchange("/discount/" + id, HttpMethod.GET, null, DiscountDto.class);
            return discountDto.getBody();
        }catch(HttpClientErrorException e){
            if(e.getStatusCode().value() == 404)
                throw new NotFoundException("User with given id doesn't exist");
            throw  new RuntimeException("Internal server error!");
        }catch (Exception e){
            throw  new RuntimeException("Internal server error!");
        }

    }
}
