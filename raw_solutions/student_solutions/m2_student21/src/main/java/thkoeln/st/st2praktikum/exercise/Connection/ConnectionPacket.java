package thkoeln.st.st2praktikum.exercise.Connection;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Room.Room;

public interface ConnectionPacket {
    /**
     *  returns the DestinationCoordinates
     */
    public Coordinate getDestinationCoordinate();

    /**
     *  validates a connection by checking if a connection exists with the given sourceRoom, sourceCoordinates and destinationRoom
     * @param sourceRoom current room
     * @param sourceCoordinate starting point of the connection
     * @param destinationRoom target room
     */
    public Boolean validateConnection(Room sourceRoom, Coordinate sourceCoordinate,Room destinationRoom);
}
