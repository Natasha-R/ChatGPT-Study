package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Command;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.UUID;

public interface PlaceInterface {
    public UUID setRoom(Integer height, Integer width);
    public UUID setTidyUpRobot(String name);
    public UUID setConnection(UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D);
    public void setObstacle(UUID roomId, Obstacle obstacle);
    public Vector2D getTidyUpRobotCoordinate(UUID tidyUpRobotId);
    public UUID getTidyUpRobotRoom(UUID tidyUpRobotId);
    public Boolean applyRobotCommand(UUID robotId, Command command);
}
