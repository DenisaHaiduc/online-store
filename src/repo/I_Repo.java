package repo;

import domain.Entity;

import java.util.ArrayList;
import java.util.Optional;

public interface I_Repo<T extends Entity> {
    void add(T entity) throws Exception;
    void update(int id, T entity) throws Exception;
    void delete(int id);
    Optional<T> findById(int id); // Updated to use Optional<T>
    ArrayList<T> getAll();
    boolean getById(int id); // Consider renaming to avoid redundancy

    int getNextId(); // New method to get the next available ID
}
