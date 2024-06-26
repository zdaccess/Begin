import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        String str;
        int j = 0;
        int[] temp1 = new int[999];
        int[] temp2 = new int[999];
        int q = 0;
        int count = 0;
        int flag = 0;
        int flagCheck = 0;
        int lenght = 0;
        int[] memory = new int[999];
        Scanner scan = new Scanner(System.in);
        str = scan.next();
        if (str.length() > 999) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        scan.close();
        lenght = str.length();
        char[] smb = str.toCharArray();

        int[][] sort = new int[999][2];
        while (q < lenght) {
            j = flag;
            if (sort[q][0] != (int) ' ') {
                if ((int) smb[j] > 65535)
                {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
                sort[q][0] = (int) smb[j];
            }
            else
                continue;
            flagCheck = 0;
            count = 0;

            while (j < lenght)
            {
                if ((smb[j] != (char) sort[q][0]) && flagCheck == 0) {
                    flag = j;
                    flagCheck++;
                }
                if (sort[q][0] == (int) smb[j]) {
                    count++;
                    smb[j] = ' ';
                }
                j++;
            }
            if (sort[q][0] != (int) ' ')
                sort[q][1] = (int) (count);
            q++;
        }
        q = 0;
        int i = 0;
        while (i < lenght)
        {
            q = 0;
            while (q < lenght - 1) {
                if (sort[q][1] < sort[q + 1][1]) {
                    temp1 = sort[q];
                    sort[q] = sort[q + 1];
                    sort[q + 1] = temp1;
                }
                q++;
            }
            i++;
        }
        q = 0;
        i = 0;
        count = 0;
        while (i < lenght)
        {
            q = 0;
            while (q < lenght - 1) {
                if (sort[q][0] == sort[q + 1][0]) {
                    if (sort[q][0] > sort[q + 1][0]) {
                        temp2 = sort[q];
                        sort[q] = sort[q + 1];
                        sort[q + 1] = temp2;
                    }
                }
                if (sort[q + 1][0] == ' ' || sort[q + 1][1] == 0) {
                    break;
                }
                q++;
            }
            i++;
        }
        j = 0;
        i = 0;
        int[][] sort2 = new int[999][999];
        while (i < 12) {
            j = 0;
            while (j < q + 1) {
                if (i == 0) {
                    sort2[i][j] = sort[j][0];
                } else if ((int) (sort[j][1] / ((float) sort[0][1] / 10)) == 0 && i == 1 || (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 1 && i == 2 || (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 2 && i == 3 ||
                        (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 3 && i == 4 || (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 4 && i == 5 ||
                        (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 5 && i == 6 || (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 6 && i == 7 ||
                        (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 7 && i == 8 || (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 8 && i == 9 ||
                        (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 9 && i == 10 ||
                        (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 10 && i == 11) {
                    sort2[i][j] = (int) (-1 * sort[j][1]);
                    if (sort2[i][j] / 10 == 0)
                        memory[j] = 1;
                    else if ((sort2[i][j] / 10) / 10 == 0)
                        memory[j] = 2;
                    else
                        memory[j] = 3;
                } else if ((int) (sort[j][1] / ((float) sort[0][1] / 10)) == 0 && i > 1 || (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 1 && i > 2 || (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 2 && i > 3 ||
                        (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 3 && i > 4 || (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 4 && i > 5 ||
                        (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 5 && i > 6 || (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 6 && i > 7 ||
                        (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 7 && i > 8 || (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 8 && i > 9 ||
                        (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 9 && i > 10 ||
                        (int) (sort[j][1] / ((float) sort[0][1] / 10)) == 10 && i > 11) {
                    sort2[i][j] = (int) ' ';
                } else {
                    sort2[i][j] = (int) '#';
                }
                j++;
            }
            i++;
        }
        i--;
        while (i != -1 ) {
            j = 0;
            while (j < 9 + 1) {
                if (sort2[i][j] < 0) {
                    System.out.print(-1 * sort2[i][j]);
                    System.out.print(" ");
                }
                else if (sort2[i][j] > 0)
                {
                    System.out.print((char)sort2[i][j]);
                    if (memory[j] == 1)
                        System.out.print(" ");
                    else if (memory[j] == 2)
                        System.out.print("  ");
                    else
                        System.out.print("   ");
                }
                j++;
            }
            System.out.print("\n");
            i--;
        }
    }
}
