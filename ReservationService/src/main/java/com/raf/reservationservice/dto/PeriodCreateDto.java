package com.raf.reservationservice.dto;

import com.raf.reservationservice.exception.BadRequestException;

import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PeriodCreateDto {

    @NotBlank
    private String startDate;
    @NotBlank
    private String endDate;

    public PeriodCreateDto() {
    }

    public boolean getParseDate(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            date = sdf.parse(strDate);
        }catch(ParseException p){
            return false;
        }
        return true;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
