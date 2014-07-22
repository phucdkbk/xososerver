/*
 * DateTimeUtils.java
 *
 * Created on August 6, 2007, 3:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.alandk.lottery.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author alandk
 */
public class DateTimeUtils {

    final private static SimpleDateFormat RFC3339_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    final public static Long A_DAY = 86400000L;

    /**
     * Creates a new instance of DateTimeUtils
     */
    public DateTimeUtils() {
    }

    public static Date getNextNDayOfDate(Date input, Long nDay) {
        Calendar objCal = Calendar.getInstance();
        objCal.setTime(input);
//        objCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        objCal.add(Calendar.DAY_OF_MONTH, nDay.intValue());
        return objCal.getTime();
    }

    public static Date convertStringToTime(String date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(date);

        } catch (ParseException e) {
//            System.out.println("Date ParseException, string value:" + date);
        }
        return null;
    }

    public static Long getYear() {
        Long tmpYear = new Long(DateTimeUtils.getDate().getYear() + 1900);
        return tmpYear;

    }

    public static Date convertStringToDate(String date) throws Exception {
        String pattern = "dd/MM/yyyy";
        return convertStringToTime(date, pattern);
    }

//    public static Date convertStringToDateTime(String date) throws Exception
//    {
//        String pattern = "dd/MM/yyyy hh24:mi:ss";
//        return convertStringToTime(date, pattern);
//    }
    public static String convertDateToString(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (date != null) {
                return dateFormat.format(date);
            } else {
                return "";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static String convertDateToString(Date date, String format) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            if (date != null) {
                return dateFormat.format(date);
            } else {
                return "";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public static String convertDateTimePickerToString(Date date) throws Exception {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null) {
            return "";
        }
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    /*
     *  @author: dungnt
     *  @todo: get sysdate
     *  @return: String sysdate
     */
    public static String getSysdate() throws Exception {
        Calendar calendar = Calendar.getInstance();
        return convertDateToString(calendar.getTime());
    }

    public static String getSysdatePicker() throws Exception {
        Calendar calendar = Calendar.getInstance();
        return convertDateTimePickerToString(calendar.getTime());
    }

    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getSysDateTime() throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            throw e;
        }
    }
    /*
     *  @author: linhdx
     *  @todo: get date default hour = 0
     *  @return: String sysdate
     */

    public static Date getDateNoHour() throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String dateStr = dateFormat.format(calendar.getTime());
            Date date = convertStringToDate(dateStr);
            return date;
        } catch (Exception e) {
            throw e;
        }
    }

    /*
     *  @author: dungnt
     *  @todo: convert from String to DateTime detail
     *  @param: String date
     *  @return: Date
     */
    public static Date convertStringToDateTime(String date) throws Exception {
        String pattern = "dd/MM/yyyy HH:mm:ss";
        return convertStringToTime(date, pattern);
    }

    public static Date convertDateTimeStringToDateTime(String date) throws Exception {
        String pattern = "dd/MM/yyyy HH:mm";
        return convertStringToTime(date, pattern);
    }

    public static Date convertStringToDateTimePicker(String date) throws Exception {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return convertStringToTime(date, pattern);
    }

    public static String convertDateTimeToString(Date date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Convert from date time to string
     *
     * @author CongLT
     * @param date
     * @param pattern
     * @return
     */
    public static String convertTimeToString(Date date, String pattern) {
        String result = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        if (date != null) {
            result = dateFormat.format(date);
        }
        return result;
    }

    /**
     * get first day of next week
     *
     * @author Namld
     * @param input
     * @return Date
     */
    public static Date getNextFirstDayOfWeek(Date input) {
        Calendar objCal = Calendar.getInstance();
        objCal.setTime(input);
        objCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        objCal.set(Calendar.HOUR_OF_DAY, 0);
        objCal.set(Calendar.MINUTE, 0);
        objCal.set(Calendar.SECOND, 0);
        objCal.add(Calendar.DAY_OF_YEAR, 7);
        return objCal.getTime();
    }

    /**
     * get last day of next week
     *
     * @author Namld
     * @param input
     * @return Date
     */
    public static Date getNextLastDayOfWeek(Date input) {
        Calendar objCal = Calendar.getInstance();
        objCal.setTime(input);
        objCal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        objCal.set(Calendar.HOUR_OF_DAY, 23);
        objCal.set(Calendar.MINUTE, 59);
        objCal.set(Calendar.SECOND, 59);
        objCal.add(Calendar.DAY_OF_YEAR, 7);
        return objCal.getTime();
    }

    /**
     * get this day of next week
     *
     * @author Namld
     * @param input
     * @return Date
     */
    public static Date getNextDayOfWeek(Date input) {
        Calendar objCal = Calendar.getInstance();
        objCal.setTime(input);
        objCal.add(Calendar.DAY_OF_YEAR, 7);
        return objCal.getTime();
    }

    public static Date convertDateRFC3339FromString(String strDate) {
        Date result = new Date();
        RFC3339_FORMAT.setLenient(false);
        try {
            return RFC3339_FORMAT.parse(strDate);

        } catch (ParseException e) {
//            System.out.println("Date ParseException, string value:" + strDate);
        }
        return result;
    }

    public static Date convertStringDDMMYYYYHHMMToDateTime(String date) throws Exception {
        return DateTimeUtils.convertStringToDateTime(date + ":00");
    }

    /**
     * Lay ngay dau tien cua thang hien tai
     *
     * @return
     */
    public static Date getFirstDateOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.set(Calendar.DATE, 1); //set the date to start of
        return cal.getTime();
    }

    public static Date getFirstDayOfYear() {
        Date startDate = new Date();
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(startDate);
        currentTime.set(Calendar.MONTH, Calendar.JANUARY);
        currentTime.set(Calendar.DATE, 1);
        startDate = currentTime.getTime();
        return startDate;
    }

    /**
     * Lay ngay cuoi cung cua thang hien tai
     *
     * @return
     */
    public static Date getLastDateOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); //set the date to start of
        return cal.getTime();
    }

    public static int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Gan thoi gian (gio, phut) vao date
     * @param date
     * @param time
     * @return 
     * @throws java.lang.Exception
     */
    public static String joinDateTime(Date date, String time) throws Exception {
        if (date == null || time == null) {
            return null;
        }
        String rslt = DateTimeUtils.convertDateToString(date, "yyyy/MM/dd") + " " + time;
        return rslt;
    }

    /**
     *
     * @return
     */
    public static Date getOneMonthAGo() {
        Date currentDate = getDate();
        if (currentDate.getMonth() != 0) {
            currentDate.setMonth(currentDate.getMonth() - 1);
        } else {
            currentDate.setMonth(11);
            currentDate.setYear(currentDate.getYear() - 1);
        }
        return currentDate;
    }

    public static Date getStartDateOfWeek(int week, int month, int year) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.WEEK_OF_MONTH, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    public static Date getEndDateOfWeek(int week, int month, int year) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.WEEK_OF_MONTH, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return cal.getTime();
    }

    // Khong tinh chu nhat
    public static Date getEndDateOfWeek2(int week, int month, int year) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.WEEK_OF_MONTH, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        return cal.getTime();
    }

    /**
     * Lich chuan nguoi Viet Nam => bat dau tu thu 2
     * @return 
     */
    public static Calendar getVietNameseCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal;
    }

    /**
     * dem so ngay trong 1 khoang thoi gian
     *
     * @param from
     * @param to
     * @return
     */
    public static Long countDayFromTo(Date from, Date to) {

        Calendar fromCal = Calendar.getInstance();
        fromCal.setTime(from);
        fromCal.set(Calendar.HOUR, 0);
        fromCal.set(Calendar.MINUTE, 0);
        fromCal.set(Calendar.SECOND, 1);
        Calendar toCal = Calendar.getInstance();
        toCal.setTime(to);
        toCal.set(Calendar.HOUR, 23);
        toCal.set(Calendar.MINUTE, 59);
        toCal.set(Calendar.SECOND, 59);
        Long result = (toCal.getTime().getTime() - fromCal.getTime().getTime()) / A_DAY + 1L;
        return result;
    }

    private static boolean isWeekend(Date check) {
        Calendar start = Calendar.getInstance();
        start.setTime(check);
        return start.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || start.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }
}
