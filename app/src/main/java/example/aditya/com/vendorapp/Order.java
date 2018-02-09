package example.aditya.com.vendorapp;

import java.util.ArrayList;

/**
 * Created by aditya on 2/6/2018.
 */

public class Order {
    ArrayList<Item> itemArrayList;
    String ID;
    String unique_id;
    String OrderBy;

    public Order(ArrayList<Item> itemArrayList, String ID,String token) {
        this.itemArrayList = itemArrayList;
        this.ID = ID;
        this.unique_id = token;
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

    public String getUniqueId() {
        return unique_id;
    }

    public void setUniqueId(String token) {
        this.unique_id = token;
    }

    @Override
    public String toString() {
        return "Order{" +
                "itemArrayList=" + itemArrayList.toString() +
                ", ID='" + ID + '\'' +
                ", token='" + unique_id + '\'' +
                '}';
    }
}
