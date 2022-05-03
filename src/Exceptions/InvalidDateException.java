package Exceptions;

public class InvalidDateException extends Throwable {
    public InvalidDateException() {
        System.out.println("The date is invalid!");
    }
}
