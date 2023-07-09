package thkoeln.st.st2praktikum.exercise.Connection;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID> {
}
