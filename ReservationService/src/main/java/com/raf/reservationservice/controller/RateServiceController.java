package com.raf.reservationservice.controller;

import com.raf.reservationservice.domain.RoomType;
import com.raf.reservationservice.dto.RateCreateDto;
import com.raf.reservationservice.dto.RateDto;
import com.raf.reservationservice.dto.RoomTypeCreateDto;
import com.raf.reservationservice.security.CheckSecurity;
import com.raf.reservationservice.service.RateService;
import com.raf.reservationservice.service.RoomTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rate")
public class RateServiceController {
    private RateService rateService;

    public RateServiceController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping
    @CheckSecurity(roles = {"admin", "manager", "client"})
    public ResponseEntity<RateDto> createRate(@RequestHeader("Authorization") String authorization, @RequestBody @Valid RateCreateDto rateCreateDto) {
        return new ResponseEntity<>(rateService.createRate(rateCreateDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin", "manager", "client"})
    public ResponseEntity<RateDto> updateRate(@RequestHeader("Authorization") String authorization, @RequestBody @Valid RateCreateDto rateCreateDto, @PathVariable Long id) {
        return new ResponseEntity<>(rateService.updateRate(rateCreateDto, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin", "manager", "client"})
    public ResponseEntity deleteRate(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        rateService.deleteRate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RateDto> getRateById(@PathVariable Long id){
        return new ResponseEntity<>(rateService.findRateById(id), HttpStatus.OK);
    }

    @GetMapping
    @CheckSecurity(roles = {"admin", "client", "manager"})
    public ResponseEntity<Page<RateDto>> getAllRates(@RequestHeader("Authorization") String authorization, Pageable pageable) {
        return new ResponseEntity<>(rateService.findAllRates(pageable), HttpStatus.OK);
    }
}
