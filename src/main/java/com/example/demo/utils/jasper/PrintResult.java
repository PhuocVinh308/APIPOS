package com.example.demo.utils.jasper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrintResult {
    private String fileName;
    private byte[] data;
    private ReportType type;
}
