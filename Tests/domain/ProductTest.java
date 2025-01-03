package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testConstructorWithGeneratedId() {
        Product product = new Product("Laptop", "Electronics", 1200);

        assertNotNull(product);
        assertTrue(product.getId() > 99); // Verificăm că ID-ul autogenerat este corect
        assertEquals("Laptop", product.getName());
        assertEquals("Electronics", product.getCategory());
        assertEquals(1200, product.getPrice());
    }

    @Test
    void testConstructorWithSpecifiedId() {
        Product product = new Product(150, "Smartphone", "Electronics", 800);

        assertNotNull(product);
        assertEquals(150, product.getId());
        assertEquals("Smartphone", product.getName());
        assertEquals("Electronics", product.getCategory());
        assertEquals(800, product.getPrice());
    }

    @Test
    void testSettersAndGetters() {
        Product product = new Product(200, "Tablet", "Electronics", 600);

        // Testăm setterii
        product.setName("Gaming Tablet");
        product.setCategory("Gadgets");
        product.setPrice(700);

        // Verificăm valorile actualizate
        assertEquals("Gaming Tablet", product.getName());
        assertEquals("Gadgets", product.getCategory());
        assertEquals(700, product.getPrice());
    }

    @Test
    void testToString() {
        Product product = new Product(300, "Monitor", "Electronics", 500);

        String expected = "Product: id=300, name='Monitor', category='Electronics', price=500";
        assertEquals(expected, product.toString());
    }

    @Test
    void testToFileString() {
        Product product = new Product(400, "Keyboard", "Accessories", 100);

        String expected = "400,Keyboard,Accessories,100";
        assertEquals(expected, product.toFileString());
    }

}
