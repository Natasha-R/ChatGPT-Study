package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class tidyUpRobot {
    int x = 0;
    int y = 0;
    String name;
    UUID ID = UUID.randomUUID();
    UUID roomid = null;
    tidyUpRobot(String name){
        this.name = name;
    }

    public void setCoordinates(int xa, int xb){
        this.x = xa;
        this.y = xb;
    }
    public void setCoordinatesFromString(String Coordinates){
        Coordinates = Coordinates.replace("(","").replace(")","");
        String[] cords = Coordinates.split(",");
        setCoordinates(Integer.parseInt(cords[0]),Integer.parseInt(cords[1]));
    }
    public void setRoom(UUID roomid){
        this.roomid = roomid;
    }

    public String getCoordinates(){
        return "("+x+","+y+")";
    }
}
