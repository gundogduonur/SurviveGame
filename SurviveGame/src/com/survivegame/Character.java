package com.survivegame;

public abstract class Character{
    private int hp;
    private int attack;
    private int position;
    private String type;


    public Character(int hp, int attack, int position, String type) {
        this.hp = hp;
        this.attack = attack;
        this.position = position;
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    @Override
    public String toString() {
        return
                "hp=" + hp +
                ", attack=" + attack +
                ", position=" + position +
                ", type='" + type + '\'' +
                '}';
    }
}
