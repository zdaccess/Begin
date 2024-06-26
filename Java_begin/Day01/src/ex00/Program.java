package src.ex00;

public class Program {
    public static void main(String[] args) {
        User John = new User("John", 4000);
        User Mike = new User("Mike", 80000);
        System.out.println(John);
        System.out.println(Mike);
        Transaction operation1 = new Transaction(John,
                Mike, Transaction.Operation.CREDIT, -500);
        System.out.println(operation1);
        System.out.println(John);
        System.out.println(Mike);
        Transaction operation2 = new Transaction(Mike,
                John, Transaction.Operation.DEBIT, 500);
        System.out.println(operation2);
        System.out.println(Mike);
        System.out.println(John);

    }
}
