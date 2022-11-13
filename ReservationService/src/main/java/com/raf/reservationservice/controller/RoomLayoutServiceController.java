package com.raf.reservationservice.controller;

import com.raf.reservationservice.dto.RoomDto;
import com.raf.reservationservice.dto.RoomLayoutCreateDto;
import com.raf.reservationservice.dto.RoomLayoutDto;
import com.raf.reservationservice.security.CheckSecurity;
import com.raf.reservationservice.service.RoomLayoutService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RoomLayoutServiceController {

    private RoomLayoutService roomLayoutService;

    public RoomLayoutServiceController(RoomLayoutService roomLayoutService) {
        this.roomLayoutService = roomLayoutService;
    }

    @PostMapping("/roomlayout")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<RoomLayoutDto> createRoomLayout(@RequestHeader("Authorization") String authorization, @RequestBody @Valid RoomLayoutCreateDto roomLayoutCreateDto) {
        return new ResponseEntity<>(roomLayoutService.createRoomLayout(roomLayoutCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/roomlayout/{id}")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<RoomLayoutDto> updateRoomLayout(@RequestHeader("Authorization") String authorization, @RequestBody @Valid RoomLayoutCreateDto roomLayoutCreateDto, @PathVariable Long id) {
        return new ResponseEntity<>(roomLayoutService.updateRoomLayout(roomLayoutCreateDto, id), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/roomlayout/{id}")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity deleteRoomLayout(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        roomLayoutService.deleteRoomLayout(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/roomlayout/{id}")
    public ResponseEntity<RoomLayoutDto> getRoomLayoutById(@PathVariable Long id){
        return new ResponseEntity<>(roomLayoutService.findRoomLayoutById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/roomlayout")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<Page<RoomLayoutDto>> getAllRoomLayouts(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        return new ResponseEntity<>(roomLayoutService.findAllRoomLayouts(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/room")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<Page<RoomDto>> getAllRooms(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        return new ResponseEntity<>(roomLayoutService.findAllRooms(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/room/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id){
        return new ResponseEntity<>(roomLayoutService.findRoomById(id), HttpStatus.OK);
    }
}
