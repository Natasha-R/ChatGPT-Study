package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public interface RoomInterface {
    public void addObstacles(Obstacle obstacle);
    public void addConnections(Connection connection);
    public Integer getHeight();
    public Integer getWidth();
    public UUID getRoomId();
    public ArrayList<Obstacle> getObstacles();
    public Connection getConnection(UUID destinationRoomId);
    public Cell[][] getCell();
}
