package com.raf.userservice.service.impl;

import com.raf.userservice.domain.Rank;
import com.raf.userservice.dto.RankCreateDto;
import com.raf.userservice.mapper.exception.BadRequestException;
import com.raf.userservice.mapper.exception.NotFoundException;
import com.raf.userservice.mapper.RankMapper;
import com.raf.userservice.repository.RankRepository;
import com.raf.userservice.service.RankService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RankImpl implements RankService {

    private RankRepository rankRepository;
    private RankMapper rankMapper;

    public RankImpl(RankRepository rankRepository, RankMapper rankMapper) {
        this.rankRepository = rankRepository;
        this.rankMapper = rankMapper;
    }

    @Override
    public Rank update(RankCreateDto rankCreateDto, Long id) {
        Rank rank = rankRepository.findRankById(id).orElseThrow(() -> new NotFoundException("Rank with given id not found!"));
        rank = rankMapper.updateRank(rank, rankCreateDto);
        rankRepository.save(rank);
        return rank;
    }

    @Override
    public Rank create(RankCreateDto rankCreateDto) {
        Optional<Rank> nameRank = rankRepository.findRankByName(rankCreateDto.getName());
        if(nameRank.isPresent()){
            throw new BadRequestException("Rank with given name already exists!");
        }
        List<Rank> ranks = rankRepository.findAll();
        for(Rank r: ranks) {
            if (rankCreateDto.getBotLimit() >= r.getBotLimit() && rankCreateDto.getBotLimit() <= r.getTopLimit()) {
                throw new BadRequestException("Rank with given bottom limit is already in range!");
            }
            if (rankCreateDto.getTopLimit() >= r.getBotLimit() && rankCreateDto.getTopLimit() <= r.getTopLimit()) {
                throw new BadRequestException("Rank with given top limit is already in range!");
            }
        }
        if(rankCreateDto.getBotLimit() > rankCreateDto.getTopLimit()){
            throw new BadRequestException("Bottom limit can't be higher than top limit!");
        }
        Rank rank = rankMapper.rankCreateDtoToRank(rankCreateDto);
        rankRepository.save(rank);
        return rank;
    }

    @Override
    public void deleteById(Long id) {
        Optional<Rank> rankCheck = rankRepository.findRankById(id);
        if(!rankCheck.isPresent()){
            throw new NotFoundException("Rank with given id doesn't exists!");
        }
        rankRepository.delete(rankCheck.get());
    }

    @Override
    public Page<Rank> findAll(Pageable pageable) {
        return rankRepository.findAll(pageable);
    }

    @Override
    public Rank getRankById(Long id) {
        Optional<Rank> rankCheck = rankRepository.findRankById(id);
        if(!rankCheck.isPresent()){
            throw new NotFoundException("Rank with given id doesn't exists!");
        }
        return rankCheck.get();
    }

    public List<Rank> getAllRanks(){
        return rankRepository.findAll();
    }
}
