package thkoeln.st.st2praktikum.exercise.repositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thkoeln.st.st2praktikum.exercise.Room;

import java.util.UUID;
@Repository
public interface RoomRepository extends CrudRepository<Room,UUID>{
    public Room getRoomByid(UUID id);
}
