package be.howest.davyloose.opdrachtwagens.models;

/**
 * Created by davy on 6/07/2017.
 */

public class Brand {
    public int id;
    public String name;

    public Brand(int ID, String name) {
        this.id = ID;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
