package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class TidyUpRobot{

    private UUID tidyUpRobotID;
    private UUID roomID;
    private String name;
    private Coordinate coordinate;


    public void setTidyUpRobotID ( UUID tidyUpRobotID){
        this.tidyUpRobotID = tidyUpRobotID;
    }

    public UUID getTidyUpRobotID() {
        return tidyUpRobotID;
    }

    public void setRoomID ( UUID roomID){
        this.roomID = roomID;
    }

    public UUID getRoomID() {
        return roomID;
    }

    public void setName ( String name){
        this.name = name;
    }

    public String getName (){
        return name;
    }

    public void setCoordinate (Coordinate coordinate){
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate (){
        return coordinate;
    }
}
