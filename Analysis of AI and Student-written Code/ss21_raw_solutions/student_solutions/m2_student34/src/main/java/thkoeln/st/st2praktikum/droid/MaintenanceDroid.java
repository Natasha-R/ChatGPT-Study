package thkoeln.st.st2praktikum.droid;

import lombok.Getter;
import thkoeln.st.st2praktikum.lib.Entity;
import thkoeln.st.st2praktikum.linearAlgebra.Rectangle;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.BoundedObstacle;
import thkoeln.st.st2praktikum.map.MapPosition;
import thkoeln.st.st2praktikum.map.Position;

import java.util.Objects;
import java.util.UUID;

@Getter
public class MaintenanceDroid extends Rectangle implements Droid, Entity {

    public static final double DEFAULT_WIDTH = 1.5;

    private final UUID id;
    private final String name;
    private Position position;

    public MaintenanceDroid(Position position, String name) {
        this.id = UUID.randomUUID();
        this.position = position;
        this.name = name;
    }

    public boolean move(Command command) {
        var movementStraight = MovementFactory.getInstance()
                .createMovement(this.position, command, this);

        return movementStraight.getTargetPosition().getMap()
                .maxMove(movementStraight).stream()
                .map(this::move)
                .findAny().orElse(false);

    }

    private boolean move(Movement movement) {
        if (!this.switchMap(movement)) {
            return false;
        }
        this.position = MapPosition.of(movement.getTargetPosition(), Vector
                .of(((int) movement.getTargetPosition().getCoordinates()
                                .getX()) + 0.5,
                        ((int) movement.getTargetPosition().getCoordinates()
                                .getY()) + 0.5));
        return true;
    }

    private boolean switchMap(Movement movement) {
        var sourcePosition = movement.getSourcePosition();
        var targetPosition = movement.getTargetPosition();
        if (sourcePosition.getMap() == null) {
            return targetPosition.getMap().enter(this, targetPosition);
        }
        if (!sourcePosition.getMap().equals(targetPosition.getMap())) {
            return sourcePosition.getMap().leave(this)
                    && targetPosition.getMap().enter(this, targetPosition);
        }
        return true;
    }

    @Override
    public BoundedObstacle[] getSites() {
        return new BoundedObstacle[]{
                new BoundedObstacle(this.getUpperLeftCorner(), this
                        .getUpperRightCorner()),
                new BoundedObstacle(this.getLowerLeftCorner(), this
                        .getLowerRightCorner()),
                new BoundedObstacle(this.getLowerLeftCorner(), this
                        .getUpperLeftCorner()),
                new BoundedObstacle(this.getLowerRightCorner(), this
                        .getUpperRightCorner())
        };
    }

    public Vector getLowerLeftCorner() {
        return this.position.getCoordinates().subtract(
                Vector.of(
                        DEFAULT_WIDTH / 2,
                        DEFAULT_WIDTH / 2
                )
        );
    }

    public Vector getUpperLeftCorner() {
        return this.position.getCoordinates().add(
                Vector.of(
                        DEFAULT_WIDTH / -2,
                        DEFAULT_WIDTH / 2
                )
        );
    }

    public Vector getLowerRightCorner() {
        return this.position.getCoordinates().add(
                Vector.of(
                        DEFAULT_WIDTH / 2,
                        DEFAULT_WIDTH / -2
                )
        );
    }

    public Vector getUpperRightCorner() {
        return this.position.getCoordinates().add(
                Vector.of(
                        DEFAULT_WIDTH / 2,
                        DEFAULT_WIDTH / 2
                )
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaintenanceDroid that = (MaintenanceDroid) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
