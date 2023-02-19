package crolopez.thecrmservice.shared.application.repositories;

import java.util.List;
import java.util.UUID;

public interface Repository<Entity> {
    Entity get(UUID id);
    List<Entity> get();
    Entity create(Entity customerEntity);
}
