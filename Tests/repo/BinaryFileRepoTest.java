package repo;

import domain.Product;
import domain.ProductBuilder;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinaryFileRepoTest {
    private static final String TEST_FILE = "test_repo.dat";
    private BinaryFileRepo<Product> repo;

    @BeforeEach
    void setUp() throws IOException, DuplicateIDException {
        // Creează un fișier gol înainte de fiecare test
        new File(TEST_FILE).delete();
        repo = new BinaryFileRepo<>(TEST_FILE, new ProductBuilder());
    }

    @AfterEach
    void tearDown() {
        // Șterge fișierul de test după fiecare test
        new File(TEST_FILE).delete();
    }

    @Test
    void testAddEntity() throws Exception {
        Product product = new Product(101, "Laptop", "Electronics", 1200);
        repo.add(product);

        List<Product> products = repo.getAll();
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
    }

    @Test
    void testUpdateEntity() throws Exception {
        Product product = new Product(101, "Laptop", "Electronics", 1200);
        repo.add(product);

        Product updatedProduct = new Product(101, "Gaming Laptop", "Electronics", 1500);
        repo.update(101, updatedProduct);

        List<Product> products = repo.getAll();
        assertEquals(1, products.size());
        assertEquals(updatedProduct, products.get(0));
    }

    @Test
    void testDeleteEntity() throws Exception {
        Product product1 = new Product(101, "Laptop", "Electronics", 1200);
        Product product2 = new Product(102, "Smartphone", "Gadgets", 800);
        repo.add(product1);
        repo.add(product2);

        repo.delete(101);

        List<Product> products = repo.getAll();
        assertEquals(1, products.size());
        assertEquals(product2, products.get(0));
    }

    @Test
    void testLoadEntitiesFromFile() throws Exception {
        Product product1 = new Product(101, "Laptop", "Electronics", 1200);
        Product product2 = new Product(102, "Smartphone", "Gadgets", 800);

        repo.add(product1);
        repo.add(product2);

        // Creează un nou repo pentru a simula încărcarea din fișier
        BinaryFileRepo<Product> newRepo = new BinaryFileRepo<>(TEST_FILE, new ProductBuilder());
        List<Product> products = newRepo.getAll();

        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    void testSaveEntitiesToFile() throws Exception {
        Product product1 = new Product(101, "Laptop", "Electronics", 1200);
        Product product2 = new Product(102, "Smartphone", "Gadgets", 800);

        repo.add(product1);
        repo.add(product2);

        File file = new File(TEST_FILE);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

}
