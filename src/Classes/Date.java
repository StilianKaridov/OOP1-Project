package Classes;

import Exceptions.InvalidDateException;
import Exceptions.InvalidDayException;

public class Date {
    private String day;
    private String month;
    private boolean isHoliday;

    public Date(String day, String month) throws InvalidDateException, InvalidDayException {
        if (validateDay(day, month)) {
            this.day = day;
            this.month = month;
            this.isHoliday = false;
        } else {
            throw new InvalidDateException();
        }
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    public boolean validateDay(String day, String month) throws InvalidDayException{
        int numberOfDays = 0;
        switch (month) {
            case "1":
            case "12":
            case "10":
            case "8":
            case "7":
            case "5":
            case "3":
                numberOfDays = 31;
                break;
            case "2":
                numberOfDays = 28;
                break;
            case "4":
            case "11":
            case "9":
            case "6":
                numberOfDays = 30;
                break;
            default:
                throw new InvalidDateException();
        }

        int check = Integer.parseInt(day);
        if (check == 0 || check > numberOfDays) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return day + "/" + month + "-isHoliday=" + isHoliday + " ";
    }
}
