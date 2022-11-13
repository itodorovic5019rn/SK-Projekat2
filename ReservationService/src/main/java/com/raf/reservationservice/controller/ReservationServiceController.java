package com.raf.reservationservice.controller;

import com.raf.reservationservice.dto.FreeTermsDto;
import com.raf.reservationservice.dto.ReservationCreateDto;
import com.raf.reservationservice.dto.ReservationDto;
import com.raf.reservationservice.security.CheckSecurity;
import com.raf.reservationservice.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reservation")
public class ReservationServiceController {

    private ReservationService reservationService;

    public ReservationServiceController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    @CheckSecurity(roles = {"admin", "manager", "client"})
    public ResponseEntity<ReservationDto> createReservation(@RequestHeader("Authorization") String authorization, @RequestBody @Valid ReservationCreateDto reservationCreateDto){
        return new ResponseEntity<>(reservationService.createReservation(reservationCreateDto), HttpStatus.OK);
    }

    @GetMapping
    @CheckSecurity(roles = {"admin", "manager", "client"})
    public ResponseEntity<Page<ReservationDto>> getAllReservations(@RequestHeader("Authorization") String authorization, Pageable pageable){
        return new ResponseEntity<>(reservationService.findAllReservations(pageable), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity deleteReservation(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        reservationService.deleteReservationById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id){
        return new ResponseEntity<>(reservationService.getReservationById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/freeterms")
    @CheckSecurity(roles = {"admin", "manager", "client"})
    public ResponseEntity<Page<FreeTermsDto>> getAllFreeTerms(@RequestHeader("Authorization") String authorization, Pageable pageable){
        return new ResponseEntity<>(reservationService.getFreeTerms(pageable), HttpStatus.OK);
    }
}
