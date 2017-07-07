package be.howest.davyloose.opdrachtwagens.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by davy on 6/07/2017.
 */

public class Car implements Parcelable {
    private int id;
    private int brandID;
    private String name;
    private double price;

    public Car(int id, int brandID, String name, double price) {
        this.id = id;
        this.brandID = brandID;
        this.name = name;
        this.price = price;
    }

    protected Car(Parcel in) {
        id = in.readInt();
        brandID = in.readInt();
        name = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return  name + " price " + price +"€";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(brandID);
        dest.writeString(name);
        dest.writeDouble(price);
    }
}
