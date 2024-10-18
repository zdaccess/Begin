import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        String[][] september = new String[32][3];
        String[] name = new String[10];
        String[][] month = new String[10][3];
        String[][] shedule = new String[40][4];
        String[][] days = new String[100][3];
        String dataName1;
        String dataName2;
        String dataName3;
        String dataName4;
        int i = 1;
        while (true) {
            september[i][0] = "TU";
            september[i][2] = i + "";
            i++;
            september[i][0] = "WE";
            september[i][2] = i + "";
            i++;
            if (i == 31)
                break;
            september[i][0] = "TH";
            september[i][2] = i + "";
            i++;
            september[i][0] = "FR";
            september[i][2] = i + "";
            i++;
            september[i][0] = "SA";
            september[i][2] = i + "";
            i++;
            september[i][0] = "SU";
            september[i][2] = i + "";
            i++;
            september[i][0] = "MO";
            september[i][2] = i + "";
            i++;
        }
        i = 1;

        int maxLenName = 0;
        String studentName;
        Scanner nameScan = new Scanner(System.in);
        studentName = nameScan.next();
        while (!studentName.equals(".") && i != 10) {
            name[i] = studentName;
            if (name[i].length() > maxLenName)
                maxLenName = name[i].length();
            i++;
            studentName = nameScan.next();
        }

        int nameLenght = i;
        i = 0;
        int j = 0;
        int flag = 0;
        dataName1 = nameScan.next();
        char[][] wer = new char[100][1];
        while (!dataName1.equals(".") && i != 10) {
            month[i][0] = dataName1;
            dataName2 = nameScan.next();
            month[i][1] = dataName2;
            wer[i] = month[i][0].toCharArray();
            if ((wer[i][0] <= '1' || wer[i][0] >= '7')
            || (!month[i][1].equals("TU") && !month[i][1].equals("WE")
            && !month[i][1].equals("TH") && !month[i][1].equals("FR")
            && !month[i][1].equals("SA") && !month[i][1].equals("SU")
            && !month[i][1].equals("MO")))
                i--;
            else {
                j = 0;
                flag = 0;
                while (j < i + 1) {
                    if (month[j][0].equals(month[i][0])
                    && month[j][1].equals(month[i][1])) {
                        flag++;
                        if (flag == 2) {
                            i--;
                            break;
                        }
                    }
                    j++;
                }
            }
            int t = 1;
            while (t < 8) {
                if (september[t][0].equals(month[i][1])) {
                    month[i][2] = september[t][2];
                    break;
                }
                t++;
            }
            i++;
            dataName1 = nameScan.next();
        }
        int monthLenght = i;
        int s = 0;
        dataName1 = nameScan.next();
        while (!dataName1.equals(".")) {
            shedule[s][0] =  dataName1;
            dataName2 = nameScan.next();
            shedule[s][1] =  dataName2;
            dataName3 = nameScan.next();
            shedule[s][2] =  dataName3;
            dataName4 = nameScan.next();
            shedule[s][3] =  dataName4;
            j = 0;
            flag = 0;
            while (j < s + 1) {
                if (shedule[j][0].equals(shedule[s][0])
                && shedule[j][1].equals(shedule[s][1])
                && shedule[j][2].equals(shedule[s][2])
                && shedule[j][3].equals(shedule[s][3])) {
                    flag++;
                    if (flag == 2) {
                        s--;
                        break;
                    }
                }
                j++;
            }
            s++;
            dataName1 = nameScan.next();
        }
        nameScan.close();
        int sheduleLenght = s;
        j = 0;
        int q;
        String[] temp = new String[10];
        char[][] wer2 = new char[100][4];
        int moveToDay;
        int oneFlag = 0;
        int twoFlag = 0;
        while (j < s) {
            q = 0;
            while (q < s - 1) {
                moveToDay = 1;
                while (moveToDay < 31) {
                    if (september[moveToDay][2].equals(shedule[q][2]))
                        oneFlag = moveToDay;
                    moveToDay++;
                }
                moveToDay = 1;
                while (moveToDay < 31) {
                    if (september[moveToDay][2].equals(shedule[q + 1][2]))
                        twoFlag = moveToDay;
                    moveToDay++;
                }
                if (oneFlag > twoFlag) {
                    temp = shedule[q];
                    shedule[q] = shedule[q + 1];
                    shedule[q + 1] = temp;
                }
                q++;
            }
            j++;
        }

        j = 0;
        while (j < s) {
            q = 0;
            while (q < s - 1) {
                if (shedule[q][2].equals(shedule[q + 1][2])) {
                    wer2[q] = shedule[q][1].toCharArray();
                    wer2[q + 1] = shedule[q + 1][1].toCharArray();
                    if (((int) wer2[q][0] > (int) wer2[q + 1][0])) {
                        temp = shedule[q];
                        shedule[q] = shedule[q + 1];
                        shedule[q + 1] = temp;
                    }
                }
                q++;
            }
            j++;
        }
        j = 0;
        while (j < i) {
            q = 0;
            while (q < i - 1) {
                wer2[q] = month[q][0].toCharArray();
                wer2[q + 1] = month[q + 1][0].toCharArray();
                if ((int) wer2[q][0] > (int) wer2[q + 1][0]) {
                    temp = month[q];
                    month[q] = month[q + 1];
                    month[q + 1] = temp;
                }
                q++;
            }
            j++;
        }

        j = 0;
        i = monthLenght;
        while (j < i) {
            q = 0;
            while (q < i - 1) {
                wer2[q] = month[q][2].toCharArray();
                wer2[q + 1] = month[q + 1][2].toCharArray();
                if ((int) wer2[q][0] > (int) wer2[q + 1][0]) {
                    temp = month[q];
                    month[q] = month[q + 1];
                    month[q + 1] = temp;
                }
                q++;
            }
            j++;
        }
        i = 1;
        j = 0;
        q = 0;
        while (i < 31) {
            j = 0;
            while(j < monthLenght) {
                if (september[i][0].equals(month[j][1])) {
                    days[q][0] = month[j][0];
                    days[q][1] = september[i][0];
                    days[q][2] = i + "";
                    q++;
                }
                j++;
            }
            i++;
        }
        int daysLenght = q;
        i = 0;
        j = 0;
        s = 0;
        while (i < nameLenght) {
            if (name[i] == null) {
                q = 0;
                while(q < maxLenName) {
                    System.out.print(" ");
                    q++;
                }
            }
            else {
                System.out.print(name[i]);
                if (name[i].length() < maxLenName) {
                    q = 0;
                    while(q < maxLenName - name[i].length()) {
                        System.out.print(" ");
                        q++;
                    }
                }
            }
            j = 0;
            while (j < daysLenght) {
                if (name[i] == null)
                    System.out.print(days[j][0] +  ":00 " + days[j][1]
                            + "    " + days[j][2] + "|");
                else {
                    flag = 0;
                    s = 0;
                    while (s < sheduleLenght) {
                        if (name[i].equals(shedule[s][0])
                        && days[j][0].equals(shedule[s][1])
                        && days[j][2].equals(shedule[s][2])
                        && shedule[s][3].equals("NOT_HERE")
                        && days[j][2].length() == 1) {
                            flag = 1;
                            System.out.print("          -1|");
                            break;
                        }
                        else if (name[i].equals(shedule[s][0])
                        && days[j][0].equals(shedule[s][1])
                        && days[j][2].equals(shedule[s][2])
                        && shedule[s][3].equals("NOT_HERE")
                        && days[j][2].length() > 1) {
                            flag = 1;
                            System.out.print("           -1|");
                            break;
                        }
                        else if (name[i].equals(shedule[s][0])
                        && days[j][0].equals(shedule[s][1])
                        && days[j][2].equals(shedule[s][2])
                        && shedule[s][3].equals("HERE")
                        && days[j][2].length() == 1) {
                            flag = 1;
                            System.out.print("           1|");
                            break;
                        }
                        else if (name[i].equals(shedule[s][0])
                        && days[j][0].equals(shedule[s][1])
                        && days[j][2].equals(shedule[s][2])
                        && shedule[s][3].equals("HERE")
                        && days[j][2].length() > 1) {
                            flag = 1;
                            System.out.print("            1|");
                            break;
                        }
                        s++;
                    }
                }
                if (flag == 0 && days[j][2].length() > 1)
                    System.out.print("             |");
                else if (flag == 0)
                    System.out.print("            |");
                j++;
            }
            System.out.print("\n");
            i++;
        }
    }
}
