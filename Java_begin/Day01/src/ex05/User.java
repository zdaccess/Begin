package src.ex05;

import java.util.UUID;

public class User {
    private Integer             identifier;
    private String              name;
    private Integer             balance;
    private TransactionsList    arrayTransList;

    public User(String name, Integer balance) {
        arrayTransList = new TransactionsLinkedList();
        if (!(balance < 0))
            this.setValues(name, balance);
        else
            System.out.println("Error: The customer's balance is negative!");
    }

    public void setValues(String name, Integer balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        this.balance = balance;
    }

    public void setSum(Integer plus) {
        this.balance = this.balance + plus;
    }

    public String getName() {
        return this.name;
    }

    public Integer getBalance() {
        return this.balance;
    }

    public Integer getId() {
        return this.identifier;
    }

    public void addTransaction(Transaction operation) {
        this.arrayTransList.setAddTransaction(operation);
    }

    public Transaction deleteTransactionUserList(UUID id)
                                                throws TransactionNotFoundException {
        Transaction operation = this.arrayTransList.deleteTransaction(id);
        return operation;
    }

    public TransactionsList getList() {
        return arrayTransList;
    }

    @Override
    public String toString() {
        return "id: " + this.identifier + ", name: "
                + this.name + ", balance: " + this.balance;
    }
}
