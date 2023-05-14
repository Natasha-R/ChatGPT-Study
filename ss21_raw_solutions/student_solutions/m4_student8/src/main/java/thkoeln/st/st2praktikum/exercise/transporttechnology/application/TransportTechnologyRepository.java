package thkoeln.st.st2praktikum.exercise.transporttechnology.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;

import java.util.UUID;

public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID>
{

}
