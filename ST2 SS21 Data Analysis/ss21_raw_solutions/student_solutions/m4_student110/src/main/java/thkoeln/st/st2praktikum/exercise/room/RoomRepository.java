package thkoeln.st.st2praktikum.exercise.room;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoomRepository extends CrudRepository<Room, UUID> {



}