package src.ex00;

public class Program {
    public static void main(String[] args) {
        if (args.length == 1) {
            String[] str = args[0].split("=");
            if (str[0].equals("--count")) {
                if (str[1].matches("-?\\d+")) {
                    Object lock = new Object();
                    Integer count = Integer.parseInt(str[1]);
                    if (count > 0 ) {
                        Integer i = 0;
                        Runnable egg = new Egg(count);
                        Runnable hen = new Hen(count);
                        Thread first = new Thread(egg);
                        Thread second = new Thread(hen);
                        first.start();
                        second.start();
                        while (true) {
                            if (!first.isAlive() && !second.isAlive()) {
                                while (i < count) {
                                    System.out.println("Human");
                                    i++;
                                }
                                break;
                            }
                        }
                    } else
                        System.out.println("Error! Please enter a " +
                                "number greater than zero!");
                } else
                    System.out.println("Error! Enter the number!");
            }
            else
                System.out.println("Error! Enter --count=number to " +
                        "run the program!");
        }
        else
            System.out.println("Error! Enter --count=number to run " +
                    "the program!");
    }
}
