package com.raf.reservationservice.mapper;

import com.raf.reservationservice.domain.Period;
import com.raf.reservationservice.dto.PeriodCreateDto;
import com.raf.reservationservice.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class PeriodMapper {

    public Period periodCreateDtoToPeriod(PeriodCreateDto periodCreateDto){
        Period period = new Period();
        boolean checkStart = periodCreateDto.getParseDate(periodCreateDto.getStartDate());
        boolean checkEnd = periodCreateDto.getParseDate(periodCreateDto.getEndDate());
        if(!(checkStart && checkEnd)){
            throw new BadRequestException("Invalid date format!");
        }
        period.setStartDate(periodCreateDto.getStartDate());
        period.setEndDate(periodCreateDto.getEndDate());
        return period;
    }

    public Period updatePeriod(Period period, PeriodCreateDto periodCreateDto){
        boolean checkStart = periodCreateDto.getParseDate(periodCreateDto.getStartDate());
        boolean checkEnd = periodCreateDto.getParseDate(periodCreateDto.getEndDate());
        if(!(checkStart && checkEnd)){
            throw new BadRequestException("Invalid date format!");
        }
        period.setStartDate(periodCreateDto.getStartDate());
        period.setEndDate(periodCreateDto.getEndDate());
        return period;
    }
}
