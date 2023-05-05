package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class tidyUpRobot {

    Coordinate coordinate;

    String name;
    UUID ID = UUID.randomUUID();
    UUID roomid = null;
    tidyUpRobot(String name){
        this.name = name;
        this.coordinate = new Coordinate(0,0);
    }

    public void setCoordinates(int x, int y){
        this.coordinate = new Coordinate(x, y);
    }
    public void setCoordinatesFromString(String Coordinates){
        Coordinates = Coordinates.replace("(","").replace(")","");
        String[] cords = Coordinates.split(",");
        setCoordinates(Integer.parseInt(cords[0]),Integer.parseInt(cords[1]));
    }
    public void setRoom(UUID roomid){
        this.roomid = roomid;
    }

    public String getCoordinateString(){
        return "("+coordinate.getX()+","+coordinate.getY()+")";
    }

    public Coordinate getCoordinates(){ return coordinate;};
}
