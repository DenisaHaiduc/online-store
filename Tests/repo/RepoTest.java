package repo;

import domain.Product;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepoTest {
    private Repo<Product> repo;

    @BeforeEach
    void setUp() {
        repo = new Repo<>();
    }

    @Test
    void testAddEntity() throws Exception {
        Product product = new Product(1, "Laptop", "Electronics", 1200);
        repo.add(product);

        ArrayList<Product> products = repo.getAll();
        assertEquals(1, products.size());
        assertEquals(product, products.get(0));
    }

    @Test
    void testAddDuplicateEntityThrowsException() {
        Product product1 = new Product(1, "Laptop", "Electronics", 1200);
        Product product2 = new Product(1, "Smartphone", "Gadgets", 800);

        assertDoesNotThrow(() -> repo.add(product1));
        Exception exception = assertThrows(DuplicateIDException.class, () -> repo.add(product2));
        assertEquals("Entity already exists", exception.getMessage());
    }

    @Test
    void testUpdateEntity() throws Exception {
        Product product = new Product(1, "Laptop", "Electronics", 1200);
        repo.add(product);

        Product updatedProduct = new Product(1, "Gaming Laptop", "Electronics", 1500);
        repo.update(1, updatedProduct);

        Optional<Product> foundProduct = repo.findById(1);
        assertTrue(foundProduct.isPresent());
        assertEquals(updatedProduct, foundProduct.get());
    }

    @Test
    void testUpdateNonExistingEntityThrowsException() {
        Product product = new Product(1, "Laptop", "Electronics", 1200);
        Exception exception = assertThrows(RepoException.class, () -> repo.update(1, product));
        assertEquals("Entity does not exist", exception.getMessage());
    }

    @Test
    void testDeleteEntity() throws Exception {
        Product product = new Product(1, "Laptop", "Electronics", 1200);
        repo.add(product);

        repo.delete(1);

        ArrayList<Product> products = repo.getAll();
        assertTrue(products.isEmpty());
    }

    @Test
    void testDeleteNonExistingEntityThrowsException() {
        Exception exception = assertThrows(RepoException.class, () -> repo.delete(1));
        assertEquals("Entity not found", exception.getMessage());
    }

    @Test
    void testFindById() throws Exception {
        Product product = new Product(1, "Laptop", "Electronics", 1200);
        repo.add(product);

        Optional<Product> foundProduct = repo.findById(1);
        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    void testFindByIdNotFound() {
        Optional<Product> foundProduct = repo.findById(1);
        assertFalse(foundProduct.isPresent());
    }

    @Test
    void testGetAll() throws Exception {
        Product product1 = new Product(1, "Laptop", "Electronics", 1200);
        Product product2 = new Product(2, "Smartphone", "Gadgets", 800);
        repo.add(product1);
        repo.add(product2);

        ArrayList<Product> products = repo.getAll();
        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    void testGetById() throws Exception {
        Product product = new Product(1, "Laptop", "Electronics", 1200);
        repo.add(product);

        assertTrue(repo.getById(1));
        assertFalse(repo.getById(2));
    }

    @Test
    void testGetNextId() throws Exception {
        Product product1 = new Product(1, "Laptop", "Electronics", 1200);
        Product product2 = new Product(2, "Smartphone", "Gadgets", 800);
        repo.add(product1);
        repo.add(product2);

        int nextId = repo.getNextId();
        assertEquals(3, nextId);
    }

    @Test
    void testGetNextIdWhenRepoIsEmpty() {
        int nextId = repo.getNextId();
        assertEquals(1, nextId);
    }
}
