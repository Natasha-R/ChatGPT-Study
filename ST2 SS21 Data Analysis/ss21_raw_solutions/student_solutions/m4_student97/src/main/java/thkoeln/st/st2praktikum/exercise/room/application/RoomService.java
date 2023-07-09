package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.repository.RoomRepository;


import java.util.UUID;


@Component
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    
    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height, width);
        return roomRepository.save(room).getId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        Room room = roomRepository.findById(roomId).get();
        room.addBarrier(barrier);
        roomRepository.save(room);
    }

    public Room findRoom(UUID roomId) {
        return roomRepository.findById(roomId).get();
    }
}
