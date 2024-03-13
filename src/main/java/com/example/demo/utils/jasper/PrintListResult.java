package com.example.demo.utils.jasper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Leemintran on 03/11/21.
 * VNPT-IT KV5
 * toitv.tgg@vnpt.vn
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrintListResult {
    private String fileName;
    private List<byte[]> data;
    private ReportType type;
}
