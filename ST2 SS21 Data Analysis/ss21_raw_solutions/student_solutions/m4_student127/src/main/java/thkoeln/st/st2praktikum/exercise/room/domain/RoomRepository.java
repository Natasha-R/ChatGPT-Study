package thkoeln.st.st2praktikum.exercise.room.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomRepository extends CrudRepository<Room, UUID> {
}