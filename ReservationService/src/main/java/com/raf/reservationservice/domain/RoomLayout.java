package com.raf.reservationservice.domain;

import javax.persistence.*;

@Entity
@Table(name = "roomlayout")
public class RoomLayout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "bottom_limit")
    private Integer bottomLimit;
    @Column(name = "top_limit")
    private Integer topLimit;
    @Column(name = "room_type_id")
    private Long roomTypeId;
    @Column(name = "hotel_id")
    private Long hotelId;
    @Column(name = "price_per_day")
    private Double pricePerDay;

    public RoomLayout() {
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
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

    public Integer getBottomLimit() {
        return bottomLimit;
    }

    public void setBottomLimit(Integer bottomLimit) {
        this.bottomLimit = bottomLimit;
    }

    public Integer getTopLimit() {
        return topLimit;
    }

    public void setTopLimit(Integer topLimit) {
        this.topLimit = topLimit;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
}
