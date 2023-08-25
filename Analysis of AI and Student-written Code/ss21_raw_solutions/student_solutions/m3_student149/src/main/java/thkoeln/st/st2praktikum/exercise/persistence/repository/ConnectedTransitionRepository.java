package thkoeln.st.st2praktikum.exercise.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.environment.transition.ConnectedTransition;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ConnectedTransitionRepository extends CrudRepository<ConnectedTransition, UUID> {
}
