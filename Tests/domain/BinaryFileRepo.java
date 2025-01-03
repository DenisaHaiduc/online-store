
package domain;
import repo.DuplicateIDException;
import repo.Repo;
import repo.RepoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileRepo<T extends Entity> extends Repo<T> {
    private String fileName;
    private final I_EntityBuilder<T> entityBuilder;

    // Constructor to initialize file path and entity builder, and load entities from file
    public BinaryFileRepo(String filePath, I_EntityBuilder<T> entityBuilder) throws IOException, DuplicateIDException {
        this.fileName = filePath;
        this.entityBuilder = entityBuilder;
        loadEntities(); // Load entities when repository is initialized
    }

    // Load entities from the binary file and add them to the repository
    private void loadEntities() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            return; // If the file doesn't exist, just return (nothing to load)
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Read objects from the binary file
            List<T> entities = (List<T>) ois.readObject();
            for (T entity : entities) {
                super.add(entity); // Add each entity to the repository
            }
        } catch (ClassNotFoundException e) {
            throw new RepoException("Error loading entities: class not found", e);
        } catch (Exception e) {
            throw new RepoException("Error reading from file: " + fileName, e);
        }
    }

    // Save all entities back to the binary file
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

        // Save the entities to the binary file using ObjectOutputStream
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            List<T> entityList = new ArrayList<>(entities); // Convert entities to a list
            oos.writeObject(entityList); // Write the list of entities to the file
        } catch (IOException e) {
            throw new RepoException("Error writing to file: " + fileName, e);
        }
    }

    // Override the add method to add entity and save to file
    @Override
    public void add(T entity) throws Exception {
        super.add(entity); // Add entity to in-memory list
        saveToFile(); // Save updated list to binary file
    }

    // Override the update method to update the entity and save to file
    @Override
    public void update(int id, T entity) throws Exception {
        super.update(id, entity); // Update entity in memory
        saveToFile(); // Save updated list to binary file
    }

    // Override the delete method to remove entity and save to file
    @Override
    public void delete(int id) {
        super.delete(id); // Remove entity from memory
        saveToFile(); // Save updated list to binary file
    }
}
