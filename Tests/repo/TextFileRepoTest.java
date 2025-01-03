package repo;

import domain.I_EntityBuilder;
import domain.Product;
import domain.ProductBuilder;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class TextFileRepoTest {
    private static final String TEST_FILE = "C:\\Users\\Denisa\\IdeaProjects\\a3-DenisaHaiduc\\Tests\\repo\\testProduct";
    private TextFileRepo<Product> repo;

    private final I_EntityBuilder<Product> productBuilder = new I_EntityBuilder<>() {
        @Override
        public Product createEntity(String line) {
            String[] fields = line.split(",");
            return new Product(Integer.parseInt(fields[0]), fields[1], fields[2], Integer.parseInt(fields[3]));
        }
    };

    @BeforeEach
    void setUp() throws Exception {
        // Creează un fișier gol înainte de fiecare test
        new File(TEST_FILE).delete();
        repo = new TextFileRepo<>(TEST_FILE, new ProductBuilder());
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

        // Verificăm dacă entitatea este salvată în fișier
        File file = new File(TEST_FILE);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
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
        // Scriem entități în fișier înainte de a inițializa repo-ul
        try (FileWriter writer = new FileWriter(TEST_FILE)) {
            writer.write("Smartphone");
        }

        // Creează un repo nou pentru a simula încărcarea din fișier
        TextFileRepo<Product> newRepo = new TextFileRepo<>(TEST_FILE,  productBuilder);
        assertEquals(2, newRepo.getAll().size());
        assertEquals( "Smartphone", newRepo.getAll().get(1).getName());
    }

    @Test
    void testSaveEntitiesToFile() throws Exception {
        Product product1 = new Product(101, "Laptop", "Electronics", 1200);
        Product product2 = new Product(102, "Smartphone", "Gadgets", 800);

        repo.add(product1);
        repo.add(product2);

        // Verificăm conținutul fișierului
        File file = new File(TEST_FILE);
        assertTrue(file.exists());

        try (Scanner scanner = new Scanner(file)) {
            assertEquals("101,Laptop,Electronics,1200", scanner.nextLine());
            assertEquals("102,Smartphone,Gadgets,800", scanner.nextLine());
        }
    }

}
