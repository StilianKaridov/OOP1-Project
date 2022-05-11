package Classes;

import Exceptions.InvalidDateException;
import Exceptions.InvalidTimeException;
import Exceptions.IsNotFreeException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        if (!date.isHoliday()) {
            if (meetings.isEmpty() || !meetings.containsKey(date)) { // If the map is empty or the date didn't exist just put
                list.add(meet);
                meetings.put(date, list);
            } else {
                for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {  // Iterate over the map
                    if (meetings.containsKey(date)) {   // If such a date exist, get the value, add the meet and put in the map
                        if (entry.getKey().equals(date)) {
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
                        }
                    } else {
                        throw new InvalidDateException();
                    }
                }
            }
            System.out.println("You successfully added the meeting!");
        } else {
            System.out.println("The date is holiday");
        }
    }

    public void unbook(Date date, String startTime, String endTime) throws InvalidDateException, InvalidTimeException {
        boolean timeExist = false;
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            if (meetings.containsKey(date)) {      // If the date exists iterate over the list of meetings
                if (entry.getKey().equals(date)) {
                    for (Meet currentMeet : entry.getValue()) {
                        if (currentMeet.getStartTime().equals(startTime) && currentMeet.getEndTime().equals(endTime)) {
                            meetings.get(entry.getKey()).remove(currentMeet);
                            System.out.println("You successfully unbooked the meeting!");
                            timeExist = true;
                            break;
                        }
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

    public void agenda(Date date) throws InvalidDateException {
        boolean isValid = false;
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            if (entry.getKey().equals(date)) {
                Collections.sort(entry.getValue());
                isValid = true;
                System.out.println("Agenda: " + entry.getValue());
                break;
            }
        }
        if (!isValid) {
            throw new InvalidDateException();
        }
    }

    public void change(Date date, String startTime, String option, String newValue) throws IsNotFreeException, InvalidDateException {
        boolean isFulfilled = false;
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            if (meetings.containsKey(date)) {
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
                                    if (entry.getValue().size() == 0) {
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
                                    } else if (newStart.isBefore(start) && currentEnd.isAfter(start) || newStart.isAfter(start) && newStart.isBefore(end)) {
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
                                    } else if (currentStart.isBefore(start) && newEnd.isAfter(start) || currentStart.isAfter(start) && currentStart.isBefore(end)) {
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
                    if (isFulfilled) {
                        break;
                    }
                }
            }
        }
    }

    public void find(String toFind) {
        boolean isFound = false;
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            for (Meet currentMeet : entry.getValue()) {
                String name = currentMeet.getName();
                String note = currentMeet.getNote();
                if (name.contains(toFind) || note.contains(toFind)) {
                    System.out.println(currentMeet.toString());
                    isFound = true;
                }
            }
        }

        if (!isFound) {
            System.out.println("No such meet with that name or note!");
        }
    }

    public void holiday(Date date) {
        if (meetings.containsKey(date)) {
            for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
                if (entry.getKey().equals(date)) {
                    entry.getKey().setHoliday(true);
                }
            }
        } else {
            date = new Date(date.getDay(), date.getMonth(), date.getYear());
            date.setHoliday(true);
            meetings.put(date, new ArrayList<>());
        }
    }

    public void busyDays(Date startDate, Date endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uu");

        //Parse the given Date objects to LocalDate
        StringBuilder start = new StringBuilder(startDate.getDay());
        start.append("/").append(startDate.getMonth()).append("/").append(startDate.getYear());

        StringBuilder end = new StringBuilder(endDate.getDay());
        end.append("/").append(endDate.getMonth()).append("/").append(endDate.getYear());

        LocalDate localStart = LocalDate.parse(start, formatter);
        LocalDate localEnd = LocalDate.parse(end, formatter);

        //Add the dates if they are from startDate to endDate
        Map<Date, List<Meet>> dates = new LinkedHashMap<>();
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            String year = entry.getKey().getYear();
            String month = entry.getKey().getMonth();
            String day = entry.getKey().getDay();
            StringBuilder date = new StringBuilder(day);
            date.append("/").append(month).append("/").append(year);

            LocalDate currentDate = LocalDate.parse(date, formatter);
            if (currentDate.isAfter(localStart) && currentDate.isBefore(localEnd)) {
                dates.put(entry.getKey(), entry.getValue());
            }
        }

        //Sort and print the map by the count busy hours
        Map<Date, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {
            int busyMinutes = 0;
            Duration busyTime;
            for (Meet currentMeet : entry.getValue()) {
                LocalTime currentStartTime = LocalTime.parse(currentMeet.getStartTime());
                LocalTime currentEndTime = LocalTime.parse(currentMeet.getEndTime());

                busyTime = Duration.between(currentStartTime, currentEndTime);
                busyMinutes += (int) busyTime.toMinutes();
            }
            sortedMap.put(entry.getKey(), busyMinutes);
        }

        sortedMap.entrySet().stream()
                .sorted(Map.Entry.<Date, Integer>comparingByValue().reversed())
                .forEach(System.out::println);
    }

    public void findSlot(Date fromDate, LocalTime hours) {
        LocalTime startWorkDay = LocalTime.parse("08:00");
        LocalTime endWorkDay = LocalTime.parse("17:00");
        StringBuilder string = new StringBuilder(fromDate.getDay());
        string.append("/").append(fromDate.getMonth()).append("/").append(fromDate.getYear());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uu");
        boolean booked = false;
        LocalDate date = LocalDate.parse(string, formatter);
        for (Map.Entry<Date, List<Meet>> entry : meetings.entrySet()) {
            if (!fromDate.isHoliday()) {
                if (!meetings.containsKey(fromDate)) {
                    System.out.println("You can book a meet on " + fromDate.toString());
                    booked = true;
                    break;
                } else {
                    if(entry.getKey().getDay().equals(fromDate.getDay()) &&
                            entry.getKey().getMonth().equals(fromDate.getMonth()) &&
                            entry.getKey().getYear().equals(fromDate.getYear())){
                        entry.getValue().sort(Comparator.comparing(Meet::getStartTime));
                        for (int i = 0; i < entry.getValue().size(); i++) {
                            LocalTime currentStart = LocalTime.parse(entry.getValue().get(i).getStartTime());
                            LocalTime currentEnd = LocalTime.parse(entry.getValue().get(i).getEndTime());
                            if (i == 0) {
                                if(currentStart.minusMinutes(hours.getHour() + hours.getMinute()).isAfter(startWorkDay)){
                                    System.out.println("You can book a meet on " + fromDate.toString());
                                    booked = true;
                                    break;
                                }
                            } else if (i == entry.getValue().size() - 1){
                                if(currentEnd.plusMinutes(hours.getHour() + hours.getMinute()).isBefore(endWorkDay)){
                                    System.out.println("You can book a meet on " + fromDate.toString());
                                    booked = true;
                                    break;
                                }
                            } else {
                                LocalTime previousEnd = LocalTime.parse(entry.getValue().get(i - 1).getEndTime());
                                if(currentStart.minusMinutes(previousEnd.getHour() + previousEnd.getMinute()).compareTo(hours) > 0){
                                    System.out.println("You can book a meet on " + fromDate.toString());
                                    booked = true;
                                    break;
                                }
                            }
                        }
                    }
                    date = date.plusDays(1);
                    String newDay = String.valueOf(date.getDayOfMonth());
                    String newMonth = "0" + date.getMonthValue();
                    String newYear = String.valueOf(date.getYear());
                    fromDate = new Date(newDay, newMonth, newYear);
                    if(booked){
                        break;
                    }
                    continue;
                }
            }

        }

    }
}
