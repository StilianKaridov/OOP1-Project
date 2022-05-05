package Exceptions;

public class InvalidDateException extends RuntimeException {
    public InvalidDateException() {
        System.out.println("The date is invalid!");
    }
}
