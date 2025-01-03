package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderBuilder implements I_EntityBuilder<Order> {

    private List<Product> availableProducts;

    public OrderBuilder() {
        this.availableProducts = new ArrayList<>(); // Initialize to an empty list
    }

    public OrderBuilder(List<Product> availableProducts) {
        this.availableProducts = availableProducts;
    }

    @Override
    public Order createEntity(String line) {
        line = line.trim(); // Remove leading and trailing spaces from the entire line

        // Split by "|" to separate the order ID, product list, and date
        String[] tokens = line.split("\\|");

        // Ensure the line contains exactly three parts: Order ID, Products, and Date
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Linia nu are formatul corect pentru o comanda: " + line);
        }

        // Extract the Order ID from the first part
        int orderId = Integer.parseInt(tokens[0].trim());  // Parse the Order ID

        // Parse the products from the second part (after the first pipe)
        String[] productTokens = tokens[1].split(";");
        List<Product> products = new ArrayList<>();
        for (String productToken : productTokens) {
            // Trim any extra spaces and split by comma to get product details
            String[] productData = productToken.trim().split(",");

            // Check if the product data has exactly four parts: ID, Name, Category, Price
            if (productData.length != 4) {
                throw new IllegalArgumentException("Linia nu are formatul corect pentru un produs: " + productToken);
            }

            // Extract and trim product data
            int id = Integer.parseInt(productData[0].trim());   // Product ID
            String name = productData[1].trim();               // Product name
            String category = productData[2].trim();           // Product category
            int price = Integer.parseInt(productData[3].trim()); // Product price

            products.add(new Product(id, name, category, price));
        }

        // Parse the order date from the third part (after the second pipe)
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(tokens[2].trim());  // Parse the date
        } catch (ParseException e) {
            throw new IllegalArgumentException("Data nu are formatul corect: " + tokens[2]);
        }

        // Return the created Order object with parsed ID, products, and date
        return new Order(orderId, products, date);
    }

}
