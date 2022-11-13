package com.raf.userservice.controller;

import com.raf.userservice.domain.Rank;
import com.raf.userservice.dto.DiscountDto;
import com.raf.userservice.dto.RankCreateDto;
import com.raf.userservice.security.CheckSecurity;
import com.raf.userservice.service.RankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@RestController
@RequestMapping("/rank")
public class RankController {

    private RankService rankService;

    public RankController(RankService rankService) {
        this.rankService = rankService;
    }

    @PutMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity<Rank> updateRank(@RequestHeader("Authorization") String authorization, @RequestBody @Valid RankCreateDto rankCreateDto, @PathVariable Long id) {
        return new ResponseEntity<>(rankService.update(rankCreateDto, id), HttpStatus.OK);
    }

    @PostMapping
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity<Rank> createRank(@RequestHeader("Authorization") String authorization, @RequestBody @Valid RankCreateDto rankCreateDto){
        return new ResponseEntity<>(rankService.create(rankCreateDto), HttpStatus.OK);
    }

    @GetMapping
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity<Page<Rank>> getAllRanks(@RequestHeader("Authorization") String authorization, Pageable pageable){
        return new ResponseEntity<>(rankService.findAll(pageable), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @CheckSecurity(roles = {"admin"})
    public ResponseEntity deleteRank(@RequestHeader("Authorization") String authorization, @PathVariable Long id){
        rankService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Rank> getRank(@PathVariable Long id){
        return new ResponseEntity<>(rankService.getRankById(id), HttpStatus.OK);
    }

}
