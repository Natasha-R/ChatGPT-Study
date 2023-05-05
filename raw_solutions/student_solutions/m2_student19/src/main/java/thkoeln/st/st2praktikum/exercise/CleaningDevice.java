package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CleaningDevice {
    private final UUID cleaningDeviceId;
    private final String name;

    private UUID currentSpaceId;
    private Vector2D coordinates;

    public CleaningDevice(String name) {
        this.cleaningDeviceId = UUID.randomUUID();
        this.name = name;
        this.coordinates = new Vector2D(0, 0);
    }

    /**
     * This method is responsible for the traverse/teleport of a cleaning device
     * @param connectionCollection collection of all connections
     * @param destinationSpaceId the ID of the Space, to which the device should be traversed
     * @return if the traversing of the device to the desired space was successful
     */
    public Boolean traverseDevice(HashMap<UUID, Connection> connectionCollection, HashMap<UUID, CleaningDevice> cleaningDeviceCollection, UUID destinationSpaceId) {
        for (Map.Entry<UUID, Connection> connectionEntry : connectionCollection.entrySet()) {

            if (this.currentSpaceId != null) {
                if (connectionEntry.getValue().getSourceSpaceId().compareTo(this.currentSpaceId) == 0 &&
                        connectionEntry.getValue().getDestinationSpaceId().compareTo(destinationSpaceId) == 0) {

                    if (this.coordinates.equals(connectionEntry.getValue().getSourceVector2D())) {

                        if (!isCoordinateIsFree(connectionEntry.getValue().getDestinationVector2D(),
                                connectionEntry.getValue().getDestinationSpaceId(), cleaningDeviceCollection)) {
                            return false;
                        }

                        Vector2D newCoordinates = connectionEntry.getValue().getDestinationVector2D();
                        this.traversedToSpace(newCoordinates, connectionEntry.getValue().getDestinationSpaceId());
                        return true;

                    } else return false;
                }
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
                        cleaningDeviceEntry.getValue().getCoordinates().equals(new Vector2D(0, 0))) {
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
     * @param cleaningDeviceCollection collection of all cleaning devices
     * @return true that the move was successful
     */
    public Boolean moveNorth(Space space, int moveCount, HashMap<UUID, CleaningDevice> cleaningDeviceCollection) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).isBorderNorth() &&
                    this.getY() + 1 < space.getSpaceHeight() &&
                    this.isCoordinateIsFree(new Vector2D(this.getX(), this.getY() + 1), space.getSpaceId(), cleaningDeviceCollection)) {
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
     * @param cleaningDeviceCollection collection of all cleaning devices
     * @return true that the move was successful
     */
    public Boolean moveEast(Space space, int moveCount, HashMap<UUID, CleaningDevice> cleaningDeviceCollection) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).isBorderEast() &&
                    this.getX() + 1 < space.getSpaceWidth() &&
                    this.isCoordinateIsFree(new Vector2D(this.getX() + 1, this.getY()), space.getSpaceId(), cleaningDeviceCollection)) {
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
     * @param cleaningDeviceCollection collection of all cleaning devices
     * @return true that the move was successful
     */
    public Boolean moveSouth(Space space, int moveCount, HashMap<UUID, CleaningDevice> cleaningDeviceCollection) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).isBorderSouth() &&
                    this.getY() - 1 >= 0 &&
                    this.isCoordinateIsFree(new Vector2D(this.getX(), this.getY() - 1), space.getSpaceId(), cleaningDeviceCollection)) {
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
     * @param cleaningDeviceCollection collection of all cleaning devices
     * @return true that the move was successful
     */
    public Boolean moveWest(Space space, int moveCount, HashMap<UUID, CleaningDevice> cleaningDeviceCollection) {
        for (int i = moveCount; i > 0; i--) {
            if (!space.getSpaceCell(this.getX(), this.getY()).isBorderWest() &&
                    this.getX() - 1 >= 0 &&
                    this.isCoordinateIsFree(new Vector2D(this.getX() - 1, this.getY()), space.getSpaceId(), cleaningDeviceCollection)) {
                this.decreaseX();
            } else break;
        }

        return true;
    }

    /**
     * checks a given coordinate if it is already occupied by a different cleaning device
     * @param coordinateToCheck Vector2D which has to be checked
     * @param spaceId space the Vecto2D is located in
     * @param cleaningDeviceCollection collection of all cleaning devices
     * @return if the given Vector2D is occupied or not
     */
    private Boolean isCoordinateIsFree(Vector2D coordinateToCheck, UUID spaceId, HashMap<UUID, CleaningDevice> cleaningDeviceCollection) {
        for (Map.Entry<UUID, CleaningDevice> cleaningDeviceEntry : cleaningDeviceCollection.entrySet()) {
            if (cleaningDeviceEntry.getValue().currentSpaceId != null) {
                if (cleaningDeviceEntry.getValue().currentSpaceId.compareTo(spaceId) == 0 &&
                        cleaningDeviceEntry.getValue().coordinates.equals(coordinateToCheck)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void traversedToSpace(Vector2D newCoordinate, UUID newSpaceId) {
        this.setCoordinates(newCoordinate);
        this.currentSpaceId = newSpaceId;
    }


    public int getX() { return this.coordinates.getX(); }
    public int getY() { return this.coordinates.getY(); }

    public String getName() { return this.name; }
    public UUID getCleaningDeviceId() { return this.cleaningDeviceId; }
    public UUID getCurrentSpaceId() { return this.currentSpaceId; }

    public Vector2D getCoordinates() { return this.coordinates; }
    public void setCoordinates(int x, int y) { this.coordinates = new Vector2D(x, y); }
    public void setCoordinates(Vector2D newCoordinate) { this.coordinates = newCoordinate; }

    public void increaseX() {
        this.coordinates = new Vector2D(this.coordinates.getX() + 1, this.coordinates.getY());
    }

    public void increaseY() {
        this.coordinates = new Vector2D(this.coordinates.getX(), this.coordinates.getY() + 1);
    }

    public void decreaseX() {
        this.coordinates = new Vector2D(this.coordinates.getX() - 1, this.coordinates.getY());
    }

    public void decreaseY() {
        this.coordinates = new Vector2D(this.coordinates.getX(), this.coordinates.getY() - 1);
    }

    public void placedInSpace(UUID spaceId) {
        this.currentSpaceId = spaceId;
    }


}
