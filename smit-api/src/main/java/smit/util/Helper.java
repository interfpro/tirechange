package smit.util;

import smit.model.AvailableTime;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Helper {

    public static boolean isTimeLaterThanUntilParameter(AvailableTime availableTime, String until) {
        LocalDate targetDateTime = LocalDate.parse(until, DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
        ZonedDateTime date = ZonedDateTime.parse(availableTime.getTime(), DateTimeFormatter.ISO_DATE_TIME);
        return !date.toLocalDate().isAfter(targetDateTime);
    }

}
