package Exceptions;

public class IsNotFreeException extends RuntimeException{
    public IsNotFreeException() {
        System.out.println("Cannot book a date! You have a meeting in this time diapason!");
    }
}
