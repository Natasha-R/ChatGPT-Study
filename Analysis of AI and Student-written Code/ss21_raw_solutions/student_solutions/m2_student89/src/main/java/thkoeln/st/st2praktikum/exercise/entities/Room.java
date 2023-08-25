package thkoeln.st.st2praktikum.exercise.entities;


import thkoeln.st.st2praktikum.exercise.Barrier;
import thkoeln.st.st2praktikum.exercise.Vector2D;
import thkoeln.st.st2praktikum.exercise.entities.Connection;

import java.util.HashMap;
import java.util.UUID;

public class Room {
    private int height, width;
    private String[][] grid;
    private HashMap<UUID, Connection> connectionHashMap = new HashMap<>();



    public Room(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new String[width][height];
    }


    public UUID addConnection(UUID entranceRoomID, Vector2D entranceCoordinates, UUID exitRoomID, Vector2D exitCoordinates){
        UUID id =UUID.randomUUID();
        connectionHashMap.put(id, new Connection(entranceRoomID,entranceCoordinates,exitRoomID,exitCoordinates));
        return id;

    }

    public HashMap<UUID, Connection> getConnectionHashMap() {
        return connectionHashMap;
    }

    public String[][] getGrid() {
        return grid;
    }

    public void ausgeben(){
        for (int i = grid[0].length - 1; i >= 0; i--) {
            for (String[] strings : grid) {
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
        int forstart, forend;

        if (fromX<0 || fromX>grid[0].length
                ||fromY<0 || fromY>grid.length
        || toX<0 || toX>grid[0].length
                ||toY<0 || toY>grid.length)
            throw new IllegalArgumentException("Barriere out of bounds nicht zulässig");        //Out of Bounds check





            if (fromX == toX) {
                if (fromY < toY) {
                    forstart = fromY;
                    forend = toY;
                } else {
                    forstart = toY;
                    forend = fromY;
                }


                for (int j = forstart; j < forend && j < grid.length; j++) {
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
                if (fromX < toX) {
                    forstart = fromX;
                    forend = toX;
                } else {
                    forstart = toX;
                    forend = fromX;
                }

                for (int j = forstart; j < forend && j < grid[0].length; j++) {
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




}



