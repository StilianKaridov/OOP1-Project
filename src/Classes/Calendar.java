package Classes;

import Exceptions.InvalidDateException;
import Exceptions.InvalidTimeException;
import Exceptions.IsNotFreeException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@XmlRootElement(name = "calendar")
@XmlAccessorType(XmlAccessType.FIELD)
public class Calendar implements Serializable {

    @XmlElement(name = "dates")
    private Map<Date, List<Meet>> dates;

    public Map<Date, List<Meet>> setDates() {   //Add all 365 days into the map
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
        this.dates = setDates();
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
                        System.out.println("You successfully booked a meet!");
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

    public void busyDays(String startDate, String endDate) throws InvalidDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");    //Pattern of the date
        String[] startDateInfo = startDate.split("/");  //Get the day and the month of the given startDate
        String[] endDateInfo = endDate.split("/");      //Get the day and the month of the given endDate

        //Parse the given date to LocalDate
        StringBuilder start = new StringBuilder();
        if (Integer.parseInt(startDateInfo[0]) < 10) {      //If the given day is below 10, append 0
            start.append("0");
        }
        start.append(startDateInfo[0]);     //Append the given day
        start.append("/");
        if (Integer.parseInt(startDateInfo[1]) < 10) {      //If the given month is below 10, append 0
            start.append("0");
        }
        start.append(startDateInfo[1]).append("/").append("2022");      //Append the given month, and the year 2022

        StringBuilder end = new StringBuilder();
        if (Integer.parseInt(endDateInfo[0]) < 10) {        //If the given day is below 10, append 0
            end.append("0");
        }
        end.append(endDateInfo[0]);     //Append the given day
        end.append("/");
        if (Integer.parseInt(endDateInfo[1]) < 10) {        //If the given month is below 10, append 0
            end.append("0");
        }
        end.append(endDateInfo[1]).append("/").append("2022");      //Append the given month, and the year 2022

        //Creating two object of class Date, so we can validate them
        Date validateStart = new Date(startDateInfo[0], startDateInfo[1]);
        Date validateEnd = new Date(endDateInfo[0], endDateInfo[1]);

        //If both dates are valid proceed, else throw an InvalidDate exception
        if (validateStart.validateDay(startDateInfo[0], startDateInfo[1]) && validateEnd.validateDay(endDateInfo[0], endDateInfo[1])) {
            //Parse the string builders to LocalDates
            LocalDate localStart = LocalDate.parse(start, formatter);
            LocalDate localEnd = LocalDate.parse(end, formatter);

            //Add the dates if they are from startDate to endDate
            Map<Date, List<Meet>> dates = new LinkedHashMap<>();
            for (Map.Entry<Date, List<Meet>> entry : this.dates.entrySet()) {
                String day = entry.getKey().getDay();
                String month = entry.getKey().getMonth();

                StringBuilder date = new StringBuilder();
                if (Integer.parseInt(day) < 10) {
                    date.append("0");
                }
                date.append(day);
                date.append("/");
                if (Integer.parseInt(month) < 10) {
                    date.append("0");
                }
                date.append(month).append("/").append("2022");

                LocalDate currentDate = LocalDate.parse(date, formatter);
                if (currentDate.equals(localStart) || currentDate.equals(localEnd)) {       //If the currentDate == startDate || currentDate == endDate, put
                    dates.put(entry.getKey(), entry.getValue());
                } else if (currentDate.isAfter(localStart) && currentDate.isBefore(localEnd)) {     //If the currentDate is between startDate and endDate, put
                    dates.put(entry.getKey(), entry.getValue());
                }
            }

            //Sort and print the map by the count busy hours
            Map<Date, Integer> sortedMap = new LinkedHashMap<>();
            for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {
                int busyMinutes = 0;
                Duration busyTime;
                for (Meet currentMeet : entry.getValue()) {
                    LocalTime currentStartTime = LocalTime.parse(currentMeet.getStartTime());   //Get startTime for the currentMeet
                    LocalTime currentEndTime = LocalTime.parse(currentMeet.getEndTime());       //Get the endTime for the currentMeet

                    busyTime = Duration.between(currentStartTime, currentEndTime);  //Get the duration of the currentMeet
                    busyMinutes += (int) busyTime.toMinutes();      //Add the time to the variable
                }
                sortedMap.put(entry.getKey(), busyMinutes);     //Put in the map, which we will sort later
            }

            //Stream for sorting and printing values in the map
            sortedMap.entrySet().stream()
                    .sorted(Map.Entry.<Date, Integer>comparingByValue().reversed())
                    .forEach(System.out::println);
        } else {
            throw new InvalidDateException();
        }
    }

    public void findSlot(String fromDate, Duration hours) throws InvalidDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalTime startWorkDay = LocalTime.parse("08:00");
        LocalTime endWorkDay = LocalTime.parse("17:00");

        String[] dateInfo = fromDate.split("/");      //Get the day and the month of the given date

        //Parse the given date to LocalDate
        StringBuilder startDate = new StringBuilder();
        if (Integer.parseInt(dateInfo[0]) < 10) {      //If the given day is below 10, append 0
            startDate.append("0");
        }
        startDate.append(dateInfo[0]);     //Append the given day
        startDate.append("/");
        if (Integer.parseInt(dateInfo[1]) < 10) {      //If the given month is below 10, append 0
            startDate.append("0");
        }
        startDate.append(dateInfo[1]).append("/").append("2022");      //Append the given month, and the year 2022
        LocalDate date = LocalDate.parse(startDate, formatter);

        boolean isAfter = false;  //To check if we passed the given date
        boolean isBooked = false; //To check whether it is possible to book a meet on a given day

        //Create an object, so we can validate the date
        Date validate = new Date(dateInfo[0], dateInfo[1]);
        if (validate.validateDay(dateInfo[0], dateInfo[1])) {       //If the dates are valid proceed, else throw an InvalidDate exception
            for (Map.Entry<Date, List<Meet>> entry : dates.entrySet()) {        //Iterate over the dates
                if (entry.getKey().getDay().equals(validate.getDay()) && entry.getKey().getMonth().equals(validate.getMonth()) || isAfter) {    //If currentKey equals the given date or we are after the given date
                    entry.getValue().sort(Comparator.comparing(Meet::getStartTime));    //Sort the meetings by startTime
                    if (!entry.getKey().isHoliday()) {      //Check if the date is not holiday
                        if (entry.getValue().size() == 0 && hours.toMinutes() <= 540) {     //If the list of meetings is empty that means we can book a meet
                            isBooked = true;
                        } else if (entry.getValue().size() == 1) {
                            //If we have only one meeting on that day, check if we can fit the meet between 08:00 and 17:00
                            LocalTime timeStart = LocalTime.parse(entry.getValue().get(0).getStartTime());
                            LocalTime timeEnd = LocalTime.parse(entry.getValue().get(0).getEndTime());
                            if (timeStart.minusMinutes(hours.toMinutes()).isAfter(startWorkDay) || timeEnd.plusMinutes(hours.toMinutes()).isBefore(endWorkDay)) {
                                isBooked = true;
                                break;
                            }
                        } else {
                            //If the meetings are more than 1, iterate over the list
                            for (int i = 0; i < entry.getValue().size(); i++) {
                                LocalTime endTime = LocalTime.parse(entry.getValue().get(i).getEndTime());
                                if (i < entry.getValue().size() - 1) {
                                    LocalTime nextStart = LocalTime.parse(entry.getValue().get(i + 1).getStartTime());
                                    if (endTime.plusMinutes(hours.toMinutes()).isBefore(nextStart)) {
                                        LocalTime startTimeSlot = endTime.plusMinutes(1);
                                        LocalTime endTimeSlot = startTimeSlot.plusMinutes(hours.toMinutes());
                                        if (startTimeSlot.isAfter(startWorkDay) && endTimeSlot.isBefore(endWorkDay)) {
                                            isBooked = true;
                                            break;
                                        }
                                        //startTimeSlot = endTime.plusMinutes(1)
                                        //endTimeSlot = nextStart.minusMinutes(1)
                                        //Creating new Meet object-Parse the start and end time
                                        //if(start.isAfter(8:00 && end.isBefore(17:00)
                                    }
                                } else {
                                    if (endTime.plusMinutes(hours.toMinutes()).isBefore(endWorkDay)) {
                                        isBooked = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    isAfter = true;     //Make isAfter true, if we can't book a meet on the given date, so we can proceed to other days
                }

                if (isBooked) {
                    System.out.println("You can book a meet on " + entry.getKey().toString());
                    break;
                }

            }
            //If all over the year we are busy, we can't book a meet
            if (!isBooked) {
                System.out.println("You can't book a meet!");
            }
        } else {
            throw new InvalidDateException();
        }
    }

    public void findSlotWith(String fromDate, Duration hours, String calendarFromFile) {
        findSlot(fromDate, hours);

    }
}
