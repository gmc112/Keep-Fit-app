package com.example.goals.goal;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class DMY{
    @NonNull
    private Integer day;

    @NonNull
    private Integer month;

    @NonNull
    private Integer year;

    @NonNull
    private long timestamp;

    public DMY(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        timestamp = new GregorianCalendar(year, month, day).getTimeInMillis();
    }

    public DMY(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        day = localDate.getDayOfMonth();
        month = localDate.getMonthValue();
        year = localDate.getYear();
        timestamp = new GregorianCalendar(year, month, day).getTimeInMillis();
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString(){
        return day + "/" + month + "/" + year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DMY dmy = (DMY) o;
        return day.equals(dmy.day) &&
                month.equals(dmy.month) &&
                year.equals(dmy.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
