package src.ex02;

public class User {
    private Integer identifier;
    private String  name;
    private Integer balance;

    public User(String name, Integer balance) {
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
        return name;
    }

    public Integer getBalance() {
        return balance;
    }

    public Integer getId() {
        return identifier;
    }

    @Override
    public String toString() {
        return ("id: " + identifier + ", name: " + this.name
                + ", balance: " + this.balance);
    }
}
