package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Room;

import java.util.UUID;
@EnableJpaRepositories
public interface RoomRepository extends CrudRepository<Room, UUID> {

}