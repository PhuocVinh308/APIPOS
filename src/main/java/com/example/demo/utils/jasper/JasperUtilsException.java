package com.example.demo.utils.jasper;

/**
 * Created by Leemintran on 03/11/21.
 * VNPT-IT KV5
 * toitv.tgg@vnpt.vn
 */
public class JasperUtilsException extends RuntimeException{
    JasperUtilsException(String message) {
        super(message);
    }

    JasperUtilsException(Exception ex) {
        super(ex);
    }
}
