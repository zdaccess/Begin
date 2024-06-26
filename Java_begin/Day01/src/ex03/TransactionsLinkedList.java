package src.ex03;

import java.util.UUID;

public class TransactionsLinkedList<T> implements TransactionsList{

    private T               operation;
    private Memory          start;
    private Memory          end;
    private static Integer  count = 0;

    public TransactionsLinkedList() {
        start = null;
        end = null;
    }

    public boolean empty() {
        return end == null;
    }

    public void setAddTransaction(Transaction operation) {
        Memory tmp = new Memory(operation);
        if (empty())
            start = tmp;
        else
            end.memoryNext = tmp;
        tmp.memoryPrev = end;
        end = tmp;
        count++;
    }

    public void deleteFirst() {
        if (start.memoryNext == null)
            end = null;
        else
            start.memoryNext.memoryPrev = null;
        start = start.memoryNext;
    }

    public void deleteEnd() {
        if (start.memoryNext == null) {
            start = null;
        }
        else {
            end.memoryPrev.memoryNext = null;
        }
        end = end.memoryPrev;
    }

    public void deleteTransaction(UUID idTransaction)
            throws TransactionNotFoundException, NullPointerException {
        Memory current = start;
        while(current != null) {
            if (current.operation.getIdentifier().equals(idTransaction)) {
                break;
            }
             current = current.memoryNext;
        }
        if (current == null) {
            throw new TransactionNotFoundException();
        }
        else if (current == start) {
            deleteFirst();
        }
        else
            current.memoryPrev.memoryNext = current.memoryNext;
        if (current == end) {
            deleteEnd();
        }
        else
            current.memoryNext.memoryPrev = current.memoryPrev;
        count--;
    }

    public Integer getCount() {
        Integer i = 0;
        Memory current = start;
        while(current != null) {
            current = current.memoryNext;
            i++;
        }
        return i;
    }

    public Transaction[] toArray() {
        Memory current = start;
        Transaction[] array = new Transaction[this.getCount()];
        current = start;
        for (Integer j = 0; current != null; j++) {
            array[j] = current.operation;
            current = current.memoryNext;
        }
        return array;
    }

    public T getOperation() {
        return operation;
    }
}
