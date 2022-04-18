package model;

public class DnDClass {
    private int classID;
    private String name;
    private int level;

    public DnDClass(int classID, String name, int level) {
        this.classID = classID;
        this.name = name;
        this.level = level;
    }

    public int getClassID() { return classID; }
    public void setClassID(int classID) { this.classID = classID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}
