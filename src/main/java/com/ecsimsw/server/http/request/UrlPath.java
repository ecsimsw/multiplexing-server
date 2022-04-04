package com.ecsimsw.server.http.request;

import java.util.HashMap;
import java.util.Map;

public class UrlPath {

    private final String path;
    private final Map<String, String> queryParams;

    private UrlPath(String path, Map<String, String> queryParams) {
        this.path = path;
        this.queryParams = queryParams;
    }

    public static UrlPath of(String urlPath) {
        try {
            final String[] pathParams = urlPath.split("\\?");
            final String path = pathParams[0];
            final Map<String, String> queryParams = new HashMap<>();

            if(pathParams.length > 1) {
                final String queryParamsLine = pathParams[1];
                for(String tuple : queryParamsLine.split("&")) {
                    final String[] keyValue = tuple.split("=");
                    final String key = keyValue[0];
                    final String value = keyValue[1];
                    queryParams.put(key, value);
                }
            }
            return new UrlPath(path, queryParams);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid format of urlPath : "+ urlPath);
        }
    }

    public String getPath() {
        return path;
    }

    public String getQueryValue(String key) {
        return queryParams.get(key);
    }
}
