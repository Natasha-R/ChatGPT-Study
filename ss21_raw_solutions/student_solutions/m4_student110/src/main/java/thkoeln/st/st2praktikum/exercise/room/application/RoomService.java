package thkoeln.st.st2praktikum.exercise.room.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.room.BarrierInRoom;
import thkoeln.st.st2praktikum.exercise.room.BarrierRepository;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.TidyUpRobotService;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class RoomService extends TidyUpRobotService  {



    public List<Room> rooms = new ArrayList<>();


    public List<BarrierInRoom> barriersInRoom = new ArrayList<>();

    public RoomService(){}


    public RoomService(List<Room> rooms, List<Barrier> barriers){



    }
    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room newRoom = new Room(height,width);



        rooms.add(newRoom);

        System.out.println(super.allRooms.size());
        return newRoom.getRoomId();

    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        Barrier newBarrier = new Barrier(roomId,barrier.getBarrierString());


        barriersInRoom.addAll(newBarrier.dissolveAllBarrierStrings());

    }
}
