package com.ecsimsw.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MySocket extends Socket implements Closeable {

    private final Socket socket;

    public MySocket(Socket socket) {
        this.socket = socket;
    }

    public String receive() throws IOException {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            final String message = in.readLine();
            System.out.println("Message from client : " + message);
            return message;
        }
    }

    public void send(String message) throws IOException {
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {
            out.write(message);
            System.out.println("Send message to client : " + message);
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
