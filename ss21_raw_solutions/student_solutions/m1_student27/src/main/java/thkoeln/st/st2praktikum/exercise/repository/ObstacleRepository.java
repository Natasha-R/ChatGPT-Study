package thkoeln.st.st2praktikum.exercise.repository;

import thkoeln.st.st2praktikum.exercise.entyties.Obstacle;

import java.util.UUID;

public interface ObstacleRepository {

    public Obstacle addObstacle(UUID spaceId, String wallString);
}
