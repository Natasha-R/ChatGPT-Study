package thkoeln.st.st2praktikum.exercise;

import lombok.*;

public class Tile {
    @Getter @Setter
    private Boolean north;
    @Getter @Setter
    private Boolean east;
    @Getter @Setter
    private Boolean south;
    @Getter @Setter
    private Boolean west;

    @Getter @Setter
    private IContent content;

    @Getter @Setter
    private Connection connection;

    public Tile(){
    }

    public Tile(boolean north, boolean east, boolean south, boolean west){
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    @Override
    public String toString() {
        var returnString = "";

        if(north != null)
            returnString += "_";
        else
            returnString += " ";

        if(west != null)
            returnString += "|";
        else
            returnString += " ";

        if(content != null)
            returnString += "█";
        else
            returnString += "░";

        if(east != null)
            returnString += "|";
        else
            returnString += " ";

        if(south != null)
            returnString += "_";
        else
            returnString += " ";

        return returnString;
    }

    public boolean isOccupied() {
        return (content != null);
    }

    public boolean isNorthWalkable(){
        return north == null;
    }
    public boolean isEastWalkable(){
        return east == null;
    }
    public boolean isSouthWalkable(){
        return south == null;
    }
    public boolean isWestWalkable(){
        return west == null;
    }
}
