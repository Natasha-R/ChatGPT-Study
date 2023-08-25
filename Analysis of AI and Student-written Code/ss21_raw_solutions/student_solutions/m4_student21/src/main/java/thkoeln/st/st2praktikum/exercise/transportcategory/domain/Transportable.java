package thkoeln.st.st2praktikum.exercise.transportcategory.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

public interface Transportable{
    /**
     *  returns the DestinationCoordinates
     */
    public Coordinate getDestinationCoordinate();

    /**
     *  validates a connection by checking if a connection exists with the given sourceRoom, sourceCoordinates and destinationRoom
     * @param sourceCoordinate starting point of the connection
     * @param destinationRoom target room
     */
    public Boolean validateConnection(Coordinate sourceCoordinate, Room destinationRoom);
}
