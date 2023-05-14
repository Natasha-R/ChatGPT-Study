package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TidyUpRobotService {

    TidyUpRobotController tidyUpRobotController;

    @Autowired
    public TidyUpRobotService(TidyUpRobotController tidyUpRobotController){
        this.tidyUpRobotController = tidyUpRobotController;
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height, width);
        tidyUpRobotController.getRoomRepository().save(room);
        return room.getId();

    }

    /**
     * This method adds a obstacle to a given room.
     * @param roomId the ID of the room the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID roomId, Obstacle obstacle) {
        Room room = tidyUpRobotController.getRoomRepository().findById(roomId).get();
        if (room.getWidth() <= obstacle.getEnd().getX() || room.getHeight() <= obstacle.getEnd().getY())
            throw new RuntimeException("Obstacle Out Of Bounce");
        room.getRoomObstacles().add(obstacle);
        tidyUpRobotController.getRoomRepository().save(room);
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = new TransportSystem(system);
        tidyUpRobotController.getTransportSystemRepository().save(transportSystem);
        return transportSystem.getId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D) {

        TransportSystem transportSystem = tidyUpRobotController.getTransportSystemRepository().findById(transportSystemId).get();
        Room sourceRoom = tidyUpRobotController.getRoomRepository().findById(sourceRoomId).get();

        if (sourceRoom.getHeight() <= sourceVector2D.getY() || sourceRoom.getWidth() <= sourceVector2D.getX() || tidyUpRobotController.getRoomRepository().findById(destinationRoomId).get().getHeight() <= destinationVector2D.getY() || tidyUpRobotController.getRoomRepository().findById(destinationRoomId).get().getWidth() <= destinationVector2D.getX())
            throw new RuntimeException("Connection Out Of Bounce");

        Connection roomConnection = new Connection(transportSystemId, sourceVector2D, destinationVector2D);
        sourceRoom.getConnections().put(destinationRoomId, roomConnection);
        tidyUpRobotController.getConnectionRepository().save(roomConnection);

        transportSystem.getConnections().add(roomConnection);
        tidyUpRobotController.getTransportSystemRepository().save(transportSystem);

        tidyUpRobotController.getRoomRepository().save(sourceRoom);

        return roomConnection.getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        tidyUpRobotController.getTidyUpRobotRepository().save(tidyUpRobot);
        return tidyUpRobot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Command command) {
        try {
            tidyUpRobotController.execute(tidyUpRobotId, command);
        }
        catch (Exception exception) {
            System.out.println("Error while executing: "+command.toString()+"\n"+exception.getMessage()+"\n");
            return false;
        }
        return true;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotController.getTidyUpRobotRepository().findById(tidyUpRobotId).get().getRoomId();
    }

    /**
     * This method returns the vector2Ds a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector2Ds of the tidy-up robot
     */
    public Vector2D getVector2D(UUID tidyUpRobotId){
        return tidyUpRobotController.getTidyUpRobotRepository().findById(tidyUpRobotId).get().getRobotPosition();
    }
}
