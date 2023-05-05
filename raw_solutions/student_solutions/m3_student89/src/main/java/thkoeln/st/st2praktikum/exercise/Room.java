package thkoeln.st.st2praktikum.exercise;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Room extends AbstractEntity {
    private int height, width;
    @Embedded
    private Grid grid = new Grid(height,width);
    @Transient
    private HashMap<UUID,Connection> connectionHashMap = new HashMap<>();



    public Room(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new Grid(height,width);
    }




    public UUID addConnection(TransportCategory transport_category,UUID entranceRoomID, Vector2D entranceCoordinates, UUID exitRoomID, Vector2D exitCoordinates){
        if (entranceCoordinates.getY()<0
                || entranceCoordinates.getX()<0
                || exitCoordinates.getX()<0
                ||exitCoordinates.getY()<0
                ||entranceCoordinates.getX()>grid.getGrid().length -1
                ||entranceCoordinates.getY()>grid.getGrid()[0].length-1
                ||exitCoordinates.getX()>grid.getGrid().length-1
                ||exitCoordinates.getY()>grid.getGrid().length-1)
            throw new IllegalArgumentException("Out of Bound Connections nicht möglich");
        UUID id =UUID.randomUUID();
        connectionHashMap.put( id,new Connection(transport_category,entranceRoomID,entranceCoordinates,exitRoomID,exitCoordinates));
        return id;

    }

    public HashMap<UUID,Connection> getConnectionHashMap() {
        return connectionHashMap;
    }

    public String[][] getGrid() {
        return grid.getGrid();
    }

    public void ausgeben(){
        for (int i = grid.getGrid()[0].length - 1; i >= 0; i--) {
            for (String[] strings : grid.getGrid()) {
                System.out.print(strings[i]);
            }
            System.out.println();
        }
    }



    public void buildBarrier(Barrier barrier) {

        int fromX = barrier.getStart().getX();
        int fromY = barrier.getStart().getY();
        int toX = barrier.getEnd().getX();
        int toY = barrier.getEnd().getY();


        if (fromX<0 || fromX>grid.getGrid()[0].length
                ||fromY<0 || fromY>grid.getGrid().length
        || toX<0 || toX>grid.getGrid()[0].length
                ||toY<0 || toY>grid.getGrid().length)
            throw new IllegalArgumentException("Barriere out of bounds nicht zulässig");        //Out of Bounds check





            if (fromX == toX) {



                for (int j = fromY; j < toY && j < grid.getGrid().length; j++) {
                    if (grid.getGrid()[fromX][j]==null) {
                        grid.getGrid()[fromX][j] = "Wan1";
                    }

                    if (fromX !=0) {
                        if (grid.getGrid()[fromX - 1][j]==null) {
                            grid.getGrid()[fromX - 1][j] = "Wan2";
                        }
                    }else {
                        if (grid.getGrid()[fromX + 1][j] ==null){
                            grid.getGrid()[fromX + 1][j] = "Wan2";}}

                }


            } else if (fromY == toY) {

                for (int j = fromX; j < toX && j < grid.getGrid()[0].length; j++) {
                    if (grid.getGrid()[j][fromY] ==null){
                        grid.getGrid()[j][fromY] = "Wan1";}

                    if (fromY !=0) {
                        if (grid.getGrid()[j][fromY - 1] ==null){
                            grid.getGrid()[j][fromY - 1] = "Wan2";
                        }
                    }else {if (grid.getGrid()[j][fromY +1] ==null){
                        grid.getGrid()[j][fromY +1] = "Wan2";
                    }}
                }
            } else
                throw new UnsupportedOperationException();


    }




}



