package thkoeln.st.st2praktikum.exercise;

public class Room implements Moveable {

    private int xPosition;
    private int yPosition;

    private Room north;
    private Room south;
    private Room east;
    private Room west;
    private boolean blocked;
    private Field field;


    public void setEast(Room east) {
        this.east = east;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Field getField() {
        return field;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public Room(int xPosition, int yPosition, Field field){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.blocked = false;
        this.field = field;
    }


    public Room moveNorth(int steps) {
        blocked = false;
        if(north == null || steps == 0 || north.blocked ) return this;
        return north.moveNorth(steps-1);
    }

    public Room moveSouth(int steps){
        blocked = false;
        if(south == null || steps == 0 || south.blocked) return this;
        return south.moveSouth(steps-1);
    }

    public Room moveEast(int steps){
        blocked = false;
        if(east == null || steps == 0 || east.blocked) return this;
        return east.moveEast(steps-1);
    }

    public Room moveWest(int steps){
        blocked = false;
        if(west == null || steps == 0 || west.blocked) return this;
        return west.moveWest(steps-1);
    }

}