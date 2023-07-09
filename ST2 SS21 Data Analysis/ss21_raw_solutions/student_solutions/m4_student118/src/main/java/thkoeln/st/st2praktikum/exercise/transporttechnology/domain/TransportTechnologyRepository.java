package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID> {

    
}
