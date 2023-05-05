package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;



import java.util.UUID;


@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;
    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(width, height);
        roomRepository.save(room);
        return room.getId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        for(Room room : roomRepository.findAll()) {
            if(room.getId().equals(roomId)) {
                room.addBarrier(barrier);
                break;
            }
        }
    }
}
