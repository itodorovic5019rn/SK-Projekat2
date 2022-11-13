package com.raf.reservationservice.controller;

import com.raf.reservationservice.domain.RoomType;
import com.raf.reservationservice.dto.RoomTypeCreateDto;
import com.raf.reservationservice.security.CheckSecurity;
import com.raf.reservationservice.service.RoomTypeService;
import org.hibernate.annotations.Check;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/roomtype")
public class RoomTypeServiceController {

    private RoomTypeService roomTypeService;

    public RoomTypeServiceController(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }


    @PostMapping
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<RoomType> createRoomType(@RequestHeader("Authorization") String authorization, @RequestBody @Valid RoomTypeCreateDto roomTypeCreateDto) {
        return new ResponseEntity<>(roomTypeService.createRoomType(roomTypeCreateDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<RoomType> updateRoomType(@RequestHeader("Authorization") String authorization, @RequestBody @Valid RoomTypeCreateDto roomTypeCreateDto, @PathVariable Long id) {
        return new ResponseEntity<>(roomTypeService.updateRoomType(roomTypeCreateDto, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity deleteUser(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        roomTypeService.deleteRoomType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomType> getRoomTypeById(@PathVariable Long id){
        return new ResponseEntity<>(roomTypeService.findRoomTypeById(id), HttpStatus.OK);
    }

    @GetMapping
    @CheckSecurity(roles = {"admin", "client", "manager"})
    public ResponseEntity<Page<RoomType>> getAllRoomTypes(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        return new ResponseEntity<>(roomTypeService.findAllRoomTypes(pageable), HttpStatus.OK);
    }
}
