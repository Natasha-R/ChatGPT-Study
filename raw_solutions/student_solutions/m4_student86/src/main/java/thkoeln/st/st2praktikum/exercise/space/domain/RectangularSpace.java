package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceException;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.*;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class RectangularSpace extends Space {

    @Getter
    private Integer width, height;

    @Transient
    private Tile[][] tileArray = null;

    //@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER )
    //@Transient
    //private final List<Tile> tileList = new ArrayList<>();

    protected RectangularSpace () {} // jpaHack001

    public RectangularSpace(Integer width, Integer height) {
        if (width <= 0 || height <= 0)
            throw new SpaceException("Nur positive Maße erlaubt");

        this.width = width;
        this.height = height;

        initJpaHack003();
    }

    private void initJpaHack003() {

        tileArray = new Tile[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //tileList.add(new Tile(new Vector2D(x, y)));
                tileArray[x][y] = new Tile(new Vector2D(x, y));
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

    private void initJpaHack004() {

        for (Blocking blocking : blockerList) {
            blocking.buildUp(this);
        }
    }

    private void jpaHack002() {
        if (tileArray == null) {
            tileArray = new Tile[width][height];
            initJpaHack003();
            initJpaHack004();
            //for (Tile tile: tileList) {
            //    tileArray[ tile.getCoordinate().getX() ][ tile.getCoordinate().getY() ] = tile;
            //}
        }
    }


    public Tile getTile (Integer x, Integer y) {
        jpaHack002();
        try {
            return tileArray[x][y];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}