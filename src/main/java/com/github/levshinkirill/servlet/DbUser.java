package com.github.levshinkirill.servlet;

public class DbUser {

    public DbUser() {

    }

    public DbUser(String id,String name,String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String name;
    public String password;
    public String id;

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getId() {
        return this.id;
    }

}
