package com.raf.reservationservice.service.impl;

import com.raf.reservationservice.domain.Hotel;
import com.raf.reservationservice.dto.HotelCreateDto;
import com.raf.reservationservice.dto.HotelDto;
import com.raf.reservationservice.dto.UserDto;
import com.raf.reservationservice.exception.BadRequestException;
import com.raf.reservationservice.exception.NotFoundException;
import com.raf.reservationservice.mapper.HotelMapper;
import com.raf.reservationservice.repository.HotelRepository;
import com.raf.reservationservice.service.HotelService;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;
    private HotelMapper hotelMapper;
    private RestTemplate userServiceApiClient;
    private Retry userServiceRetry;

    public HotelServiceImpl(HotelRepository hotelRepository, HotelMapper hotelMapper, @Qualifier("userServiceApiClient") RestTemplate userServiceApiClient,
                            Retry userServiceRetry) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
        this.userServiceApiClient = userServiceApiClient;
        this.userServiceRetry = userServiceRetry;
    }

    @Override
    public Page<HotelDto> findAllHotels(Pageable pageable) {
        List<Hotel> hotelList = hotelRepository.findAll();
        List<HotelDto> hotelDtos = new ArrayList<>();
        for(Hotel h: hotelList){

            UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto(h.getManagerId())).get();

            hotelDtos.add(hotelMapper.hotelToHotelDto(h, userDto));

        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), hotelList.size());
        final Page<HotelDto> page = new PageImpl<>(hotelDtos.subList(start, end), pageable, hotelDtos.size());
        return page;
    }

    @Override
    public HotelDto updateHotel(HotelCreateDto hotelCreateDto, Long id) {
        Optional<Hotel> hotelCheck = hotelRepository.findById(id);
        if(!hotelCheck.isPresent()){
            throw new NotFoundException("Hotel with given id doesn't exist!");
        }

             UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto(hotelCreateDto.getManagerId())).get();

            Hotel hotel = hotelMapper.update(hotelCheck.get(), hotelCreateDto);
            hotelRepository.save(hotel);
            return hotelMapper.hotelToHotelDto(hotel, userDto);

    }

    @Override
    public HotelDto createHotel(HotelCreateDto hotelCreateDto) {
        Optional<Hotel> hotelCheck = hotelRepository.findByHotelName(hotelCreateDto.getHotelName());
        if(hotelCheck.isPresent()){
            throw new BadRequestException("Hotel with given name already exists!");
        }

            UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto(hotelCreateDto.getManagerId())).get();

            Hotel hotel = hotelMapper.hotelCreateDtoToHotel(hotelCreateDto);
            hotelRepository.save(hotel);
            return hotelMapper.hotelToHotelDto(hotel, userDto);

    }

    @Override
    public void deleteHotelById(Long id) {
        Optional<Hotel> hotelCheck = hotelRepository.findById(id);
        if(!hotelCheck.isPresent()){
            throw new NotFoundException("Hotel with given id doesn't exist!");
        }
        hotelRepository.delete(hotelCheck.get());
    }

    @Override
    public HotelDto findHotelById(Long id) {
        Optional<Hotel> hotelCheck = hotelRepository.findById(id);
        if(!hotelCheck.isPresent()){
            throw new NotFoundException("Hotel with given id doesn't exist!");
        }

        UserDto userDto = Retry.decorateSupplier(userServiceRetry, () -> getUserDto(hotelCheck.get().getManagerId())).get();
        return hotelMapper.hotelToHotelDto(hotelCheck.get(), userDto);

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
}
