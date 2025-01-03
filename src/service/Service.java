package service;

import domain.Order;
import domain.Product;
import repo.Repo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Service {
    private final Repo<Product> productRepo;
    private final Repo<Order> orderRepo;

    // Constructor accepts any type of Repo (including TextFileRepo and BinaryFileRepo)
    public Service(Repo<Product> productRepo, Repo<Order> orderRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
    }

    // Add a new product to the product repository
    public void addProduct(String name, String category, int price) throws Exception {
        int id = productRepo.getNextId(); // Get the next available ID
        Product product = new Product(id, name, category, price);
        productRepo.add(product); // Add product to repository
    }

    // Add a new order to the order repository
    public void addOrder(List<Integer> productID, Date date) throws Exception {
        List<Product> products = new ArrayList<>();
        int id_order = orderRepo.getNextId(); // Get the next available ID
        for (int id : productID) {
            // Verify that each product exists before adding it to the order
            productRepo.findById(id).ifPresentOrElse(
                    products::add,
                    () -> {
                        throw new RuntimeException("Product with ID " + id + " does not exist.");
                    }
            );
        }
        Order order = new Order(id_order, products, date);
        orderRepo.add(order); // Add order to repository
    }

    // Update an existing product in the product repository
    public void updateProduct(int id, String name, String category, int price) throws Exception {
        Product product = new Product(id, name, category, price);
        productRepo.update(id, product); // Update product in repository
    }

    // Update an existing order in the order repository
    public void updateOrder(int id, List<Integer> productID, Date date) throws Exception {
        List<Product> products = new ArrayList<>();
        for (int product_id : productID) {
            // Verify that each product exists before adding it to the order
            productRepo.findById(product_id).ifPresentOrElse(
                    products::add,
                    () -> {
                        throw new RuntimeException("Product with ID " + product_id + " does not exist.");
                    }
            );
        }
        Order order = new Order(id, products, date);
        orderRepo.update(id, order); // Update order in repository
    }

    // Delete a product by its ID from the product repository
    public void deleteProduct(int id) {
        productRepo.delete(id); // Delete product from repository
    }

    // Delete an order by its ID from the order repository
    public void deleteOrder(int id) {
        orderRepo.delete(id); // Delete order from repository
    }

    // Get all products from the product repository
    public ArrayList<Product> getAllProducts() {
        return productRepo.getAll(); // Return list of all products
    }

    // Get all orders from the order repository
    public ArrayList<Order> getAllOrders() {
        return orderRepo.getAll(); // Return list of all orders
    }

    // Check if a product exists by its ID in the product repository
    public boolean getProductById(int id) {
        return productRepo.getById(id); // Check if product exists by ID
    }

    // Check if an order exists by its ID in the order repository
    public boolean getOrderById(int id) {
        return orderRepo.getById(id); // Check if order exists by ID
    }
}
