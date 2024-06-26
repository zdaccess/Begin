import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        int test1 = 0;
        int test2 = 0;
        int test3 = 0;
        int test4 = 0;
        int test5 = 0;
        int test = 0;
        long arrowNumber;
        long duplicate;
        duplicate = 0;
        arrowNumber = 0;
        String weekText = "";
        int weekNumber = 1;
        int check = 0;
        Scanner scan = new Scanner(System.in);
        weekText = scan.nextLine();
        while (weekNumber != 19) {
            if (weekText.equals("42")) {
                break;
            }
            else if (!weekText.equals("Week " + weekNumber) ) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            int i = 0;
            while (i != 5) {

                if (!scan.hasNextInt()) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                } else {
                    test = scan.nextInt();
                    if (i == 0)
                        test1 = test;
                    else if (i == 1)
                        test2 = test;
                    else if (i == 2)
                        test3 = test;
                    else if (i == 3)
                        test4 = test;
                    else if (i == 4) {
                        test5 = test;
                        break;
                    }
                }
                i++;
            }
            if ((test1 < 1 || test1 > 9) || (test2 < 1 || test2 > 9) ||
                    (test3 < 1 || test3 > 9) || (test4 < 1 || test4 > 9) || (test5 < 1 || test5 > 9)) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            int minValue = 0;
            int minValue2 = 0;
            int minTestValue = 0;
            if (test1 < test2)
                minValue = test1;
            else
                minValue = test2;
            if (test3 < test4)
                minValue2 = test3;
            else
                minValue2 = test4;
            if (minValue2 < minValue)
                minTestValue = minValue2;
            else
                minTestValue = minValue;
            if (test5 < minTestValue)
                minValue = test5;
            else
                minValue = minTestValue;
            arrowNumber = (arrowNumber * 10) + minValue;
            weekNumber++;
            if (weekNumber != 19) {
                scan.nextLine();
                weekText = scan.nextLine();
            }
            else
                System.out.println();
        }
        scan.close();
        check = 0;
        duplicate = arrowNumber;
        long duplicate2 = 0;
        while (check < weekNumber-1) {
            duplicate = arrowNumber % 10;
            arrowNumber /= 10;
            duplicate2 = (duplicate2 * 10) + duplicate;
            check++;
        }
        check = 1;
        long duplicate3 = duplicate2;
        long nmb = 0;
        while(check < weekNumber)
        {
            System.out.print("Week " + check + " ");
            duplicate3 = duplicate2 % 10;
            duplicate2 /= 10;
            nmb = 0;
            while (nmb < duplicate3)
            {
                System.out.print("=");
                nmb++;
            }
            System.out.println(">");
            check++;
        }
    }
}