package domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected final int id;

    public Entity(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public abstract String toString();
    public abstract String toFileString();

}
