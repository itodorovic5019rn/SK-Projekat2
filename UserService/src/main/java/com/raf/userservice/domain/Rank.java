package com.raf.userservice.domain;

import javax.persistence.*;

@Entity
@Table(name="ranks")
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="bottom_limit")
    private Integer botLimit;
    @Column(name="top_limit")
    private Integer topLimit;
    private String name;
    private Double discount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
