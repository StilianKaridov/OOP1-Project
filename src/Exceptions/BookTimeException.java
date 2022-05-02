package Exceptions;

public class BookTimeException extends RuntimeException{
    public BookTimeException() {
        System.out.println("You already have a meeting in this time range");
    }
}
