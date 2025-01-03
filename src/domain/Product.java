package domain;

import java.io.Serializable;

public class Product extends Entity implements Serializable {
    private String name;
    private String category;
    private int price;
    private static int count = 99;

    public Product(String name, String category, int price) {
        super(++count);
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Product(int id, String name, String category, int price) {
        super(id);
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product: " + "id=" + id + ", name='" + name + "', category='" + category + "', price=" + price;
    }

    @Override
    public String toFileString() {
        return id + "," + name + "," + category + "," + price;
    }
}
