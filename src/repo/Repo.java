package repo;

import domain.Entity;
import domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repo<T extends Entity> implements I_Repo<T> {
    protected final List<T> entities = new ArrayList<>();

    @Override
    public void add(T entity) throws Exception {
        if (entityExists(entity.getId())) {
            throw new DuplicateIDException("Entity already exists");
        }
        entities.add(entity);
    }

    @Override
    public void update(int id, T newEntity) throws Exception {
        int index = findIndexById(id);
        if (index == -1) {
            throw new RepoException("Entity does not exist");
        }
        entities.set(index, newEntity);
    }

    @Override
    public void delete(int id) {
        int index = findIndexById(id);
        if (index == -1) {
            throw new RepoException("Entity not found");
        }
        entities.remove(index);
    }

    @Override
    public Optional<T> findById(int id) {
        return entities.stream()
                .filter(entity -> entity.getId() == id)
                .findFirst();  // Returns Optional<T>
    }

    @Override
    public ArrayList<T> getAll() {
        return new ArrayList<>(entities);
    }

    @Override
    public boolean getById(int id) {
        return findById(id).isPresent();
    }


    private boolean entityExists(int id) {
        return findById(id).isPresent();
    }

    private int findIndexById(int id) {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getNextId() {
        int maxId = 0;
        for (T entity : entities) {
            if (entity.getId() > maxId) {
                maxId = entity.getId();
            }
        }
        return maxId + 1;
    }
}
