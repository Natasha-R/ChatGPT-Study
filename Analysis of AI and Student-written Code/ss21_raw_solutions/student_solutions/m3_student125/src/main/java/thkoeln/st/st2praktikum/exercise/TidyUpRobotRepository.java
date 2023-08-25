package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID>
{

}

