package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public interface RoomInterface {
    public void addObstacle(String obstacleString);
    public void addConnections(Connection connection);
    public Integer getHeight();
    public Integer getWidth();
    public UUID getRoomId();
    public ArrayList<Pair<Integer,Integer>> getObstacleStartCoordinate();
    public ArrayList<Pair<Integer,Integer>> getObstacleEndCoordinate();
    public Connection getConnection(UUID destinationRoomId);
    public Cell[][] getCell();
}
