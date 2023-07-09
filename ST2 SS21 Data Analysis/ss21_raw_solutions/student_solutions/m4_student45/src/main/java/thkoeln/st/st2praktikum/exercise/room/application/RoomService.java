package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.Square;
import thkoeln.st.st2praktikum.exercise.room.domain.Wall;
import thkoeln.st.st2praktikum.exercise.room.repository.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.repository.SquareRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.repository.ConnectionRepository;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;


@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final SquareRepository squareRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, SquareRepository squareRepository){
        this.roomRepository = roomRepository;
        this.squareRepository = squareRepository;
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height, width);
        UUID id = room.getId();

        squareRepository.saveAll(room.getSquares());
        roomRepository.save(room);

        return id;
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        Room room = roomRepository.findById(roomId).orElseThrow(EntityNotFoundException::new);
        room.addWall(wall);

        squareRepository.saveAll(room.getSquares());
        roomRepository.save(room);
    }
}
