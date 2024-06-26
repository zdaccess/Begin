package src.ex05;

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
                       Integer transferAmount, UUID Identifier)
                        throws IllegalTransactionException {
        setValuesTR(sender, recipient, transferCategory, transferAmount, Identifier);
    }

    public void setValuesTR(User sender, User recipient,
                            Operation transferCategory,
                            Integer transferAmount, UUID identifier)
                            throws IllegalTransactionException {
        if (transferCategory == Operation.CREDIT) {
            nameOperation = "credit";
            if ((-1) * transferAmount > sender.getBalance())
                throw new  IllegalTransactionException();
            else
                sender.setSum(transferAmount);
        }
        else if (transferCategory == Operation.DEBIT)
        {
            nameOperation = "debit";
            sender.setSum(transferAmount);
        }
        this.identifier = identifier;
        this.transferAmount = transferAmount;
        this.sender = sender;
        this.recipient = recipient;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
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

    public String getNameOperation() {
        return nameOperation;
    }

    public UUID getIdentifier() {
        return this.identifier;
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


