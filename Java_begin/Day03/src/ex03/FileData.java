package src.ex03;

import java.io.*;
import java.nio.file.Path;

public class FileData {
    private String[][]  data;

    public FileData(Path uri) throws IOException {
        BufferedReader readerFirst;
        readerFirst = new BufferedReader(new FileReader(uri.toFile()));
        String firstStr;
        Integer i = 0;
        while ((firstStr = readerFirst.readLine()) != null) {
            i++;
        }
        readerFirst.close();
        BufferedReader readerSecond;
        readerSecond = new BufferedReader(new FileReader(uri.toFile()));
        data = new String[i][4];
        String[] str;
        for (Integer j = 0; (firstStr = readerSecond.readLine()) != null; j++) {
            str = firstStr.split(" ");
            data[j][0] = str[0];
            data[j][1] = str[1];
            data[j][2] = "notuploaded";
        }
        readerSecond.close();
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }
}
