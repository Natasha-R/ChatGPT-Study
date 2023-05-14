package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;
import java.util.*;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
public class TidyUpRobot{
    private String name;
    @Id
    private UUID tidyUpRobotId;
    private UUID RoomId;
    private String currentPosition;
    private Integer currentUpDown;
    private Integer curentLeftRight;
    private Boolean isSpawned;
    private Boolean hasMoved;

    public TidyUpRobot(String willBeName){

        name = willBeName;
        tidyUpRobotId = UUID.randomUUID();
        RoomId = null;
        currentPosition = "(0,0)";
        currentUpDown= 0;
        curentLeftRight = 0;
        isSpawned = false;
        hasMoved = false;

    }
    //executes the command
    public boolean understandCommandString(Command command){
        CommandType commandtype = command.getCommandType();
        if(command.isMoveCommand(commandtype)){
            //move command
            this.moveRobot(command);
            return true;
        }
        if(commandtype.equals(CommandType.TRANSPORT)){
            //transport command
            return this.connectionTravel(this.traversableConnectionsearch(this.RoomId));
        }
        else{
            //entrance command
            return this.spawnRobot(command.getGridId());
        }
    }

    //checks for connection
    private boolean traversableConnectionsearch(TidyUpRobot this, UUID currentRoomId){
        if(TraversableConnectionMap.findConnectionInRoom(this.RoomId).getSourcePoint().getCurrentPosition().equals(this.currentPosition)){
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
            this.currentPosition = TraversableConnectionMap.findConnectionInRoom(this.RoomId).getDestinationPoint().getCurrentPosition();
            this.RoomId = TraversableConnectionMap.findConnectionInRoom(this.RoomId).getDestinationRoomId();
            this.updatePositonIntegerAfterTravel();
            return true;
        }
        else {
            return false;
        }
    }

    public Point getPoint(){
     Point point = new Point(this.currentPosition);
     return point;
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
    private boolean spawnRobot(UUID roomId){
        if(!this.isSpawnedCheck()&&TidyUpRobotMap.unMovedRobot(roomId).equals(false)){
            this.setRoomId(roomId);
            this.setIsSpawned(true);
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
    private Integer addOrSubtract(Command command){
        CommandType commandType = command.getCommandType();
        if(commandType.equals(CommandType.NORTH)||commandType.equals(CommandType.EAST)){
            return 1;
        }
        else{
            return -1;
        }

    }

    //Looks for the amount to go
    /*private Integer howFarToGo(String commandString){
        return Integer.parseInt(commandString.substring(4,commandString.indexOf("]")));
    }*/

    // checks if robots has moved since spawning
    private boolean hasRobotMoved(TidyUpRobot this){
        if(this.currentPosition.equals("(0,0)")){
            return this.hasMoved=false;
        }
        else return this.hasMoved = true;
    }

    //makes the robot move
    private void moveRobot(TidyUpRobot this, Command command){
        Integer newLeftRight, newUpDown;
        Integer amount = command.getNumberOfSteps()*addOrSubtract(command);
        boolean upDown = command.getIsHorizontalMovement();
        newLeftRight = amountUnderZero(this.curentLeftRight+amount);
        newUpDown = amountUnderZero(this.currentUpDown+amount);
        if(!command.getIsHorizontalMovement()){
            this.currentUpDown = this.wichWall(!upDown,newUpDown);
            this.currentPosition ="("+curentLeftRight+","+currentUpDown+")";
            this.hasMoved = this.hasRobotMoved();
        }
        else {
            this.curentLeftRight = this.wichWall(!upDown, newLeftRight);
            this.currentPosition ="("+curentLeftRight+","+currentUpDown+")";
            this.hasMoved = this.hasRobotMoved();
        }




    }

    //checks if robot crashes with outer room wall
    private Integer checkBounderis(TidyUpRobot this, Integer newLeftOrUpDown, Boolean upDown){
        Integer maxHeight = RoomMap.getByUUID(this.RoomId).getHeight()-1;
        Integer maxWidth = RoomMap.getByUUID(this.RoomId).getWidth()-1;
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
        allRelevantWalls = WallMap.findAllRelevantWallsInRoom(this.RoomId, upDown);
        if(upDown){
            for (int i =0;i<allRelevantWalls.size();i++){
                if(this.curentLeftRight>=allRelevantWalls.get(i).getStart().getX()&&this.curentLeftRight<allRelevantWalls.get(i).getEnd().getX()){
                    return this.isInXclusionZone(newLeftRightOrUpDown,allRelevantWalls.get(i));
                }
            }
            return this.checkBounderis(newLeftRightOrUpDown, upDown);
        }
        else{
            allRelevantWalls = WallMap.findAllRelevantWallsInRoom(this.RoomId, upDown);
            for (int i =0;i<allRelevantWalls.size();i++){
                if(this.currentUpDown>=allRelevantWalls.get(i).getStart().getY()&&this.currentUpDown<allRelevantWalls.get(i).getEnd().getY()){
                    return this.isInXclusionZone(newLeftRightOrUpDown,allRelevantWalls.get(i));
                }
                return this.checkBounderis(newLeftRightOrUpDown, !upDown);
            }
        }
        return this.checkBounderis(newLeftRightOrUpDown, upDown);
    }

    //checks for inner wall crash
    private Integer isInXclusionZone(TidyUpRobot this, Integer newLeftRightOrUpDown, Wall wall){
        if (wall.getIsHorizontal()){
            if(this.curentLeftRight>=wall.getStart().getX()&&this.curentLeftRight<wall.getEnd().getX()){
                if(this.currentUpDown<wall.getStart().getY()){
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
            if(this.currentUpDown>=wall.getStart().getY()&&this.currentUpDown<wall.getEnd().getY()){
                if(this.curentLeftRight<wall.getStart().getX()){
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
        if(wall.getIsHorizontal()){
            if(newUpDownOrLeftRight>=wall.getStart().getY()) newUpDownOrLeftRight=wall.getStart().getY()-1; return newUpDownOrLeftRight;
        }
        else {
            if(newUpDownOrLeftRight>=wall.getStart().getX()) newUpDownOrLeftRight=wall.getStart().getX()-1; return newUpDownOrLeftRight;
        }
    }

    //position when infront of wall
    private Integer isInFront(TidyUpRobot this, Integer newUpDownOrLeftRight, Wall wall){
        if(wall.getIsHorizontal()){
            if(newUpDownOrLeftRight<wall.getStart().getY()) newUpDownOrLeftRight=wall.getStart().getY(); return newUpDownOrLeftRight;
        }
        else{
            if(newUpDownOrLeftRight<wall.getStart().getX()) newUpDownOrLeftRight=wall.getStart().getX(); return newUpDownOrLeftRight;
        }
    }
}


