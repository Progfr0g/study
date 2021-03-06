package ru.sfedu.photosearch.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

/**
 *  класс для работы с датами, их форматами
 */
public class Formatter {
    private static SimpleDateFormat dayFormatter = new SimpleDateFormat("dd-MM-yyyy");
    private static DateFormat csvDayFormatter = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH);
    private static SimpleDateFormat dayFormatterFromDB  = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static DateFormat dateFormatFromDB = new SimpleDateFormat("yyyy-MM-dd");
    public static final Logger log = LogManager.getLogger(Formatter.class);

    public static Date normalFormatDay(String date){
        Date birthDay;
        try {
            birthDay = dayFormatter.parse(date);
            return birthDay;
        } catch (ParseException e) {
            log.error(e);
        }
        return null;
    }

    public static String normalFormatDay(Date date){
        String birthDay;
        birthDay = dateFormat.format(date);
        return birthDay;
    }

    public static Date birthDayFromDB(String date){
        Date birthDay;
        try {
            birthDay = dayFormatterFromDB.parse(date);
            return birthDay;
        } catch (ParseException e) {
            log.error(e);
        }
        return null;
    }

    public static String birthDayToDB(Date date){
        String new_date;
        new_date = dayFormatterFromDB.format(date);
        return new_date;
    }

    public static String dateOfRegistration(Date date){
        String dateOfRegistration;
        dateOfRegistration = dateFormat.format(date);
        return dateOfRegistration;
    }

    public static Date dateOfRegistration(String date){
        Date dateOfRegistration;
        try {
            dateOfRegistration = dayFormatter.parse(date);
            return dateOfRegistration;
        } catch (ParseException e) {
            log.error(e);
        }
        return null;
    }

    public static Date dateOfRegistrationFromDB(String date){
        Date dateOfRegistration;
        try {
            dateOfRegistration = dayFormatterFromDB.parse(date);
            return dateOfRegistration;
        } catch (ParseException e) {
            log.error(e);
        }
        return null;
    }
    public static Date csvDateFromString(String date){
        Date dateOfRegistration;
        try {
            dateOfRegistration = csvDayFormatter.parse(date);
            return dateOfRegistration;
        } catch (ParseException e) {
            log.error(e);
        }
        return null;
    }

    public static Date localAsDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

}
