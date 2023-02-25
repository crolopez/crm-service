package crolopez.thecrmservice.shared.infrastructure.persistence.mappers;

public interface Mapper<ExternalEntity, Entity> {
    Entity externalEntityToEntity(ExternalEntity externalEntity);
    ExternalEntity entityToExternalEntity(Entity entity);
}
