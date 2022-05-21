package Classes;

import Exceptions.InvalidDateException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement(name = "date")
@XmlAccessorType(XmlAccessType.FIELD)
public class Date implements Serializable {
    @XmlElement(name = "day")
    private String day;
    @XmlElement(name = "month")
    private String month;
    @XmlElement(name = "holiday")
    private boolean isHoliday;

    public Date() {
    }

    public Date(String day, String month) throws InvalidDateException {
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

    public boolean validateDay(String day, String month) throws InvalidDateException{
        int numberOfDays;
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
