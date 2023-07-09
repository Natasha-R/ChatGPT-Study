package thkoeln.st.st2praktikum.exercise;


import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class ShipDeck {
    private int height, width;
    private String[][] grid;
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
        int forstart, forend;

        if (fromX == toX) {
            if (fromY < toY) {
                forstart = fromY;
                forend = toY;
            } else {
                forstart = toY;
                forend = fromY;
            }
            for (int j = forstart; j < forend && j < grid.length; j++) {
                if (grid[fromX][j] == null) {
                    grid[fromX][j] = "Wan1";
                }

                if (fromX != 0) {
                    if (grid[fromX - 1][j] == null) {
                        grid[fromX - 1][j] = "Wan2";
                    }
                } else {
                    if (grid[fromX + 1][j] == null) {
                        grid[fromX + 1][j] = "Wan2";
                    }
                }
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
                if (grid[j][fromY] == null) {
                    grid[j][fromY] = "Wan1";
                }

                if (fromY != 0) {
                    if (grid[j][fromY - 1] == null) {
                        grid[j][fromY - 1] = "Wan2";
                    }
                } else {
                    if (grid[j][fromY + 1] == null) {
                        grid[j][fromY + 1] = "Wan2";
                    }
                }
            }
        } else
            throw new UnsupportedOperationException();
    }
}



