package com.raf.reservationservice.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "termin")
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "termin_id")
    private Long id;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "end_date")
    private String endDate;

    public Period() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
