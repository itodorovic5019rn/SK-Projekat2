package com.raf.reservationservice.service.impl;

import com.raf.reservationservice.domain.Room;
import com.raf.reservationservice.domain.RoomType;
import com.raf.reservationservice.dto.RoomTypeCreateDto;
import com.raf.reservationservice.exception.BadRequestException;
import com.raf.reservationservice.exception.NotFoundException;
import com.raf.reservationservice.mapper.RoomTypeMapper;
import com.raf.reservationservice.repository.RoomTypeRepository;
import com.raf.reservationservice.service.RoomTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoomTypeServiceImpl implements RoomTypeService {

    private RoomTypeRepository roomTypeRepository;
    private RoomTypeMapper roomTypeMapper;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository, RoomTypeMapper roomTypeMapper) {
        this.roomTypeRepository = roomTypeRepository;
        this.roomTypeMapper = roomTypeMapper;
    }

    @Override
    public RoomType createRoomType(RoomTypeCreateDto roomTypeCreateDto) {
        Optional<RoomType> roomTypeCheck = roomTypeRepository.findByName(roomTypeCreateDto.getName());
        if(roomTypeCheck.isPresent()){
            throw new BadRequestException("Room type with given name already exists!");
        }
        RoomType roomType = roomTypeMapper.roomTypeCreateDtoToRoomType(roomTypeCreateDto);
        roomTypeRepository.save(roomType);
        return roomType;
    }

    @Override
    public RoomType updateRoomType(RoomTypeCreateDto roomTypeCreateDto, Long id) {
        Optional<RoomType> rtCheck = roomTypeRepository.findById(id);
        if(!rtCheck.isPresent()){
            throw new NotFoundException("Room type with given id doesn't exist!");
        }
        RoomType roomType = roomTypeMapper.update(rtCheck.get(), roomTypeCreateDto);
        roomTypeRepository.save(roomType);
        return roomType;
    }

    @Override
    public void deleteRoomType(Long id) {
        Optional<RoomType> rtCheck = roomTypeRepository.findById(id);
        if(!rtCheck.isPresent()){
            throw new NotFoundException("Room type with given id doesn't exist!");
        }
        roomTypeRepository.delete(rtCheck.get());
    }

    @Override
    public RoomType findRoomTypeById(Long id) {
        Optional<RoomType> rtCheck = roomTypeRepository.findById(id);
        if(!rtCheck.isPresent()){
            throw new NotFoundException("Room type with given id doesn't exist!");
        }
        return rtCheck.get();
    }

    @Override
    public Page<RoomType> findAllRoomTypes(Pageable pageable) {
        return roomTypeRepository.findAll(pageable);
    }
}
