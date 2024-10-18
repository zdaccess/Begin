package edu.school21.socketsclient;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static BufferedReader   in;
    private static Scanner          reader;
    private static PrintWriter      out;
    private static Writer           write;
    private int                     port;

    public Client (int port) {
        this.port = port;
    }

    public static void stopClient(Integer nmb) throws IOException {
        reader.close();
        in.close();
        out.close();
        System.exit(nmb);
    }

    public void startClient () throws IOException {
        try {
            Socket socket = new Socket("localhost", port);
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            reader = new Scanner(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
            write = new Writer(reader, out);
            String serverMessage;
            while (true) {
                try {
                    serverMessage = in.readLine();
                    if (serverMessage == null) {
                        write.closeWriter();
                        stopClient(-1);
                    }
                    System.out.println(serverMessage);
                } catch (IOException e) {
                    System.out.println("Error! The server is disabled: "
                            + e.getMessage());
                    write.closeWriter();
                    stopClient(-1);
                    break;
                }
            }
        } catch (RuntimeException | ConnectException e) {
            write.closeWriter();
            stopClient(1);
            System.out.println("Error! Server unavailable: " + e.getMessage());
        } catch (IOException e) {
            write.closeWriter();
            stopClient(1);
            System.out.println("Error! Server unavailable: " + e.getMessage());
        } finally {
            write.closeWriter();
            stopClient(0);
        }
    }
}