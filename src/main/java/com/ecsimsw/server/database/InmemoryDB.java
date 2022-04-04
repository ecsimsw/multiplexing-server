package com.ecsimsw.server.database;

import static com.ecsimsw.server.ServerConfig.DB_ACCESS_TIME;

public class InmemoryDB {

    private static int USER_COUNT = 0;

    public static int select() {
        try {
            Thread.sleep(DB_ACCESS_TIME);
        } catch (InterruptedException ignored) {}

        return USER_COUNT;
    }

    public static void up() {
        USER_COUNT++;
    }

    public static void update(int updatedCount) {
        USER_COUNT = updatedCount;
    }

    public static void delete() {
        USER_COUNT = 0;
    }
}
