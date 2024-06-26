package src.ex04;

import java.util.UUID;

public interface TransactionsList
{
    void setAddTransaction(Transaction operation);

    void deleteTransaction(UUID idTransaction)
            throws TransactionNotFoundException;

    Transaction[] toArray();
}
