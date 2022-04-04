package com.ecsimsw.server.socket;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
        final List<String> lines = new ArrayList<>();
        String tempLine;
        while (!(tempLine = in.readLine()).equals("")) {
            lines.add(tempLine);
        }
        final String message = String.join("\n", lines);

        System.out.println("[RECEIVE] : \n" + message);
        return message;
    }

    public void send(String message) throws IOException {
        out.write(message);
        out.flush();
        System.out.println("[SEND] : \n" + message);
    }

    @Override
    public void close() throws IOException {
        socket.close();
        in.close();
        out.close();
        System.out.println("[CLOSE] : close socket");
    }
}
