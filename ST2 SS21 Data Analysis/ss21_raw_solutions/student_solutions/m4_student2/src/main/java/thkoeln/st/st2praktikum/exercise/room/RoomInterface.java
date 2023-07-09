package thkoeln.st.st2praktikum.exercise.room;

import thkoeln.st.st2praktikum.exercise.room.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import java.util.UUID;

public interface RoomInterface {
    public void addObstacles(Obstacle obstacle);
    public void addConnections(Connection connection);
    public Connection getConnection(UUID destinationRoomId);
    public void deleteAllObstacle();
    public void deleteObstacle(Obstacle obstacle);
}
