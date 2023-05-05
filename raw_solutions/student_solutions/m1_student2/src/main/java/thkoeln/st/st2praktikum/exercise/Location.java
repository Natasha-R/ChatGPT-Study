package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Location {
    private Pair<Integer,Integer> oldCoordinate;
    private Pair<Integer,Integer> newCoordinate;
    private UUID robotNewRoomId;

    public void setNewRoom(UUID newRoomId){
        this.robotNewRoomId = newRoomId;
    }

    public void setOldCoordinate(Pair<Integer,Integer> oldCoordinate){
        this.oldCoordinate = oldCoordinate;
    }

    public void setNewCoordinate(Pair<Integer,Integer> newCoor){
        this.newCoordinate = newCoor;
    }

    public Pair<Integer,Integer> getOldCoordinate() {
        return oldCoordinate;
    }

    public Pair<Integer,Integer> getNewCoordinate(){
        return newCoordinate;
    }

    public Pair<Integer,Integer> getCoordinate() {
        if(newCoordinate != null) return newCoordinate;
        else if(oldCoordinate != null && newCoordinate == null) return oldCoordinate;
        else return null;
    }

    public UUID getRobotNewRoomId(){
        return robotNewRoomId;
    }

}
