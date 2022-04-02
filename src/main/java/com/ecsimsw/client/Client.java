package com.ecsimsw.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        try (Socket socket = new Socket(InetAddress.getLocalHost(), 8080)) {
            System.out.println("socket info : " + socket);

            String data = ">> Data from client <<";
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
            out.println(data);
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str2 = in.readLine();
            System.out.println("Message from server : " + str2);
        } catch (IOException e) {

        }
    }
}
