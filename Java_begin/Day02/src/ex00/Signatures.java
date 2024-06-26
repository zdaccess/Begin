import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.nio.file.*;

public class Signatures {
    public static void main(String[] args) throws IOException {
        Path signaturesTxt = Paths.get("signatures.txt");
        Path resultTxt = Paths.get("result.txt");
        if (signaturesTxt.toFile().exists()) {
            Integer flag = 0;
            String str = "";
            Map<String, String> map = addMap(signaturesTxt.toString());
            FileOutputStream outputStream = new FileOutputStream(resultTxt.toString());
            Scanner scanner = new Scanner(System.in);
            String path = scanner.next();
            while (!path.equals("42")) {
                flag = 0;
                str = "";
                Path file = Paths.get(path);
                if (file.isAbsolute() && file.toFile().exists() && file.toFile().isFile()) {
                    FileInputStream inputStream = new FileInputStream(file.toFile());
                    byte[] bytes = new byte[8];
                    inputStream.read(bytes);
                    String value = "";
                    String key = "";
                    for (byte b : bytes) {
                        str = str + String.format("%02X ", b);
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            value = entry.getValue();
                            key = entry.getKey() + "\n";
                            if (value.equals(str.trim())) {
                                flag = 1;
                                break;
                            }
                        }
                        if (flag == 1)
                            break;
                    }
                    if (flag == 0) {
                        System.out.println("UNDEFINED");
                        path = scanner.next();
                    } else {
                        outputStream.write(key.getBytes());
                        System.out.println("PROCESSED");
                        inputStream.close();
                        path = scanner.next();
                    }
                } else {
                    System.out.println("Error! The file either does not exist or you entered a non-absolute file path!");
                    path = scanner.next();
                }
            }
            outputStream.close();
        } else {
            System.out.println("Error! The file called signatures.txt is missing from the folder!");
        }
    }

    public static Map<String, String> addMap (String filePathSignatures) throws IOException {
        Map<String, String> map = new HashMap<>();
        FileInputStream inputStreamTxt = new FileInputStream(filePathSignatures);
        int i;
        String format = "";
        String hex = "";
        Integer flag = 0;
        while ((i = inputStreamTxt.read()) != -1) {
            if ((char) i != ',' && flag == 0)
                format += (char) i;
            else if ((char) i == ',')
                flag = 1;
            else if ((char) i == ' ' && flag == 1)
                flag = 2;
            else if (flag == 2)
                hex += (char) i;
            if (i == '\n') {
                map.put(format, hex.trim());
                format = "";
                hex = "";
                flag = 0;
            }
        }
        map.put(format, hex.trim());
        inputStreamTxt.close();
        return map;
    }
}

