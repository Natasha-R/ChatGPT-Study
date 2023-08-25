package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TransportCategory;

import java.util.UUID;

public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID> {
}
