package Classes;

import java.util.ArrayList;
import java.util.List;

public class Day {
    private String dayNumber;
    private List<Book> meets;   //For each day, there will be List of meetings
    private boolean isHoliday;  //If the method 'holiday'() is called, make this true;

    public Day(String dayNumber) {
        this.dayNumber = dayNumber;
        this.meets = new ArrayList<>();
        this.isHoliday = false;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public List<Book> getMeets() {
        return meets;
    }

    public void setMeets(List<Book> meets) {
        this.meets = meets;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }
}
