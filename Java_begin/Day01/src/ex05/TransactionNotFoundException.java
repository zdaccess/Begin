package src.ex05;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        System.out.println("Error! The transaction by ID was not found!");
    }
}
