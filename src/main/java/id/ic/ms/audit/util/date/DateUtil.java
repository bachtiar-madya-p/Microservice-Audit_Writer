package id.ic.ms.audit.util.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter T_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter D_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter UNLOCK_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


    private DateUtil() {}

    public static String formatDateTime(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(DT_FORMATTER);
        }
        return null;
    }

    public static String formatUnlockDateTime(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(UNLOCK_TIME_FORMATTER);
        }
        return null;
    }

    public static String formatTime(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(T_FORMATTER);
        }
        return null;
    }

    public static String formatDate(LocalDateTime dt) {
        if (dt != null) {
            return dt.format(D_FORMATTER);
        }
        return null;
    }

    public static String formatDate(LocalDate dt) {
        if (dt != null) {
            return dt.format(D_FORMATTER);
        }
        return null;
    }

    public static LocalDateTime parseDateTime(String str) {
        return LocalDateTime.parse(str, DT_FORMATTER);
    }

    public static LocalDate parseDate(String str) {
        return LocalDate.parse(str, D_FORMATTER);
    }

}
