package repo;

import domain.Entity;
import domain.I_EntityBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileRepo<T extends Entity> extends Repo<T> {
    private String fileName;
    private final I_EntityBuilder<T> entityBuilder;

    public BinaryFileRepo(String filePath, I_EntityBuilder<T> entityBuilder) throws IOException, DuplicateIDException {
        this.fileName = filePath;
        this.entityBuilder = entityBuilder;
        loadEntities();
    }

    private void loadEntities() throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File does not exist: " + fileName);
            return;
        }
        if (file.length() == 0) {
            System.out.println("File is empty: " + fileName);
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<T> entities = (List<T>) ois.readObject();
            for (T entity : entities) {
                super.add(entity);
            }
        } catch (EOFException e) {
            throw new RepoException("Unexpected end of file: " + fileName, e);
        } catch (ClassNotFoundException e) {
            throw new RepoException("Error loading entities: class not found", e);
        } catch (InvalidClassException e) {
            throw new RepoException("Invalid class in file: " + fileName, e);
        } catch (StreamCorruptedException e) {
            throw new RepoException("Corrupted stream in file: " + fileName, e);
        } catch (Exception e) {
            throw new RepoException("Error reading from file: " + fileName, e);
        }
    }

    private void saveToFile() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Created new file: " + fileName);
                }
            } catch (IOException e) {
                throw new RepoException("Error creating the file: " + fileName, e);
            }
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            List<T> entityList = new ArrayList<>(entities);
            oos.writeObject(entityList);
        } catch (IOException e) {
            throw new RepoException("Error writing to file: " + fileName, e);
        }
    }

    @Override
    public void add(T entity) throws Exception {
        super.add(entity);
        saveToFile();
    }

    @Override
    public void update(int id, T entity) throws Exception {
        super.update(id, entity);
        saveToFile();
    }

    @Override
    public void delete(int id) {
        super.delete(id);
        saveToFile();
    }
}