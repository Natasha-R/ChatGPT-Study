package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.core.RobotCommand;
import thkoeln.st.st2praktikum.exercise.interfaces.Connectable;
import thkoeln.st.st2praktikum.exercise.interfaces.Blocking;
import thkoeln.st.st2praktikum.exercise.interfaces.Walkable;
import thkoeln.st.st2praktikum.exercise.repository.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repository.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.repository.TransportSystemRepository;

import java.util.UUID;


@Service
public class TidyUpRobotService {
    private TidyUpRobotRepository tidyUpRobotRepository;
    private RoomRepository roomRepository;
    private TransportSystemRepository transportSystemRepository;


    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository, TransportSystemRepository transportSystemRepository) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
        this.transportSystemRepository = transportSystemRepository;
    }



    //public final Operable map = new Map();
    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height,width);   //TODO - Repository mit Buildable
        this.roomRepository.save(room);
        return room.getId();
//        Buildable room = new Room(height,width);
//        map.addRoom(room);
//        return room.getId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        Blocking obstacle = new Obstacle(barrier);
        this.roomRepository.findById(roomId).get().addObstacle(obstacle);
//        Blocking obstacle = new Obstacle(barrier);
//        map.getRoomById(roomId).addObstacle(obstacle);
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system) {
        TransportSystem transportSystem = new TransportSystem(system);
        this.transportSystemRepository.save(transportSystem);
        return transportSystem.getId();
//        TransportSystem transportSystem = new TransportSystem(system);
//        map.addTransportSystem(transportSystem);
//        return transportSystem.getId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate) {
        Connectable connection = new Connection( (Walkable) roomRepository.findById(sourceRoomId).get() ,sourceCoordinate, (Walkable) roomRepository.findById(destinationRoomId).get(), destinationCoordinate);
        this.transportSystemRepository.findById(transportSystemId).get().addConnection(connection);
        //TODO - Room soll keine Connections mehr halten
        this.roomRepository.findById(sourceRoomId).get().addConnection(connection);
        return connection.getId();

//        Connectable connection = new Connection( (Walkable) map.getRoomById(sourceRoomId) ,sourceCoordinate, (Walkable) map.getRoomById(destinationRoomId), destinationCoordinate);
//        map.getTransportSystemById(transportSystemId).addConnection(connection);
//        map.getRoomById(sourceRoomId).addConnection(connection);
//        return connection.getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot robot = new TidyUpRobot(name);  //TODO - Repository mit Instructable
        this.tidyUpRobotRepository.save(robot);
        return robot.getId();

//        Instructable robot = new TidyUpRobot(name);
//        map.addRobot(robot);
//        return robot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        RobotCommand robotCommand = new RobotCommand(task, this.roomRepository);
        return this.tidyUpRobotRepository.findById(tidyUpRobotId).get().readCommand(robotCommand);
//        RobotCommand robotCommand = new RobotCommand(task, map);
//        return map.getRobotById(tidyUpRobotId).readCommand(robotCommand);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return this.tidyUpRobotRepository.findById(tidyUpRobotId).get().getRoomId();
        //return map.getRobotById(tidyUpRobotId).getRoomId();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId){
        return this.tidyUpRobotRepository.findById(tidyUpRobotId).get().getCoordinate();
        //return map.getRobotById(tidyUpRobotId).getCoordinate();
    }
}
