import Exceptions.InvalidDateException;
import Option2.Calendar;
import Option2.Date;
import Option2.Meet;


public class Main {
    public static void main(String[] args) throws InvalidDateException {
        Calendar calendar = new Calendar();
        try {
            Date date = new Date("31","12","22");
            Meet meet = new Meet("12:24", "13:00", "Stilian", "To buy coffee");
            calendar.book(date, meet);
            Date date2 = new Date("31", "12", "22");
            Meet meet2 = new Meet("12:20", "13:23", "Ivan", "Exception");
            calendar.book(date2, meet2);
        } catch (InvalidDateException e){
            System.out.println(e.getMessage());
        }
        System.out.println(calendar.toString());
    }
}
