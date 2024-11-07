package store.core.util;

import camp.nextstep.edu.missionutils.DateTimes;

public class Date {
    public static final int FIRST_ELEMENT = 0;
    public static final int SECOND_ELEMENT = 1;
    public static final int THIRD_ELEMENT = 2;
    public static final String DELIMITER = "-";
    public static final String DATE_TIME_SEPARATOR = "T";

    public static boolean isDateInRange(String startDate, String endDate){
        String[] startDateComponents = dateSplitter(startDate);
        String[] todayDateComponents = dateSplitter(todayDate());
        String[] endDateComponents = dateSplitter(endDate  );

        return (yearInRange(startDateComponents[FIRST_ELEMENT], todayDateComponents[FIRST_ELEMENT],endDateComponents[FIRST_ELEMENT])
        && monthInRange(startDateComponents[SECOND_ELEMENT], todayDateComponents[SECOND_ELEMENT],endDateComponents[SECOND_ELEMENT])
        && dateInRange(startDateComponents[THIRD_ELEMENT], todayDateComponents[THIRD_ELEMENT],endDateComponents[THIRD_ELEMENT]));
    }

    public static String todayDate(){
        String todayDate = String.valueOf(DateTimes.now());

        return todayDate.split(DATE_TIME_SEPARATOR)[FIRST_ELEMENT];
    }

    private static String[] dateSplitter(String date) {
        return date.split(DELIMITER);
    }

    private static boolean yearInRange(String startYear, String todayYear, String endYear){
        int today = Integer.parseInt(todayYear);

        return today >= Integer.parseInt(startYear) && today <= Integer.parseInt(endYear);
    }
    private static boolean monthInRange(String startMonth, String todayMonth, String endMonth){
        int today = Integer.parseInt(todayMonth);

        return today >= Integer.parseInt(startMonth) && today <= Integer.parseInt(endMonth);
    }

    private static boolean dateInRange(String startDate, String todayDate, String endDate){
        int today = Integer.parseInt(todayDate);

        return today >= Integer.parseInt(startDate) && today <= Integer.parseInt(endDate);
    }
}
