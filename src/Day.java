public class Day {
    private boolean isHoliday;
    private String dayNumber;

    public Day(String dayNumber) {
        this.dayNumber = dayNumber;
        this.isHoliday = false;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }
}
