import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DayOfTheProgrammer {

    private final static Integer NOT_LEAP_YEAR_TIL_SEPTEMBER_START_IN_DAYS = 243;
    private final static Integer LEAP_YEAR_TIL_SEPTEMBER_START_IN_DAYS = 244;
    private final static Integer RUSSIAN_YEAR_1918_TIL_SEPTEMBER_START_IN_DAYS = NOT_LEAP_YEAR_TIL_SEPTEMBER_START_IN_DAYS - 13;
    private final static String DAY_OF_PROGRAMMER_PATTERN = "%s.09.%s";

    // Complete the dayOfProgrammer function below.
    static String dayOfProgrammer(int year) {

        if (year == 1918)
            return String.format(DAY_OF_PROGRAMMER_PATTERN, 256 - RUSSIAN_YEAR_1918_TIL_SEPTEMBER_START_IN_DAYS, year);

        boolean leapYear;
        if (year < 1918)
            leapYear = (year%4 == 0);
        else
            leapYear = (year%400 == 0) || (year%4 == 0 && year%100 != 0);

        Integer days =  leapYear ? LEAP_YEAR_TIL_SEPTEMBER_START_IN_DAYS : NOT_LEAP_YEAR_TIL_SEPTEMBER_START_IN_DAYS;
        return String.format(DAY_OF_PROGRAMMER_PATTERN, 256 - days, year);

    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int year = Integer.parseInt(bufferedReader.readLine().trim());

        String result = dayOfProgrammer(year);

        System.out.println(result);

        bufferedReader.close();
    }
}
