package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.TransportTechnology;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID> {
}
