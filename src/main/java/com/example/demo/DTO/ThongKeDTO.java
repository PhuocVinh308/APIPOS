package com.example.demo.DTO;

import java.time.LocalDate;

public class ThongKeDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
