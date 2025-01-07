package com.tsoftware.qtd.commonlib.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;

public class DateTimeUtil {

  public static final String DATE_FORMAT = "yyyy-MM-dd";
  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";

  public static final String DDMMYYYYHHMM = "yyMMddHHmm";
  public static final long ONE_MINUTE = 60 * 1000;

  static final DateTimeFormatter flexibleFormatter =
      new DateTimeFormatterBuilder()
          .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
          .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
          .appendOptional(DateTimeFormatter.ofPattern(DATE_FORMAT))
          .appendOptional(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
          .appendOptional(DateTimeFormatter.ofPattern(DD_MM_YYYY_HH_MM_SS))
          .appendOptional(DateTimeFormatter.ofPattern(DDMMYYYYHHMM))
          .toFormatter();

  public static boolean isValid(String dateStr, String dateFormatter) {
    try {
      LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateFormatter));
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }

  public static LocalDate parseDate(String dateStr, String dateTimeFormat) {
    if (StringUtils.isBlank(dateStr)) {
      return null;
    }
    return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(dateTimeFormat));
  }

  public static LocalDate parseDate(String dateStr) {
    if (StringUtils.isBlank(dateStr)) {
      return null;
    }
    return LocalDate.parse(dateStr, flexibleFormatter);
  }

  public static String formatDate(LocalDate date) {
    if (date == null) {
      return null;
    }
    return flexibleFormatter.format(date);
  }

  public static String formatDate(LocalDate date, String dateFormat) {
    return formatDate(date);
  }

  public static LocalDateTime parseDateTime(String dateTimeStr) {
    if (StringUtils.isBlank(dateTimeStr)) {
      return null;
    }
    return LocalDateTime.parse(dateTimeStr, flexibleFormatter);
  }

  public static LocalDateTime parseDateTimeDDMMYYYYHHMM(String dateTimeStr) {
    return parseDateTime(dateTimeStr);
  }

  public static String formatDateTime(LocalDateTime dateTime) {
    if (dateTime == null) {
      return null;
    }
    return flexibleFormatter.format(dateTime);
  }

  public static String formatDateTime(Date date) {
    if (date == null) {
      return null;
    }
    Format formatter = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS);
    return formatter.format(date);
  }

  public static LocalDateTime getEndOfLocalDateTimeNow() {
    LocalTime localTime = LocalTime.of(23, 59, 59);
    LocalDate localDate = LocalDate.now();
    return LocalDateTime.of(localDate, localTime);
  }

  public static LocalDateTime getBeginOfLocalDateTimeNow() {
    LocalTime localTime = LocalTime.of(0, 0, 0);
    LocalDate localDate = LocalDate.now();
    return LocalDateTime.of(localDate, localTime);
  }

  public static LocalDateTime getDateBefore(int numberDateBefore) {
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DAY_OF_MONTH, -numberDateBefore);
    TimeZone timeZone = calendar.getTimeZone();
    ZoneId zoneId = timeZone == null ? ZoneId.systemDefault() : timeZone.toZoneId();
    return LocalDateTime.ofInstant(calendar.toInstant(), zoneId);
  }

  public static LocalDate getLocalDateBefore(int dateBefore) {
    LocalDate localDate = LocalDate.now();
    return localDate.minusDays(dateBefore);
  }

  public static LocalDate getLocalDateByDayOfMonth(int dayOfMonth) {
    LocalDate localDate = LocalDate.now();
    return LocalDate.of(localDate.getYear(), localDate.getMonth(), dayOfMonth);
  }

  public static String getLocalDateStringAfter(LocalDate localDate, int dateAfter) {
    LocalDate localDateAfter = localDate.plusDays(dateAfter);
    return formatDate(localDateAfter);
  }
}
