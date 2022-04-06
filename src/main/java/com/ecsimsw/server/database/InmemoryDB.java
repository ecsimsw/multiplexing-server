package com.ecsimsw.server.database;

public class InmemoryDB {

    private static int userCount = 0;

    public static int select() {
        return userCount;
    }

    public static void update(int updatedCount) {
        userCount = updatedCount;
    }

    public static void delete() {
        userCount = 0;
    }
}
