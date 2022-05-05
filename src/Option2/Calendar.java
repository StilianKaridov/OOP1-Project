package Option2;

import Exceptions.InvalidDateException;

import java.time.LocalTime;
import java.util.*;

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

    public void book(Date date, Meet meet) throws InvalidDateException{
        LocalTime start;
        LocalTime end;
        LocalTime currentStart = LocalTime.parse(meet.getStartTime());
        LocalTime currentEnd = LocalTime.parse(meet.getEndTime());
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            if (entry.getKey().getDay().equals(date.getDay()) &&
                entry.getKey().getMonth().equals(date.getMonth()) &&
                entry.getKey().getYear().equals(date.getYear())) {
                for (Meet currentMeet : entry.getValue()) {
                    start = LocalTime.parse(currentMeet.getStartTime());
                    end = LocalTime.parse(currentMeet.getEndTime());
                    if (currentStart.isBefore(start) && currentEnd.isAfter(start) || currentStart.isAfter(start) && currentStart.isBefore(end)) {
                        throw new InvalidDateException();
                    }
                }
            }
        }

        List<Meet> list = new ArrayList<>();
        if (this.meetings.containsKey(date)) {
            list = this.meetings.get(date);
        }
        list.add(meet);
        this.meetings.put(date, list);
    }

    public void unbook(Date date, String startTime, String endTime) {
        //throw InvalidDateException if the date does not exist, throw InvalidTime if the time isn't right

    }

}
