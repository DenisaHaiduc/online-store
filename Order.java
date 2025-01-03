package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Order extends Entity implements Serializable {
    private List<Product> orders;
    private Date date;
    private static int orderId = 99;

    public Order(List<Product> products, Date date) {
        super(++orderId);
        this.orders = products;
        this.date = date;
    }

    public Order(int id, List<Product> products, Date date) {
        super(id);
        this.orders = products;
        this.date = date;
    }

    public List<Product> getOrders() {
        return orders;
    }

    public void setOrders(List<Product> orders) {
        this.orders = orders;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comanda: " + "id=" + this.id + ", produse=" + this.orders.toString() + ", data=" + this.date;
    }

    public String toFileString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append(this.id).append("|");
        for (Product product : this.orders) {
            sb.append(product.getId()).append(",").append(product.getName()).append(",").append(product.getCategory()).append(",").append(product.getPrice()).append(";");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("|").append(sdf.format(this.date));
        return sb.toString();
    }
}
