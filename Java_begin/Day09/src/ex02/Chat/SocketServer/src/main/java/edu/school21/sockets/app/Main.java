package edu.school21.sockets;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1) {
            String[] splitArgs = args[0].split("=");
            if (splitArgs[0].equals("--port")) {
                if (splitArgs[1].matches("\\d+")) {
                    try {
                        Server server = new Server();
                        server.run(Integer.parseInt(splitArgs[1]));
                    } catch (IOException | NumberFormatException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Error! You must enter a port number!");
                }
            } else {
                System.out.println("Error! Enter --port=[number] of server. " +
                        "Example --port=5567");
            }
        } else {
            System.out.println("Error! Enter --port=[number] of server. " +
                    "Example --port=5567");
        }
    }
}
