public class Program {
    public static void main(String[] args)
    {
        int sum = 0;
        int number = 479598;
        sum = sum + number % 10;
        number = number / 10;
        sum = sum + number % 10;
        number = number / 10;
        sum = sum + number % 10;
        number = number / 10;
        sum = sum + number % 10;
        number = number / 10;
        sum = sum + number % 10;
        number = number / 10;
        sum = sum + number % 10;
        System.out.println(sum);
    }
}