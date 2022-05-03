package Option2;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Calendar {
    private Map<Date, List<Meet>> meetings;

    public Calendar() {
        this.meetings = new LinkedHashMap<>();
    }

    public Map<Date, List<Meet>> getMeetings() {
        return meetings;
    }

    @Override
    public String toString() {
        return "Schedule for: " + meetings;
    }

    public void book(Date date, Meet meet) {
        List<Meet> list = new ArrayList<>();
        if (this.meetings.containsKey(date)) {
            list = this.meetings.get(date);
            list.add(meet);
            this.meetings.put(date, list);
        } else {
            list.add(meet);
            this.meetings.put(date, list);
        }
    }

    public void unbook(Date date, String startTime, String endTime) {
        //Implement logic here
    }

}
