package ui;

import domain.Product;
import domain.Order;
import service.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// Clasa UI pentru interacțiunea cu utilizatorul
public class ui {
    private final Service service;
    private final Scanner scanner;

    public ui(Service service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while (running) {
           ! System.out.println("1. Add pl product");
            System.out.println("2. Add order");
            System.out.println("3. Read products");
            System.out.println("4. Read orders");
            System.out.println("5. Update product");
            System.out.println("6. Update order");
            System.out.println("7. Delete product");
            System.out.println("8. Delete order");
            System.out.println("9. Get product");
            System.out.println("10. Get order");
            System.out.println("0. Exit");
            int option = scanner.nextInt();
            scanner.nextLine();
            try{
                switch (option) {
                    case 1 -> addProduct();
                    case 2 -> addOrder();
                    case 3 -> readProducts();
                    case 4 -> readOrders();
                    case 5 -> updateProduct();
                    case 6 -> updateOrder();
                    case 7 -> deleteProduct();
                    case 8 -> deleteOrder();
                    case 9 -> getProduct();
                    case 10 -> getOrder();
                    case 0 -> running = false;
                    default -> System.out.println("Opțiune invalidă!");
                }
            } catch (Exception e) {
                System.out.println("Eroare: " + e.getMessage());
            }

        }
    }

    private void getOrder() throws Exception {
        System.out.println("Enter order ID");
        int id = scanner.nextInt();
        System.out.println(service.getOrderById(id));
    }

    private void getProduct() throws Exception {
        System.out.println("Enter product ID");
        int id = scanner.nextInt();
        System.out.println(service.getProductById(id));
    }

    private void deleteOrder() throws Exception {
        System.out.println("Enter order ID");
        int id = scanner.nextInt();
        try {
            service.deleteOrder(id);
            System.out.println("Order deleted");
        } catch (Exception e) {
            System.out.println("Error deleting order: " + e.getMessage());
        }
    }

    private void deleteProduct() throws Exception {
        System.out.println("Enter product ID");
        int id = scanner.nextInt();
        try {
            service.deleteProduct(id);
            System.out.println("Product deleted");
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
        }
    }

    private void updateOrder() {
        System.out.println("Enter order ID to update");
        int id = scanner.nextInt();
        List<Integer> productIDs = new ArrayList<>();
        System.out.println("Enter the number of products in the order:");
        int number = scanner.nextInt();
        for (int i = 0; i < number; i++) {
            System.out.println("Enter product ID:");
            int idProduct = scanner.nextInt();
            productIDs.add(idProduct);
        }
        Date date = new Date();
        try {
            service.updateOrder(id, productIDs, date);
            System.out.println("Order added successfully");
        } catch (Exception e) {
            System.out.println("Error while adding order: " + e.getMessage());
        }
    }

    private void updateProduct() throws Exception {
        System.out.println("Enter product ID to update");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter product name: ");
        String name = scanner.nextLine();
        System.out.println("Enter product category: ");
        String category = scanner.nextLine();
        System.out.println("Enter product price: ");
        int price = scanner.nextInt();
        try {
            service.updateProduct(id, name, category, price);
            System.out.println("Product updated");
        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    private void readOrders() {
        List<Order> orders = service.getAllOrders();
        System.out.println("Orders are: ");
        orders.forEach(System.out::println);
    }

    private void readProducts() {
        List<Product> products = service.getAllProducts();
        System.out.println("Products are: ");
        products.forEach(System.out::println);
    }

    private void addOrder() throws Exception {
        List<Integer> productIDs = new ArrayList<>();
        System.out.println("Enter the number of products in the order:");
        int number = scanner.nextInt();
        for (int i = 0; i < number; i++) {
            System.out.println("Enter product ID:");
            int id = scanner.nextInt();
            productIDs.add(id);
        }
        Date date = new Date();
        try {
            service.addOrder(productIDs, date);
            System.out.println("Order added successfully");
        } catch (Exception e) {
            System.out.println("Error while adding order: " + e.getMessage());
        }
    }

    private void addProduct() throws Exception {
        System.out.println("Enter product name: ");
        String name = scanner.nextLine();
        System.out.println("Enter product category: ");
        String category = scanner.nextLine();
        System.out.println("Enter product price: ");
        int price = scanner.nextInt();
        try {
            service.addProduct(name, category, price);
            System.out.println("Product added successfully");
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }
}
