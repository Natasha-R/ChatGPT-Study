package thkoeln.st.st2praktikum.exercise.room.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.exception.RoomNotFound;
import thkoeln.st.st2praktikum.exercise.repositories.BarrierRepository;
import thkoeln.st.st2praktikum.exercise.repositories.PortalRepository;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.utility.World;


import java.util.UUID;


@Service
public class RoomService
{
    @Autowired
    private BarrierRepository barrierRepository;
    @Autowired
    private RoomRepository roomRepository;

    private final World world = World.getInstance();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width)
    {
        final UUID roomUUID = getWorld().addRoom(height, width);

        try
        {
            final Room room = getWorld().getRoom(roomUUID);

            roomRepository.save(room);
        }
        catch (RoomNotFound roomNotFound)
        {
            roomNotFound.printStackTrace();
        }

        return roomUUID;
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier)
    {
        try
        {
            getWorld().addWall(roomId, barrier);

            barrierRepository.save(barrier);
        }
        catch (RoomNotFound roomNotFound)
        {
            roomNotFound.printStackTrace();
        }
    }

    private World getWorld()
    {
        return world;
    }
}
