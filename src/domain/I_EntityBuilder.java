package domain;

public interface I_EntityBuilder <T extends Entity> {
    T createEntity(String line);
}
