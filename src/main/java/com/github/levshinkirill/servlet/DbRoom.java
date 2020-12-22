package com.github.levshinkirill.servlet;

public class DbRoom {

    public DbRoom() {

    }

    public DbRoom(String id,String name,String userName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }

    private String name;
    private String userName;
    private String id;

    public String getName() {
        return this.name;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getId() {
        return this.id;
    }

}
