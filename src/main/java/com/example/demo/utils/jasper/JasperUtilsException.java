package com.example.demo.utils.jasper;
public class JasperUtilsException extends RuntimeException{
    JasperUtilsException(String message) {
        super(message);
    }

    JasperUtilsException(Exception ex) {
        super(ex);
    }
}
