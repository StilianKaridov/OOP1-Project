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
        if (meetings.isEmpty() || !meetings.containsKey(date)) { // If the map is empty or the date didn't exist just put
            list.add(meet);
            meetings.put(date, list);
        } else {
            for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {  // Iterate over the map
                if (entry.getKey().getDay().equals(date.getDay()) &&
                        entry.getKey().getMonth().equals(date.getMonth()) &&
                        entry.getKey().getYear().equals(date.getYear())) {   // If such a date exist, get the value, add the meet and put in the map
                    for (Meet currentMeet : entry.getValue()) {  // Iterate over the date's list of meetings
                        start = LocalTime.parse(currentMeet.getStartTime());
                        end = LocalTime.parse(currentMeet.getEndTime());
                        if (currentStart.isBefore(start) && currentEnd.isAfter(start) ||
                            currentStart.isAfter(start) && currentStart.isBefore(end)) {  // Check if the time is free
                            throw new IsNotFreeException();
                        }
                    }
                    list = entry.getValue();    // Get the existing meetings
                    list.add(meet);     // Add the meet to the existing meetings
                    meetings.put(entry.getKey(), list);     // Put them in the map
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
            if (entry.getKey().getDay().equals(date.getDay()) &&
                entry.getKey().getMonth().equals(date.getMonth()) &&
                entry.getKey().getYear().equals(date.getYear())) {      // If the date exists iterate over the list of meetings
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
        if (!timeExist) {       // Throw the exception if the given times are invalid
            throw new InvalidTimeException();
        }
    }

    public void agenda(Date date) throws InvalidDateException{
        boolean isValid = false;
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            if (entry.getKey().getDay().equals(date.getDay()) && entry.getKey().getMonth().equals(date.getMonth()) && entry.getKey().getYear().equals(date.getYear())) {
                Collections.sort(entry.getValue());
                isValid = true;
                System.out.println("Agenda: " + entry.getValue());
            }
        }
        if(!isValid){
            throw new InvalidDateException();
        }
    }

    public void change(Date date, String startTime, String option, String newValue) throws IsNotFreeException, InvalidDateException{
        boolean isFulfilled = false;
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            if (entry.getKey().getDay().equals(date.getDay()) && entry.getKey().getMonth().equals(date.getMonth()) && entry.getKey().getYear().equals(date.getYear())) {
                for (Meet currentMeet : entry.getValue()) {
                    if (currentMeet.getStartTime().equals(startTime)) {
                        isFulfilled = true;
                        switch (option) {
                            case "date":
                                String[] values = newValue.split("/");
                                String newDay = values[0];
                                String newMonth = values[1];
                                String newYear = values[2];
                                Date newDate = new Date(newDay, newMonth, newYear);
                                if (newDate.validateDay(newDay, newMonth, newYear) && newDate.validateMonth(newMonth)) {
                                    book(newDate, currentMeet);
                                    entry.getValue().remove(currentMeet);
                                    if(entry.getValue().size() == 0){
                                        meetings.remove(entry.getKey());
                                    }
                                } else {
                                    throw new InvalidDateException();
                                }
                                break;
                            case "startTime":
                                LocalTime newStart = LocalTime.parse(newValue);
                                LocalTime currentEnd = LocalTime.parse(currentMeet.getEndTime());
                                for (Meet meet : entry.getValue()) {
                                    LocalTime start = LocalTime.parse(meet.getStartTime());
                                    LocalTime end = LocalTime.parse(meet.getEndTime());
                                    if (currentMeet.getStartTime().compareTo(meet.getStartTime()) == 0) {
                                        continue;
                                    } else if (newStart.isBefore(start) && currentEnd.isAfter(start) || newStart.isAfter(start) && newStart.isBefore(end)){
                                        throw new IsNotFreeException();
                                    }
                                }
                                currentMeet.setStartTime(newValue);
                                break;
                            case "endTime":
                                LocalTime currentStart = LocalTime.parse(currentMeet.getStartTime());
                                LocalTime newEnd = LocalTime.parse(newValue);
                                for (Meet meet : entry.getValue()) {
                                    LocalTime start = LocalTime.parse(meet.getStartTime());
                                    LocalTime end = LocalTime.parse(meet.getEndTime());
                                    if (currentMeet.getStartTime().compareTo(meet.getStartTime()) == 0) {
                                        continue;
                                    } else if (currentStart.isBefore(start) && newEnd.isAfter(start) || currentStart.isAfter(start) && currentStart.isBefore(end)){
                                        throw new IsNotFreeException();
                                    }
                                }
                                currentMeet.setEndTime(newValue);
                                break;
                            case "name":
                                currentMeet.setName(newValue);
                                break;
                            case "note":
                                currentMeet.setNote(newValue);
                                break;
                        }
                    }
                    if(isFulfilled){
                        break;
                    }
                }
            }
        }
    }

    
}
