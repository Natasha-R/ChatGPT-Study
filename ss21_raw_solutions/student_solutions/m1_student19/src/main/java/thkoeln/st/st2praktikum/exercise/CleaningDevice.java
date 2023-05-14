package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CleaningDevice {
    private final UUID cleaningDeviceId;
    private final String name;

    private UUID currentSpaceId;
    private final Coordinate coordinates;

    public CleaningDevice(String name) {
        this.cleaningDeviceId = UUID.randomUUID();
        this.name = name;
        this.coordinates = new Coordinate();
    }

    /**
     * This method is responsible for the traverse/teleport of a cleaning device
     * @param connectionCollection collection of all connections
     * @param destinationSpaceId the ID of the Space, to which the device should be traversed
     * @return if the traversing of the device to the desired space was successful
     */
    public Boolean traverseDevice(HashMap<UUID, Connection> connectionCollection, UUID destinationSpaceId) {
        for (Map.Entry<UUID, Connection> connectionEntry : connectionCollection.entrySet()) {
            if (connectionEntry.getValue().getSourceSpaceId().compareTo(this.getCurrentSpaceId()) == 0 &&
                    connectionEntry.getValue().getDestinationSpaceId().compareTo(destinationSpaceId) == 0) {

                if (this.getCoordinateString().equals(connectionEntry.getValue().getSourceCoordinate())) {
                    Coordinate newCoordinates = Coordinate.fromString(connectionEntry.getValue().getDestinationCoordinate());
                    this.traversedToSpace(newCoordinates, connectionEntry.getValue().getDestinationSpaceId());
                    return true;
                } else return false;
            }
        }

        return false;
    }

    /**
     * This method is responsible for the placing of a cleaning device in a place
     * @param spaceCollection collection of all spaces
     * @param cleaningDeviceCollection collection of all cleaning devices
     * @param destinationSpaceId the ID of the space where the device should be placed
     * @return if the placing of the device was possible or not
     */
    public Boolean placeDevice(HashMap<UUID, Space> spaceCollection, HashMap<UUID, CleaningDevice> cleaningDeviceCollection, UUID destinationSpaceId) {
        if (spaceCollection.get(destinationSpaceId) == null)
            throw new IllegalArgumentException("This destination space doesn't exist: " + destinationSpaceId);

        for (Map.Entry<UUID, CleaningDevice> cleaningDeviceEntry : cleaningDeviceCollection.entrySet()) {
            if (cleaningDeviceEntry.getValue().getCurrentSpaceId() != null) {
                if (cleaningDeviceEntry.getValue().getCurrentSpaceId().compareTo(destinationSpaceId) == 0 &&
                        cleaningDeviceEntry.getValue().getCoordinateString().equals("(0,0)")) {
                    return false;
                }
            }
        }

        this.placedInSpace(destinationSpaceId);
        return true;
    }

    /**
     * This method is responsible for moving the device north in a space
     *      => decreasing the y coordinate value of the device
     * @param space space object in which the device is located
     * @param moveCount the count of moves the device should do in the given direction
     * @return true that the move was successful
     */
    public Boolean moveNorth(Space space, int moveCount) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).isBorderNorth() &&
                    this.getY() + 1 < space.getSpaceHeight()) {
                this.increaseY();
            } else break;
        }

        return true;
    }

    /**
     * This method is responsible for moving the device east in a space
     *      => decreasing the y coordinate value of the device
     * @param space space object in which the device is located
     * @param moveCount the count of moves the device should do in the given direction
     * @return true that the move was successful
     */
    public Boolean moveEast(Space space, int moveCount) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).isBorderEast() &&
                    this.getX() + 1 < space.getSpaceWidth()) {
                this.increaseX();
            } else break;
        }

        return true;
    }

    /**
     * This method is responsible for moving the device south in a space
     *      => decreasing the y coordinate value of the device
     * @param space space object in which the device is located
     * @param moveCount the count of moves the device should do in the given direction
     * @return true that the move was successful
     */
    public Boolean moveSouth(Space space, int moveCount) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).isBorderSouth() &&
                    this.getY() - 1 >= 0) {
                this.decreaseY();
            } else break;
        }

        return true;
    }

    /**
     * This method is responsible for moving the device west in a space
     *      => decreasing the y coordinate value of the device
     * @param space space object in which the device is located
     * @param moveCount the count of moves the device should do in the given direction
     * @return true that the move was successful
     */
    public Boolean moveWest(Space space, int moveCount) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).isBorderWest() &&
                    this.getX() - 1 >= 0 ) {
                this.decreaseX();
            } else break;
        }

        return true;
    }

    public void traversedToSpace(int x, int y, UUID newSpaceId) {
        this.setCoordinates(x, y);
        this.currentSpaceId = newSpaceId;
    }

    public void traversedToSpace(Coordinate newCoordinate, UUID newSpaceId) {
        this.setCoordinates(newCoordinate);
        this.currentSpaceId = newSpaceId;
    }

    public String getCoordinateString() {
        return this.coordinates.toString();
    }
    public int getX() { return this.coordinates.getX(); }
    public int getY() { return this.coordinates.getY(); }

    public UUID getCleaningDeviceId() { return this.cleaningDeviceId; }
    public String getName() { return this.name; }
    public UUID getCurrentSpaceId() { return this.currentSpaceId; }

    public void setCoordinates(int x, int y) { this.coordinates.setNewCoordinates(x, y); }
    public void setCoordinates(Coordinate newCoordinate) { this.coordinates.setNewCoordinates(newCoordinate); }

    public void increaseX() { this.coordinates.increaseX(); }
    public void increaseY() { this.coordinates.increaseY(); }
    public void decreaseX() { this.coordinates.decreaseX(); }
    public void decreaseY() { this.coordinates.decreaseY(); }

    public void placedInSpace(UUID spaceId) {
        this.currentSpaceId = spaceId;
    }


}
