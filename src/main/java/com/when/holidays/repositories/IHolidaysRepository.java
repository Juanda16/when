package com.when.holidays.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.when.holidays.entities.Holiday;

@Repository
public interface IHolidaysRepository extends JpaRepository<Holiday, Integer> {
    List<Holiday> findByMonth(int month);
    List<Holiday> findByDay(int day);
    List<Holiday> findByName(String name);
    List<Holiday> findByType(String type);
    List<Holiday> findByEasterDay(int easterDay);
}

