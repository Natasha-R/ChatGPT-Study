package thkoeln.st.st2praktikum.exercise.spaceShipDeck;

import org.springframework.data.util.Pair;
import thkoeln.st.st2praktikum.exercise.command.Direction;
import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.coordinate.CoordinateInterface;
import thkoeln.st.st2praktikum.exercise.exception.SquareNotFoundException;
import thkoeln.st.st2praktikum.exercise.obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.obstacle.ObstacleInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class SpaceShipDeck {
    private final Integer height;
    private final Integer width;
    private final UUID spaceShipDeckID = UUID.randomUUID();
    private final ArrayList<ObstacleInterface> allObstacles = new ArrayList<>();
    private final HashMap<UUID, Square> squareHashMap = new HashMap<>();

    public SpaceShipDeck(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        fillSquareHashMap();
    }

    private void fillSquareHashMap(){
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Square tmpSquare = new Square(new Coordinate(x,y));
                squareHashMap.put(tmpSquare.getSquareID(),tmpSquare);
            }
        }
    }

    public Square findSquareAt(CoordinateInterface at) throws SquareNotFoundException{
        AtomicReference<Square> resultSquare = new AtomicReference<>();
        squareHashMap.forEach((uuid,square)->{
            if(square.getCoordinates().getX().equals(at.getX()) && square.getCoordinates().getY().equals(at.getY())){
                resultSquare.set(square);
            }
        });
        if(resultSquare.get() != null){
            return resultSquare.get();
        }
        else throw new SquareNotFoundException("Koordinaten Fehlerhaft("+at.getX()+","+at.getY()+")");
    }

    public ArrayList<ObstacleInterface> getAllObstacles() {
        return allObstacles;
    }

    public UUID getSpaceShipDeckID() {
        return spaceShipDeckID;
    }

    public HashMap<UUID, Square> getSquareHashMap() {
        return squareHashMap;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public UUID getNextSquareUUIDinDirection(Direction direction, UUID squareID) throws IllegalArgumentException, SquareNotFoundException {
        CoordinateInterface nextSquareCoordinate;
        CoordinateInterface tmpCoordinate = squareHashMap.get(squareID).getCoordinates();
        switch (direction){
            case NO:
                nextSquareCoordinate = new Coordinate(tmpCoordinate.getX(), tmpCoordinate.getY()+1);
                return findSquareAt(nextSquareCoordinate).getSquareID();
            case EA:
                nextSquareCoordinate = new Coordinate(tmpCoordinate.getX()+1, tmpCoordinate.getY());
                return findSquareAt(nextSquareCoordinate).getSquareID();
            case SO:
                nextSquareCoordinate = new Coordinate(tmpCoordinate.getX(), tmpCoordinate.getY()-1);
                return findSquareAt(nextSquareCoordinate).getSquareID();
            case WE:
                nextSquareCoordinate = new Coordinate(tmpCoordinate.getX()-1, tmpCoordinate.getY());
                return findSquareAt(nextSquareCoordinate).getSquareID();
            default: throw new IllegalArgumentException(direction.toString());
        }
    }
}
