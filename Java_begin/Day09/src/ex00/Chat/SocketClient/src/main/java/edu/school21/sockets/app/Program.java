package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;
import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        if (args.length == 1) {
            String[] splitArgs = args[0].split("=");
            if (splitArgs[0].equals("--port") && splitArgs.length == 2) {
                if (splitArgs[1].matches("\\d+")) {
                    Client client = new Client(Integer.parseInt(splitArgs[1]));
                    try {
                        client.startClient();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Ошибка!");
                }
            } else {
                System.out.println("Ошибка!");
            }
        } else {
            System.out.println("Ошибка!");
        }
    }
}

