package crolopez.thecrmservice.shared.application.repositories;

import java.util.List;

public interface Repository<Entity> {
    Entity get(String id);
    List<Entity> get();
    Entity create(Entity customerEntity);
    void delete(String id);
    Entity update(String id, Entity customerEntity);
}
