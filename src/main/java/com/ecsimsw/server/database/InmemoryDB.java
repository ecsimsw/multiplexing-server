package com.ecsimsw.server.database;

public class InmemoryDB {

    private static final Long DB_ACCESS_TIME = 3000L;

    private static int userCount = 0;

    public static int select() {
        try {
            Thread.sleep(DB_ACCESS_TIME);
        } catch (InterruptedException ignored) {
        }

        return userCount;
    }

    public static void up() {
        userCount++;
    }

    public static void update(int updatedCount) {
        userCount = updatedCount;
    }

    public static void delete() {
        userCount = 0;
    }
}
