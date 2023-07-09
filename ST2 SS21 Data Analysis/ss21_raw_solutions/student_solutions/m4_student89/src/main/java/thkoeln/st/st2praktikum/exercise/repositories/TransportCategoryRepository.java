package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;

import java.util.UUID;

@Repository
public interface TransportCategoryRepository extends CrudRepository <TransportCategory,UUID>{
    public TransportCategory getTransportCategoryByid(UUID id);
}
