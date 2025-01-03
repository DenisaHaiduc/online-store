package service;

import domain.Order;
import domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repo.Repo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private Repo<Product> productRepo;
    private Repo<Order> orderRepo;
    private Service service;

    @BeforeEach
    void setUp() {
        productRepo = new Repo<>();
        orderRepo = new Repo<>();
        service = new Service(productRepo, orderRepo);
    }

    @Test
    void testAddProduct() throws Exception {
        service.addProduct("Laptop", "Electronics", 1500);

        ArrayList<Product> products = service.getAllProducts();
        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).getName());
    }

    @Test
    void testAddOrder() throws Exception {
        service.addProduct("Laptop", "Electronics", 1500);
        service.addProduct("Smartphone", "Gadgets", 800);

        List<Integer> productIds = Arrays.asList(1, 2);
        service.addOrder(productIds, new Date());

        ArrayList<Order> orders = service.getAllOrders();
        assertEquals(1, orders.size());
        // Verificăm ID-ul comenzii
        assertEquals(1, orders.get(0).getId());
    }

    @Test
    void testUpdateProduct() throws Exception {
        service.addProduct("Laptop", "Electronics", 1500);
        service.updateProduct(1, "Gaming Laptop", "Electronics", 2000);

        Product updatedProduct = service.getAllProducts().get(0);
        assertEquals("Gaming Laptop", updatedProduct.getName());
        assertEquals(2000, updatedProduct.getPrice());
    }

    @Test
    void testUpdateOrder() throws Exception {
        service.addProduct("Laptop", "Electronics", 1500);
        service.addProduct("Smartphone", "Gadgets", 800);
        service.addOrder(Arrays.asList(1), new Date());

        service.updateOrder(1, Arrays.asList(1, 2), new Date());

        ArrayList<Order> orders = service.getAllOrders();
        // Verificăm că ordinea a fost actualizată corect
        assertEquals(2, orders.get(0).getOrders().size());  // Verificăm câte produse sunt în comandă
    }

    @Test
    void testDeleteProduct() throws Exception {
        service.addProduct("Laptop", "Electronics", 1500);
        service.deleteProduct(1);

        assertTrue(service.getAllProducts().isEmpty());
    }

    @Test
    void testDeleteOrder() throws Exception {
        service.addProduct("Laptop", "Electronics", 1500);
        service.addOrder(Arrays.asList(1), new Date());

        service.deleteOrder(1);

        assertTrue(service.getAllOrders().isEmpty());
    }

    @Test
    void testGetProductById() throws Exception {
        service.addProduct("Laptop", "Electronics", 1500);
        service.addProduct("Smartphone", "Gadgets", 800);

        ArrayList<Product> products = service.getAllProducts();
        boolean found = products.stream().anyMatch(p -> p.getId() == 1);
        assertTrue(found);
    }

    @Test
    void testGetOrderById() throws Exception {
        service.addProduct("Laptop", "Electronics", 1500);
        service.addOrder(Arrays.asList(1), new Date());

        ArrayList<Order> orders = service.getAllOrders();
        boolean found = orders.stream().anyMatch(o -> o.getId() == 1);
        assertTrue(found);
    }
}
