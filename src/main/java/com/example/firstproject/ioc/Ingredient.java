package com.example.firstproject.ioc;

public abstract class Ingredient {
    private  String name;

    public String getName() {
        return name;
    }

    public Ingredient(String name) {
        this.name = name;
    }
}
