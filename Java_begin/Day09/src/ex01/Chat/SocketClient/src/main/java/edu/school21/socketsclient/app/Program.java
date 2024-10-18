package edu.school21.socketsclient;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        if (args.length == 1) {
            String[] splitArgs = args[0].split("=");
            if (splitArgs[0].equals("--server-port") && splitArgs.length == 2) {
                if (splitArgs[1].matches("\\d+")) {
                    Client client = new Client(Integer.parseInt(splitArgs[1]));
                    try {
                        client.startClient();
                        System.exit(0);
                    } catch (IOException ignored) {
                    } catch (NullPointerException e) {
                        System.out.println("Error! Server unavailable: "
                                + e.getMessage());
                    }
                } else {
                    System.out.println("Error! You must enter a port number!");
                }
            } else {
                System.out.println("Error! Enter --server-port=[number] of" +
                        " server. Example --server-port=5567");
            }
        } else {
            System.out.println("Error! Enter --server-port=[number] of" +
                    " server. Example --server-port=5567");
        }
    }
}

