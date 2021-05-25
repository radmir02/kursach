package com.kursovaya.kursachapp;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Pet {
    private static final int SHOT_COST = 100;
    private String name;
    private int age;
    private int hunger;
    private int thirst;
    private int health;
    private int death;
    private int happy;
    private int bonus;
    private int ageTime;
    private int awayTime;
    private int randomEvent;
    private int randomEventPercentage;
    private String creature;
    private ArrayList<String> events;

    public String getName(){return name;}
    public int getAge(){return age;}
    public int getAgeTime(){return ageTime;}
    public int getHunger(){return hunger;}
    public int getThirst(){return thirst;}
    public int gethealth(){return health;}
    public int getDeath(){return death;}
    public int getHappy(){return happy;}
    public int getBonus(){return  bonus;}
    public int getRandomEvent(){return randomEventPercentage;}
    public String getCreature(){return creature;}

    public Pet(){}

    public Pet(String newName, int newAge, int newHunger, int newThirst, int newHealth,
               int newDeath, int newHappy, int newAgeTime, int newAwayTime, int newRandomEventPercentage, String newCreature){
        name = newName;
        age = newAge;
        hunger = newHunger;
        thirst = newThirst;
        health = newHealth;
        death = newDeath;
        happy = newHappy;
        ageTime = newAgeTime;
        awayTime = newAwayTime;
        randomEventPercentage = newRandomEventPercentage;
        events = new ArrayList<>();
        creature = newCreature;

        // Fell down stairs 0
        events.add(name + " Упал по дороге! Причинил себе увечия, счастье (–10) и здоровье (–15)");

        // Received roses 1
        events.add(name + "Дошел без проишествий, счастье (+15)");

        // Got picked on 2
        events.add(name + " Вчера съел что-то просроченное, счастье (–10)");

        // Got in a fight 3
        events.add(name + " Подрался с другим питомцем! счастье (-10) и здоровье (-15)");


    }
    public void updateRandomEvent(){
        randomEventPercentage--;
        if(randomEventPercentage <= 0){
            randomEventPercentage = 0;
        }
    }
    public void updateHunger(int calories, boolean treat){
        if(hunger + calories > 100) {
            hunger = 100;
        }
        else if(hunger + calories <= 0){
            hunger = 0;
            updateHealth(calories);
        }
        else{
            if(treat){
                hunger += calories;
                updateHappy(5, false);
                updateHealth(-2);
            }
            else{
                hunger += calories;
            }
        }
    }

    public void updateThirst(int water){
        if(thirst + water > 100)
            thirst = 100;
        else if(thirst + water <= 0) {
            thirst = 0;
            updateHealth(water);
        }
        else
            thirst += water;
    }

    public void updateHealth(int heal){
        if(health + heal > 100)
            health = 100;
        else if(health + heal <= 0) {
            updateDeath(heal);
            health = 0;
        }
        else
            health += heal;
    }
    public void updateDeath(int major){
        major = abs(major);
        if(death + major > 100) {
            death = 100;
        }
        else if(death + major <= 0){
            death = 0;
        } else {
            death += major;
        }
    }

    public void updateHappy(int gameCost, boolean isShot){
        if(happy + gameCost > 100){
            happy = 100;
        } else if(happy + gameCost <= 0){
            if(!isShot)
                updateHealth(-(abs(happy + gameCost)));
            happy = 0;
        } else {
            happy += gameCost;
        }
    }

    public void giveShot(){
        updateHealth(SHOT_COST);
        updateHappy(-60, true);
    }

    public void updateAwayTime(int time){
        // 1 minutes
        if((time - awayTime) / 60 > 0){
            int hoursAway = (time - awayTime) / 60;
            int hoursAwayHealth = (time - awayTime) / 120;
            updateHunger(-hoursAway, false);
            updateThirst(-hoursAway);
            updateHappy(-hoursAway, false);
            updateHealth(-hoursAwayHealth);
        }
        awayTime = time;
    }

    public boolean ageUp(int time){
        if(age <= 100){
        if(time - ageTime >= 300){
            age += (time - ageTime) / 300;
            ageTime = time;
            return true;
        }
        }
        return false;
    }

    public boolean isDead(){
        if(death >= 100)
            return true;
        return false;
    }
    public void play(){
        if(happy < 98){
        updateHappy(2, false);

        }
    }

    public String randomEvent(){
        randomEvent = abs((int) (Math.random() * events.size()) - 1);
        if(randomEvent == 0){
            updateHappy(-10, false);
            updateHealth(-15);
        } else if(randomEvent == 1){
            updateHappy(15, false);
        } else if(randomEvent == 2){
            updateHappy(-10, false);
        }
        return events.get(randomEvent);
    }
}
