package thkoeln.st.st2praktikum.exercise;


import java.util.ArrayList;
import java.util.UUID;

public class Room implements Roomable {

    private final UUID id;
    private final ArrayList<Passable> obstacles = new ArrayList<>();
    private final ArrayList<Occupied> occupiedFields = new ArrayList<>();
    private final ArrayList<Connectable> connections = new ArrayList<>();
    private final Integer roomHeight;
    private final Integer roomWidth;


    public Room(Integer roomHeight, Integer roomWidth) {
        this.id = UUID.randomUUID();
        this.roomHeight = roomHeight;
        this.roomWidth = roomWidth;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void addObstacle(Passable obstacle) {
        this.obstacles.add(obstacle);
    }

    @Override
    public void removeObstacle(UUID obstacleID) {
        ArrayList<Passable> removeElements = new ArrayList<>();
        for (Passable element : obstacles) {
            if (element.getId().equals(obstacleID)) {
                removeElements.add(element);
            }
        }
        for (Passable element : removeElements) {
            obstacles.remove(element);
        }
    }

    @Override
    public void addOccupiedPosition(Occupied occupiedElement) {
        this.occupiedFields.add(occupiedElement);
    }

    @Override
    public void removeOccupiedPosition(UUID OccupiedFieldID) {
        ArrayList<Occupied> removeElements = new ArrayList<>();
        for (Occupied element: occupiedFields) {
            if (element.getId() == OccupiedFieldID) {
                removeElements.add(element);
            }
        }
        for (Occupied element : removeElements) {
            occupiedFields.remove(element);
        }
    }

    @Override
    public Boolean roomPositionIsEmpty(XYPositionable position) {
        for (Occupied element: occupiedFields) {
            if (element.isOccupied(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public XYMovable validateMovement(XYMovable movement) {
        while (movement.getMovementStatus() == MovementStatus.READY || movement.getMovementStatus() == MovementStatus.RUNNING) {
            if (validateNextStep(movement)) {
                movement.takeSingleStep();
            } else {
                movement.setStatusBlocked();
            }
        }
        return movement;
    }

    private Boolean validateNextStep(XYMovable movement) {
        if (!validateRoomBorder(movement))
            return false;
        if (!validateObstacles(movement))
            return false;
        if (!validateOccupiedFields(movement))
            return false;
        return true;
    }

    private Boolean validateRoomBorder(XYMovable movement) {
        int xPos = movement.nextRequestedPositionFromCurrentPosition().getXPos();
        int yPos = movement.nextRequestedPositionFromCurrentPosition().getYPos();
        if (xPos < 0 || xPos >= roomWidth)
            return false;
        if (yPos < 0 || yPos >= roomHeight)
            return false;
        return true;
    }

    private Boolean validateObstacles(XYMovable movement) {
        for (Passable element : obstacles) {
            if (!element.isPassable(movement))
                return false;
        }
        return true;
    }

    private Boolean validateOccupiedFields(XYMovable movement) {
        for (Occupied element : occupiedFields) {
            if (element.isOccupied(movement.nextRequestedPositionFromCurrentPosition()))
                return false;
        }
        return true;
    }

    @Override
    public void addConnection(Connectable connection) {
        this.connections.add(connection);
    }

    @Override
    public Boolean isTransportable(XYPositionable currentPosition, Identifiable targetRoom) {
        try {
            findFirstMatchingConnection(currentPosition, targetRoom);
        } catch (NoDataFoundException e) {
            return false;
        }
        return true;
    }

    @Override
    public XYPositionable getNewTransportedPosition(XYPositionable currentPosition, Identifiable targetRoom)
            throws NoDataFoundException, PositionBlockedException
    {
        Connectable transportConnection = findFirstMatchingConnection(currentPosition, targetRoom);
        XYPositionable newPosition = transportConnection.getToPosition();
        if (newPosition.getRoom().roomPositionIsEmpty(newPosition)) {
            return newPosition;
        }
        throw new PositionBlockedException("Transport location is blocked or occupied at the moment.");
    }

    private Connectable findFirstMatchingConnection(XYPositionable position, Identifiable targetRoom)
            throws NoDataFoundException
    {
        for (Connectable connection : this.connections) {
            if (connection.getFromPosition().equals(position)) {
                if (connection.getToPosition().getRoomID().equals(targetRoom.getId())) {
                    return connection;
                }
            }
        }
        throw new NoDataFoundException("No matching connection found for current position and target room id.");
    }

    @Override
    public void debugPrintRoomStatus() {
        System.out.println("RoomID: "+id);
        System.out.println("Width/Height: ("+roomWidth+","+roomHeight+")");
        debugPrintPassable();
        debugPrintOccupiedFields();
        debugPrintConnections();
        System.out.println("\n");
    }

    private void debugPrintPassable() {
        System.out.println("Passable: ");
        if (obstacles.isEmpty())
            System.out.println("\tEMPTY");
        for (Passable obstacle : obstacles) {
            System.out.println(obstacle.debugPassableObjectToString());
        }
    }

    private void debugPrintOccupiedFields() {
        System.out.println("OccupiedFields: ");
        if (occupiedFields.isEmpty())
            System.out.println("\tEMPTY");
        for (Occupied occupied : occupiedFields) {
            System.out.println(occupied.debugOccupationStatusToString());
        }
    }

    private void debugPrintConnections() {
        System.out.println("Connections: ");
        if (connections.isEmpty())
            System.out.println("\tEMPTY");
        for (Connectable connection : connections) {
            System.out.println(connection.debugConnectionToString());
        }
    }
}
