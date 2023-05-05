package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import thkoeln.st.st2praktikum.exercise.entities.Portal;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface PortalRepository extends CrudRepository<Portal, UUID>
{

}