package src.ex04;

import java.util.UUID;

public class Program {
    public static void main(String[] args) throws TransactionNotFoundException, UserNotFoundException, IllegalTransactionException {
        TransactionsService facade = new TransactionsService();
        User John = new User("John", 10000);
        User Mike = new User("Mike", 5000);
        facade.add(John);
        facade.add(Mike);
        System.out.println("Баланс Mike - " + facade.showBalance(Mike.getId()));
        System.out.println("Баланс John - " + facade.showBalance(John.getId()));
        facade.addTransactions(John.getId(), Mike.getId(), 100);
        facade.addTransactions(John.getId(), Mike.getId(), 200);
        Transaction[] transactionsListJohn = facade.setArrayTransaction(John.getId());
        for (Integer i = 0; i < transactionsListJohn.length; i++) {
            System.out.println(transactionsListJohn[i]);
        }
        Transaction[] transactionsListMike = facade.setArrayTransaction(Mike.getId());
        for (Integer i = 0; i < transactionsListMike.length; i++) {
            System.out.println(transactionsListMike[i]);
        }
        System.out.println("Баланс Mike - " + facade.showBalance(Mike.getId()));
        System.out.println("Баланс John - " + facade.showBalance(John.getId()));
        UUID transactionDeleteIdJohn = transactionsListJohn[0].getIdentifier();
        facade.deleteTransaction(John.getId(), transactionDeleteIdJohn);
        Transaction[] transactionsListJohnA = facade.setArrayTransaction(John.getId());
        for (Integer i = 0; i < transactionsListJohnA.length; i++) {
            System.out.println(transactionsListJohnA[i]);
        }
        Transaction [] transactionsFirst = facade.checkValidityTransactions();
        for (Integer i = 0; i < transactionsFirst.length; i++) {
            System.out.println("Непарная транзакция 1: " + transactionsFirst[i]);
        }
        System.out.println("Проверка");
        UUID transactionDeleteIdMike = transactionsListMike[0].getIdentifier();
        facade.deleteTransaction(Mike.getId(), transactionDeleteIdMike);
        Transaction[] transactionsSecond = facade.checkValidityTransactions();
        for (Integer i = 0; i < transactionsSecond.length; i++) {
            System.out.println("Непарная транзакция 2: " + transactionsSecond[i]);
        }
    }
}
