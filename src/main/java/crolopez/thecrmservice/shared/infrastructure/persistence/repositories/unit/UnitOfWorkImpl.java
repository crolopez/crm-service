package crolopez.thecrmservice.shared.infrastructure.persistence.repositories.unit;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class UnitOfWorkImpl implements UnitOfWork {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    @Override
    public <Value, Entity> List<Entity> get(String field, Value value, Class<Entity> entityType) {
        prepareTransaction();
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery criteria = criteriaBuilder.createQuery(entityType);
        Root<Entity> root = criteria.from(entityType);
        criteria.where(criteriaBuilder.equal(root.get(field), value));

        return session.createQuery(criteria).getResultList();
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

    private void prepareTransaction() {
        if (!sessionFactory.getCurrentSession().getTransaction().isActive())
            sessionFactory.getCurrentSession().beginTransaction();
    }

}
