package com.raf.userservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RankCreateDto {

    @NotNull
    private Integer botLimit;
    @NotNull
    private Integer topLimit;
    @NotBlank
    private String name;
    @NotNull
    private Double discount;

    public Integer getBotLimit() {
        return botLimit;
    }

    public void setBotLimit(Integer botLimit) {
        this.botLimit = botLimit;
    }

    public Integer getTopLimit() {
        return topLimit;
    }

    public void setTopLimit(Integer topLimit) {
        this.topLimit = topLimit;
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
