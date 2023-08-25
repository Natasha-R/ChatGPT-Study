package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConnectionRepo extends JpaRepository<Connection, UUID> {
    Connection findConnectionById(UUID id);
    List<Connection> findAllConnectionByCategory(TransportCategory transportCategory);
    void deleteConnectionById(UUID id);
}
