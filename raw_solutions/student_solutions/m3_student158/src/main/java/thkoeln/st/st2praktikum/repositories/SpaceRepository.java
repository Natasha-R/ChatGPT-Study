package thkoeln.st.st2praktikum.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.*;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface SpaceRepository extends CrudRepository<Space, UUID> {

}
