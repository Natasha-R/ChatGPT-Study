package thkoeln.st.st2praktikum.exercise.map.connection;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID> {
}
