package com.raf.reservationservice.service.impl;

import com.raf.reservationservice.domain.Reservation;
import com.raf.reservationservice.domain.Room;
import com.raf.reservationservice.domain.RoomLayout;
import com.raf.reservationservice.dto.RoomDto;
import com.raf.reservationservice.dto.RoomLayoutCreateDto;
import com.raf.reservationservice.dto.RoomLayoutDto;
import com.raf.reservationservice.exception.BadRequestException;
import com.raf.reservationservice.exception.NotFoundException;
import com.raf.reservationservice.mapper.RoomLayoutMapper;
import com.raf.reservationservice.repository.ReservationRepository;
import com.raf.reservationservice.repository.RoomLayoutRepository;
import com.raf.reservationservice.repository.RoomRepository;
import com.raf.reservationservice.service.RoomLayoutService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomLayoutServiceImpl implements RoomLayoutService {

    private RoomLayoutRepository roomLayoutRepository;
    private RoomLayoutMapper roomLayoutMapper;
    private RoomRepository roomRepository;
    private ReservationRepository reservationRepository;

    public RoomLayoutServiceImpl(RoomLayoutRepository roomLayoutRepository, RoomLayoutMapper roomLayoutMapper, RoomRepository roomRepository,
                                 ReservationRepository reservationRepository) {
        this.roomLayoutRepository = roomLayoutRepository;
        this.roomLayoutMapper = roomLayoutMapper;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public RoomLayoutDto createRoomLayout(RoomLayoutCreateDto roomLayoutCreateDto) {
        Optional<RoomLayout> roomLayoutCheck = roomLayoutRepository.findRoomLayoutByHotelIdAndRoomTypeId(roomLayoutCreateDto.getHotelId(), roomLayoutCreateDto.getRoomTypeId());
        if(roomLayoutCheck.isPresent()){
            throw new BadRequestException("Room layout with given hotel id and room type id already exists!");
        }
        for(int i = roomLayoutCreateDto.getBottomLimit(); i <= roomLayoutCreateDto.getTopLimit(); i++){
            Room room = new Room();
            room.setRoomNum(i);
            room.setHotelId(roomLayoutCreateDto.getHotelId());
            room.setTypeId(roomLayoutCreateDto.getRoomTypeId());
            roomRepository.save(room);
        }
        RoomLayout roomLayout = roomLayoutMapper.roomLayoutCreateDtoToRoomLayout(roomLayoutCreateDto);
        roomLayoutRepository.save(roomLayout);
        return roomLayoutMapper.roomLayoutToRoomLayoutDto(roomLayout);
    }

    @Override
    public RoomLayoutDto updateRoomLayout(RoomLayoutCreateDto roomLayoutCreateDto, Long id) {
        Optional<RoomLayout> roomLayoutCheck = roomLayoutRepository.findById(id);
        if(!roomLayoutCheck.isPresent()){
            throw new NotFoundException("Room layout with given id doesn't exist!");
        }
        List<Room> rooms = roomRepository.getAllByHotelIdAndTypeId(roomLayoutCheck.get().getHotelId(), roomLayoutCheck.get().getRoomTypeId());
        for(Room r: rooms){
            roomRepository.delete(r);
        }
        for(int i = roomLayoutCreateDto.getBottomLimit(); i <= roomLayoutCreateDto.getTopLimit(); i++){
            Room room = new Room();
            room.setRoomNum(i);
            room.setHotelId(roomLayoutCreateDto.getHotelId());
            room.setTypeId(roomLayoutCreateDto.getRoomTypeId());
            roomRepository.save(room);
        }
        RoomLayout roomLayout = roomLayoutMapper.update(roomLayoutCheck.get(), roomLayoutCreateDto);
        roomLayoutRepository.save(roomLayout);
        return roomLayoutMapper.roomLayoutToRoomLayoutDto(roomLayout);
    }

    @Override
    public void deleteRoomLayout(Long id) {
        Optional<RoomLayout> roomLayoutCheck = roomLayoutRepository.findById(id);
        if(!roomLayoutCheck.isPresent()){
            throw new NotFoundException("Room layout with given id doesn't exist!");
        }
        List<Room> rooms = roomRepository.getAllByHotelIdAndTypeId(roomLayoutCheck.get().getHotelId(), roomLayoutCheck.get().getRoomTypeId());
        boolean check = false;
        for(Room r: rooms){
            Optional<Reservation> reservation = reservationRepository.findReservationByRoomId(r.getId());
            if(reservation.isPresent()){
                check = true;
            }
        }
        if(check){
            throw new BadRequestException("Cannot delete rooms because they are reserved.");
        }
        for(Room r: rooms){
            roomRepository.delete(r);
        }
        roomLayoutRepository.delete(roomLayoutCheck.get());
    }

    @Override
    public RoomLayoutDto findRoomLayoutById(Long id) {
        Optional<RoomLayout> roomLayoutCheck = roomLayoutRepository.findById(id);
        if(!roomLayoutCheck.isPresent()){
            throw new NotFoundException("Room layout with given id doesn't exist!");
        }
        return roomLayoutMapper.roomLayoutToRoomLayoutDto(roomLayoutCheck.get());
    }

    @Override
    public Page<RoomLayoutDto> findAllRoomLayouts(Pageable pageable) {
        return roomLayoutRepository.findAll(pageable).map(roomLayoutMapper::roomLayoutToRoomLayoutDto);
    }

    @Override
    public Page<RoomDto> findAllRooms(Pageable pageable) {
        return roomRepository.findAll(pageable).map(roomLayoutMapper::roomToRoomDto);
    }

    @Override
    public RoomDto findRoomById(Long id) {
        Optional<Room> roomCheck = roomRepository.findById(id);
        if(!roomCheck.isPresent()){
            throw new NotFoundException("Room with given id doesn't exist!");
        }
        return roomLayoutMapper.roomToRoomDto(roomCheck.get());
    }

    @Override
    public RoomLayoutDto findRoomLayoutByHotelIdAndRoomTypeId(Long hotelId, Long roomTypeId) {
        Optional<RoomLayout> roomLayoutCheck = roomLayoutRepository.findRoomLayoutByHotelIdAndRoomTypeId(hotelId, roomTypeId);
        if(!roomLayoutCheck.isPresent()){
            throw new NotFoundException("Room layout with given hotel id and room type id doesn't exist!");
        }
        return roomLayoutMapper.roomLayoutToRoomLayoutDto(roomLayoutCheck.get());
    }
}
