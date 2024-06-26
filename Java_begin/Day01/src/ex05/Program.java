package src.ex05;

public class Program {
    public static void main(String[] args) {
        try {
            Menu start = null;
            if (args[0].equals("--profile=dev")) {
                start = new Menu("dev");
            } else if (args[0].equals("--profile=production"))
                start = new Menu("production");
            else
                System.out.println("Error! You must enter " +
                        "\"--profile=dev\" or \"--profile=production\"");
        } catch (ArrayIndexOutOfBoundsException | TransactionNotFoundException
                 | IllegalTransactionException e) {
            System.out.println("Error! You must enter " +
                    "\"--profile=dev\" or \"--profile=production\"");
        }
    }
}
