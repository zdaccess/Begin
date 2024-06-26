package src.ex03;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException() {
        System.out.println("The transaction by ID was not found!");
    }
}
