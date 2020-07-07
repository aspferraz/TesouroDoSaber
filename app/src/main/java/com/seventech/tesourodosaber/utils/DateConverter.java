package com.seventech.tesourodosaber.utils;

import androidx.room.TypeConverter;
import android.text.format.DateFormat;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static String getDayMonth(Date date) {

        String day = (String) DateFormat.format("dd", date); // 20
        String monthString = (String) DateFormat.format("MMM", date); // Jun
        return day + monthString;
    }
}
