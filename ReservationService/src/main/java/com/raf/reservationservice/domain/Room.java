package com.raf.reservationservice.domain;

import javax.persistence.*;

@Entity
@Table(name = "soba")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soba_id")
    private Long id;
    @Column(name = "hotel_id")
    private Long hotelId;
    @Column(name = "tip_id")
    private Long typeId;
    @Column(name = "broj_sobe")
    private Integer roomNum;

    public Room() {
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}
