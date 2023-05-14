package thkoeln.st.st2praktikum.map;

import lombok.AccessLevel;
import lombok.Getter;
import thkoeln.st.st2praktikum.droid.Droid;
import thkoeln.st.st2praktikum.droid.Movement;
import thkoeln.st.st2praktikum.lib.AbstractEntity;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.*;
import java.util.function.Function;

public class SpaceShipDeck extends AbstractEntity implements Map {

    @Getter(AccessLevel.PACKAGE)
    private final List<Obstacle> obstacles;
    private final double height;
    private final double width;

    public SpaceShipDeck(double height, double width) {
        this.obstacles = new LinkedList<>();
        this.height = height;
        this.width = width;
    }

    public SpaceShipDeck(List<Obstacle> obstacles, double height, double width) {
        this.obstacles = new LinkedList<>(obstacles);
        this.height = height;
        this.width = width;
    }

    public Optional<Movement> maxMove(Movement move) {
        if (!move.getTargetPosition().getMap().equals(this)) {
            return Optional.empty();
        }

        var sourcePosition = move.getSourcePosition();
        var targetPosition =
                this.obstacles.stream().flatMap(it -> move.cut(it).stream())
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
        this.obstacles.add(obstacle);
    }

    @Override
    public boolean isInBounds(Position position) {
        if (!position.getMap().equals(this)) {
            return false;
        }
        return new RectangleObstacle(
                Vector.of(0 - Obstacle.DEFAULT_OBSTACLE_WIDTH,
                        this.height + Obstacle.DEFAULT_OBSTACLE_WIDTH),
                Vector.of(0 - Obstacle.DEFAULT_OBSTACLE_WIDTH, 0 - Obstacle.DEFAULT_OBSTACLE_WIDTH),
                Vector.of(this.width + Obstacle.DEFAULT_OBSTACLE_WIDTH,
                        this.height + Obstacle.DEFAULT_OBSTACLE_WIDTH),
                Vector.of(this.width + Obstacle.DEFAULT_OBSTACLE_WIDTH,
                        0 - Obstacle.DEFAULT_OBSTACLE_WIDTH)
        ).cut(position).isPresent();
    }

    @Override
    public boolean enter(Droid droid, Position targetPosition) {
        if (this.obstacles.contains(droid)) {
            return false;
        }
        if(!this.isInBounds(targetPosition)) {
            return false;
        }

        var cutsAnything = this.obstacles.stream()
                .flatMap(it -> it.cut(targetPosition).stream())
                .findAny().isPresent();
        if (cutsAnything) {
            return false;
        }
        return this.obstacles.add(droid);
    }

    @Override
    public boolean leave(Droid droid) {
        return this.obstacles.remove(droid);
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
            List<Obstacle> boundaries = Arrays.asList(
                    new InfiniteObstacle(Vector.of(1, 0), Vector.of(0, 0)),
                    new InfiniteObstacle(Vector.of(1, 0), Vector.of(0,
                            height - Obstacle.DEFAULT_OBSTACLE_WIDTH / 2)),
                    new InfiniteObstacle(Vector.of(0, 1), Vector.of(0, 0)),
                    new InfiniteObstacle(Vector.of(0, 1),
                            Vector.of(width - Obstacle.DEFAULT_OBSTACLE_WIDTH / 2, 0))
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
