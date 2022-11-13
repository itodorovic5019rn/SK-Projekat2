package com.raf.reservationservice.controller;

import com.raf.reservationservice.dto.HotelCreateDto;
import com.raf.reservationservice.dto.HotelDto;
import com.raf.reservationservice.repository.HotelRepository;
import com.raf.reservationservice.security.CheckSecurity;
import com.raf.reservationservice.service.HotelService;
import org.apache.coyote.Response;
import org.hibernate.annotations.Check;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/hotel")
public class HotelServiceController {

    private HotelService hotelService;

    public HotelServiceController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    @CheckSecurity(roles = {"admin", "client", "manager"})
    public ResponseEntity<Page<HotelDto>> getAllHotels(@RequestHeader("Authorization") String authorization, Pageable pageable){
        return new ResponseEntity<>(hotelService.findAllHotels(pageable), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<HotelDto> createHotel(@RequestHeader("Authorization") String authorization, @RequestBody @Valid HotelCreateDto hotelCreateDto) {
        return new ResponseEntity<>(hotelService.createHotel(hotelCreateDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id){
        return new ResponseEntity<>(hotelService.findHotelById(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity deleteHotel(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        hotelService.deleteHotelById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<HotelDto> updateHotel(@RequestBody @Valid HotelCreateDto hotelCreateDto, @RequestHeader("Authorization") String authorization, @PathVariable Long id) {
        return new ResponseEntity<>(hotelService.updateHotel(hotelCreateDto, id), HttpStatus.OK);
    }

}
