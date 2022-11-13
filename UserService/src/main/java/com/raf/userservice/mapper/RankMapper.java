package com.raf.userservice.mapper;

import com.raf.userservice.domain.Rank;
import com.raf.userservice.dto.RankCreateDto;
import org.springframework.stereotype.Component;

@Component
public class RankMapper {

    public Rank rankCreateDtoToRank(RankCreateDto rankCreateDto){
        Rank rank = new Rank();
        rank.setName(rankCreateDto.getName());
        rank.setBotLimit(rankCreateDto.getBotLimit());
        rank.setTopLimit(rankCreateDto.getTopLimit());
        rank.setDiscount(rankCreateDto.getDiscount());
        return rank;
    }

    public Rank updateRank(Rank rank, RankCreateDto rankCreateDto){
        rank.setBotLimit(rankCreateDto.getBotLimit());
        rank.setTopLimit(rankCreateDto.getTopLimit());
        rank.setName(rankCreateDto.getName());
        rank.setDiscount(rankCreateDto.getDiscount());
        return rank;
    }
}
