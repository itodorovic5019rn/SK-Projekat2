package com.raf.reservationservice.controller;

import com.raf.reservationservice.domain.Period;
import com.raf.reservationservice.dto.PeriodCreateDto;
import com.raf.reservationservice.security.CheckSecurity;
import com.raf.reservationservice.service.PeriodService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/period")
public class PeriodServiceController {

    private PeriodService periodService;

    public PeriodServiceController(PeriodService periodService) {
        this.periodService = periodService;
    }

    @PutMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<Period> updatePeriod(@RequestHeader("Authorization") String authorization, @RequestBody @Valid PeriodCreateDto periodCreateDto, @PathVariable Long id) {
        return new ResponseEntity<>(periodService.updatePeriod(periodCreateDto, id), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<Period> createPeriod(@RequestHeader("Authorization") String authorization, @RequestBody @Valid PeriodCreateDto periodCreateDto){
        return new ResponseEntity<>(periodService.addPeriod(periodCreateDto), HttpStatus.OK);
    }

    @GetMapping
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity<Page<Period>> getAllPeriods(@RequestHeader("Authorization") String authorization, Pageable pageable){
        return new ResponseEntity<>(periodService.findAllPeriods(pageable), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin", "manager"})
    public ResponseEntity deletePeriod(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        periodService.deletePeriod(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Period> getPeriod(@PathVariable Long id){
        return new ResponseEntity<>(periodService.getPeriodById(id), HttpStatus.OK);
    }
}
