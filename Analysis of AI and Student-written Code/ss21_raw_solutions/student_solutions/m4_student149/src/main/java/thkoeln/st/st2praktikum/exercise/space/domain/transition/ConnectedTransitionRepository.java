package thkoeln.st.st2praktikum.exercise.space.domain.transition;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ConnectedTransitionRepository extends CrudRepository<ConnectedTransition, UUID> {
}
