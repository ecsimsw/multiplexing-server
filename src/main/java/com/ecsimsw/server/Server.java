package com.ecsimsw.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server is opened with port[" + serverSocket.getLocalPort() + "]");
            Socket socket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            String messageFromClient = in.readLine();
            System.out.println("Message from client : " + messageFromClient);

            out.write(messageFromClient);
            System.out.println("Send message to client : " + messageFromClient);

            out.flush();
            in.close();
            socket.close();
        } catch (IOException e) {
        }
    }
}
