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
    boolean isReady;
    boolean isComplete;
    boolean isDeleted;
    String orderId;
    long total;
    String time;

    public Order(ArrayList<Item> itemArrayList, String ID,String token,Boolean isReady,Boolean isComplete, String orderId,boolean isDeleted, String time) {
        this.itemArrayList = itemArrayList;
        this.ID = ID;
        this.unique_id = token;
        this.isReady = isReady;
        this.isComplete = isComplete;
        this.orderId = orderId;
        this.isDeleted = isDeleted;
        total =0;
        this.time = time;
        for(int i=0;i<itemArrayList.size();i++){
            total = total+ (itemArrayList.get(i).getPrice()*itemArrayList.get(i).getQuantity());
        }
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

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(String orderBy) {
        OrderBy = orderBy;
    }

    public boolean isReady() {
        return isReady;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
