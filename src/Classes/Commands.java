package Classes;

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

public class Commands {
    private Calendar calendar = new Calendar();
    private File file;
    private String fileDirectory;
    private boolean isOpened;

    public Commands() {
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(String fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    //Opens and reads the file with the give path
    public void open(String fileDirectory) throws IOException, JAXBException {
        if (isOpened()) {
            System.out.println("You already opened that file!");
        } else {
            File file = new File(fileDirectory);    //Create file
            if (file.exists()) {      //Check if the file exists
                FileInputStream fileOpen = new FileInputStream(fileDirectory);      //InputStream
                if (fileOpen.available() != 0) {        //Until EOF
                    JAXBContext jaxbContext = JAXBContext.newInstance(Calendar.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                    this.calendar = (Calendar) unmarshaller.unmarshal(file);
                    fileOpen.close();
                }
                System.out.println("Successfully opened " + file.getName());
            } else {
                if (file.createNewFile()) {
                    System.out.println("Successfully created " + file.getName());
                }
            }
            this.file = file;
            this.fileDirectory = fileDirectory;
            setOpened(true);
        }
    }

    //To check if the user opened a file
    public boolean CheckIsFileOpen() {
        if (this.file == null) {
            return false;
        }
        return true;
    }

    //Closes currently opened file
    public void close() {
        System.out.println("Successfully closed " + file.getName());
        this.file = null;
        this.fileDirectory = null;
        this.calendar = new Calendar();
        setOpened(false);

    }

    //Saves the changes on the currently opened file
    public void save() {
        if (isOpened()) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Calendar.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                jaxbMarshaller.marshal(calendar, getFile());
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            System.out.println("Successfully saved " + this.file.getName());
        } else {
            System.out.println("First you need to open a file!");
        }
    }

    //Saves the changes in a new file
    public void saveAs(String fileDirectory) {
        if (isOpened()) {
            File file = new File(fileDirectory);
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Calendar.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                jaxbMarshaller.marshal(calendar, file);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
            System.out.println("Successfully saved " + file.getName());
        } else {
            System.out.println("First you need to open a file!");
        }

    }

    //Prints help
    public void help() {
        System.out.println("\n\nThe following commands are supported: ");
        System.out.println("open <file> opens <file> " +
                "\nclose         closes currently opened file " +
                "\nsave          saves the currently open file " +
                "\nsaveas <file>   saves the currently open file in <file> " +
                "\nhelp          prints this information " +
                "\nexit          exists the program " +
                "\nbook <date> <start time> <end time> <name> <note>      books a date with the given info" +
                "\nunbook <date> <start time> <end time>      unbooks a date with start time and end time" +
                "\nagenda <date>        prints the meetings you have on that day, chronologically" +
                "\nchange <date> <start time> <option> <new value>        change a meet value on date with start time and replaces it with the new value" +
                "\nfind <string>        finds a meet which has a name or note that contains the given string" +
                "\nholiday <date>       makes the given date a holiday" +
                "\nbusydays <from> <to>     from date to date prints load statistics" +
                "\nfindslot <fromdate> <hours>      finds a place to book a meet with duration from date" +
                "\nfindslotwith <fromdate> <hours> <calendar>       finds a place to book a meet with duration from date synchronized with given calendar" +
                "\nmerge <calendar>     reads the info from the calendar from file and merges it with the current calendar\n\n");
    }

    public void menu() throws JAXBException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Choose operation: ");
        String input = scanner.nextLine();
        while (!input.equals("exit")) {
            String[] commands = input.split("\\s+");
            switch (commands[0].toLowerCase()) {
                case "open":
                    System.out.print("Please enter file directory: ");
                    this.fileDirectory = scanner.nextLine();
                    open(getFileDirectory());
                    break;
                case "close":
                    close();
                    break;
                case "save":
                    save();
                    break;
                case "saveas":
                    System.out.print("Please enter file directory which you wanna save this file: ");
                    this.fileDirectory = scanner.nextLine();
                    saveAs(getFileDirectory());
                    break;
                case "help":
                    help();
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
}
