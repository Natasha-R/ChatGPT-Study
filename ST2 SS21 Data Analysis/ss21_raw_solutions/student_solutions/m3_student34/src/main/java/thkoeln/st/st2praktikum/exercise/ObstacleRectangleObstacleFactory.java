package thkoeln.st.st2praktikum.exercise;

import org.springframework.stereotype.Component;

@Component
public class ObstacleRectangleObstacleFactory {

    public RectangleObstacle createRectangleObstacle(thkoeln.st.st2praktikum.exercise.Obstacle obstacle) {
        return new RectangleObstacle(
                this.upperLeftCorner(obstacle),
                this.lowerLeftCorner(obstacle),
                this.upperRightCorner(obstacle),
                this.lowerRightCorner(obstacle)
        );
    }

    private Vector lowerLeftCorner(thkoeln.st.st2praktikum.exercise.Obstacle obstacle) {
        return Vector.of(obstacle.getStart()).subtract(
                Vector.of(
                        IObstacle.DEFAULT_OBSTACLE_WIDTH /2,
                        IObstacle.DEFAULT_OBSTACLE_WIDTH /2
                )
        );
    }

    private Vector upperLeftCorner(thkoeln.st.st2praktikum.exercise.Obstacle obstacle) {
        if (obstacle.isVertical()) {
            return Vector.of(obstacle.getEnd()).add(
                    Vector.of(
                            IObstacle.DEFAULT_OBSTACLE_WIDTH / -2,
                            IObstacle.DEFAULT_OBSTACLE_WIDTH / 2
                    )
            );
        }
        return Vector.of(obstacle.getStart()).add(
                Vector.of(
                        IObstacle.DEFAULT_OBSTACLE_WIDTH / -2,
                        IObstacle.DEFAULT_OBSTACLE_WIDTH / 2
                )
        );
    }

    private Vector lowerRightCorner(thkoeln.st.st2praktikum.exercise.Obstacle obstacle) {
        if (obstacle.isVertical()) {
            return Vector.of(obstacle.getStart()).add(
                    Vector.of(
                            IObstacle.DEFAULT_OBSTACLE_WIDTH /2,
                            IObstacle.DEFAULT_OBSTACLE_WIDTH /-2
                    )
            );
        }
        return Vector.of(obstacle.getEnd()).add(
                Vector.of(
                        IObstacle.DEFAULT_OBSTACLE_WIDTH /2,
                        IObstacle.DEFAULT_OBSTACLE_WIDTH /-2
                )
        );
    }

    private Vector upperRightCorner(thkoeln.st.st2praktikum.exercise.Obstacle obstacle) {
        return Vector.of(obstacle.getEnd()).add(
                Vector.of(
                        IObstacle.DEFAULT_OBSTACLE_WIDTH /2,
                        IObstacle.DEFAULT_OBSTACLE_WIDTH /2
                )
        );
    }
}
