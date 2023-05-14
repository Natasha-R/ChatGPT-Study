package thkoeln.st.st2praktikum.exercise.Repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TransportTechnology;

import java.util.UUID;

public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID> {
}
