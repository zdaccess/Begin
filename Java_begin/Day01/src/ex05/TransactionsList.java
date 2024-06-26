package src.ex05;

import java.util.UUID;

public interface TransactionsList
{
    void setAddTransaction(Transaction operation);

    Transaction deleteTransaction(UUID idTransaction)
            throws TransactionNotFoundException;

    Transaction[] toArray();
}
