package thkoeln.st.st2praktikum.exercise.robot;

import java.util.UUID;

public class Robot{

    private UUID id = UUID.randomUUID();
    private UUID roomId;

    private String name;

    private int x;
    private int y;

    public void moveNorth(){
        this.y += 1;
    }

    public void moveSouth(){
        this.y -= 1;
    }

    public void moveEast(){
        this.x += 1;
    }

    public void moveWest(){
        this.x -= 1;
    }

    public Robot(String name){
        this.name = name;
        this.roomId = null;
        this.y = 0;
        this.x = 0;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRoomId(){
        return roomId;
    }

    public void setRoomId(UUID roomId){
        this.roomId = roomId;
    }

    public String getPosition(){
        return "("+x+","+y+")";
    }

    public int getX() {
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
}
