package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportCategoryRepository;

import java.util.UUID;


@Service
public class TidyUpRobotService {
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    private TransportCategoryRepository transportCategoryRepository;

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {

        if (height<1 || width <1)
            throw new UnsupportedOperationException();

        Room room=new Room(height,width);
        roomRepository.save(room);

        return room.getId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        Room room= roomRepository.getRoomByid(roomId);
        if (room==null){
            throw new UnsupportedOperationException();
        }
        room.buildBarrier(barrier);
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory=new TransportCategory(category);
        transportCategoryRepository.save(transportCategory);
        return transportCategory.getId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D) {
        TransportCategory transportCategory= transportCategoryRepository.getTransportCategoryByid(transportCategoryId);

        UUID connectionID= roomRepository.getRoomByid(sourceRoomId).addConnection(transportCategory,sourceRoomId,sourceVector2D,destinationRoomId,destinationVector2D);
        if (connectionID==null)
        {
            throw new UnsupportedOperationException();
        }
        else
            return connectionID;
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot=new TidyUpRobot(name);

        tidyUpRobotRepository.save(tidyUpRobot);
        return tidyUpRobot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {



        //Methodenaufrufe


        switch (task.getTaskType()) {

            case TRANSPORT:
                return tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).traverse(task.getGridId(), roomRepository.getRoomByid(getTidyUpRobotRoomId(tidyUpRobotId)));
            case ENTER:
                if (spaceIsFree(task.getGridId())) {
                    tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).en(task.getGridId());
                    return true;
                }
                else

                    return false;
            case NORTH:
                tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).moveNorth(task.getNumberOfSteps(), roomRepository.getRoomByid(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
            case EAST:
                tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).moveEast(task.getNumberOfSteps(), roomRepository.getRoomByid(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
            case SOUTH:
                tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).moveSouth(task.getNumberOfSteps(), roomRepository.getRoomByid(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
            case WEST:
                tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).moveWest(task.getNumberOfSteps(), roomRepository.getRoomByid(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
        }

        throw new UnsupportedOperationException();
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){

        if (tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).getNow_in_room()!=null) {
            return tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).getNow_in_room();
        }
        else
            throw new UnsupportedOperationException();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId){

        if (tidyUpRobotId!=null) {
            return tidyUpRobotRepository.getTidyUpRobotByid(tidyUpRobotId).getVector2D();
        }
        else
            throw new UnsupportedOperationException();
    }


    public boolean spaceIsFree(UUID roomid ){
        for (TidyUpRobot value : tidyUpRobotRepository.findAll()) {



            if (value.getPosx()==0&& value.getPosy()==0&&value.getNow_in_room().equals( roomid)) {

                return false;
            }
        }

        return true;


    }
}
