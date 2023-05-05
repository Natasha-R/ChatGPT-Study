package thkoeln.st.st2praktikum.exercise;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.*;
import java.util.function.Function;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpaceShipDeck extends AbstractEntity implements Map {

//    @Transient
//    private List<IObstacle> obstacles = new LinkedList<>();
    private double height;
    private double width;

    /* only for persisting */
    @ElementCollection
    private List<InfiniteObstacle> infiniteObstacles = new LinkedList<>();
    @ElementCollection
    private List<BoundedObstacle> boundedObstacles = new LinkedList<>();
    @OneToMany
    private List<MaintenanceDroid> maintenanceDroids = new LinkedList<>();
    @ElementCollection
    private List<RectangleObstacle> rectangleObstacles = new LinkedList<>();

    public SpaceShipDeck(double height, double width) {
        this.height = height;
        this.width = width;
    }

    public SpaceShipDeck(List<IObstacle> obstacles, double height, double width) {
        obstacles.forEach(this::appendToObstacles);
        this.height = height;
        this.width = width;
    }

    public Optional<Movement> maxMove(Movement move) {
        if (!move.getTargetPosition().getMap().equals(this)) {
            return Optional.empty();
        }

        var sourcePosition = move.getSourcePosition();
        var targetPosition =
                this.getObstacles().stream().flatMap(it -> move.cut(it).stream())
                        .map((Function<Vector, Position>) it -> MapPosition
                                .of(move.getTargetPosition(), it))
                        .min(Comparator.comparing(sourcePosition::distance))
                        .orElse(move.getTargetPosition());

        if (targetPosition == move.getTargetPosition()) {
            return Optional.of(move);
        }
        try {
            return Optional.of(move.changeTargetPosition(targetPosition));
        } catch (IllegalStateException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void addRectangleObstacle(RectangleObstacle obstacle) {
        var upperLeftPosition = MapPosition.of(this,
                obstacle.getUpperLeftCorner());
        var lowerLeftPosition = MapPosition.of(this,
                obstacle.getLowerLeftCorner());
        var upperRightPosition = MapPosition.of(this,
                obstacle.getUpperRightCorner());
        var lowerRightPosition = MapPosition.of(this,
                obstacle.getLowerRightCorner());

        if (!(this.isInBounds(upperLeftPosition)
                && this.isInBounds(lowerLeftPosition)
                && this.isInBounds(upperRightPosition)
                && this.isInBounds(lowerRightPosition))) {
            throw new IllegalArgumentException(
                    "obstacle is not in bounds: " + obstacle);
        }
        this.appendToObstacles(obstacle);
    }

    @Override
    public boolean isInBounds(Position position) {
        if (!position.getMap().equals(this)) {
            return false;
        }
        return new RectangleObstacle(
                Vector.of(0 - IObstacle.DEFAULT_OBSTACLE_WIDTH,
                        this.height + IObstacle.DEFAULT_OBSTACLE_WIDTH),
                Vector.of(0 - IObstacle.DEFAULT_OBSTACLE_WIDTH, 0 - IObstacle.DEFAULT_OBSTACLE_WIDTH),
                Vector.of(this.width + IObstacle.DEFAULT_OBSTACLE_WIDTH,
                        this.height + IObstacle.DEFAULT_OBSTACLE_WIDTH),
                Vector.of(this.width + IObstacle.DEFAULT_OBSTACLE_WIDTH,
                        0 - IObstacle.DEFAULT_OBSTACLE_WIDTH)
        ).cut(position).isPresent();
    }

    @Override
    public boolean enter(Droid droid, Position targetPosition) {
        if (this.getObstacles().contains(droid)) {
            return false;
        }
        if(!this.isInBounds(targetPosition)) {
            return false;
        }

        var cutsAnything = this.getObstacles().stream()
                .flatMap(it -> it.cut(targetPosition).stream())
                .findAny().isPresent();
        if (cutsAnything) {
            return false;
        }
        return this.appendToObstacles(droid);
    }

    @Override
    public boolean leave(Droid droid) {
        return this.maintenanceDroids.remove(droid);
    }

    private boolean appendToObstacles(IObstacle obstacle) {
        if(obstacle instanceof InfiniteObstacle) {
            return this.infiniteObstacles.add((InfiniteObstacle) obstacle);
        } else if(obstacle instanceof BoundedObstacle) {
            return this.boundedObstacles.add((BoundedObstacle) obstacle);
        } else if(obstacle instanceof MaintenanceDroid) {
            return this.maintenanceDroids.add((MaintenanceDroid) obstacle);
        } else if(obstacle instanceof RectangleObstacle) {
            return this.rectangleObstacles.add((RectangleObstacle) obstacle);
        }
        return false;
        //return this.obstacles.add(obstacle);
    }

    private List<IObstacle> getObstacles() {
        List<IObstacle> obstacles = new LinkedList<>();
        obstacles.addAll(this.boundedObstacles);
        obstacles.addAll(this.infiniteObstacles);
        obstacles.addAll(this.maintenanceDroids);
        obstacles.addAll(this.rectangleObstacles);
        return obstacles;
    }

    @PostLoad
    private void postLoad() {

    }

    public static class SpaceShipDeckBuilder {

        private double height = Double.MAX_VALUE;
        private double width = Double.MAX_VALUE;

        public static SpaceShipDeckBuilder builder() {
            return new SpaceShipDeckBuilder();
        }

        public SpaceShipDeck build() {
            if (this.height == Double.MAX_VALUE || this.width == Double.MAX_VALUE) {
                throw new IllegalStateException();
            }
            List<IObstacle> boundaries = Arrays.asList(
                    new InfiniteObstacle(Vector.of(1, 0), Vector.of(0, 0)),
                    new InfiniteObstacle(Vector.of(1, 0), Vector.of(0,
                            height - IObstacle.DEFAULT_OBSTACLE_WIDTH / 2)),
                    new InfiniteObstacle(Vector.of(0, 1), Vector.of(0, 0)),
                    new InfiniteObstacle(Vector.of(0, 1),
                            Vector.of(width - IObstacle.DEFAULT_OBSTACLE_WIDTH / 2, 0))
            );
            return new SpaceShipDeck(boundaries, this.height, this.width);
        }

        public SpaceShipDeckBuilder setHeight(double height) {
            this.height = height;
            return this;
        }

        public SpaceShipDeckBuilder setWidth(double width) {
            this.width = width;
            return this;
        }
    }
}
