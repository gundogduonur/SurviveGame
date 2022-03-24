package com.survivegame;

public class Hero extends Character {

    private static  Hero INSTANCE;
    private int resourceDistance;

    private Hero(int hp, int attack, int position, String type,int resourceDistance) {
        super(hp, attack, position, type);
        this.resourceDistance=resourceDistance;
    }


    public static Hero getINSTANCE(int hp, int attack, int position, String type,int resourceDistance)
    {
        if(INSTANCE==null)
        {
            INSTANCE=new Hero(hp,attack,position,type,resourceDistance);
        }
        return INSTANCE;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "resourceDistance=" + resourceDistance +
                "} " + super.toString();
    }
}

