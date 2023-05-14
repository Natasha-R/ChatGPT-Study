package thkoeln.st.st2praktikum.exercise.transportcategory.application;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID> {
}

