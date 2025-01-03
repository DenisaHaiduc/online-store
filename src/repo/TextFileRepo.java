package repo;

import domain.Entity;
import domain.I_EntityBuilder;
import domain.Product;
import domain.Order;

import java.io.*;
import java.util.Scanner;

public class TextFileRepo<T extends Entity> extends Repo<T> {
    private String fileName;
    private final I_EntityBuilder<T> entityBuilder;

    // Constructor to initialize file path and entity builder, and load entities from file
    public TextFileRepo(String filePath, I_EntityBuilder<T> entityBuilder) throws FileNotFoundException, DuplicateIDException {
        this.fileName = filePath;
        this.entityBuilder = entityBuilder;
        loadEntities(); // Load entities when repository is initialized
    }

    // Load entities from the file and add them to the repository
    private void loadEntities() throws RepoException {
        File file = new File(fileName);
        if (!file.exists()) {
            return; // If file doesn't exist, just return (nothing to load)
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                T entity = entityBuilder.createEntity(line);
                super.add(entity); // Add the created entity to the repository
            }
        } catch (Exception e) {
            throw new RepoException("Error reading from file: " + fileName, e);
        }
    }

    // Save all entities back to the file
    private void saveToFile() {
        // Ensure the file exists, or create it
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile(); // Creates the file if it doesn't exist
            } catch (IOException e) {
                throw new RepoException("Error creating the file: " + fileName, e);
            }
        }

        // Save the entities to the file using PrintWriter
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (T entity : entities) {
                writer.println(entity.toFileString());
            }
        } catch (IOException e) {
            throw new RepoException("Error writing to file: " + fileName, e);
        }
    }

    // Override the add method to add entity and save to file
    @Override
    public void add(T entity) throws Exception {
        super.add(entity); // Add entity to in-memory list
        saveToFile(); // Save updated list to file
    }

    // Override the update method to update the entity and save to file
    @Override
    public void update(int id, T entity) throws Exception {
        super.update(id, entity); // Update entity in memory
        saveToFile(); // Save updated list to file
    }

    // Override the delete method to remove entity and save to file
    @Override
    public void delete(int id) {
        super.delete(id); // Remove entity from memory
        saveToFile(); // Save updated list to file
    }
}
