package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class TidyUpRobot implements Robot {
    private UUID robotId;
    private String name;
    private Vector2D coordinate;
    private UUID robotRoomId;
    private Location location;
    private ApplyRobotCommand applyRobotCommand;

    public TidyUpRobot(String name){
        this.name = name;
        this.robotId = UUID.randomUUID();
        this.location = new Location();
        this.applyRobotCommand = new ApplyRobotCommand();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getRobotId() {
        return robotId;
    }

    @Override
    public UUID getRobotRoomId() {
        this.robotRoomId = location.getRobotNewRoomId();
        return robotRoomId;
    }

    @Override
    public Vector2D getCoordinate() {
        coordinate = location.getCoordinate();
        return coordinate;
    }

    @Override
    public Location getLocation(){
        return location;
    }

    public Boolean robotCommandInitialize(TidyUpRobot tidyUpRobot, Room room, Command command){
        return applyRobotCommand.roomInitialize(tidyUpRobot, room, command);
    }

    public Boolean robotCommandTransport(TidyUpRobot tidyUpRobot, Room currentRoom, Room destinationRoom, Command command){
        return applyRobotCommand.transportTo(tidyUpRobot, currentRoom, destinationRoom, command);
    }

    public Boolean robotCommandMove(TidyUpRobot tidyUpRobot, Room room, Command command){
        if(command.getCommandType().equals(CommandType.EAST)) {
            return applyRobotCommand.moveEast(tidyUpRobot, room, command);
        }
        else if(command.getCommandType().equals(CommandType.NORTH)){
            return applyRobotCommand.moveNorth(tidyUpRobot, room, command);
        }
        else if(command.getCommandType().equals(CommandType.SOUTH)){
            return applyRobotCommand.moveSouth(tidyUpRobot, room, command);
        }
        else
            return applyRobotCommand.moveWest(tidyUpRobot, room, command);
    }
}
