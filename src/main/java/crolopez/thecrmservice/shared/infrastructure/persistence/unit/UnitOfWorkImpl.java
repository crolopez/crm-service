package crolopez.thecrmservice.shared.infrastructure.persistence.unit;

import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.List;

@Component
public class UnitOfWorkImpl implements UnitOfWork {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public <Value, Entity> List<Entity> get(String field, Value value, Class<Entity> entityType) {
        prepareTransaction();
        return getEntity(field, value, entityType);
    }

    @Transactional
    @Override
    public <Entity> List<Entity> get(Class<Entity> entityType) {
        prepareTransaction();
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery criteria = criteriaBuilder.createQuery(entityType);
        Root<Entity> root = criteria.from(entityType);
        criteria.select(root);

        return session.createQuery(criteria).getResultList();
    }

    @Transactional
    @Override
    public <Entity> void create(Entity entityClass) {
        prepareTransaction();
        sessionFactory.getCurrentSession().save(entityClass);
        sessionFactory.getCurrentSession().getTransaction().commit();
        prepareTransaction();
    }

    @Transactional
    @Override
    public <Entity> void delete(String id, Class<Entity> entityType) {
        prepareTransaction();
        var entity = getEntity("id", id, entityType).get(0);
        sessionFactory.getCurrentSession().delete(entity);
        sessionFactory.getCurrentSession().getTransaction().commit();
        prepareTransaction();
    }

    @SneakyThrows
    @Transactional
    @Override
    public <Entity> void update(String id, Entity entity, Class<Entity> entityType) {
        prepareTransaction();
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaUpdate criteria = criteriaBuilder.createCriteriaUpdate(entityType);
        Root<Entity> root = criteria.from(entityType);

        Field[] fields = entityType.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(entity);
            criteria.set(root.get(field.getName()), criteriaBuilder.literal(value));
        }

        criteria.where(criteriaBuilder.equal(root.get("id"), id));
        session.createQuery(criteria).executeUpdate();
        sessionFactory.getCurrentSession().getTransaction().commit();
        prepareTransaction();
    }

    private void prepareTransaction() {
        if (!sessionFactory.getCurrentSession().getTransaction().isActive())
            sessionFactory.getCurrentSession().beginTransaction();
    }

    private <Entity, Value> List<Entity> getEntity(String field, Value value, Class<Entity> entityType) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery criteria = criteriaBuilder.createQuery(entityType);
        Root<Entity> root = criteria.from(entityType);
        criteria.where(criteriaBuilder.equal(root.get(field), value));

        return session.createQuery(criteria).getResultList();
    }

}
