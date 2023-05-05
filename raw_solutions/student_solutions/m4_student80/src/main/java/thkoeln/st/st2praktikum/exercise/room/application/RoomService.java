package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thkoeln.st.st2praktikum.exercise.room.domain.*;


import java.util.UUID;


@Service
public class RoomService {


    RoomList roomList = new RoomList();
    @Autowired
    RoomRepository RoomRepository;

    @Autowired
    BarrierRepository BarrierRepository;
    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {

        UUID roomUUID = UUID.randomUUID();
        Room newRoom = new Room(height, width, roomUUID);
        RoomRepository.save(newRoom);
        roomList.add(newRoom);


        Barrier roomBarrierOne = Barrier.fromString("(" + 0 + "," + 0 + ")-(" + 0 + "," + (height)+")");
        Barrier roomBarrierTwo = Barrier.fromString("(" + 0 + "," + 0 + ")-(" + (width) + "," + 0+")");
        Barrier roomBarrierThree = Barrier.fromString("(" + 0 + "," + (height) + ")-(" + (height) + "," + (width)+")");
        Barrier roomBarrierFour = Barrier.fromString("(" + (width) + "," + 0 + ")-(" + (width) + "," + (height)+")");
        addBarrier(roomUUID, roomBarrierOne);
        addBarrier(roomUUID, roomBarrierTwo);
        addBarrier(roomUUID, roomBarrierThree);
        addBarrier(roomUUID, roomBarrierFour);
        return roomUUID;
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {

        BarrierRepository.save(barrier);
        for (Room room : roomList.getRoomList()) {
            if (room.getRoomUUID() == roomId) {
                RoomRepository.deleteById(roomId);
                room.getBarrierList().add(barrier);
                RoomRepository.save(room);
            }
        }

    }
}
