package com.example.janche.security.utils;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class ResponseUtils {

    public  static  void addResponseHeader(HttpServletResponse response , String origins, String originHeader) {
        String[] IPs = origins.split(",");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader( "Access-Control-Allow-Headers", "Content-Type");
        if (Arrays.asList(IPs).contains(originHeader)) {
            response.setHeader("Access-Control-Allow-Origin", originHeader);
        }
    }
}
