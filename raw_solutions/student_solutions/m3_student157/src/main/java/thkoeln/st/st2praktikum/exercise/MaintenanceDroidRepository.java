package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface MaintenanceDroidRepository extends CrudRepository<MaintenanceDroid, UUID> {
}
