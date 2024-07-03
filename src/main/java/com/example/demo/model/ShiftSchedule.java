package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Entity
@Data
@Table(name = "shift_schedule")
public class ShiftSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Temporal(TemporalType.TIME)
    private Time startTime;

    @Temporal(TemporalType.TIME)
    private Time endTime;

    private String repeatType;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private int capacity;

}
