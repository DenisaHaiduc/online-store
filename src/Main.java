import domain.Product;
import domain.ProductBuilder;
import domain.Order;
import domain.OrderBuilder;
import repo.Repo;
import repo.TextFileRepo;
import repo.BinaryFileRepo;
import service.Service;
import ui.ui;

import java.io.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();

        // Load the settings.properties file
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("resource/settings.properties")) {
            if (input == null) {
                System.out.println("Fișierul settings.properties nu a fost găsit.");
                return;
            }
            properties.load(input);
            System.out.println("Fișierul settings.properties a fost încărcat cu succes.");
        } catch (IOException e) {
            System.out.println("Eroare la încărcarea fișierului settings.properties: " + e.getMessage());
            return;
        }

        // Fetch repository settings
        String repositoryType = properties.getProperty("repositoryType");
        if (repositoryType == null || (!repositoryType.equals("text") && !repositoryType.equals("binary") && !repositoryType.equals("memory"))) {
            throw new IllegalArgumentException("Tipul de repository specificat este invalid.");
        }

        // Log the file paths for products and orders
        System.out.println("Product file: " + properties.getProperty("ProductsFile"));
        System.out.println("Order file: " + properties.getProperty("OrdersFile"));

        // Declare repositories for products and orders
        Repo<Product> productRepo;
        Repo<Order> orderRepo;

        // Initialize the appropriate repositories based on the repositoryType
        switch (repositoryType) {
            case "text":
                // Use TextFileRepo for both Product and Order
                productRepo = new TextFileRepo<>(properties.getProperty("ProductsFile"), new ProductBuilder());
                orderRepo = new TextFileRepo<>(properties.getProperty("OrdersFile"), new OrderBuilder());
                break;
            case "binary":
                // Use BinaryFileRepo for both Product and Order
                productRepo = new BinaryFileRepo<>(properties.getProperty("ProductsFile"), new ProductBuilder());
                orderRepo = new BinaryFileRepo<>(properties.getProperty("OrdersFile"), new OrderBuilder());
                break;
            case "memory":
                // Use in-memory repositories for Product and Order
                productRepo = new Repo<>();
                orderRepo = new Repo<>();
                break;
            default:
                throw new IllegalArgumentException("Tipul de repository nu este suportat: " + repositoryType);
        }

        // Initialize the Service with the chosen repositories
        Service service = new Service(productRepo, orderRepo);

        // Start the UI (user interface)
        ui ui = new ui(service);
        ui.run();
    }

}
