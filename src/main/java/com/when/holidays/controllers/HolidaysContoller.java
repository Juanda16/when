package com.when.holidays.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.when.holidays.services.HolidaysService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/holidays")
class HolidaysController {

    @Autowired
    private HolidaysService holidaysService;

    // @GetMapping("/all")
    // public List<Holiday> getAllHolidays() {
    //     return holidaysService.getAllHolidays();
    // }

    // @GetMapping("/byMonth/{month}")
    // public List<Holiday> getHolidaysByMonth(@PathVariable("month") int month) {
    //     return holidaysService.getHolidaysByMonth(month);
    // }

    // @GetMapping("/byDay/{day}")
    // public List<Holiday> getHolidaysByDay(@PathVariable("day") int day) {
    //     return holidaysService.getHolidaysByDay(day);
    // }

    // @GetMapping("/byName/{name}")
    // public List<Holiday> getHolidaysByName(@PathVariable("name") String name) {
    //     return holidaysService.getHolidaysByName(name);
    // }

    // @GetMapping("/byType/{type}")
    // public List<Holiday> getHolidaysByType(@PathVariable("type") String type) {
    //     return holidaysService.getHolidaysByType(type);
    // }

    // @GetMapping("/byEasterDay/{easterDay}")
    // public List<Holiday> getHolidaysByEasterDay(@PathVariable("easterDay") int easterDay) {
    //     return holidaysService.getHolidaysByEasterDay(easterDay);
    // }

    @CrossOrigin(origins = "*")
    @GetMapping(value="checkHoliday/{year}/{month}/{day}")
    public String getMethodName(@PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {
        if (holidaysService.esFechaValida(String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day))) {
            Date fecha = new Date(year - 1900, month - 1, day);
            return holidaysService.esFestivo(fecha) ? "Es Festivo" : "No es festivo";
        } else { 
            return "Fecha No valida";
        }
    
    }


    
}