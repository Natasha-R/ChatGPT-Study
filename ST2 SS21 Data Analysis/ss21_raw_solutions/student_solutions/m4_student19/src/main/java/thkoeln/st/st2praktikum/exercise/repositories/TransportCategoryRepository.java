package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;

import java.util.UUID;

public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID> {
}
