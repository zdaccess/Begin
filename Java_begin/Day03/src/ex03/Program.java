package src.ex03;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Program {
    public static void main(String[] args) throws IOException {
        Object flag = new Object();
        Path path = Paths.get("./src/ex03/files_urls.txt");

        if (args.length == 1) {
            String[] str = args[0].split("=");
            if (str[0].equals("--threadsCount") && str.length == 2) {
                if (str[1].matches("-?\\d+")) {
                    if (path.toFile().exists()) {
                        Integer threadCount = Integer.parseInt(str[1]);
                        FileData fileData = new FileData(path);
                        JobThreads[] runnable = new JobThreads[threadCount];
                        for (Integer i = 0; i < threadCount; i++) {
                            runnable[i] = new JobThreads(i, flag, fileData);
                            Thread thread = new Thread(runnable[i]);
                            thread.start();
                        }
                    } else
                        System.out.println("Error! The file files_urls.txt " +
                                "does not exist!");
                } else
                    System.out.println("Error! Enter the number of threads " +
                            "to run the program!");
            } else
                System.out.println("Error! Enter --threadsCount=number to " +
                        "run the program!");
        } else
            System.out.println("Error! Enter the number of threads to run " +
                    "the program! --threadsCount=number");
    }
}
