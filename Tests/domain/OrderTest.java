package domain;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrderConstructorWithAutoId() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", "Electronics", 1200));
        products.add(new Product(2, "Phone", "Electronics", 800));

        Date date = new Date();
        Order order = new Order(products, date);

        assertNotNull(order);
        assertEquals(products, order.getOrders());
        assertEquals(date, order.getDate());
    }

    @Test
    void testOrderConstructorWithId() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", "Electronics", 1200));

        Date date = new Date();
        Order order = new Order(101, products, date);

        assertEquals(101, order.getId());
        assertEquals(products, order.getOrders());
        assertEquals(date, order.getDate());
    }

    @Test
    void testSetOrders() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", "Electronics", 1200));

        Date date = new Date();
        Order order = new Order(products, date);

        List<Product> newProducts = new ArrayList<>();
        newProducts.add(new Product(2, "Tablet", "Electronics", 500));
        order.setOrders(newProducts);

        assertEquals(newProducts, order.getOrders());
    }

    @Test
    void testSetDate() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", "Electronics", 1200));

        Date initialDate = new Date();
        Order order = new Order(products, initialDate);

        Date newDate = new Date(initialDate.getTime() + 86400000); // +1 zi
        order.setDate(newDate);

        assertEquals(newDate, order.getDate());
    }

    @Test
    void testToString() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", "Electronics", 1200));
        products.add(new Product(2, "Phone", "Electronics", 800));

        Date date = new Date();
        Order order = new Order(products, date);

        String expected = "Comanda: id=" + order.getId() + ", produse=" + products.toString() + ", data=" + date.toString();
        assertEquals(expected, order.toString());
    }

    @Test
    void testToFileString() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", "Electronics", 1200));
        products.add(new Product(2, "Phone", "Electronics", 800));

        Date date = new Date();
        Order order = new Order(101, products, date);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String expected = "101|1,Laptop,Electronics,1200;2,Phone,Electronics,800|" + sdf.format(date);

        assertEquals(expected, order.toFileString());
    }
}
