package src.ex03;

import java.util.UUID;

public class Program {
    public static void main(String[] args) throws TransactionNotFoundException {
        UsersList users = new UsersArrayList();
        User John = new User("John", 10000);
        User Mike = new User("Mike", 5000);
        users.add(John);
        users.add(Mike);
        TransactionsList transactionsListJohn = John.getList();
        TransactionsList transactionsListMike = Mike.getList();

        Transaction operation1 = new Transaction(John,
                Mike, Transaction.Operation.DEBIT, 500);
        Transaction operation2 = new Transaction(Mike,
                John, Transaction.Operation.CREDIT, -500);
        Transaction operation3 = new Transaction(Mike,
                John, Transaction.Operation.CREDIT, -700);
        Transaction operation4 = new Transaction(John,
                Mike, Transaction.Operation.DEBIT, 700);

        transactionsListJohn.setAddTransaction(operation1);
        transactionsListJohn.setAddTransaction(operation2);
        transactionsListJohn.setAddTransaction(operation3);
        transactionsListJohn.setAddTransaction(operation4);

        transactionsListMike.setAddTransaction(operation4);

        for (Transaction a: transactionsListJohn.toArray())
            System.out.println(a);

        System.out.println();
        transactionsListJohn.deleteTransaction(operation1.getIdentifier());

        for (Transaction a: transactionsListJohn.toArray())
            System.out.println(a);

        System.out.println();

        for (Transaction a: transactionsListMike.toArray())
            System.out.println(a);
    }
}
