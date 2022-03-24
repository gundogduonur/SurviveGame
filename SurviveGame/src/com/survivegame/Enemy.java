package com.survivegame;



public class Enemy extends Character {
    private String name;
    public Enemy(int hp, int attack, int position,String name) {

        super(hp, attack, position,"Enemy");
        this.name= name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  name  +
                ":" + super.toString();
    }
}
