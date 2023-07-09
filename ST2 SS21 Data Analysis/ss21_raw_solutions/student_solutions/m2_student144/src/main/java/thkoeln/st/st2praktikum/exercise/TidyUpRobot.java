package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.*;
public class TidyUpRobot implements Spawnable, Movable{
    private String name;
    private UUID tidyUpRobotId;
    private UUID currentRoomId;
    private String currentPosition;
    private Integer currentUpDown;
    private Integer curentLeftRight;
    private  Boolean spawned;
    private Boolean hasMoved;

    public TidyUpRobot(String willBeName){

        name = willBeName;
        tidyUpRobotId = UUID.randomUUID();
        currentRoomId = null;
        currentPosition = "(0,0)";
        currentUpDown= 0;
        curentLeftRight = 0;
        spawned = false;
        hasMoved = false;

    }

    public void travel(UUID destinationRoomId, String destinationPosition){
        this.currentPosition = destinationPosition;
        this.currentRoomId = destinationRoomId;
        this.updatePositonIntegerAfterTravel();
    }
    //updates postition integers after connection travel
    private TidyUpRobot updatePositonIntegerAfterTravel(TidyUpRobot this){
        String currentLeftRightString = this.currentPosition.substring(this.currentPosition.lastIndexOf("(")+1,this.currentPosition.indexOf(","));
        String currentUpDownString = this.currentPosition.substring(this.currentPosition.lastIndexOf(",")+1,this.currentPosition.indexOf(")"));
        Integer actualLeftRight = Integer.parseInt(currentLeftRightString);
        Integer actualUpDown = Integer.parseInt(currentUpDownString);

        this.curentLeftRight = actualLeftRight;
        this.currentUpDown = actualUpDown;
        return this;

    }

    public Point curentPointOfRobot(){
     Point point = new Point(this.currentPosition);
     return point;
    }

    //checks if Robot is spawned
    private boolean isSpawnedCheck(TidyUpRobot this){
        if(this.spawned ==true){
            return true;
        }
        else {
            return false;
        }
    }

    //spawns robot in given room
    public Boolean spawnRobot(UUID currentRoomId, Boolean spawnPointBlocked){
        if(!this.isSpawnedCheck()&&spawnPointBlocked.equals(false)){
            this.currentRoomId = currentRoomId;
            this.spawned = true;
            return true;
        }
        else return false;
    }

    // checks if robot has moved since spawning
    private Boolean hasRobotMoved(TidyUpRobot this){
        if(this.currentPosition.equals("(0,0)")){
            return this.hasMoved=false;
        }
        else return this.hasMoved = true;
    }

    //makes the robot move
    public void moveRobot(Command command, List<Wall> relevantWalls, Integer maxHeight, Integer maxWith){
        Integer roomMaxHeight = maxHeight;
        Integer roomMaxWidth = maxWith;
        Integer newLeftRight, newUpDown;
        Integer amount = command.getNumberOfSteps()*command.addOrSubtract();
        Boolean upDown = command.getIsHorizontalMovement();
        newLeftRight = amountUnderZero(this.curentLeftRight+amount);
        newUpDown = amountUnderZero(this.currentUpDown+amount);
        if(!command.getIsHorizontalMovement()){
            this.currentUpDown = this.wichWall(!upDown,newUpDown, relevantWalls,roomMaxHeight,roomMaxWidth);
            this.currentPosition ="("+curentLeftRight+","+currentUpDown+")";
            this.hasMoved = this.hasRobotMoved();
        }
        else {
            this.curentLeftRight = this.wichWall(!upDown, newLeftRight, relevantWalls, roomMaxHeight,roomMaxWidth);
            this.currentPosition ="("+curentLeftRight+","+currentUpDown+")";
            this.hasMoved = this.hasRobotMoved();
        }




    }

    //checks if robot crashes with outer room wall
    private Integer checkBounderis(TidyUpRobot this, Integer newLeftOrUpDown, Boolean upDown,Integer roomMaxHeight, Integer roomMaxWidth){
        Integer maxHeight = roomMaxHeight;
        Integer maxWidth = roomMaxWidth;
        Integer minWidthHeight = 0;
        if(upDown.equals(false)){
            if(newLeftOrUpDown>maxWidth){
                newLeftOrUpDown = maxWidth;
                return newLeftOrUpDown;
            }
            if(newLeftOrUpDown<minWidthHeight){
                newLeftOrUpDown=minWidthHeight;
                return newLeftOrUpDown;
            }
            else {
                return newLeftOrUpDown;
            }
        }
        else {
            if(newLeftOrUpDown>maxHeight){
                newLeftOrUpDown=maxHeight;
                return newLeftOrUpDown;
            }
            if(newLeftOrUpDown<minWidthHeight){
                newLeftOrUpDown= minWidthHeight;
                return newLeftOrUpDown;
            }
            else {
                return newLeftOrUpDown;
            }
        }
    }

    //cheks if robot moves to negativ position
    private Integer amountUnderZero (Integer newLeftRightUpDown){
        if(newLeftRightUpDown<0){
            return 0;
        }
        else return newLeftRightUpDown;
    }

    // start wallcheck
    private Integer wichWall(Boolean upDown, Integer newLeftRightOrUpDown,List<Wall> relevantWalls, Integer maxHeight, Integer maxWidth){
        List<Wall> allRelevantWalls;
        allRelevantWalls = relevantWalls;
        if(upDown){
            for (int i =0;i<allRelevantWalls.size();i++){
                if(this.curentLeftRight>=allRelevantWalls.get(i).getStart().getX()&&this.curentLeftRight<allRelevantWalls.get(i).getEnd().getX()){
                    return this.robotIsInXclusionZone(newLeftRightOrUpDown,allRelevantWalls.get(i));
                }
            }
            return this.checkBounderis(newLeftRightOrUpDown, upDown,maxHeight,maxWidth);
        }
        else{
            allRelevantWalls = relevantWalls;
            for (int i =0;i<allRelevantWalls.size();i++){
                if(this.currentUpDown>=allRelevantWalls.get(i).getStart().getY()&&this.currentUpDown<allRelevantWalls.get(i).getEnd().getY()){
                    return this.robotIsInXclusionZone(newLeftRightOrUpDown,allRelevantWalls.get(i));
                }
                return this.checkBounderis(newLeftRightOrUpDown, !upDown,maxHeight,maxWidth);
            }
        }
        return this.checkBounderis(newLeftRightOrUpDown, upDown,maxHeight,maxWidth);
    }

    //checks for inner wall crash
    private Integer robotIsInXclusionZone(TidyUpRobot this, Integer newLeftRightOrUpDown, Wall wall){
        if (wall.getIsHorizontal()){
            if(this.curentLeftRight>=wall.getStart().getX()&&this.curentLeftRight<wall.getEnd().getX()){
                if(this.currentUpDown<wall.getStart().getY()){
                    //robot is in exclusionZone Back
                    return this.robotIsInBack(newLeftRightOrUpDown, wall);
                }
                else {
                    //robot is in exclusionZone Front
                    return this.robotIsInFront(newLeftRightOrUpDown, wall);
                }
            }
            else{
                //robot is not in exclusionZone
                return newLeftRightOrUpDown;
            }
        }
        else{
            if(this.currentUpDown>=wall.getStart().getY()&&this.currentUpDown<wall.getEnd().getY()){
                if(this.curentLeftRight<wall.getStart().getX()){
                    //robot is in exclusionZone back
                    return this.robotIsInBack(newLeftRightOrUpDown, wall);
                }
                else {
                    //robot is in exclusionZone Front
                    return this.robotIsInFront(newLeftRightOrUpDown, wall);
                }
            }
            else{
                //robot is not in exclusionZone
                return newLeftRightOrUpDown;
            }
        }
    }

    //positon when behinde wall
    private Integer robotIsInBack(TidyUpRobot this, Integer newUpDownOrLeftRight, Wall wall){
        if(wall.getIsHorizontal()){
            if(newUpDownOrLeftRight>=wall.getStart().getY()) newUpDownOrLeftRight=wall.getStart().getY()-1; return newUpDownOrLeftRight;
        }
        else {
            if(newUpDownOrLeftRight>=wall.getStart().getX()) newUpDownOrLeftRight=wall.getStart().getX()-1; return newUpDownOrLeftRight;
        }
    }

    //position when infront of wall
    private Integer robotIsInFront(TidyUpRobot this, Integer newUpDownOrLeftRight, Wall wall){
        if(wall.getIsHorizontal()){
            if(newUpDownOrLeftRight<wall.getStart().getY()) newUpDownOrLeftRight=wall.getStart().getY(); return newUpDownOrLeftRight;
        }
        else{
            if(newUpDownOrLeftRight<wall.getStart().getX()) newUpDownOrLeftRight=wall.getStart().getX(); return newUpDownOrLeftRight;
        }
    }

    public Boolean getSpawned() {
        return spawned;
    }

    public Boolean getHasMoved() {
        return hasMoved;
    }

    public UUID getCurrentRoomId() {
        return currentRoomId;
    }

    public UUID getTidyUpRobotId() {
        return tidyUpRobotId;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }
}


