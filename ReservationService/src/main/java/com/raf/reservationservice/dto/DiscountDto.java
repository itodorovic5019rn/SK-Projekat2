package com.raf.reservationservice.dto;

public class DiscountDto {
    private String name;
    private Double discount;

    public DiscountDto(String name, Double discount) {
        this.name = name;
        this.discount = discount;
    }

    public DiscountDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
