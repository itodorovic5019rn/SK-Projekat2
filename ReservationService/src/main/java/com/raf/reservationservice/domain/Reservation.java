package com.raf.reservationservice.domain;

import javax.persistence.*;

@Entity
@Table(name = "rezervacija")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rezervacija_id")
    private Long id;
    @Column(name = "soba_id")
    private Long roomId;
    @Column(name = "termin_id")
    private Long periodId;
    @Column(name = "korisnik_id")
    private Long userId;
    private Double price;

    public Reservation() {
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
