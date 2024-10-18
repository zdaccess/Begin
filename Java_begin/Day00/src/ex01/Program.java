import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        int j = 0;
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        if (n <= 1) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        scan.close();
        for (int i = 1; i * i <= n; i++) {
           if(n % i == 0 && j != 0 ) {
                System.out.println("false " + j);
                System.exit(0);
            }
            j++;
        }
        System.out.println("true " + j);
    }
}