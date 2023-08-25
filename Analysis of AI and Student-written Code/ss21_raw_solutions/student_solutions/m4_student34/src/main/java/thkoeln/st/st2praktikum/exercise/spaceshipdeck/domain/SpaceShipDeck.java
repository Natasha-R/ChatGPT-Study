package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.core.Maybe;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpaceShipDeck extends AbstractEntity {

    @ElementCollection
    private List<Obstacle> obstacles;
    @OneToMany
    private List<MaintenanceDroid> droids;

    private Integer width;
    private Integer height;

    public SpaceShipDeck(int width, int height) {
        this.obstacles = new LinkedList<>();
        this.droids = new LinkedList<>();

        this.width = width;
        this.height = height;
    }

    public boolean isExecutable(TaskType taskType, MaintenanceDroid maintenanceDroid) {
        switch (taskType) {
            case ENTER:
                return !this.isOccupied(new Coordinate(0, 0));
            case NORTH:
            case EAST:
            case SOUTH:
            case WEST:
                Coordinate targetCoordinate;
                try {
                    targetCoordinate = maintenanceDroid.getCoordinate().move(taskType, 1);
                } catch (IllegalArgumentException e) {
                    return false;
                }
                return !this.isOccupied(targetCoordinate)
                        && this.isInBounds(targetCoordinate)
                        && !this.cutsAnything(taskType, maintenanceDroid.getCoordinate());
            default:
                throw new UnsupportedOperationException();
        }
    }

    public boolean isExecutable(Connection connection, MaintenanceDroid maintenanceDroid) {
        if(this.equals(connection.getSourceSpaceShip())) {
            return this.droids.contains(maintenanceDroid);
        }

        if(this.equals(connection.getDestinationSpaceShipDeck())) {
            return this.isInBounds(connection.getDestinationCoordinate()) && !this.isOccupied(connection.getDestinationCoordinate());
        }

        return false;
    }

    private boolean isOccupied(Coordinate coordinate) {
        return this.droids.stream()
                .anyMatch(it -> it.getCoordinate() != null && it.getCoordinate().equals(coordinate));
    }

    public void addObstacle(Obstacle obstacle) {
        if (!(this.isInBounds(obstacle.getStart()) && this.isInBounds(obstacle.getEnd()))) {
            throw new OutOfBoundsException("Obstacle " + obstacle + " is out of bounds");
        }
        this.obstacles.add(obstacle);
    }

    public boolean enter(MaintenanceDroid droid) {
        if (!this.isExecutable(TaskType.ENTER, droid)) {
            return false;
        }
        return this.droids.add(droid);
    }

    public void leave(MaintenanceDroid droid) {
        this.droids.remove(droid);
    }

    public List<MaintenanceDroid> getDroids() {
        return Collections.unmodifiableList(this.droids);
    }

    private boolean isInBounds(Coordinate coordinate) {
        return coordinate.getX() <= this.width
                && coordinate.getY() <= this.height
                && coordinate.getX() >= 0
                && coordinate.getY() >= 0;
    }

    private boolean cutsAnything(TaskType taskType, Coordinate sourceCoordinate) {
        return this.obstacles.stream()
                .anyMatch(it -> it.cuts(taskType, sourceCoordinate));
    }
}
