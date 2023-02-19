package unit.crolopez.thecrmservice.shared.infrastructure.persistence.repositories.unit;

import crolopez.thecrmservice.shared.infrastructure.persistence.repositories.unit.UnitOfWorkImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UnitOfWorkImplUnitTest {

    @Mock
    private SessionFactory sessionFactory;

    @InjectMocks
    private UnitOfWorkImpl unitOfWork;

    @AfterEach
    public void after() {
        Mockito.verifyNoMoreInteractions(sessionFactory);
        Mockito.reset(sessionFactory);
    }

    @Test
    public void whenCallGet_thenTheSavedEntitiesAreReturned() {
        List<Integer> entityList = Mockito.mock(List.class);
        var mockedSession = Mockito.mock(Session.class);
        var mockedTransaction = Mockito.mock(Transaction.class);
        var mockedCriteriaBuilder = Mockito.mock(CriteriaBuilder.class);
        var mockedCriteriaQuery = Mockito.mock(CriteriaQuery.class);
        var mockedRoot = Mockito.mock(Root.class);
        var query = Mockito.mock(Query.class);
        when(sessionFactory.getCurrentSession()).thenReturn(mockedSession);
        when(mockedSession.getCriteriaBuilder()).thenReturn(mockedCriteriaBuilder);
        when(mockedCriteriaBuilder.createQuery(Integer.class)).thenReturn(mockedCriteriaQuery);
        when(mockedCriteriaQuery.from(Integer.class)).thenReturn(mockedRoot);
        when(mockedSession.getTransaction()).thenReturn(mockedTransaction);
        when(mockedSession.createQuery(mockedCriteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(entityList);

        var response = unitOfWork.get(Integer.class);

        assertEquals(entityList, response);
        Mockito.verify(sessionFactory, atLeastOnce()).getCurrentSession();
        Mockito.verify(mockedSession).getTransaction();
        Mockito.verify(mockedTransaction).isActive();
        Mockito.verify(mockedSession).getCriteriaBuilder();
    }

}
