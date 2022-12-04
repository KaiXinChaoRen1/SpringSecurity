package com.lwq.springsecurity.controller;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FilterExceptionController {

    @RequestMapping("/filterExceptionHandler")
    public void name1(HttpServletRequest request) throws Exception {
        if (request.getAttribute("exception") instanceof Exception) {
            throw ((Exception) request.getAttribute("exception"));
        }
    }

}
