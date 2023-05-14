package thkoeln.st.st2praktikum.exercise;
import thkoeln.st.st2praktikum.exercise.commandInterpreter.CommandInterpreter;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.robot.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.robot.Transportable;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.Roomable;
import thkoeln.st.st2praktikum.exercise.wall.FieldBlockable;
import thkoeln.st.st2praktikum.exercise.wall.Wall;

import java.util.UUID;

public class TidyUpRobotService
{
    private Mapper mapper = new Mapper();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width)
    {
        Roomable room = new Room(width,height);
        this.mapper.addRoom(room);
        return room.identify();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID roomId, String wallString)
    {
        FieldBlockable wall = new Wall(wallString);
        this.mapper.getRoomByID(roomId).addWallToRoom(wall);
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate)
    {
        Roomable sourceRoom = mapper.getRoomByID(sourceRoomId);
        Roomable destinationRoom = mapper.getRoomByID(destinationRoomId);
        Connectable connection = new Connection(sourceRoom,sourceCoordinate,destinationRoom,destinationCoordinate);
        this.mapper.getRoomByID(sourceRoomId).addRoomConnection(connection);
        this.mapper.addConnection(connection);
        return connection.identify();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name)
    {
        Transportable robot = new TidyUpRobot(name);
        this.mapper.addRobot(robot);
        return robot.identify();
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString)
    {
        CommandInterpreter interpreter = new CommandInterpreter();
        Transportable robot = mapper.getRobotByID(tidyUpRobotId);

        switch (interpreter.interpretCommand(commandString))
        {
            case EN:
                if(this.mapper.getRobotByID(tidyUpRobotId).placeMovable(mapper.getRoomByID(interpreter.getRoomID())))
                {
                    this.mapper.getRoomByID(interpreter.getRoomID()).addRobotToRoom(robot);
                    return true;
                }
                break;
            case TR:
                UUID roomID = interpreter.getRoomID();
                Roomable sourceRoom = robot.showRoom();
                Roomable destinationRoom = mapper.getRoomByID(roomID);
                Connectable connection = mapper.getConnectionByID(sourceRoom.hasConnectionToRoom(roomID));
                if (this.mapper.getRobotByID(tidyUpRobotId).transportToRoom(destinationRoom,connection))
                {
                    this.mapper.getRoomByID(roomID).addRobotToRoom(robot);
                    this.mapper.getRoomByID(sourceRoom.identify()).removeRobot(tidyUpRobotId);
                    return true;
                }
                break;
            case NO:
                 this.mapper.getRobotByID(tidyUpRobotId).moveNorth(interpreter.getSteps());
                 return true;
            case EA:
                this.mapper.getRobotByID(tidyUpRobotId).moveEast(interpreter.getSteps());
                return true;
            case SO:
                this.mapper.getRobotByID(tidyUpRobotId).moveSouth(interpreter.getSteps());
                return true;
            case WE:
                this.mapper.getRobotByID(tidyUpRobotId).moveWest(interpreter.getSteps());
                return true;
            case ERROR:
                return false;
        }

        return false;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId)
    {
        return this.mapper.getRobotByID(tidyUpRobotId).showTransportableRoomID();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId)
    {
        return this.mapper.getRobotByID(tidyUpRobotId).showActualPosition();
    }
}
