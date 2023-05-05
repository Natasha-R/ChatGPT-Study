package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface PlaceInterface {
    public UUID setRoom(Integer height, Integer width);
    public UUID setTidyUpRobot(String name);
    public UUID setTransportSystem(String system);
    public UUID setConnection(UUID transportSystemId, UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D);
    public void setObstacle(UUID roomId, Obstacle obstacle);
    public Vector2D getTidyUpRobotCoordinate(UUID tidyUpRobotId);
    public UUID getTidyUpRobotRoom(UUID tidyRobotId);
    public Boolean applyRobotCommand(UUID robotId, Command command);
}
