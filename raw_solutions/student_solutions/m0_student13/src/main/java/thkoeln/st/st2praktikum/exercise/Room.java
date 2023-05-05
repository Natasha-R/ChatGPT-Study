package thkoeln.st.st2praktikum.exercise;

public class Room {

    public int xPosition;
    public int yPosition;

    private Room north;
    private Room south;
    private Room east;
    private Room west;


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

    //Konstruktor
    public Room(int xPosition, int yPosition){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    // TODO: public Room moveNorth(int steps){ }
    // TODO: public Room moveSouth(int steps){ }
    // TODO: public Room moveEast(int steps){ }
    // TODO: public Room moveWest(int steps){ }

    public Room moveNorth(int steps) {
        if(north == null || steps == 0 ) return this;
        return north.moveNorth(steps-1);
    }

    public Room moveSouth(int steps){
        if(south == null || steps == 0) return this;
        return south.moveSouth(steps-1);
    }

    public Room moveEast(int steps){
        if(east == null || steps == 0) return this;
        return east.moveEast(steps-1);
    }

    public Room moveWest(int steps){
        if(west == null || steps == 0) return this;
        return west.moveWest(steps-1);
    }

}
