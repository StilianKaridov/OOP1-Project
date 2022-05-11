import Exceptions.InvalidDateException;
import Exceptions.InvalidTimeException;
import Exceptions.IsNotFreeException;
import Classes.Calendar;
import Classes.Date;
import Classes.Meet;

import java.time.LocalTime;


public class Main {
    public static void main(String[] args) throws InvalidDateException, IsNotFreeException, InvalidTimeException {
        Calendar calendar = new Calendar();
        Date date = new Date("01","01","22");
        Meet meet = new Meet("12:10", "13:00", "Stilian", "First meet");
        Meet meet2 = new Meet("08:00", "10:30", "Stilian", "Second meet");
        calendar.book(date, meet);
        calendar.book(date,meet2);
        Date date2 = new Date("25", "03", "22");
        Meet meet3 = new Meet("08:00", "13:00", "Ivan", "First meet");
        Meet meet4 = new Meet("13:05", "17:00", "Ivan", "Second meet");
        calendar.book(date2, meet3);
        calendar.book(date2, meet4);
        Date date3 = new Date("23","03", "22");
        calendar.findSlot(date2, LocalTime.parse("01:00"));
    }
}
