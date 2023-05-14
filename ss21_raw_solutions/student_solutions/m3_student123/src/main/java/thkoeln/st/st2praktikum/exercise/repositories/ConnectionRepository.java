package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.Connection;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ConnectionRepository extends CrudRepository<Connection, UUID> {
}
