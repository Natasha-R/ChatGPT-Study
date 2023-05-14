package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;


import java.util.ArrayList;
import java.util.List;
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
        Room room = new Room();

        room.setHeight(height);
        room.setWidth(width);

        roomRepository.save(room);
        return room.getRoomID();


    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {


        if ( barrier.getStart().getX().equals(barrier.getEnd().getX())){
            roomRepository.findById(roomId).get().getVertikalBarriers().add(barrier);
        }else {
            roomRepository.findById(roomId).get().getHorizontalBarriers().add(barrier);
        }
    }
}
