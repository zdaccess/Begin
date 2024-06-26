package src.ex04;

public class IllegalTransactionException extends RuntimeException {
    public IllegalTransactionException() {
        System.out.println("Error! The client's balance is " +
                "less than the transfer amount or the transfer amount is zero!");
    }
}
