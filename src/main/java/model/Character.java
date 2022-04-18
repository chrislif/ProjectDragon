package model;

import java.util.ArrayList;

public class Character {
    private int characterID;
    private String name;
    private String race;
    private int healthMax;
    private int currentHealth;
    private Account account;
    private CharacterStat stat;
    private ArrayList<DnDClass> dndClassList;

    public Character() {}

    public Character(int characterID, String name, ArrayList<DnDClass> dndClassList) {
        this.characterID = characterID;
        this.name = name;
        this.dndClassList = dndClassList;
    }

    public Character(int characterID, String name, String race, int healthMax, int currentHealth, Account account, CharacterStat stat, ArrayList<DnDClass> dndClassList) {
        this.characterID = characterID;
        this.name = name;
        this.race = race;
        this.healthMax = healthMax;
        this.currentHealth = currentHealth;
        this.account = account;
        this.stat = stat;
        this.dndClassList = dndClassList;
    }

    public int getCharacterID() { return characterID; }
    public void setCharacterID(int characterID) { this.characterID = characterID; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRace() { return race; }
    public void setRace(String race) { this.race = race; }

    public int getHealthMax() { return healthMax; }
    public void setHealthMax(int healthMax) { this.healthMax = healthMax; }

    public int getCurrentHealth() { return currentHealth; }
    public void setCurrentHealth(int currentHealth) { this.currentHealth = currentHealth; }

    public CharacterStat getCharacterStat() { return stat;}
    public void setCharacterStat(CharacterStat stat) { this.stat = stat; }

    public ArrayList<DnDClass> getDnDClassList() { return dndClassList; }
    public void setDnDClassList(ArrayList<DnDClass> classList) { this.dndClassList = dndClassList; }
}
