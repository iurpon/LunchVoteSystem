package ru.firstproject;

public class AuthorizedUser {
    private static int id ;
    public static int id() {
        return 100000;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }
}
