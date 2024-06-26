package src.ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length == 1) {
            String[] str = args[0].split("=");
            if (str[0].equals("--count")) {
                if (str[1].matches("-?\\d+")) {
                    Integer count = Integer.parseInt(str[1]);
                    Object flag = new Object();
                    if (count > 0 ) {
                        Integer i = 0;
                        Runnable egg = new Egg(count, flag);
                        Runnable hen = new Hen(count, flag);
                        Thread first = new Thread(egg);
                        Thread second = new Thread(hen);
                        second.start();
                        first.start();
                    } else
                        System.out.println("Error! Please enter " +
                                "a number greater than zero!");
                } else
                    System.out.println("Error! Enter the number!");
            }
            else
                System.out.println("Error! Enter --count=number " +
                        "to run the program!");
        }
        else
            System.out.println("Error! Enter --count=number to" +
                    " run the program!");
    }
}
