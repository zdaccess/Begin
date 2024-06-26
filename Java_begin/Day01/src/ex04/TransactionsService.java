package src.ex04;

import java.util.UUID;

public class TransactionsService {

    private UsersList   userlist;
    private UUID        identifier;
    private User        userFirst;
    private User        userSecond;
    private Integer     countTransaction = 0;

    public TransactionsService(Integer size) {
        userlist = new UsersArrayList(size);
    }

    public TransactionsService() {
        userlist = new UsersArrayList();
    }

    public void add(User name) {
        userlist.add(name);
    }

    public Integer showBalance(Integer id) throws UserNotFoundException {
        return userlist.getUsersId(id).getBalance();
    }

    public void addTransactions(Integer idFirst, Integer idSecond,
                                Integer transferAmount)
                                throws UserNotFoundException {
        this.userFirst = userlist.getUsersId(idFirst);
        this.userSecond = userlist.getUsersId(idSecond);
        this.identifier = UUID.randomUUID();
        Transaction operation1 = new Transaction(this.userFirst,
                this.userSecond, Transaction.Operation.CREDIT,
                (-1 * transferAmount), this.identifier);
        Transaction operation2 = new Transaction(this.userSecond,
                this.userFirst, Transaction.Operation.DEBIT,
                transferAmount, this.identifier);
        userlist.getUsersId(idFirst).addTransaction(operation1);
        userlist.getUsersId(idSecond).addTransaction(operation2);
    }

    void deleteTransaction(Integer id, UUID identifier)
                            throws UserNotFoundException, TransactionNotFoundException {
        userlist.getUsersId(id).deleteTransactionUserList(identifier);
    }

    Transaction[] setArrayTransaction(Integer id)
                                        throws UserNotFoundException {
        return userlist.getUsersId(id).getList().toArray();
    }

    Integer countUnpairedTransaction(Transaction[] array) {
        Integer i = 0;
        Integer a;
        Integer flag;
        Integer count = 0;
        while (i < countTransaction) {
            flag = 1;
            a = 0;
            if (array[i] == null)
                break;
            while (a < countTransaction) {
                if (array[a] == null)
                    break;
                if (array[i].getIdentifier() == array[a].getIdentifier())
                    flag++;
                a++;
            }
            if ((flag - 1) % 2 == 1) {
                count = count + 1;
            }
            i++;
        }
        return count;
    }

    Transaction[] findUnpairedTransactions(Transaction[] array, Integer len) {
        Transaction[] unpairedTransactions = new Transaction[len];
        Integer i = 0;
        Integer a;
        Integer flag;
        Integer count = 0;

        while (i < countTransaction) {
            flag = 1;
            a = 0;
            if (array[i] == null)
                break;
            while (a < countTransaction) {
                if (array[a] == null)
                    break;
                if (array[i].getIdentifier() == array[a].getIdentifier())
                    flag++;
                a++;
            }
            if ((flag - 1) % 2 == 1) {
                unpairedTransactions[count++] = array[i];
            }
            i++;
        }
        return unpairedTransactions;
    }

    Transaction[] checkValidityTransactions() throws UserNotFoundException {
        for (Integer i = 1; i < userlist.getCount() + 1; i++) {
            TransactionsList userTransactions = userlist.getUsersId(i).getList();
            for (Transaction b : userTransactions.toArray()) {
                countTransaction++;
            }
        }
        Transaction[] array = new Transaction[countTransaction];
        Integer v = 0;
        for (Integer i = 1; i < userlist.getCount() + 1; i++) {
            TransactionsList userTransactions = userlist.getUsersId(i).getList();
            for (Transaction a : userTransactions.toArray()) {
                array[v] = a;
                v++;
            }
        }
        Integer count = countUnpairedTransaction(array);
        Transaction[] unpairedTransactions = findUnpairedTransactions(array, count);
        return unpairedTransactions;
    }
}
