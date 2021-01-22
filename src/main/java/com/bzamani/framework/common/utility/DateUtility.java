package com.bzamani.framework.common.utility;

import net.time4j.calendar.PersianCalendar;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.format.expert.PatternType;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class DateUtility {

    public static long getDiffSeconds(Date from, Date to) {
        return (to.getTime() - from.getTime()) / 1000;
    }

    public static ChronoFormatter<PersianCalendar> persianChronoFormatter = ChronoFormatter.ofPattern("yyyy/MM/dd", PatternType.CLDR, new Locale("en"), PersianCalendar.axis());

    public static String todayShamsi(int addDayAmount) {
        PersianCalendar jalali = PersianCalendar.nowInSystemTime();
        return persianChronoFormatter.format(jalali.plus(addDayAmount, PersianCalendar.Unit.DAYS));
    }

    public static String todayShamsi() {
        currentTime();
        PersianCalendar jalali = PersianCalendar.nowInSystemTime();
        return persianChronoFormatter.format(jalali);
    }

    public static String currentTime() {
        LocalTime now = LocalTime.now(ZoneId.of("GMT+03:30"));
        return now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
    }


}
