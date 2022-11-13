package com.raf.reservationservice.service.impl;

import com.raf.reservationservice.domain.Period;
import com.raf.reservationservice.dto.PeriodCreateDto;
import com.raf.reservationservice.exception.BadRequestException;
import com.raf.reservationservice.exception.NotFoundException;
import com.raf.reservationservice.mapper.PeriodMapper;
import com.raf.reservationservice.repository.PeriodRepository;
import com.raf.reservationservice.service.PeriodService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class PeriodServiceImpl implements PeriodService{

    private PeriodRepository periodRepository;
    private PeriodMapper periodMapper;

    public PeriodServiceImpl(PeriodRepository periodRepository, PeriodMapper periodMapper) {
        this.periodRepository = periodRepository;
        this.periodMapper = periodMapper;
    }

    @Override
    public Page<Period> findAllPeriods(Pageable pageable) {
        return periodRepository.findAll(pageable);
    }

    @Override
    public Period addPeriod(PeriodCreateDto periodCreateDto) {
        boolean startDate;
        boolean endDate;
        try{
            startDate = periodCreateDto.getParseDate(periodCreateDto.getStartDate());
            endDate = periodCreateDto.getParseDate(periodCreateDto.getEndDate());
            if(!(startDate && endDate)){
                throw new BadRequestException("Invalid date format!");
            }
            Optional<Period> periodCheck = periodRepository.findPeriodByStartDateAndEndDate(periodCreateDto.getStartDate(), periodCreateDto.getEndDate());
            if(periodCheck.isPresent()){
                throw new BadRequestException("Period with given start date and end date already exists!");
            }
            Period period = periodMapper.periodCreateDtoToPeriod(periodCreateDto);
            periodRepository.save(period);
            return period;
        }catch (BadRequestException e){
            throw e;
        }
    }

    @Override
    public void deletePeriod(Long id) {
        Optional<Period> periodCheck = periodRepository.findById(id);
        if(!periodCheck.isPresent()){
            throw new NotFoundException("Period with given id doesn't exist!");
        }
        periodRepository.delete(periodCheck.get());
    }

    @Override
    public Period getPeriodById(Long id) {
        Optional<Period> periodCheck = periodRepository.findById(id);
        if(!periodCheck.isPresent()){
            throw new NotFoundException("Period with given id doesn't exist!");
        }
        return periodCheck.get();
    }

    @Override
    public Period updatePeriod(PeriodCreateDto periodCreateDto, Long id) {
        Optional<Period> periodCheck = periodRepository.findById(id);
        if(!periodCheck.isPresent()){
            throw new NotFoundException("Period with given id doesn't exist!");
        }
        try{
            Period period = periodMapper.updatePeriod(periodCheck.get(), periodCreateDto);
            periodRepository.save(period);
            return period;
        }catch (BadRequestException e){
            throw e;
        }
    }
}
