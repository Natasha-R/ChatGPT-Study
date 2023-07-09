package thkoeln.st.st2praktikum.map;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

@Component
public class StringRectangleObstacleFactory {

    public RectangleObstacle createRectangleObstacle(String obstacleString) {
        Pair<Vector, Vector> coordinates;
        try {
            coordinates = this.parseObstacleString(obstacleString);
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(e);
        }
        coordinates = this.switchPointsWhenNecessary(coordinates);
        return new RectangleObstacle(
                this.upperLeftCorner(coordinates),
                this.lowerLeftCorner(coordinates),
                this.upperRightCorner(coordinates),
                this.lowerRightCorner(coordinates)
        );
    }

    private Pair<Vector, Vector> parseObstacleString(String input) {
        try {
            var stringTokenizer = new StringTokenizer(input, "-");
            return Pair.of(
                    Vector.of(stringTokenizer.nextToken()),
                    Vector.of(stringTokenizer.nextToken())
            );
        } catch (Exception e) {
            throw new IllegalArgumentException(input + " could not be parsed"
                    , e);
        }
    }

    private boolean isVertical(Pair<Vector, Vector> coordinates) {
        return coordinates.getFirst().getX() == coordinates.getSecond().getX();
    }

    private Pair<Vector, Vector> switchPointsWhenNecessary(Pair<Vector,
            Vector> coordinates) {
        if ((this.isVertical(coordinates) && coordinates
                .getFirst().getY() > coordinates.getSecond().getY())
                || (!this.isVertical(coordinates) && coordinates
                .getFirst().getX() > coordinates.getSecond().getX())) {
            return Pair.of(coordinates.getSecond(), coordinates.getFirst());
        }
        return coordinates;
    }

    private Vector lowerLeftCorner(Pair<Vector, Vector> coordinates) {
        return coordinates.getFirst().subtract(
                Vector.of(
                        Obstacle.DEFAULT_OBSTACLE_WIDTH / 2,
                        Obstacle.DEFAULT_OBSTACLE_WIDTH / 2
                )
        );
    }

    private Vector upperLeftCorner(Pair<Vector, Vector> coordinates) {
        if (this.isVertical(coordinates)) {
            return coordinates.getSecond().add(
                    Vector.of(
                            Obstacle.DEFAULT_OBSTACLE_WIDTH / -2,
                            Obstacle.DEFAULT_OBSTACLE_WIDTH / 2
                    )
            );
        }
        return coordinates.getFirst().add(
                Vector.of(
                        Obstacle.DEFAULT_OBSTACLE_WIDTH / -2,
                        Obstacle.DEFAULT_OBSTACLE_WIDTH / 2
                )
        );
    }

    private Vector lowerRightCorner(Pair<Vector, Vector> coordinates) {
        if (this.isVertical(coordinates)) {
            return coordinates.getFirst().add(
                    Vector.of(
                            Obstacle.DEFAULT_OBSTACLE_WIDTH /2,
                            Obstacle.DEFAULT_OBSTACLE_WIDTH /-2
                    )
            );
        }
        return coordinates.getSecond().add(
                Vector.of(
                        Obstacle.DEFAULT_OBSTACLE_WIDTH /2,
                        Obstacle.DEFAULT_OBSTACLE_WIDTH /-2
                )
        );
    }

    private Vector upperRightCorner(Pair<Vector, Vector> coordinates) {
        return coordinates.getSecond().add(
                Vector.of(
                        Obstacle.DEFAULT_OBSTACLE_WIDTH /2,
                        Obstacle.DEFAULT_OBSTACLE_WIDTH /2
                )
        );
    }
}
