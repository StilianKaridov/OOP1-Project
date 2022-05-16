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

    private Map<Date, List<Meet>> dates;

    public Map<Date, List<Meet>> addDates() {   //Add all 365 days into the map
        Map<Date, List<Meet>> map = new LinkedHashMap<>();
        for (int i = 1; i <= 12; i++) {
            String month = String.valueOf(i);
            for (int j = 1; j <= 28; j++) {
                String day = String.valueOf(j);
                map.put(new Date(day, month), new ArrayList<>());
            }
            switch (i) {
                case 1:
                    map.put(new Date("29", "1"), new ArrayList<>());
                    map.put(new Date("30", "1"), new ArrayList<>());
                    map.put(new Date("31", "1"), new ArrayList<>());
                    break;
                case 3:
                    map.put(new Date("29", "3"), new ArrayList<>());
                    map.put(new Date("30", "3"), new ArrayList<>());
                    map.put(new Date("31", "3"), new ArrayList<>());
                    break;
                case 4:
                    map.put(new Date("29", "4"), new ArrayList<>());
                    map.put(new Date("30", "4"), new ArrayList<>());
                    break;
                case 5:
                    map.put(new Date("29", "5"), new ArrayList<>());
                    map.put(new Date("30", "5"), new ArrayList<>());
                    map.put(new Date("31", "5"), new ArrayList<>());
                    break;
                case 6:
                    map.put(new Date("29", "6"), new ArrayList<>());
                    map.put(new Date("30", "6"), new ArrayList<>());
                    break;
                case 7:
                    map.put(new Date("29", "7"), new ArrayList<>());
                    map.put(new Date("30", "7"), new ArrayList<>());
                    map.put(new Date("31", "7"), new ArrayList<>());
                    break;
                case 8:
                    map.put(new Date("29", "8"), new ArrayList<>());
                    map.put(new Date("30", "8"), new ArrayList<>());
                    map.put(new Date("31", "8"), new ArrayList<>());
                    break;
                case 9:
                    map.put(new Date("29", "9"), new ArrayList<>());
                    map.put(new Date("30", "9"), new ArrayList<>());
                    break;
                case 10:
                    map.put(new Date("29", "10"), new ArrayList<>());
                    map.put(new Date("30", "10"), new ArrayList<>());
                    map.put(new Date("31", "10"), new ArrayList<>());
                    break;
                case 11:
                    map.put(new Date("29", "11"), new ArrayList<>());
                    map.put(new Date("30", "11"), new ArrayList<>());
                    break;
                case 12:
                    map.put(new Date("29", "12"), new ArrayList<>());
                    map.put(new Date("30", "12"), new ArrayList<>());
                    map.put(new Date("31", "12"), new ArrayList<>());
                    break;
            }
        }
        return map;
    }

    public Calendar() {
        this.dates = addDates();
    }

    public Map<Date, List<Meet>> getDates() {
        return dates;
    }

    @Override
    public String toString() {
        return "Schedule for: " + dates;
    }

    public void book(String date, Meet meet) throws IsNotFreeException, InvalidDateException {
        LocalTime start;
        LocalTime end;
        LocalTime currentStart = LocalTime.parse(meet.getStartTime());  //Start time of the parameter meet
        LocalTime currentEnd = LocalTime.parse(meet.getEndTime());  //End time of the parameter meet
        List<Meet> list;    //To store meets, if any
        String[] dateInfo = date.split("/");    //Split the parameter date, so we can get the day and the month
        Date validate = new Date(dateInfo[0], dateInfo[1]);    //Create an instance of Date, so we can use Validate methods

        if (validate.validateDay(validate.getDay(), validate.getMonth())) {  //If the date is valid
            for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {    //Iterate over the map: dates
                if (entry.getKey().getDay().equals(dateInfo[0]) && entry.getKey().getMonth().equals(dateInfo[1])) {     //If currentKey equals the date
                    if (!entry.getKey().isHoliday()) {  //And if it's not holiday
                        for (Meet currentMeet : entry.getValue()) {  // Iterate over the date's list of meetings
                            start = LocalTime.parse(currentMeet.getStartTime());    //Get the currentMeet startTime
                            end = LocalTime.parse(currentMeet.getEndTime());    //Get the currentMeet endTime
                            if (currentStart.isBefore(start) && currentEnd.isAfter(start) ||
                                    currentStart.isAfter(start) && currentStart.isBefore(end)) {
                                throw new IsNotFreeException();     // Check if the time is free, if not throw an exception
                            }
                        }
                        list = entry.getValue();    // Get the existing meetings
                        list.add(meet);     // Add the meet to the existing meetings
                        dates.put(entry.getKey(), list);     // Put them in the map
                    }
                }
            }
        } else {
            throw new InvalidDateException();   //If the date is invalid throw an exception for InvalidDate
        }
    }

    public void unbook(String date, String startTime, String endTime) throws InvalidTimeException, InvalidDateException {
        boolean timeExists = false;      //To check if the time exists
        boolean doesIterate = false;    //To check if iteration is made in the nested foreach
        String[] dateInfo = date.split("/");    //Split the parameter date, so we can get the day and the month
        Date validate = new Date(dateInfo[0], dateInfo[1]);    //Create an instance of Date, so we can use Validate methods

        if (validate.validateDay(validate.getDay(), validate.getMonth())) {  //If the date is valid
            for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {    //Iterate over the map
                if (entry.getKey().getDay().equals(dateInfo[0]) && entry.getKey().getMonth().equals(dateInfo[1])) {     //If currentKey equals the date
                    for (Meet currentMeet : entry.getValue()) {     //Iterate over the list of meetings
                        doesIterate = true;     //Make true, so we can break later
                        if (currentMeet.getStartTime().equals(startTime) && currentMeet.getEndTime().equals(endTime)) {     //If the times exists, remove the meet
                            dates.get(entry.getKey()).remove(currentMeet);
                            System.out.println("You successfully unbooked the meeting!");
                            timeExists = true;
                        }
                        break;
                    }
                }
                if (doesIterate) {
                    break;  //Break if iteration is made in the nested foreach
                }
            }
            if (!timeExists) {
                throw new InvalidTimeException();   // Throw the exception if the given times are invalid
            }
        } else {
            throw new InvalidDateException();   //If the date is not valid, throw an exception
        }
    }

    public void agenda(String date) throws InvalidDateException {
        boolean isValid = false;
        String[] dateInfo = date.split("/");    //Get the day and the month of the given date
        Date validate = new Date(dateInfo[0], dateInfo[1]);     //Create instance of Date, so we can validate the given date
        if (validate.validateDay(validate.getDay(), validate.getMonth())) {   //If the date is valid
            for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {    //Iterate over the map
                if (entry.getKey().getDay().equals(dateInfo[0]) && entry.getKey().getMonth().equals(dateInfo[1])) {     //If currentKey equals date
                    Collections.sort(entry.getValue());     //Sort the list of meetings
                    isValid = true;
                    System.out.println("Agenda for " + entry.getKey().toString() + " - " + entry.getValue());  //Print the sorted list
                    break;
                }
            }
        } else {
            throw new InvalidDateException();   //If the date is invalid throw an exception
        }
    }

    public void change(String date, String startTime, String option, String newValue) throws IsNotFreeException, InvalidDateException {
        boolean isFulfilled = false;
        String[] dateInfo = date.split("/");    //Get the day and the month of the given date
        Date validate = new Date(dateInfo[0], dateInfo[1]);     //Create instance of Date, so we can validate the given date
        if (validate.validateDay(validate.getDay(), validate.getMonth())) {     //If its valid proceed
            for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {    //Iterate over the map
                if (entry.getKey().getDay().equals(validate.getDay()) && entry.getKey().getMonth().equals(validate.getMonth())) {   //If the currentKey is equal to the given date
                    for (Meet currentMeet : entry.getValue()) {     //Iterate over the list of meetings
                        if (currentMeet.getStartTime().equals(startTime)) {     //If startTime equals the given startTime
                            isFulfilled = true;     //Make this true so we can break later
                            switch (option) {
                                case "date":
                                    String[] values = newValue.split("/");      //Get the info of the new date
                                    String newDay = values[0];
                                    String newMonth = values[1];
                                    Date newDate = new Date(newDay, newMonth);
                                    if (newDate.validateDay(newDay, newMonth)) {    //Validate
                                        book(newValue, currentMeet);    //book a meet on the new date
                                        entry.getValue().remove(currentMeet);   //and remove meet from the current list of meetings
                                    } else {
                                        throw new InvalidDateException();   // If not throw an exception
                                    }
                                    break;
                                case "startTime":
                                    LocalTime newStart = LocalTime.parse(newValue);     //Get the new startTime value
                                    LocalTime currentEnd = LocalTime.parse(currentMeet.getEndTime());
                                    for (Meet meet : entry.getValue()) {    //Iterate and if the exception in not thrown, then we can set new startTime
                                        LocalTime start = LocalTime.parse(meet.getStartTime());
                                        LocalTime end = LocalTime.parse(meet.getEndTime());
                                        if (newStart.isBefore(start) && currentEnd.isAfter(start) || newStart.isAfter(start) && newStart.isBefore(end) && currentMeet.getStartTime().compareTo(meet.getStartTime()) != 0) {
                                            throw new IsNotFreeException();
                                        }
                                    }
                                    currentMeet.setStartTime(newValue);
                                    break;
                                case "endTime":
                                    LocalTime currentStart = LocalTime.parse(currentMeet.getStartTime());
                                    LocalTime newEnd = LocalTime.parse(newValue);
                                    for (Meet meet : entry.getValue()) {    //Iterate and if the exception is not thrown, then we can set new endTime
                                        LocalTime start = LocalTime.parse(meet.getStartTime());
                                        LocalTime end = LocalTime.parse(meet.getEndTime());
                                        if (currentStart.isBefore(start) && newEnd.isAfter(start) || currentStart.isAfter(start) && currentStart.isBefore(end) && currentMeet.getStartTime().compareTo(meet.getStartTime()) != 0) {
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
        } else {    //If its not valid throw an exception
            throw new InvalidDateException();
        }
    }

    public void find(String toFind) {
        boolean isFound = false;
        for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {
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

    public void holiday(String date) throws InvalidDateException {
        String[] dateInfo = date.split("/");    //Get the day and the month of the given date
        Date validate = new Date(dateInfo[0], dateInfo[1]);     //Create instance of Date, so we can validate the given date
        if (validate.validateDay(validate.getDay(), validate.getMonth())) {     //Check if the date is valid
            for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {    //Iterate over the map
                if (entry.getKey().getDay().equals(validate.getDay()) && entry.getKey().getMonth().equals(validate.getMonth())) {   //If the currentKey is equal to the given date
                    entry.getKey().setHoliday(true);    //Set holiday = true
                    entry.getValue().clear();   //Remove all meetings on that date
                    break;
                }
            }
        } else {
            throw new InvalidDateException();   //If its not valid throw an exception
        }
    }

    public void busyDays(Date startDate, Date endDate) {    //TODO if u can, do it with strings, not with date objects
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uu");

        //Parse the given Date objects to LocalDate
        StringBuilder start = new StringBuilder(startDate.getDay());
        start.append("/").append(startDate.getMonth()).append("/");

        StringBuilder end = new StringBuilder(endDate.getDay());
        end.append("/").append(endDate.getMonth()).append("/");

        LocalDate localStart = LocalDate.parse(start, formatter);
        LocalDate localEnd = LocalDate.parse(end, formatter);

        //Add the dates if they are from startDate to endDate
        Map<Date, List<Meet>> dates = new LinkedHashMap<>();
        for (Map.Entry<Date, List<Meet>> entry : this.dates.entrySet()) {
            String month = entry.getKey().getMonth();
            String day = entry.getKey().getDay();
            StringBuilder date = new StringBuilder(day);
            date.append("/").append(month).append("/");

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

//    public void findSlot(Date fromDate, Duration hours) {
//        LocalTime startWorkDay = LocalTime.parse("08:00");
//        LocalTime endWorkDay = LocalTime.parse("17:00");
//        StringBuilder string = new StringBuilder(fromDate.getDay());
//        string.append("/").append(fromDate.getMonth()).append("/");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uu");
//        boolean isBooked = false;
//        boolean doesContain = false;
//
//        boolean after = false;
//        //TODO try with foreach, when the day and month are equal to entry-day and entry-month then check if the date.isHoliday and proceed
//        for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {
//            if (!entry.getKey().isHoliday()) {
//                if (entry.getKey().getDay().equals(dayAndMonth[0]) && entry.getKey().getMonth().equals(dayAndMonth[1])) {
//
//                    after = true;
//                } else if (after) {
//
//                }
//            }
//
//        }
//
//
//        LocalDate date = LocalDate.parse(string, formatter);
//        for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {
//            if (entry.getKey().getDay().equals(fromDate.getDay()) &&
//                    entry.getKey().getMonth().equals(fromDate.getMonth())) {
//                doesContain = true;
//                break;
//            }
//        }
//
//        for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {
//            if (!fromDate.isHoliday()) {
//                if (!doesContain) {
//
//                    isBooked = true;
//                    break;
//                } else {
//                    LocalTime startTime;
//                    LocalTime endTime;
//                    entry.getValue().sort(Comparator.comparing(Meet::getStartTime));
//                    for (int i = 0; i < entry.getValue().size(); i++) {
//                        startTime = LocalTime.parse(entry.getValue().get(i).getStartTime());
//                        endTime = LocalTime.parse(entry.getValue().get(i).getEndTime());
//
//                        if (startTime.minusMinutes(hours.toMinutes()).isBefore(startWorkDay) || endTime.plusMinutes(hours.toMinutes()).isBefore(endWorkDay)) {
//                            isBooked = true;
//                            break;
//                        }
//                    }
//
//                    if (isBooked) {
//                        System.out.println("You can book a meet on " + fromDate.toString());
//                        break;
//                    } else {
//                        date = date.plusDays(1);
//                        String newDay = String.valueOf(date.getDayOfMonth());
//                        String newMonth = "0" + date.getMonthValue();
//                        String newYear = String.valueOf(date.getYear());
//                        fromDate = new Date(newDay, newMonth);
//                    }
//                }
//            }
//        }
//    }
//
//    public void findSlotWith(Date fromDate, LocalTime hours, Calendar calendar) {

//}
}
