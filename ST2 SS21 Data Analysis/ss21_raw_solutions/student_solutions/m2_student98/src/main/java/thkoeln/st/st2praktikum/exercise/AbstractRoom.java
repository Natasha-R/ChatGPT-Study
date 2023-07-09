package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public abstract class AbstractRoom implements Identifiable {

    protected UUID id;
    protected Integer roomWidth;
    protected Integer roomHeight;
    protected ArrayList<Passable> obstacles = new ArrayList<>();
    protected ArrayList<Occupied> occupiedFields = new ArrayList<>();
    protected ArrayList<Connectable> connections = new ArrayList<>();


    abstract public Boolean roomPositionIsEmpty(Vector2DRoom position);

    abstract public Moveable validateMovement(Moveable movement);

    abstract public Boolean isTransportable(Vector2DRoom currentPosition, Identifiable targetRoom);

    abstract public Vector2DRoom getNewTransportedPosition(Vector2DRoom currentPosition, Identifiable targetRoom) throws PositionBlockedException;


    @Override
    public UUID getId() {
        return id;
    }

    public void addObstacle(Passable obstacle) {
        obstacles.add(obstacle);
    }

    public void addOccupiedPosition(Occupied occupiedElement) {
        this.occupiedFields.add(occupiedElement);
    }

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

    public void addConnection(Connectable connection) {
        this.connections.add(connection);
    }
}
