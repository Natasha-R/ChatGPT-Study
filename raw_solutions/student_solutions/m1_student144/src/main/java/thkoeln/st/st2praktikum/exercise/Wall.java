package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
public class Wall {
    protected UUID roomId, wallId;
    protected String wallString;
    protected boolean isUpDown;
    protected Integer firstLeftRight, firstUpDown, secondLeftRight, secondUpDown;

    public Wall(UUID destinationRoomId, String positionString){
        firstLeftRight = firstLeftRightInt(positionString);
        firstUpDown = firstUpDownInt(positionString);
        secondLeftRight = secondLeftRight(positionString);
        secondUpDown = secondUpDown(positionString);
        roomId = destinationRoomId;
        wallId = UUID.randomUUID();
        wallString = positionString;

        isUpDown = isItUpDown();

    }


    //checks if wall is horizontal or vertical
    private boolean isItUpDown(Wall this){
        if(this.firstLeftRight.equals(this.secondLeftRight) & !this.firstUpDown.equals(this.secondUpDown)){
            return false;
        }
        else return true;
    }

    //wall coordinates as Integer
    private Integer firstLeftRightInt(String positionString){
        return Integer.parseInt(positionString.substring(1,positionString.indexOf(",")));
    }
    private Integer firstUpDownInt(String positionString){
        return Integer.parseInt(positionString.substring(positionString.indexOf(",")+1,positionString.indexOf(")")));
    }
    private Integer secondLeftRight(String positionString){
        return Integer.parseInt(positionString.substring(positionString.lastIndexOf("(")+1,positionString.lastIndexOf(",")));
    }
    private Integer secondUpDown(String positionString){
        return Integer.parseInt(positionString.substring(positionString.lastIndexOf(",")+1,positionString.lastIndexOf(")")));
    }
}


