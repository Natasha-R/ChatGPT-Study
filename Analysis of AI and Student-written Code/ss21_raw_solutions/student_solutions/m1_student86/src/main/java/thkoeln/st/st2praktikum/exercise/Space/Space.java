package thkoeln.st.st2praktikum.exercise.Space;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Obstacle.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

abstract public class Space {
    @Id
    @Getter
    private UUID id = UUID.randomUUID();

    protected List<Obstacle> obstacleList = new ArrayList<>();

    public void addObstacle (Obstacle obstacle) {
        obstacle.buildUp(this);
        obstacleList.add(obstacle);
    }

    public void removeObstacle (Obstacle obstacle) {
        obstacle.tearDown(this);
        obstacleList.remove(obstacle);
    }

    public Tile getTile (Coordinate position) {
        return getTile(position.getX(), position.getY());
    }

    abstract public Tile getTile (Integer x, Integer y);
}
