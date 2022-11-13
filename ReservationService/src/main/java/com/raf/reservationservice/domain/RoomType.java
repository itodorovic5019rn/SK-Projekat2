package com.raf.reservationservice.domain;

import javax.persistence.*;

@Entity
@Table(name = "tip_soba")
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tip_id")
    private Long id;
    @Column(name = "naziv")
    private String name;

    public RoomType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
