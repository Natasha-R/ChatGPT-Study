package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RepositoryRestResource(exported = false)
public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
    public List<String> findByName(String name);
    public TidyUpRobot findByRoomId(UUID roomId);
    public TidyUpRobot findByVector2D(Vector2D vector2D);
}
