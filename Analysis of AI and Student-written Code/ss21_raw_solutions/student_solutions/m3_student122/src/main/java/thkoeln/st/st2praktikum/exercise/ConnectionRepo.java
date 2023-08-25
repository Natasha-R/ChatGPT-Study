package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.Connection;
import thkoeln.st.st2praktikum.exercise.TransportCategory;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConnectionRepo extends JpaRepository<Connection, UUID> {
    Connection findConnectionById(UUID id);
    List<Connection> findAllConnectionByCategory(TransportCategory transportCategory);
    void deleteConnectionById(UUID id);
}
