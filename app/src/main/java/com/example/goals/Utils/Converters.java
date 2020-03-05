package com.example.goals.Utils;

import androidx.room.TypeConverter;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date timestampToDate(Long timestamp){
        if (timestamp == null){
            return null;
        }
        return new Date(timestamp);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date){
        if (date == null){
            return null;
        }
        return date.getTime();
    }

}
