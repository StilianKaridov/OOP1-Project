package Exceptions;

public class InvalidTimeException extends RuntimeException{
    public InvalidTimeException() {
        System.out.println("The given times are not valid!");
    }
}
