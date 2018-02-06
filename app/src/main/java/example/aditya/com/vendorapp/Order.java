package example.aditya.com.vendorapp;

import java.util.ArrayList;

/**
 * Created by aditya on 2/6/2018.
 */

public class Order {
    ArrayList<Item> itemArrayList;
    String ID;

    public Order(ArrayList<Item> itemArrayList, String ID) {
        this.itemArrayList = itemArrayList;
        this.ID = ID;
    }

    public ArrayList<Item> getItemArrayList() {
        return itemArrayList;
    }

    public void setItemArrayList(ArrayList<Item> itemArrayList) {
        this.itemArrayList = itemArrayList;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
