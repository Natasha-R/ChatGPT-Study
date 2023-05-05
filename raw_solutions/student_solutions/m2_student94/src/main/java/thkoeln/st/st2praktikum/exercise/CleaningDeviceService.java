package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.assembler.Assembler;
import thkoeln.st.st2praktikum.exercise.cleaningDevice.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningDevice.Instructable;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.connection.Connection;
import thkoeln.st.st2praktikum.exercise.assembler.Operable;
import thkoeln.st.st2praktikum.exercise.space.Space;
import thkoeln.st.st2praktikum.exercise.space.SpaceManageable;
import thkoeln.st.st2praktikum.exercise.space.Walkable;

import java.util.UUID;

public class CleaningDeviceService {
    public final Operable assembler=new Assembler();

    /**
     * This method creates a new space.
     * @param height the height of the space
     * @param width the width of the space
     * @return the UUID of the created space
     */
    public UUID addSpace(Integer height, Integer width) {
        SpaceManageable space= new Space(height,width);
        assembler.addSpace(space);
        return space.getId();
    }

    /**
     * This method adds a obstacle to a given space.
     * @param spaceId the ID of the space the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID spaceId, Obstacle obstacle) {
        Walkable actualSpace= (Walkable) assembler.getSpaceByItsId(spaceId);

        if (!(obstacle.getStart().getX()<actualSpace.getWidth() && (obstacle.getEnd().getX() < actualSpace.getWidth()) && obstacle.getStart().getY()<actualSpace.getHeight() && obstacle.getEnd().getY()<actualSpace.getHeight())){
            throw new RuntimeException();
        }
        assembler.getSpaceByItsId(spaceId).addObstacle(obstacle);

    }

    /**
     * This method adds a traversable connection between two spaces. Connections only work in one direction.
     * @param sourceSpaceId the ID of the space where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationSpaceId the ID of the space where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {
        Walkable sourceSpace= (Walkable) assembler.getSpaceByItsId(sourceSpaceId);
        Walkable destinationSpace=(Walkable) assembler.getSpaceByItsId(destinationSpaceId);

        if (!(sourceCoordinate.getX()<sourceSpace.getWidth() && sourceCoordinate.getY() < sourceSpace.getHeight() && destinationCoordinate.getX()<destinationSpace.getWidth() && destinationCoordinate.getY()<destinationSpace.getHeight())){
            throw new RuntimeException();
        }

        Connectable connection= new Connection(sourceSpaceId,sourceCoordinate,destinationSpaceId,destinationCoordinate);
        return assembler.getSpaceByItsId(sourceSpaceId).addConnection(connection);

    }

    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {
        Instructable cleaningDevice = new CleaningDevice(name);
        assembler.addCleaningDevice(cleaningDevice);
        return cleaningDevice.getId();
    }

    /**
     * This method lets the cleaning device execute a order.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Order order) {
        ////Wegen Fehler im Cleaning Device
        return assembler.getCleaningDeviceByItsId(cleaningDeviceId).analysedCommand(order);
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return assembler.getCleaningDeviceByItsId(cleaningDeviceId).getSpace().getId();
    }

    /**
     * This method returns the coordinates a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinates of the cleaning device
     */
    public Coordinate getCoordinate(UUID cleaningDeviceId){
        return assembler.getCleaningDeviceByItsId(cleaningDeviceId).getPosition();
    }
}
