package domain;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderBuilderTest {

    @Test
    void testCreateEntityValidInput() throws Exception {
        String line = "101|1,Laptop,Electronics,1200;2,Phone,Electronics,800|2024-10-30";
        OrderBuilder orderBuilder = new OrderBuilder();

        Order order = orderBuilder.createEntity(line);

        assertNotNull(order);
        assertEquals(101, order.getId());

        List<Product> expectedProducts = Arrays.asList(
                new Product(1, "Laptop", "Electronics", 1200),
                new Product(2, "Phone", "Electronics", 800)
        );

        assertEquals(expectedProducts.size(), order.getOrders().size());
        assertEquals(expectedProducts.get(0).getName(), order.getOrders().get(0).getName());
        assertEquals(expectedProducts.get(1).getCategory(), order.getOrders().get(1).getCategory());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("2024-10-30", sdf.format(order.getDate()));
    }

    @Test
    void testCreateEntityInvalidOrderFormat() {
        String line = "101|1,Laptop,Electronics|2024-10-30"; // Missing price for product
        OrderBuilder orderBuilder = new OrderBuilder();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderBuilder.createEntity(line);
        });

        assertEquals("Linia nu are formatul corect pentru un produs: 1,Laptop,Electronics", exception.getMessage());
    }

    @Test
    void testCreateEntityInvalidDateFormat() {
        String line = "101|1,Laptop,Electronics,1200;2,Phone,Electronics,800|invalid-date";  // Data invalida
        OrderBuilder orderBuilder = new OrderBuilder();

        // Verificăm că se aruncă excepția corectă
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderBuilder.createEntity(line);
        });

        assertEquals("Data nu are formatul corect: invalid-date", exception.getMessage());
    }

    @Test
    void testCreateEntityMissingFields() {
        String line = "101||2024-10-30"; // Missing products
        OrderBuilder orderBuilder = new OrderBuilder();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderBuilder.createEntity(line);
        });

        assertEquals("Linia nu are formatul corect pentru un produs: ", exception.getMessage());
    }

    @Test
    void testCreateEntityEmptyLine() {
        String line = ""; // Empty input
        OrderBuilder orderBuilder = new OrderBuilder();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderBuilder.createEntity(line);
        });

        assertEquals("Linia nu are formatul corect pentru o comanda: ", exception.getMessage());
    }
}
