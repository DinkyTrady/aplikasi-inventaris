package com.inven.models;

public class User {
    private static String currentUser;
    private static String roleUser;

    public static void setCurrentUser(String username) {
        currentUser = username;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public static void setRoleUser(String role) {
        roleUser = role;
    }

    public static String getRoleUser() {
        return roleUser;
    }
}
