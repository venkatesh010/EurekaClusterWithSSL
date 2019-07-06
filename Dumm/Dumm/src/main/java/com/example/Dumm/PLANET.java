package com.example.Dumm;

public enum PLANET {

    MERCURY(10,20),
    EARTH(10,30),
    MARS(4,10);


    int mass;
    int radius;


    PLANET(int mass, int radius){
        this.mass = mass;
        this.radius=radius;

    }


    int calculateWeight(int mass){
        return mass*10;
    }


}
