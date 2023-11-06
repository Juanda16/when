package com.when.holidays.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.when.holidays.services.HolidaysService;

// import com.when.holidays.services.HolidaysService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/holidays")
public class HolidaysController {

    @Autowired
    private HolidaysService holidaysService;

    @GetMapping(value = "/test")
    public String getMethodName() {
        return "test OK";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "checkHoliday/{year}/{month}/{day}")
    public String getMethodName(@PathVariable("year") int year,
            @PathVariable("month") int month,
            @PathVariable("day") int day) {
        if (holidaysService
                .esFechaValida(String.valueOf(year) + "-" + String.valueOf(month) + "-" +
                        String.valueOf(day))) {
            Date fecha = new Date(year - 1900, month - 1, day);
            System.out.println("Fecha: " + fecha);
            return holidaysService.esFestivo(fecha) ? "Es Festivo" : "No es festivo";
        } else {
            return "Fecha No valida";
        }

    }

}