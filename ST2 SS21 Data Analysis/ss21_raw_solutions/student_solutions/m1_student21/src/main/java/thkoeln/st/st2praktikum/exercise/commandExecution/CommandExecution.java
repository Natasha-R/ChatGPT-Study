package thkoeln.st.st2praktikum.exercise.commandExecution;

import thkoeln.st.st2praktikum.exercise.connection.ConnectionMap;
import thkoeln.st.st2praktikum.exercise.robot.RobotMap;
import thkoeln.st.st2praktikum.exercise.room.RoomMap;

import java.util.*;

public class CommandExecution {
    ConnectionMap connectionMap;
    RobotMap robotMap;
    RoomMap roomMap;

    public boolean executeCommand(UUID robotId,String commandString){
        String[] commandArray = commandString.replaceAll("[\\[\\]]","").split(",");
        if(commandArray[0].matches("no|so|we|ea")){
            moveRobot(robotMap.getRoomId(robotId),robotId,commandArray[0],Integer.parseInt(commandArray[1]));
            return true;
        }else if(commandArray[0].equals("tr")){
            UUID destinationRoomId = UUID.fromString(commandArray[1]);
            return transportRobotTo(robotId,robotMap.getRoomId(robotId),destinationRoomId);
        }else if(commandArray[0].equals("en")){
            UUID roomId = UUID.fromString(commandArray[1]);
            return placeRobotInRoom(robotId,roomId,0,0);
        }
        return false;
    }

    // checks if the robot will hit a wall
    // the collision of robots is handled in the RobotMap Class
    private void moveRobot(UUID roomId,UUID robotId,String direction,Integer steps){
        if(steps==0){return;}
        if(roomMap.canIGoThere(roomId,direction,robotMap.getX(robotId), robotMap.getY(robotId))){
            robotMap.moveRobot(robotId,direction);
        }
        moveRobot(roomId,robotId,direction,steps-1);
        return;
    }

    // check if the connection between the rooms even exists
    // check if robot is on the right transport position of the source room
    // move the robot to the desired location
    // collisions are handled by the placeRobotAt method
    private boolean transportRobotTo(UUID robotId,UUID sourceRoomId,UUID destinationRoomId) {
        if(connectionMap.connectionExists(sourceRoomId, destinationRoomId)){
            UUID connectionId = connectionMap.getConnectionByRoomIds(sourceRoomId, destinationRoomId);
            if (connectionMap.getSourceY(connectionId) == robotMap.getY(robotId) &&
                    connectionMap.getSourceX(connectionId) == robotMap.getX(robotId)) {
                return placeRobotInRoom(robotId,destinationRoomId, connectionMap.getDestinationX(connectionId),connectionMap.getDestinationY(connectionId));
            }
        }
        return false;
    }

    // save old position and room
    // move to desired location
    // if robot is colliding then revert back to old position and room
    private boolean placeRobotInRoom(UUID robotId, UUID roomId,Integer x,Integer y) {
        Integer oldX = robotMap.getX(robotId);
        Integer oldY = robotMap.getY(robotId);
        UUID oldRoom = robotMap.getRoomId(robotId);
        robotMap.setRoomId(robotId,roomId);
        robotMap.setX(robotId,x);
        robotMap.setY(robotId,y);
        if(robotMap.robotCollision(robotId)){
            robotMap.setRoomId(robotId,oldRoom);
            return false;
        }
        return true;
    }

    public CommandExecution(RobotMap robotMap,RoomMap roomMap,ConnectionMap connectionMap){
        this.connectionMap = connectionMap;
        this.roomMap = roomMap;
        this.robotMap = robotMap;
    }
}