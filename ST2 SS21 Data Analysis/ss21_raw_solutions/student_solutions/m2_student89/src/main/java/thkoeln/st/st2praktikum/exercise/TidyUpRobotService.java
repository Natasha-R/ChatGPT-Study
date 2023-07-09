package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.entities.Room;
import thkoeln.st.st2praktikum.exercise.entities.TidyUpRobot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TidyUpRobotService {
    private HashMap<UUID, TidyUpRobot> robotHashMap= new HashMap<>();
    private HashMap<UUID, Room> roomHashMap= new HashMap<>();

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
        UUID id =UUID.randomUUID();
        roomHashMap.put(id,room);
        return id;

    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {

        Room room=roomHashMap.get(roomId);

        room.buildBarrier(barrier);

    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector2Ds of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector2Ds of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D) {

        if (sourceVector2D.getY()<0
                || sourceVector2D.getX()<0
                || destinationVector2D.getX()<0
                ||destinationVector2D.getY()<0
                ||sourceVector2D.getX()>roomHashMap.get(sourceRoomId).getGrid().length -1
                ||sourceVector2D.getY()>roomHashMap.get(sourceRoomId).getGrid()[0].length-1
                ||destinationVector2D.getX()>roomHashMap.get(sourceRoomId).getGrid().length-1
                ||destinationVector2D.getY()>roomHashMap.get(sourceRoomId).getGrid()[0].length-1)
            throw new IllegalArgumentException("Out of Bound Connections nicht möglich");
        UUID connectionID;
        connectionID= roomHashMap.get(sourceRoomId).addConnection(sourceRoomId,sourceVector2D,destinationRoomId,destinationVector2D);
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
        UUID id=UUID.randomUUID();
        robotHashMap.put(id,tidyUpRobot);

        return id;

    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {


        TaskType tasktype=task.getTaskType();


        //Methodenaufrufe


        switch (tasktype) {

            case TRANSPORT:
                return robotHashMap.get(tidyUpRobotId).traverse(task.getGridId(),roomHashMap.get(getTidyUpRobotRoomId(tidyUpRobotId)));
            case ENTER:
                if (spaceIsFree(task.getGridId())) {
                    robotHashMap.get(tidyUpRobotId).en(task.getGridId());
                    return true;
                }
                else

                    return false;
            case NORTH:
                robotHashMap.get(tidyUpRobotId).moveNorth(task.getNumberOfSteps(),roomHashMap.get(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
            case EAST:
                robotHashMap.get(tidyUpRobotId).moveEast(task.getNumberOfSteps(),roomHashMap.get(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
            case SOUTH:
                robotHashMap.get(tidyUpRobotId).moveSouth(task.getNumberOfSteps(),roomHashMap.get(getTidyUpRobotRoomId(tidyUpRobotId)));
                return true;
            case WEST:
                robotHashMap.get(tidyUpRobotId).moveWest(task.getNumberOfSteps(),roomHashMap.get(getTidyUpRobotRoomId(tidyUpRobotId)));
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

        if (robotHashMap.get(tidyUpRobotId).getNow_in_room()!=null) {
            return robotHashMap.get(tidyUpRobotId).getNow_in_room();
        }
        else
            throw new UnsupportedOperationException();
    }

    /**
     * This method returns the vector2Ds a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector2Ds of the tidy-up robot
     */
    public Vector2D getVector2D(UUID tidyUpRobotId){

        if (tidyUpRobotId!=null) {
            return robotHashMap.get(tidyUpRobotId).getVektor2D();
        }
        else
            throw new UnsupportedOperationException();
    }

    public boolean spaceIsFree(UUID roomid ){
        for (Map.Entry<UUID, TidyUpRobot> entry : robotHashMap.entrySet()) {
            UUID key = entry.getKey();
            TidyUpRobot value = entry.getValue();

            if (value.getPosx()==0&& value.getPosy()==0&&value.getNow_in_room().equals( roomid)) {

                return false;
            }
        }

        return true;


    }
}
