package Classes;

import java.util.LinkedHashMap;
import java.util.Map;

public class Calendar {
    private Map<String, Month> months;

    public Calendar() {
        this.months = setMonths();
    }

    public Map<String, Month> getMonths() {
        return months;
    }

    public Map<String, Month> setMonths() {
        Map<String, Month> month = new LinkedHashMap<>();
        month.put("January", new Month("January"));
        month.put("February", new Month("February"));
        month.put("March", new Month("March"));
        month.put("April", new Month("April"));
        month.put("May", new Month("May"));
        month.put("June", new Month("June"));
        month.put("July", new Month("July"));
        month.put("August", new Month("August"));
        month.put("September", new Month("September"));
        month.put("October", new Month("October"));
        month.put("November", new Month("November"));
        month.put("December", new Month("December"));
        return month;
    }
}
