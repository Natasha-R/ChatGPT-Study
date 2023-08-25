package thkoeln.st.st2praktikum.exercise.Objects;

public class Tile {

    public Obstacle North;
    public Obstacle East;
    public Obstacle South;
    public Obstacle West;

    public MiningMachine Content;

    public Connection connection;

    public Tile(){
    }

    public Tile(Obstacle north, Obstacle east, Obstacle south, Obstacle west){
        North = north;
        East = east;
        South = south;
        West = west;
    }

    @Override
    public String toString() {
        var returnString = "";

        if(North != null)
            returnString += "_";
        else
            returnString += " ";

        if(West != null)
            returnString += "|";
        else
            returnString += " ";

        if(Content != null)
            returnString += "█";
        else
            returnString += "░";

        if(East != null)
            returnString += "|";
        else
            returnString += " ";

        if(South != null)
            returnString += "_";
        else
            returnString += " ";

        return returnString;
    }

    public boolean isOccupied() {
        return (Content != null);
    }

    public boolean isNorthWalkable(){
        return North == null;
    }
    public boolean isEastWalkable(){
        return East == null;
    }
    public boolean isSouthWalkable(){
        return South == null;
    }
    public boolean isWestWalkable(){
        return West == null;
    }
}
