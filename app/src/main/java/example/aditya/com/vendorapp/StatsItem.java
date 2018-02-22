package example.aditya.com.vendorapp;

/**
 * Created by aditya on 2/6/2018.
 **/

public class StatsItem {
    String name;
    long quantity;
    long price;
    long id;

    public StatsItem(String name, long quantity, long price, long id) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StatsItem{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", id=" + id +
                '}';
    }
}
