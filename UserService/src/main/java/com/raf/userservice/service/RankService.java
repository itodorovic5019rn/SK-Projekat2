package com.raf.userservice.service;

import com.raf.userservice.domain.Rank;
import com.raf.userservice.dto.RankCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RankService {

    Rank update(RankCreateDto rankCreateDto, Long id);

    Rank create(RankCreateDto rankCreateDto);

    void deleteById(Long id);

    Page<Rank> findAll(Pageable pageable);

    Rank getRankById(Long id);

}
