package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.UUID;

public class Robot {
    private String name;
    @Getter
    private Coordinate position = null;
    @Getter
    private Room currentRoom = null;
    @Getter
    UUID id = UUID.randomUUID();

    Robot(String name){
        this.name = name;
    }

    public boolean setRoom(Room newRoom){
        if( currentRoom == null ){
            this.currentRoom = newRoom;
            this.position = new Coordinate(0,0);
            return true;
        }

        return false;
    }

    public boolean canMove(){
        if( position != null ) {
            if (currentRoom != null) {
                if(currentRoom.isInBoundaries(position)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean switchRoom(Room newRoom, Coordinate newPosition){
        if( currentRoom != null ){
            this.currentRoom = newRoom;
            this.position = newPosition;
            return true;
        }

        return false; // if the currentRoom is null the robot is not able to use a connection
    }

    public void move(int amountX, int amountY){
        this.position = new Coordinate(position.getX() + amountX, position.getY() + amountY);
    }
}
