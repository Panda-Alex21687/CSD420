package com.baldree.mod10;

public class Fan {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String favoriteTeam;

    public Fan(int id, String firstName, String lastName, String favoriteTeam) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.favoriteTeam = favoriteTeam;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFavoriteTeam() {
        return favoriteTeam;
    }
}