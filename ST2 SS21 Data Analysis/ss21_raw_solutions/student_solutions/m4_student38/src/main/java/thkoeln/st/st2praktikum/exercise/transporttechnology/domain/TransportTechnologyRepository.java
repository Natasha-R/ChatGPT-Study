package thkoeln.st.st2praktikum.exercise.transporttechnology.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


@RepositoryRestResource(exported = false)
public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, String> {
    @Override
    Iterable<TransportTechnology> findAll();
}
