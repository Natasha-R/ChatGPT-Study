package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Robot implements Blocking{

    private Vector2D coordinates;
    private Room room;
    private String name;
    private UUID id = UUID.randomUUID();


    public Robot(String name){
        this.name = name;
    }

    public void move(Vector2D coordinates){
        this.coordinates = coordinates;
    }

    public Boolean isBlockingCoordinate(Vector2D coordinates){
        if(this.coordinates.equals(coordinates))
            return true;
        return false;
    }

    public UUID getId(){
        return id;
    }
    public Room getRoom() {
        return room;
    }

    public Vector2D getCoordinates() {
        return coordinates;
    }
    public void setRoom(Room room) {
        this.room = room;
    }

}
