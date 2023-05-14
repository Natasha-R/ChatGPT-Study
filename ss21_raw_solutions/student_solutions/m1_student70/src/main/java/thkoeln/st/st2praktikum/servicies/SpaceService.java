package thkoeln.st.st2praktikum.servicies;

import thkoeln.st.st2praktikum.exercise.*;
import thkoeln.st.st2praktikum.interfacies.SpaceInterface;

import java.util.*;

public class SpaceService implements SpaceInterface {

    private final Map<UUID, Space> spaces = new HashMap<>();


    @Override
    public UUID creatSpace(Integer height, Integer width) {
        Space space = new Space(height, width);
        spaces.put(space.getSpaceId(), space);
        return space.getSpaceId();
    }

    @Override
    public void addWall(UUID spaceId, String wallString) {
        Wall wall = new Wall(wallString);
        Space space = spaces.get(spaceId);
        space.getWalls().add(wall);
    }

    @Override
    public UUID addConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        getSpaceById(destinationSpaceId);// check space found
        Connection connection = new Connection(sourceSpaceId, sourceCoordinate, destinationSpaceId, destinationCoordinate);
        spaces.get(sourceSpaceId).getConnections().add(connection);// check space found
        return connection.getConnectionId();
    }

    @Override
    public Space getSpaceById(UUID spaceId) {
        return spaces.get(spaceId);
    }

    @Override
    public void removeCleaningDevice(UUID spaceId, UUID cleaningDeviceId) {
        spaces.get(spaceId).getCleaningDevices().remove(cleaningDeviceId);

    }




    public boolean transportCleaningDevice(CleaningDevice cleaningDevice, String spaceId) {

        for (Connection connection : getSpaceById(cleaningDevice.getSpaceId()).getConnections()) {
            final boolean isDestinationRight=connection.getDestinationSpaceId().equals(UUID.fromString(spaceId));
            final boolean isDviceXInTrPosition=cleaningDevice.getPosition().getX() == connection.getSourceCoordinate().getX();
            final boolean isDviceYInTrPosition=cleaningDevice.getPosition().getY() == connection.getSourceCoordinate().getY();
            final boolean isDestinationCoordinateEmpty=getSpaceById(UUID.fromString(spaceId))
                    .isCoordinateExistAndEmpty(new Point( connection.getDestinationCoordinate().getX(),
                    connection.getDestinationCoordinate().getY()),"tr");

            if (isDestinationRight && isDviceXInTrPosition
                    && isDviceYInTrPosition  &&isDestinationCoordinateEmpty ) {
                removeCleaningDevice(cleaningDevice.getSpaceId(),cleaningDevice.getCleaningDeviceId());
                cleaningDevice.setSpaceId(UUID.fromString(spaceId));
                cleaningDevice.getPosition().setX(connection.getDestinationCoordinate().getX());
                cleaningDevice.getPosition().setY(connection.getDestinationCoordinate().getY());
                return true;
            }
        }
        return false;
    }
}
