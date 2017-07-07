package be.howest.davyloose.opdrachtwagens.models;

/**
 * Created by davy on 6/07/2017.
 */

public class Categorie {
    private int id;
    private String name;

    public Categorie(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
