package org.acme.schooltimetabling.domain;

/*  
 * Credits: This file is built oriented with the TimeFold "Hello World" Quick Start Guide
 */

public class Room {

    private String name;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}