import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        int j = 0;
        int sum = 0;
        int flag = 0;
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        while (true) {
            if (n < 0)
                n *= -1;
            sum = 0;
            if (n == 42)
                break;
            while (n > 0) {
                sum = sum + n % 10;
                n = n / 10;
            }
            j = 0;
            for (int i = 1; ; i++) {
                if (sum % i == 0) {
                    j++;
                }
                if (sum == i) {
                    if (j < 3) {
                        flag++;
                    }
                    break;
                }
            }
            n = scan.nextInt();
        }
        scan.close();
        System.out.println("Count of coffee-request " + '-' + " " + flag);
    }
}