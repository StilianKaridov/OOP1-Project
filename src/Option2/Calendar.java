package Option2;

import Exceptions.InvalidDateException;
import Exceptions.InvalidTimeException;
import Exceptions.IsNotFreeException;

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

    public void book(Date date, Meet meet) throws IsNotFreeException, InvalidDateException {
        LocalTime start;
        LocalTime end;
        LocalTime currentStart = LocalTime.parse(meet.getStartTime());
        LocalTime currentEnd = LocalTime.parse(meet.getEndTime());
        List<Meet> list = new ArrayList<>();
        if (meetings.isEmpty()) {
            list.add(meet);
            meetings.put(date, list);
        } else {
            for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
                if (entry.getKey().getDay().equals(date.getDay()) &&
                        entry.getKey().getMonth().equals(date.getMonth()) &&
                        entry.getKey().getYear().equals(date.getYear())) {
                    for (Meet currentMeet : entry.getValue()) {
                        start = LocalTime.parse(currentMeet.getStartTime());
                        end = LocalTime.parse(currentMeet.getEndTime());
                        if (currentStart.isBefore(start) && currentEnd.isAfter(start) || currentStart.isAfter(start) && currentStart.isBefore(end)) {
                            throw new IsNotFreeException();
                        }
                    }
                    list = entry.getValue();
                    list.add(meet);
                    meetings.put(entry.getKey(), list);
                } else {
                    throw new InvalidDateException();
                }
            }
        }
        System.out.println("You successfully added the meeting!");
    }

    public void unbook(Date date, String startTime, String endTime) throws IsNotFreeException, InvalidDateException, InvalidTimeException {
        boolean timeExist = false;
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            if (entry.getKey().getDay().equals(date.getDay()) && entry.getKey().getMonth().equals(date.getMonth()) && entry.getKey().getYear().equals(date.getYear())) {
                for (Meet currentMeet : entry.getValue()) {
                    if (currentMeet.getStartTime().equals(startTime) && currentMeet.getEndTime().equals(endTime)) {
                        meetings.get(entry.getKey()).remove(currentMeet);
                        System.out.println("You successfully unbooked the meeting!");
                        timeExist = true;
                        break;
                    }
                }
            } else {
                throw new InvalidDateException();
            }
            break;
        }
        if (!timeExist) {
            throw new InvalidTimeException();
        }
    }

    public void agenda(Date date) {
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            if (entry.getKey().getDay().equals(date.getDay()) && entry.getKey().getMonth().equals(date.getMonth()) && entry.getKey().getYear().equals(date.getYear())) {
                Collections.sort(entry.getValue());
                System.out.println("Agenda: " + entry.getValue());
            }
        }
    }

}
