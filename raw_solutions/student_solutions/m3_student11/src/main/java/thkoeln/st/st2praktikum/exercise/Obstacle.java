package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.containerClasses.CoordinatePair;
import thkoeln.st.st2praktikum.exercise.containerClasses.IntegerPair;
import thkoeln.st.st2praktikum.exercise.containerClasses.CoordinateWiseObstacle;
import thkoeln.st.st2praktikum.exercise.exceptions.DiagonalObstacleException;
import thkoeln.st.st2praktikum.exercise.exceptions.ObstacleFormatException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Obstacle {

    @Getter
    @Embedded
    private final Coordinate start;
    @Embedded
    @Getter
    private final Coordinate end;

    @Transient
    private final List<CoordinateWiseObstacle> Borders = new ArrayList<>();

    public Obstacle(Coordinate pos1, Coordinate pos2) {
        if(checkOrder(pos1, pos2)){
            this.start = pos1;
            this.end = pos2;
        }else{
            this.start = pos2;
            this.end = pos1;
        }

        if(getDirectionOfObstacle(putCoordinates(pos1.toString() + "," + pos2.toString())) == null )
            throw new DiagonalObstacleException();
        addObstacle(pos1.toString() + "-" + pos2.toString());
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        if(getDirectionOfObstacle(putCoordinates(obstacleString)) == null)
            throw new DiagonalObstacleException();
        if(obstacleFormatTestFailed(obstacleString))
            throw new ObstacleFormatException();
        String[] separatedCords = obstacleString.split("-");
        Coordinate first = new Coordinate(separatedCords[0]);
        Coordinate second = new Coordinate(separatedCords[1]);

        if(checkOrder(first, second)){
            this.start = first;
            this.end = second;
        }else{
            this.end = first;
            this.start = second;
        }

        addObstacle(obstacleString);
    }

    protected Obstacle(){
        start = null;
        end = null;
    }

    //returns true if there's an Obstacle in given direction of the coordinate
    public boolean blocksCoordinateInDirection(Coordinate coordinate, DirectionsType direction){
        for(thkoeln.st.st2praktikum.exercise.containerClasses.CoordinateWiseObstacle coordinateObstacle : Borders){
            if(coordinate.equals(coordinateObstacle.getCoordinate()) && coordinateObstacle.getDirection().equals(direction))
                return true;
        }
        return false;
    }

    /**
     Adds coordinate wise Obstacles
     */
    private void addObstacle(String Coordinates){
        CoordinatePair coordinatePair = putCoordinates(Coordinates);

        DirectionsType direction = getDirectionOfObstacle(coordinatePair);
        if(direction == null) throw new ObstacleFormatException();

        IntegerPair startAndFinish = getStartAndFinish(direction, coordinatePair);

        assert startAndFinish != null;
        for(int i = startAndFinish.getFirst(); i < startAndFinish.getSecond(); i++) {
            Coordinate entry1;
            Coordinate entry2;
            DirectionsType entry1direction;
            DirectionsType entry2direction;

            if(direction == DirectionsType.NORTH || direction == DirectionsType.SOUTH){
                entry1 = new Coordinate(coordinatePair.getFirst().getX() - 1, i);   //adding border in east coordinate
                entry1direction = DirectionsType.EAST;

                entry2 = new Coordinate(coordinatePair.getFirst().getX(), i);          //adding border in west coordinate
                entry2direction = DirectionsType.WEST;
            }
            else{
                entry1 = new Coordinate(i,coordinatePair.getFirst().getY());           //adding border in south coordinate
                entry1direction = DirectionsType.SOUTH;

                entry2 = new Coordinate(i, coordinatePair.getFirst().getY()-1);    //adding border in north coordinate
                entry2direction = DirectionsType.NORTH;
            }
            Borders.add(new CoordinateWiseObstacle(entry1, entry1direction));
            Borders.add(new CoordinateWiseObstacle(entry2, entry2direction));
        }
    }

    //returns true if order is right, else false
    private boolean checkOrder(Coordinate first, Coordinate second){
        //closer to the bottom left corner-> start has the lowest x and y coordinate
        if(first.getX() < second.getX()){
            return true;
        }
        else if(first.getX() > second.getX()){
            return false;
        }
        else{
            return first.getY() < second.getY();
        }
    }

    //get start and finish coordinate (x or y, depending on direction) of obstacle
    private IntegerPair getStartAndFinish(DirectionsType direction, CoordinatePair coordinatePair){
        if(direction == DirectionsType.NORTH || direction == DirectionsType.SOUTH){
            if(coordinatePair.getFirst().getY() < coordinatePair.getSecond().getY()){
                return new IntegerPair(coordinatePair.getFirst().getY(), coordinatePair.getSecond().getY());
            }else{
                return new IntegerPair(coordinatePair.getSecond().getY(), coordinatePair.getFirst().getY());
            }
        }else if(direction == DirectionsType.EAST || direction == DirectionsType.WEST){
            if(coordinatePair.getFirst().getX() < coordinatePair.getSecond().getX()){
                return new IntegerPair(coordinatePair.getFirst().getX(), coordinatePair.getSecond().getX());
            }else{
                return new IntegerPair(coordinatePair.getSecond().getX(), coordinatePair.getFirst().getX());
            }
        }
        return null;
    }

    private DirectionsType getDirectionOfObstacle(CoordinatePair coordinatePair){
        if(coordinatePair.getFirst().getX().equals(coordinatePair.getSecond().getX())){                                   //north or south
            if(coordinatePair.getFirst().getY() < coordinatePair.getSecond().getY())
                return DirectionsType.NORTH;
            else
                return DirectionsType.SOUTH;
        }
        else if(coordinatePair.getFirst().getY().equals(coordinatePair.getSecond().getY())){                              //west or east
            if(coordinatePair.getFirst().getX() < coordinatePair.getSecond().getX())
                return DirectionsType.EAST;
            else
                return DirectionsType.WEST;
        }
        throw new ObstacleFormatException();
    }

    private CoordinatePair putCoordinates(String Coordinates){
        Coordinates = Coordinates.replace("(","").replace(")","").replace("-",",");
        String[] separatedCords = Coordinates.split(",");

        Coordinate first = new Coordinate(Integer.parseInt(separatedCords[0]), Integer.parseInt(separatedCords[1]));
        Coordinate second = new Coordinate(Integer.parseInt(separatedCords[2]), Integer.parseInt(separatedCords[3]));

        return new CoordinatePair(first, second);
    }

    //returns true if format is right
    private boolean obstacleFormatTest(String Input){
        return Input.charAt(0) == Input.charAt(6) && Input.charAt(4) == Input.charAt(10) && Input.charAt(2) == Input.charAt(8) && Input.charAt(5) == '-';
    }

    private boolean obstacleFormatTestFailed(String Input){
        return !obstacleFormatTest(Input);
    }
}
