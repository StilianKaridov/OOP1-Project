package Classes;

import java.util.ArrayList;
import java.util.List;

public class Month {
    private List<Day> dayList;
    private String name;
    private int numberOfDays;

    public Month(String name) {
        this.name = name;
        setNumberOfDays();
        this.dayList = addDaysToList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays() {
        switch (this.name) {
            case "January":
            case "December":
            case "October":
            case "August":
            case "July":
            case "May":
            case "March":
                this.numberOfDays = 31;
                break;
            case "February":
                this.numberOfDays = 28;
                break;
            case "April":
            case "November":
            case "September":
            case "June":
                this.numberOfDays = 30;
                break;
        }
    }

    public List<Day> getDayList() {
        return dayList;
    }

    public List<Day> addDaysToList() {
        List<Day> list = new ArrayList<>();
        for (int i = 1; i <= this.numberOfDays; i++) {
            list.add(new Day(String.valueOf(i)));
        }
        return list;
    }


}
