import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Program {
    static ArrayList<String> arrayList = new ArrayList<>();
    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            File fileFirst = new File(args[0]);
            File fileSecond = new File(args[1]);
            if (!fileFirst.exists())
                System.out.println("Error! The file in argument 1 does not exist!");
            else if (!fileSecond.exists())
                System.out.println("Error! The file in argument 2 does not exist!");
            else if (fileFirst.length() > 10 * 1024 * 1024) {
                System.out.println("Error! The file in 1 argument is more than 10 MB!");
            }
            else if (fileSecond.length() > 10 * 1024 * 1024) {
                System.out.println("Error! The file in argument 2 is larger than 10 MB!");
            }
            else {
                BufferedReader readerFirst = new BufferedReader(new FileReader(args[0]));
                String strFirst = "";
                String firstStr;
                while ((firstStr = readerFirst.readLine()) != null) {
                    strFirst = strFirst + " " + firstStr;
                }
                readerFirst.close();
                BufferedReader readerSecond = new BufferedReader(new FileReader(args[1]));
                String strSecond = "";
                String secondStr;
                while ((secondStr = readerSecond.readLine()) != null) {
                    strSecond = strSecond + " " + secondStr;
                }
                readerFirst.close();
                String dictionary = mainProgram(strFirst, strSecond);
                BufferedWriter writeFile = new BufferedWriter(new FileWriter("dictionary.txt"));
                writeFile.write(dictionary);
                writeFile.close();
            }
        }
        else
            System.out.println("Error! You must enter 2 arguments - the addresses of two files!");
    }

    public static String mainProgram(String nameA, String nameB) {
        String str = nameA.trim() + " " + nameB.trim();
        String[] arrayStr = str.split(" ");
        addDictionary(arrayStr);
        String[] strA = clearSpaceStr(nameA);
        String[] strB = clearSpaceStr(nameB);
        Integer[] vectorA = getVector(arrayList, strA);
        Integer[] vectorB = getVector(arrayList, strB);
        float numeratorAB = formulaNumerator(vectorA, vectorB);
        float denominatorAB = formulaDenominator(vectorA, vectorB);
        float similarity = numeratorAB / denominatorAB;
        int nmb =  (int)(similarity * 100);
        System.out.println("Similarity = " + (float)nmb/100);
        String strSimilarity = "";
        for (Integer b = 0; b < arrayList.size(); b++) {
            if (b != arrayList.size() - 1)
                strSimilarity = strSimilarity + arrayList.get(b) + " ";
            else
                strSimilarity = strSimilarity + arrayList.get(b);
        }
        return strSimilarity;
    }

    public static float formulaNumerator(Integer[] vectorA, Integer[] vectorB) {
        float sum = 0;
        for (Integer b = 0; b < arrayList.size(); b++) {
            sum = sum + (vectorA[b] * vectorB[b]);
        }
        return sum;
    }

    public static float formulaDenominator(Integer[] vectorA, Integer[] vectorB) {
        float sumA = 0;
        float sumB = 0;
        float sum = 0;
        for (Integer b = 0; b < arrayList.size(); b++) {
            sumA = sumA + (vectorA[b] * vectorA[b]);
            sumB = sumB + (vectorB[b] * vectorB[b]);
        }
        sum = (float) (Math.sqrt(sumA) * Math.sqrt(sumB));
        return sum;
    }

    public static void addDictionary(String[] arrayStr) {
        Integer a;
        for (Integer i = 0; i < arrayStr.length; i++) {
            a = i + 1;
            while (a < arrayStr.length) {
                if (arrayStr[a].equals(arrayStr[i]))
                    arrayStr[i] = " ";
                a++;
            }
            if (!arrayStr[i].equals(" ")) {
                if (!arrayStr[i].equals(""))
                    arrayList.add(arrayStr[i]);
            }
        }
    }

    public static Integer[] getVector(ArrayList<String> dictionary, String[] str) {
        Integer[] vector = new Integer[dictionary.size()];
        Arrays.fill(vector, 0);
        Integer a = 0;
        Integer b = 0;
        for (Integer i = 0; i < dictionary.size(); i++) {
            b = 0;
            while (b < str.length) {
                if (str[b].equals(dictionary.get(i))) {
                    vector[a] += 1;
                }
                b++;
            }
            a++;
        }
        return vector;
    }

    public static String[] clearSpaceStr(String str) {
        Integer i = 0;
        String[] array = str.trim().split(" ");
        for (Integer a = 0; a < array.length; a++) {
            if (!array[a].equals(" ")) {
                if (!array[a].equals("")) {
                    i++;
                }
            }
        }
        String[] clearSpaceStr = new String[i];
        Integer b = 0;
        for (Integer a = 0; a < array.length; a++) {
            if (!array[a].equals(" ")) {
                if (!array[a].equals("")) {
                    clearSpaceStr[b] = array[a];
                    b++;
                }
            }
        }
        return clearSpaceStr;
    }
}
