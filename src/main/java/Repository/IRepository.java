package Repository;

import Domain.Entity;

import java.util.List;

public interface IRepository<T extends Entity> {

    T findById(String id);

    /**
     * Adds  an entity .
     * @param entity the entity to add or update.
     */
    void insert(T entity);

    /**
     * updates an entity if already exists
     * @param entity
     */
    void update(T entity);

    /**
     * Removes an entity with a given id.
     * @param id the id.
     * @throws RuntimeException if there is no entity with the given id.
     */
    void remove(String id);

    List<T> getAll();
}
