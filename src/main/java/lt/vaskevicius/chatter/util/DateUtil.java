package lt.vaskevicius.chatter.util;

import lt.vaskevicius.chatter.domain.exception.ChatterException;
import lt.vaskevicius.chatter.domain.exception.ChatterExceptionCode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DateUtil {

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final String LOCALE_NEUTRAL_DATE_FORMAT = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$";

    public static DateTimeFormatter getDateTimeFormat() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    }
    public static void validateDate(String date) {
        if (!Pattern.matches(LOCALE_NEUTRAL_DATE_FORMAT, date)) {
            throw new ChatterException(ChatterExceptionCode.CHAT2, "An error occurred, while parsing provided date. Make sure date matches locale neutral format (YYYY-MM-DDTHH:mm:ss)");
        }
    }

    public static LocalDateTime parseLocalDate(String date) {
        validateDate(date);

        return LocalDateTime.parse(date);
    }
}
