package thkoeln.st.st2praktikum.exercise;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import thkoeln.st.st2praktikum.core.AbstractEntity;


import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ShipDeck extends AbstractEntity {

    private int height, width;
    @Autowired
    private String[][] grid = new String[width][height];
    @Transient
    private HashMap<UUID,Connection> connectionHashMap = new HashMap<>();



    public ShipDeck(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new String[width][height];
    }


    public HashMap<UUID, Connection> getConnectionHashMap() {
        return connectionHashMap;
    }


    public void buildWall(Wall wallString) {

        int fromX = wallString.getStart().getX();
        int fromY = wallString.getStart().getY();
        int toX = wallString.getEnd().getX();
        int toY = wallString.getEnd().getY();


        if (fromX<0 || fromX > grid[0].length || fromY <0 || fromY > grid.length || toX<0 || toX>grid[0].length || toY<0 || toY > grid.length)
        throw new IllegalArgumentException("Barriere out of bounds nicht zulässig");        //Out of Bounds check


        if (fromX == toX) {
            for (int j = fromY; j < toY && j < grid.length; j++) {
                if (grid[fromX][j]==null) {
                    grid[fromX][j] = "Wan1";
                }

                if (fromX !=0) {
                    if (grid[fromX - 1][j]==null) {
                        grid[fromX - 1][j] = "Wan2";
                    }
                }else {
                    if (grid[fromX + 1][j] ==null){
                        grid[fromX + 1][j] = "Wan2";}}

            }

        } else if (fromY == toY) {

            for (int j = fromX; j < toX; j++) {
                if (grid[j][fromY] ==null){
                    grid[j][fromY] = "Wan1";}

                if (fromY !=0) {
                    if (grid[j][fromY - 1] ==null){
                        grid[j][fromY - 1] = "Wan2";
                    }
                }else {if (grid[j][fromY +1] ==null){
                    grid[j][fromY +1] = "Wan2";
                }}
            }
        } else
            throw new UnsupportedOperationException();
    }


    public UUID addConnection(UUID transportTechnologyId,UUID sourceSpaceShipDeckId, Coordinate sourceCoordinate, UUID destinationSpaceShipDeckId, Coordinate destinationCoordinate){

        if(sourceCoordinate.getX() > width ||sourceCoordinate.getY() > height || destinationCoordinate.getX() > width || destinationCoordinate.getX() > height){

            throw new UnsupportedOperationException("Diese Connection ist out of bound gesetzt");
        }else{
            UUID id = UUID.randomUUID();
            connectionHashMap.put(id, new Connection(transportTechnologyId,sourceSpaceShipDeckId,sourceCoordinate,destinationSpaceShipDeckId,destinationCoordinate));
            return id;
        }
    }
}



