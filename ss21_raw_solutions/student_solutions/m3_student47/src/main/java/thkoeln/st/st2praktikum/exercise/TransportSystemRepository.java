package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.TransportSystem;

import java.util.UUID;

@Repository
public interface TransportSystemRepository extends CrudRepository<TransportSystem, UUID> {
}
