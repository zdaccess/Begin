package src.ex03;

import java.util.UUID;

public class Transaction {
    private UUID    identifier;
    private User    recipient;
    private User    sender;
    private Integer transferAmount;
    private String  nameOperation;

    enum Operation {
        DEBIT(),
        CREDIT()
    }

    public Transaction(User sender, User recipient,
                       Transaction.Operation transferCategory, Integer transferAmount) {
        setValuesTR(sender, recipient, transferCategory, transferAmount);
    }

    public void setValuesTR(User sender, User recipient,
                            Transaction.Operation transferCategory,
                            Integer transferAmount) {
        if (transferCategory == Transaction.Operation.CREDIT) {
            nameOperation = "credit";
            if (transferAmount >= 0)
                this.errorSumTransferMinus(transferAmount);
            else if ((-1) * transferAmount > sender.getBalance())
                this.errorUserBalance(sender.getName());
            else
                sender.setSum(transferAmount);
        } else if (transferCategory == Transaction.Operation.DEBIT) {
            nameOperation = "debit";
            if (transferAmount <= 0)
                this.errorSumTransferPlus(transferAmount);
            else
                sender.setSum(transferAmount);
        }
        this.identifier = UUID.randomUUID();
        this.transferAmount = transferAmount;
        this.sender = sender;
        this.recipient = recipient;
    }

    public UUID getIdentifier() {
        return this.identifier;
    }
    
    private void errorUserBalance(String sender) {
        System.out.println("The sender - " + sender
                + " does not have enough money for the transfer");
    }

    private void errorSumTransferMinus(Integer minus) {
        System.out.println("The value must be negative! " +
                "The amount sent: " + minus);
    }

    private void errorSumTransferPlus(Integer plus) {
        System.out.println("The value must be positive! " +
                "The amount sent: " + plus);
    }

    @Override
    public String toString() {
        if (this.nameOperation.equals("credit")) {
            return (this.sender.getName() + " -> " + this.recipient.getName()
                    + ", " + this.transferAmount
                    + ", OUTCOME, " + this.identifier);
        } else {
            return (this.sender.getName() + " -> " + this.recipient.getName()
                    + ", +" + this.transferAmount
                    + ", INCOME, " + this.identifier);
        }
    }
}
