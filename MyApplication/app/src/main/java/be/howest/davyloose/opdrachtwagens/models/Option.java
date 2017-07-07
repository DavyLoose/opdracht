package be.howest.davyloose.opdrachtwagens.models;

/**
 * Created by davy on 6/07/2017.
 */

public class Option {
    private int id;
    private Integer carID;
    private String option;
    private  String categorie;
    private double price;

    public Option(int id, Integer carID, String option, double price,String categorie) {
        this.id = id;
        this.carID = carID;
        this.option = option;
        this.categorie = categorie;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCarID() {
        return carID;
    }

    public void setCarID(Integer carID) {
        this.carID = carID;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return option + " "+ price+ "€";
    }
}
