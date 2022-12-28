package lt.vaskevicius.chatter;

import lt.vaskevicius.chatter.domain.exception.ChatterException;
import lt.vaskevicius.chatter.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateUtilTest {

    @Test
    void parseDateFromStringWrongDateFormat() {
        String date = "2022-04-20";

        assertThrows(
                ChatterException.class,
                () -> DateUtil.parseLocalDate(date),
                "Expected DateUtil.parseLocalDate to throw exception, but it didn't");
    }

    @Test
    void parseDateFromString_OK() {
        String parsableDate = "2022-04-20T00:04:20";
        LocalDateTime expectedDate = LocalDateTime.now()
                .withYear(2022)
                .withMonth(4)
                .withDayOfMonth(20)
                .withHour(0)
                .withMinute(4)
                .withSecond(20)
                .withNano(0);

        LocalDateTime localDateTime = DateUtil.parseLocalDate(parsableDate);

        assertEquals(localDateTime, expectedDate);
    }
}
