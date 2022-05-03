package Option2;

import Exceptions.InvalidDateException;

public class Date {
    private String day;
    private String month;
    private String year;
    private boolean isHoliday;

    public Date(String day, String month, String year) throws InvalidDateException {

        if(validateDay(day, month, year) && validateMonth(month)){
            this.day = day;
            this.month = month;
            this.year = year;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    public boolean validateDay(String day, String month, String year) {
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
                if(Integer.parseInt(year) % 4 == 0){
                    numberOfDays = 29;
                }
                break;
            case "4":
            case "11":
            case "9":
            case "6":
                numberOfDays = 30;
                break;
        }

        int check = Integer.parseInt(day);
        if (check == 0 || check > numberOfDays) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validateMonth(String month){
        int monthNumber = Integer.parseInt(month);
        if(monthNumber < 1 || monthNumber > 12){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year + "-isHoliday=" + isHoliday + " ";
    }
}
