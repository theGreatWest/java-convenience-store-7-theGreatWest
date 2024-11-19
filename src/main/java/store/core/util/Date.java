package store.core.util;

import camp.nextstep.edu.missionutils.DateTimes;

public class Date {
    private static final String DELIMITER_T = "T";
    private static final String DELIMITER_HYPHEN = "-";
    private static final int YEAR = 0;
    private static final int MONTH = 1;
    private static final int Date = 2;

    public static boolean checkActive(String start, String end) {
        String[] startDate = start.split(DELIMITER_HYPHEN);
        String[] today = DateTimes.now().toString().split(DELIMITER_T)[0].split(DELIMITER_HYPHEN);
        String[] endDate = end.split(DELIMITER_HYPHEN);

        if (isBetweenYear(Integer.parseInt(startDate[YEAR]), Integer.parseInt(today[YEAR]), Integer.parseInt(endDate[YEAR]))) {
            if (isBetweenMonth(Integer.parseInt(startDate[MONTH]), Integer.parseInt(today[MONTH]), Integer.parseInt(endDate[MONTH]), Integer.parseInt(startDate[Date]), Integer.parseInt(today[Date]), Integer.parseInt(endDate[Date]))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isBetweenYear(int start, int today, int end) {
        return today >= start && today <= end;
    }

    private static boolean isBetweenMonth(int startMonth, int todayMonth, int endMonth, int startDate, int todayDate, int endDate) {
        if (todayMonth == startMonth && todayMonth == endMonth) return isBetweenDate(startDate, todayDate, endDate);
        if (todayMonth == startMonth && todayMonth < endMonth) return isBetweenDate(startDate, todayDate, todayDate);
        if (todayMonth > startMonth && todayMonth == endMonth) return isBetweenDate(todayDate, todayDate, endDate);

        return todayMonth >= startMonth && todayMonth <= endMonth;
    }

    private static boolean isBetweenDate(int start, int today, int end) {
        return today >= start && today <= end;
    }
}
