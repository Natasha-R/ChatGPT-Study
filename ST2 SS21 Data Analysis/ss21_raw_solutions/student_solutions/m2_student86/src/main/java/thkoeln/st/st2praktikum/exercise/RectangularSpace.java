package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Exception.SpaceException;


public class RectangularSpace extends Space {

    @Getter
    private Integer width, height;
    private Tile[][] tileArray;

    public RectangularSpace(Integer width, Integer height) {
        if (width <= 0 || height <= 0)
            throw new SpaceException("Nur positive Maße erlaubt");

        this.width = width;
        this.height = height;
        this.tileArray = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileArray[x][y] = new Tile();
            }
        }

        /* Verknüft die Tiles untereinander zu einem Netz */
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = getTile(x, y);
                tile.setWestNeighbor(getTile(x-1, y));
                tile.setEastNeighbor(getTile(x+1, y));
                tile.setSouthNeighbor(getTile(x, y-1));
                tile.setNorthNeighbor(getTile(x, y+1));
            }
        }
    }

    public Tile getTile (Integer x, Integer y) {
        try {
            return tileArray[x][y];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}