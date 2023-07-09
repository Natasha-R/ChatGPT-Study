package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Room;
import thkoeln.st.st2praktikum.exercise.Roomable;

import java.util.UUID;

@EnableJpaRepositories

public interface RoomRepository extends CrudRepository<Room, UUID>
{

}
