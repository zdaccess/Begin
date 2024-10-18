package edu.school21.socketsclient;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int port;
    private Scanner scan;

    public Client (int port) {
        this.port = port;
    }

    public void stopClient() throws IOException {
        in.close();
        out.close();
        socket.close();
        System.exit(0);
    }

    public void startClient () throws IOException {
        try {
            socket = new Socket("localhost", port);
            scan = new Scanner(new InputStreamReader(System.in));
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            out = new PrintWriter(socket.getOutputStream(), true);
            String clientMessage;
            String serverMessage;
            while (true) {
                socket.setSoTimeout(5000);
                serverMessage = in.readLine();
                System.out.println(serverMessage);
                if (serverMessage.equals("Successful!")) {
                    stopClient();
                    break;
                }
                if (scan.hasNextLine()) {
                    clientMessage = scan.nextLine();
                    out.println(clientMessage);
                    out.flush();
                } else {
                    stopClient();
                    break;
                }
            }
        } catch (RuntimeException | ConnectException e) {
            System.out.println("Error! Server unavailable: " + e.getMessage());
        }catch (IOException e) {
            System.out.println("Error! Server unavailable: " + e.getMessage());
        } finally {
            stopClient();
        }
    }
}