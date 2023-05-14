package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import java.util.*;
public class TidyUpRobot{
    protected String name;
    protected UUID tidyUpRobotId;
    protected UUID currentRoomId;
    protected String currentPosition;
    protected Integer currentUpDown;
    protected Integer curentLeftRight;
    protected  Boolean isSpawned;
    protected Boolean hasMoved;

    public TidyUpRobot(String willBeName){
        name = willBeName;
        tidyUpRobotId = UUID.randomUUID();
        currentRoomId = null;
        currentPosition = "(0,0)";
        currentUpDown= 0;
        curentLeftRight = 0;
        isSpawned = false;
        hasMoved = false;

    }
    //executes the command
    public boolean understandCommandString(TidyUpRobot this,String commandString){
        String commandtype = commandString.substring(1,3);
        if(commandtype.equals("no")||commandtype.equals("we")||commandtype.equals("so")||commandtype.equals("ea")){
            //move command
            this.moveRobot(commandString);
            return true;
        }
        if(commandtype.equals("tr")){
            //transport command
            return this.connectionTravel(this.traversableConnectionsearch(this.currentRoomId));
        }
        else{
            //entrance command
            return this.spawnRobot(nFindUUID(commandString));
        }
    }

    //checks for connection
    private boolean traversableConnectionsearch(TidyUpRobot this, UUID currentRoomId){
        if(TraversableConnectionMap.findConnectionInRoom(this.currentRoomId).sourceCoordinate.equals(this.currentPosition)){
            return true;
        }
        else return false;
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

    //robot uses Connection if found
    private Boolean connectionTravel(TidyUpRobot this, boolean connectionFound){
        if(connectionFound==true){
            this.currentPosition = TraversableConnectionMap.findConnectionInRoom(this.currentRoomId).destinationCoordinate;
            this.currentRoomId = TraversableConnectionMap.findConnectionInRoom(this.currentRoomId).destinationRoomId;
            this.updatePositonIntegerAfterTravel();
            return true;
        }
        else {
            return false;
        }
    }

    //finds UUID in commandstring
    private UUID nFindUUID(String commansString){
        UUID theUUID = UUID.fromString(commansString.substring(4,commansString.indexOf("]"))) ;
        return theUUID;
    }

    //checks if Robot is spawned
    private boolean isSpawnedCheck(TidyUpRobot this){
        if(this.isSpawned==true){
            return true;
        }
        else {
            return false;
        }
    }

    //spawns robot in given room
    private boolean spawnRobot(TidyUpRobot this, UUID currentRoomId){
        if(!this.isSpawnedCheck()&&TidyUpRobotMap.unMovedRobot(currentRoomId).equals(false)){
            this.currentRoomId = currentRoomId;
            this.isSpawned = true;
            return true;
        }
        else return false;
    }

    //checks for the right coordinate to change
    private boolean firstOrSecondCoordinate(String commandString){
        boolean upDown;
        String direction = commandString.substring(1,3);
        if(direction.equals("no")||direction.equals("so")){
            return upDown = true;
        }
        if(direction.equals("ea")||direction.equals("we")){
            return  upDown = false;
        }
        return false;
    }

    //checks if to add or to subtract
    private Integer addOrSubtract(String commandString){
        String direction = commandString.substring(1,3);
        if(direction.equals("no")|| direction.equals("ea")){
            return 1;
        }
        else{
            return -1;
        }

    }

    //Looks for the amount to go
    private Integer howFarToGo(String commandString){
        return Integer.parseInt(commandString.substring(4,commandString.indexOf("]")));
    }

    // cheks if robots has moved since spawning
    private boolean hasRobotMoved(TidyUpRobot this){
        if(this.currentPosition.equals("(0,0)")){
            return this.hasMoved=false;
        }
        else return this.hasMoved = true;
    }

    //makes the robot move
    private void moveRobot(TidyUpRobot this, String commandString){
        Integer newLeftRight, newUpDown;
        Integer amount = howFarToGo(commandString)*addOrSubtract(commandString);
        boolean upDown =firstOrSecondCoordinate(commandString);
        newLeftRight = amountUnderZero(this.curentLeftRight+amount);
        newUpDown = amountUnderZero(this.currentUpDown+amount);
        if(upDown){
            this.currentUpDown = this.wichWall(upDown,newUpDown);
            this.currentPosition ="("+curentLeftRight+","+currentUpDown+")";
            this.hasMoved = this.hasRobotMoved();
        }
        else {
            this.curentLeftRight = this.wichWall(upDown, newLeftRight);
            this.currentPosition ="("+curentLeftRight+","+currentUpDown+")";
            this.hasMoved = this.hasRobotMoved();
        }




    }

    //checks if robot crashes with outer room wall
    private Integer checkBounderis(TidyUpRobot this, Integer newLeftOrUpDown, Boolean upDown){
        Integer maxHeight = RoomMap.getByUUID(this.currentRoomId).height-1;
        Integer maxWidth = RoomMap.getByUUID(this.currentRoomId).width-1;
        Integer minMaxWidthHeight = 0;
        if(upDown.equals(false)){
            if(newLeftOrUpDown>maxWidth){
                newLeftOrUpDown = maxWidth;
                return newLeftOrUpDown;
            }
            if(newLeftOrUpDown<minMaxWidthHeight){
                newLeftOrUpDown=minMaxWidthHeight;
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
            if(newLeftOrUpDown<minMaxWidthHeight){
                newLeftOrUpDown= minMaxWidthHeight;
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
    private Integer wichWall(TidyUpRobot this, Boolean upDown, Integer newLeftRightOrUpDown){
        List<Wall> allRelevantWalls;
        allRelevantWalls = WallMap.findAllRelevantWallsInRoom(this.currentRoomId, upDown);
        if(upDown){
            for (int i =0;i<allRelevantWalls.size();i++){
                if(this.curentLeftRight>=allRelevantWalls.get(i).firstLeftRight&&this.curentLeftRight<allRelevantWalls.get(i).secondLeftRight){
                    return this.isInXclusionZone(newLeftRightOrUpDown,allRelevantWalls.get(i));
                }
            }
            return this.checkBounderis(newLeftRightOrUpDown, upDown);
        }
        else{
            allRelevantWalls = WallMap.findAllRelevantWallsInRoom(this.currentRoomId, upDown);
            for (int i =0;i<allRelevantWalls.size();i++){
                if(this.currentUpDown>=allRelevantWalls.get(i).firstUpDown&&this.currentUpDown<allRelevantWalls.get(i).secondUpDown){
                    return this.isInXclusionZone(newLeftRightOrUpDown,allRelevantWalls.get(i));
                }
                return this.checkBounderis(newLeftRightOrUpDown, !upDown);
            }
        }
        return this.checkBounderis(newLeftRightOrUpDown, upDown);
    }

    //checks for inner wall crash
    private Integer isInXclusionZone(TidyUpRobot this, Integer newLeftRightOrUpDown, Wall wall){
        if (wall.isUpDown){
            if(this.curentLeftRight>=wall.firstLeftRight&&this.curentLeftRight<wall.secondLeftRight){
                if(this.currentUpDown<wall.firstUpDown){
                    //robot is in exclusionZone Back
                    return this.isInBack(newLeftRightOrUpDown, wall);
                }
                else {
                    //robot is in exclusionZone Front
                    return this.isInFront(newLeftRightOrUpDown, wall);
                }
            }
            else{
                //robot is not in exclusionZone
                return newLeftRightOrUpDown;
            }
        }
        else{
            if(this.currentUpDown>=wall.firstUpDown&&this.currentUpDown<wall.secondUpDown){
                if(this.curentLeftRight<wall.firstLeftRight){
                    //robot is in exclusionZone back
                    return this.isInBack(newLeftRightOrUpDown, wall);
                }
                else {
                    //robot is in exclusionZone Front
                    return this.isInFront(newLeftRightOrUpDown, wall);
                }
            }
            else{
                //robot is not in exclusionZone
                return newLeftRightOrUpDown;
            }
        }
    }

    //positon when behinde wall
    private Integer isInBack(TidyUpRobot this, Integer newUpDownOrLeftRight, Wall wall){
        if(wall.isUpDown){
            if(newUpDownOrLeftRight>=wall.firstUpDown) newUpDownOrLeftRight=wall.firstUpDown-1; return newUpDownOrLeftRight;
        }
        else {
            if(newUpDownOrLeftRight>=wall.firstLeftRight) newUpDownOrLeftRight=wall.firstLeftRight-1; return newUpDownOrLeftRight;
        }
    }

    //position when infront of wall
    private Integer isInFront(TidyUpRobot this, Integer newUpDownOrLeftRight, Wall wall){
        if(wall.isUpDown){
            if(newUpDownOrLeftRight<wall.firstUpDown) newUpDownOrLeftRight=wall.firstUpDown; return newUpDownOrLeftRight;
        }
        else{
            if(newUpDownOrLeftRight<wall.firstLeftRight) newUpDownOrLeftRight=wall.firstLeftRight; return newUpDownOrLeftRight;
        }
    }
}


