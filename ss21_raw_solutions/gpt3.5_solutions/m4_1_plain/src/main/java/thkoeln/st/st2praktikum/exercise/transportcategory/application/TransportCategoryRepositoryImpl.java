package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository
public abstract class TransportCategoryRepositoryImpl implements TransportCategoryRepository {

    private final EntityManager entityManager;

    @Autowired
    public TransportCategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public TransportCategory findByName(String name) {
        String queryString = "SELECT tc FROM TransportCategory tc WHERE tc.name = :name";
        TypedQuery<TransportCategory> query = entityManager.createQuery(queryString, TransportCategory.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}