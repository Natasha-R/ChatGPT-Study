package thkoeln.st.st2praktikum.exercise.robot;

import java.util.*;

public class RobotMap {

    HashMap<UUID, Robot> robotMap;

    // performs the move first and then checks if it is correct
    // if a robot collides it just moves to its original position
    public void moveRobot(UUID robotId,String direction){
        if(direction.equals("no")){
            getRobotById(robotId).moveNorth();
            if(robotCollision(robotId)){
                getRobotById(robotId).moveSouth();
            }
        }
        if(direction.equals("so")){
            getRobotById(robotId).moveSouth();
            if(robotCollision(robotId)){
                getRobotById(robotId).moveNorth();
            }
        }
        if(direction.equals("ea")){
            getRobotById(robotId).moveEast();
            if(robotCollision(robotId)){
                getRobotById(robotId).moveWest();
            }
        }
        if(direction.equals("we")){
            getRobotById(robotId).moveWest();
            if(robotCollision(robotId)){
                getRobotById(robotId).moveEast();
            }
        }
    }

    // the method checks if any other robot collides with the given robotId
    // if the robot has no roomId it can not collide with anything
    // it filters out all robots without roomId and itself and then checks if another robot collides with it
    public boolean robotCollision(UUID robotId){
        if(getRoomId(robotId) != null) {
            return robotMap.values().stream().filter(robot -> robot.getRoomId() != null && !robot.equals(getRobotById(robotId)))
                    .anyMatch(robot -> robot.getRoomId().equals(getRoomId(robotId)) &&
                            robot.getPosition().equals(robotPosition(robotId)));
        }else{
            return false;
        }
    }


    public Robot getRobotById(UUID robotId){
        return robotMap.get(robotId);
    }

    public String robotPosition(UUID robotId){
        return getRobotById(robotId).getPosition();
    }

    public UUID getRoomId(UUID robotId){
        return robotMap.get(robotId).getRoomId();
    }

    public void setRoomId(UUID robotId,UUID roomId){
        robotMap.get(robotId).setRoomId(roomId);
    }

    public void setX(UUID robotId,Integer x){
        robotMap.get(robotId).setX(x);
    }

    public void setY(UUID robotId,Integer y){
        robotMap.get(robotId).setY(y);
    }

    public Integer getX(UUID robotId){
        return getRobotById(robotId).getX();
    }

    public Integer getY(UUID robotId){
        return getRobotById(robotId).getY();
    }

    public RobotMap(){
        robotMap = new HashMap<>();
    }

    public UUID createRobot(String name){
        Robot robot = new Robot(name);
        robotMap.put(robot.getId(),robot);
        return robot.getId();
    }
}
