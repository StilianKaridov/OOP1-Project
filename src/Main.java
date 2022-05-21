import Classes.Calendar;
import Classes.Date;
import Classes.Meet;
import Exceptions.InvalidDateException;
import Exceptions.InvalidTimeException;
import Exceptions.IsNotFreeException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.time.Duration;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, JAXBException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose operation: ");
        String input = scanner.nextLine();
        Calendar calendar = new Calendar();
        String fileDirectory = "C:\\Users\\stili\\Desktop\\OOP1-Project\\src\\Files\\dot.xml";
        while (!input.equals("exit")) {
            String[] commands = input.split("\\s+");
            switch (commands[0].toLowerCase()) {
                case "open":
                    System.out.print("Please enter file directory: ");
                    fileDirectory = scanner.nextLine();
                    open(fileDirectory);
                    break;
                case "close":

                    break;
                case "save":
                    save(fileDirectory);
                    break;
                case "saveas":

                    break;
                case "help":

                    break;
                case "book":
                    System.out.print("Please give us a date in the format(1/1): ");
                    String date = scanner.nextLine();
                    System.out.print("Info for the date: ");
                    System.out.print("\nStart time: ");
                    String startTime = scanner.nextLine();
                    System.out.print("End time: ");
                    String endTime = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Note: ");
                    String note = scanner.nextLine();
                    try {
                        calendar.book(date, new Meet(startTime, endTime, name, note));
                    } catch (IsNotFreeException | InvalidDateException e) {

                    }
                    break;
                case "unbook":
                    System.out.print("Please give us a date in the format(1/1): ");
                    date = scanner.nextLine();
                    System.out.print("Info for the date: ");
                    System.out.print("\nStart time: ");
                    startTime = scanner.nextLine();
                    System.out.print("End time: ");
                    endTime = scanner.nextLine();
                    try {
                        calendar.unbook(date, startTime, endTime);
                    } catch (InvalidTimeException | InvalidDateException e) {

                    }
                    break;
                case "agenda":
                    System.out.print("Please give us a date in the format(1/1): ");
                    date = scanner.nextLine();
                    try {
                        calendar.agenda(date);
                    } catch (InvalidDateException e) {

                    }
                    break;
                case "change":
                    System.out.print("Please give us a date in the format(1/1): ");
                    date = scanner.nextLine();
                    System.out.print("\nStart time: ");
                    startTime = scanner.nextLine();
                    System.out.print("Option: ");
                    String option = scanner.nextLine();
                    System.out.print("New value: ");
                    String newValue = scanner.nextLine();
                    try {
                        calendar.change(date, startTime, option, newValue);
                    } catch (IsNotFreeException | InvalidDateException e) {

                    }
                    break;
                case "find":
                    System.out.print("Search for: ");
                    String toFind = scanner.nextLine();
                    calendar.find(toFind);
                    break;
                case "holiday":
                    System.out.print("Please give us a date in the format(1/1): ");
                    date = scanner.nextLine();
                    try {
                        calendar.holiday(date);
                    } catch (InvalidDateException e) {

                    }
                    break;
                case "busydays":
                    System.out.print("Please give us a start date in the format(1/1): ");
                    date = scanner.nextLine();
                    System.out.print("Please give us an end date in the format(1/1): ");
                    String endDate = scanner.nextLine();
                    try {
                        calendar.busyDays(date, endDate);
                    } catch (InvalidDateException e) {

                    }
                    break;
                case "findslot":
                    System.out.print("Please give us a start date in the format(1/1): ");
                    date = scanner.nextLine();
                    System.out.print("Duration of the meet: ");
                    String time = scanner.nextLine();
                    Duration duration = Duration.parse(time);
                    try {
                        calendar.findSlot(date, duration);
                    } catch (InvalidDateException e) {

                    }
                    break;
                case "findslotwith":

                    break;
                case "merge":

                    break;
                default:
                    System.out.println("Invalid operation!");
                    break;
            }
            System.out.print("Choose operation: ");
            input = scanner.nextLine();
        }
        System.out.println("Exiting the program...");
    }

    public static void open(String fileDirectory) throws IOException, JAXBException {
        File file = new File(fileDirectory);    //Create file
        if (file.exists()) {      //Check if the file exists
            JAXBContext jaxbContext = JAXBContext.newInstance(Calendar.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Calendar calendar = (Calendar) unmarshaller.unmarshal(file);
            System.out.println("Successfully opened " + file.getName());
        } else {
            if (file.createNewFile()) {
                System.out.println("Successfully created " + file.getName());
            }
        }
    }

    public static void save(String fileDirectory) {
        Calendar calendar = new Calendar();
        Meet meet = new Meet("12:30", "13:30", "Stilian", "Neshto si");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Meet.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(meet, new File(fileDirectory));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully saved " + fileDirectory);
    }
}