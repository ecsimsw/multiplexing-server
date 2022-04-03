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

    private final BufferedReader in;
    private final BufferedWriter out;

    public MySocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public String receive() throws IOException {
        final String message = in.readLine();
        System.out.println("[RECV       ] : Message from client : " + message);
        return message;
    }

    public void send(String message) throws IOException {
        out.write(message);
        out.flush();
        System.out.println("[SEND       ] : Send message to client : " + message);
    }

    @Override
    public void close() throws IOException {
        socket.close();
        System.out.println("[CLOSE      ] : close socket");
    }
}
