package com.example.demo.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "attendance")
@Data
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shift_schedule_id")
    private ShiftSchedule shiftSchedule;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "check_in")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkIn;

    @Column(name = "check_out")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkOut;

    @Column(name = "status")
    private String status; // "Ve som", "Đi muộn", "Nghỉ phép"

    @Column(name = "explanation")
    private String explanation; // Giải trình khi quên chấm công
}
