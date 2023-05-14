package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.UUID;

public class Location {
    private Vector2D oldCoordinate;
    private Vector2D newCoordinate;
    private UUID robotNewRoomId;

    public void setNewRoom(UUID robotNewRoomId){
        this.robotNewRoomId = robotNewRoomId;
    }

    public void setOldCoordinate(Vector2D oldCoordinate){
        this.oldCoordinate = oldCoordinate;
    }

    public void setNewCoordinate(Vector2D newCoordinate){
        this.newCoordinate = newCoordinate;
    }

    public Vector2D getOldCoordinate(){
        return oldCoordinate;
    }

    public Vector2D getNewCoordinate(){
        return newCoordinate;
    }

    public Vector2D getCoordinate(){
        if(newCoordinate != null) return newCoordinate;
        else if(oldCoordinate != null && newCoordinate == null) return oldCoordinate;
        else return null;
    }

    public UUID getRobotNewRoomId(){
        return robotNewRoomId;
    }
}
