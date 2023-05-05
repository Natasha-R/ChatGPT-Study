package thkoeln.st.st2praktikum.exercise;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@javax.persistence.Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MaintenanceDroid extends Rectangle implements Droid, Entity {

    public static final double DEFAULT_WIDTH = 1.5;

    @Id
    private UUID id;
    private String name;
    @Transient
    private Position position;

    /*only for persisting*/
    @Embedded
    private StartPosition startPosition;
    @Embedded
    private MapPosition mapPosition;

    public MaintenanceDroid(Position position, String name) {
        this.id = UUID.randomUUID();
        this.setPosition(position);
        this.name = name;
    }

    public boolean move(Command command) {
        var movementStraight = MovementFactory.getInstance()
                .createMovement(this.getPosition(), command, this);

        return movementStraight.getTargetPosition().getMap()
                .maxMove(movementStraight).stream()
                .map(this::move)
                .findAny().orElse(false);

    }

    private boolean move(Movement movement) {
        if (!this.switchMap(movement)) {
            return false;
        }
        this.setPosition(MapPosition.of(movement.getTargetPosition(), Vector
                .of(((int) movement.getTargetPosition().getCoordinates()
                                .getX()) + 0.5,
                        ((int) movement.getTargetPosition().getCoordinates()
                                .getY()) + 0.5))
        );
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
        return this.getPosition().getCoordinates().subtract(
                Vector.of(
                        DEFAULT_WIDTH / 2,
                        DEFAULT_WIDTH / 2
                )
        );
    }

    public Vector getUpperLeftCorner() {
        return this.getPosition().getCoordinates().add(
                Vector.of(
                        DEFAULT_WIDTH / -2,
                        DEFAULT_WIDTH / 2
                )
        );
    }

    public Vector getLowerRightCorner() {
        return this.getPosition().getCoordinates().add(
                Vector.of(
                        DEFAULT_WIDTH / 2,
                        DEFAULT_WIDTH / -2
                )
        );
    }

    public Vector getUpperRightCorner() {
        return this.getPosition().getCoordinates().add(
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

    private void setPosition(Position position) {
        this.mapPosition = MapPosition.NULL;
        this.startPosition = StartPosition.NULL;
        if(position instanceof StartPosition) {
            this.startPosition = (StartPosition) position;
        } else if (position instanceof MapPosition) {
            this.mapPosition = (MapPosition) position;
        }
        this.postLoad();
    }

    @PostLoad
    private void postLoad() {
        if(this.startPosition != StartPosition.NULL) {
            this.position = this.startPosition;
        } else if(this.mapPosition != MapPosition.NULL) {
            this.position = this.mapPosition;
        } else {
            throw new IllegalStateException("Either startPosition or mapPosition must be set");
        }
    }

    public Position getPosition() {
        this.postLoad();
        return this.position;
    }

    public UUID getSpaceShipDeckId() {
        return this.getPosition().getMap().getId();
    }

    public Coordinate getCoordinate() {
        return new Coordinate(this.getPosition().getCoordinates());
    }
}
