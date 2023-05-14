package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TransportTechnology;

import java.util.UUID;

public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID> {
}
