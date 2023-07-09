package thkoeln.st.st2praktikum.exercise.repositories;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.entities.Room;

import java.util.HashMap;
import java.util.UUID;

public class RoomRepository {
    @Getter
    private final HashMap<UUID, Room> rooms = new HashMap<>();
}
