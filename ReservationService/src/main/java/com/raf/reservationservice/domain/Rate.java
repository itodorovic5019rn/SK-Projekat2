package com.raf.reservationservice.domain;

import javax.persistence.*;

@Entity
@Table(name = "ocena")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ocena_id")
    private Long id;
    @Column(name = "ocena")
    private Double rate;
    @Column(name = "komentar")
    private String comment;
    @Column(name = "klijent_id")
    private Long clientId;
    @Column(name = "hotel_id")
    private Long hotelId;

    public Rate() {
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }


}
