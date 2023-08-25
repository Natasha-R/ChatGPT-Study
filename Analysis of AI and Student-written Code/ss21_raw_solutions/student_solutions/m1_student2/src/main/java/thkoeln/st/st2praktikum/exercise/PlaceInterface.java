package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface PlaceInterface {
    public UUID setRoom(Integer height,Integer width);
    public UUID setTidyUpRobot(String name);
    public UUID setConnection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate);
    public void setObstacle(UUID roomId, String obstacleString);
    public String getTidyUpRobotCoordinate(UUID tidyUpRobotId);
    public UUID getTidyUpRobotRoom(UUID tidyUpRobotRoomId);
    public Boolean applyRobotCommand(UUID robotId, String command);

}
