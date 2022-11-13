package com.raf.reservationservice.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class ReservationCreateDto {

    @NotNull
    private Long roomId;
    @NotNull
    private Long periodId;
    @NotNull
    private Long userId;


    public ReservationCreateDto() {
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
