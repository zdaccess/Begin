package src.ex04;

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
                       Transaction.Operation transferCategory,
                       Integer transferAmount, UUID identifier)
                        throws IllegalTransactionException {
        setValuesTR(sender, recipient, transferCategory,
                transferAmount, identifier);
    }

    public void setValuesTR(User sender, User recipient,
                            Transaction.Operation transferCategory,
                            Integer transferAmount, UUID identifier)
            throws IllegalTransactionException {
        if (transferCategory == Transaction.Operation.CREDIT) {
            nameOperation = "credit";
            if ((-1) * transferAmount > sender.getBalance())
                throw new IllegalTransactionException();
            else
                sender.setSum(transferAmount);
        }
        else if (transferCategory == Transaction.Operation.DEBIT)
        {
            nameOperation = "debit";
            sender.setSum(transferAmount);
        }
        this.identifier = identifier;
        this.transferAmount = transferAmount;
        this.sender = sender;
        this.recipient = recipient;
    }

    public UUID getIdentifier() {
        return this.identifier;
    }

    private void errorSumTransferMinus(Integer minus) {
        System.out.println("The value must be negative! " +
                "The amount sent: " + minus);
    }

    private void errorSumTransferPlus(Integer plus) {
        System.out.println("The value must be positive! " +
                "The amount sent: " + plus);
    }

    public String getNameOperation() {
        return nameOperation;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Integer getTransferAmount() {
        return transferAmount;
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


