package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.core.Identifiable;
import thkoeln.st.st2praktikum.exercise.core.Moveable;
import thkoeln.st.st2praktikum.exercise.core.PositionBlockedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public abstract class AbstractRoom implements Identifiable {

    @Id
    protected UUID id;
    protected Integer roomWidth;
    protected Integer roomHeight;

    @Getter
    @OneToMany (cascade = CascadeType.ALL)
    protected List<Obstacle> obstacles = new ArrayList<>();
    @Getter
    @OneToMany (cascade = CascadeType.ALL)
    protected List<TidyUpRobot> occupiedFields = new ArrayList<>();
    @Getter
    @OneToMany (cascade = CascadeType.ALL)
    protected List<Connection> connections = new ArrayList<>();


    abstract public Boolean roomPositionIsEmpty(Vector2D position);

    abstract public Moveable validateMovement(Moveable movement);

    abstract public Boolean isTransportable(Vector2D currentPosition, Room targetRoom);

    abstract public Vector2D getNewTransportedPosition(Vector2D currentPosition, Room targetRoom) throws PositionBlockedException;


    @Override
    public UUID getId() {
        return id;
    }

    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    public void addOccupiedPosition(TidyUpRobot robot) {
        this.occupiedFields.add(robot);
    }

    public void removeOccupiedPosition(UUID robotId) {
        ArrayList<TidyUpRobot> removeElements = new ArrayList<>();
        for (TidyUpRobot robot: occupiedFields) {
            if (robot.getId() == robotId) {
                removeElements.add(robot);
            }
        }
        for (TidyUpRobot robot : removeElements) {
            occupiedFields.remove(robot);
        }
    }

    public void addConnection(Connection connection) {
        this.connections.add(connection);
    }
}
