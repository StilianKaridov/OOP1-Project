package Exceptions;

public class CannotFindSlotException extends RuntimeException{
    public CannotFindSlotException() {
        System.out.println("You can't book a meet!");
    }
}
