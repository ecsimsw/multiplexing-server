package com.ecsimsw.server.server.http.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResponseFile {

    private final String file;

    public ResponseFile(String file) {
        this.file = file;
    }

    public static ResponseFile of(String filePath) throws IOException {
        try {
            final InputStream inputStream = ResponseFile.class.getResourceAsStream(filePath);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            final StringBuilder sb = new StringBuilder();
            String tempLine;
            while ((tempLine = reader.readLine())!=null) {
                sb.append(tempLine + "\n");
            }
            return new ResponseFile(sb.toString());
        } catch (NullPointerException e) {
            throw new IOException("filePath : " + filePath);
        }
    }

    public String asString() {
        return file;
    }
}
