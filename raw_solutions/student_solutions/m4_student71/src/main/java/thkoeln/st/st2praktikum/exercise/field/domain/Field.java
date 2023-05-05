package thkoeln.st.st2praktikum.exercise.field.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import javax.management.InvalidAttributeValueException;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class Field extends UUIDBase implements Serializable {

    private int height;
    private int width;

    @OneToMany
    @Getter
    @JsonIgnore
    private List<Tile> board;

    public Field(int height, int width) {
        super();
        this.height = height;
        this.width = width;

        board = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                var tempTile = new Tile();

                if(y == 0)
                    tempTile.setNorth(true);
                if(y == height - 1)
                    tempTile.setSouth(true);
                if(x == 0)
                    tempTile.setWest(true);
                if(x == width - 1)
                    tempTile.setEast(true);

                board.add(tempTile);//setTileNormal(x,y,tempTile);
            }
        }
    }
    @JsonIgnore
    public Tile getTile(Coordinate coordinates){
        if(coordinates == null)
            return null;
        return getTile(coordinates.getX(),coordinates.getY());
    }

    //Gets tile in ST2 coordinate system
    @JsonIgnore
    public Tile getTile(int st2x, int st2y){
        if(board == null)
            return null;
        return board.get(((height-st2y -1)*width )+st2x);
    }
    @JsonIgnore
    private Tile getTileNormal(int x, int y) {
        if(board == null)
            return null;

        return board.get((y*width)+x);
    }
    @JsonIgnore
    private void setTileNormal(int x, int y, Tile tile) {
        if(board == null)
            return;
        board.set((y*width)+x, tile);
    }

    //Adds obstacle "(0,3)-(2,3)" -> 0,3 to 2,3
    @JsonIgnore
    public void addObstacle(Obstacle obstacle) throws InvalidAttributeValueException {
        if(obstacle == null)
            return;
        addObstacle(obstacle.getStart(),obstacle.getEnd());
    }
    @JsonIgnore
    public void addObstacle(Coordinate point1, Coordinate point2){
        if(point1 == null ||point2 == null)
            return;

        //draw line vertical
        if(point1.getX().equals(point2.getX())){
            var x = point1.getX();
            var fromY = (point1.getY() < point2.getY()) ? point1.getY() : point2.getY();
            var toY = (point1.getY() < point2.getY()) ? point2.getY() : point1.getY();

            for (int y = fromY; y < toY; y++) {
                getTile(x,y).setWest(true);
                getTile(x-1,y).setEast(true);
            }
        }

        //draw line horizontal
        if(point1.getY().equals(point2.getY())){
            var y = point1.getY();
            var fromX = (point1.getX() < point2.getX()) ? point1.getX() : point2.getX();
            var toX = (point1.getX() < point2.getX()) ? point2.getX() : point1.getX();

            for (int x = fromX; x < toX; x++) {
                getTile(x,y).setSouth(true);
                getTile(x,y-1).setNorth(true);
            }
        }

    }

    public void spawnMiningMachine(MiningMachine miningMachine, Coordinate coordinates) throws SpawnMiningMachineException {
        var tile = getTile(coordinates.getX(),coordinates.getY());

        if(tile.isOccupied())
            throw new SpawnMiningMachineException("Tile is already occupied");

        miningMachine.changePosition(this, coordinates);

        tile.setContent(miningMachine);
    }

//    @Override
//    public String toString() {
//        StringBuilder returnString = new StringBuilder();
//
//        for (int y = 0; y < height; y++) {
//            StringBuilder line1String = new StringBuilder();
//            StringBuilder line2String = new StringBuilder();
//            StringBuilder line3String = new StringBuilder();
//
//            for (int x = 0; x < width; x++) {
//                var muLineCharArray = getTileNormal(x,y).toString().toCharArray();
//
//                line1String.append(" ").append(muLineCharArray[0]).append(" ");
//                line2String.append(muLineCharArray[1]).append(muLineCharArray[2]).append(muLineCharArray[3]);
//                line3String.append(" ").append(muLineCharArray[4]).append(" ");
//
//            }
//            returnString.append(line1String).append("\n").append(line2String).append("\n").append(line3String).append("\n");
//        }
//
//        return returnString.toString();
//    }


    public void setConnection(Connection connection) {
        getTile(connection.getSourceCoordinates()).setConnection(connection);
    }

}
