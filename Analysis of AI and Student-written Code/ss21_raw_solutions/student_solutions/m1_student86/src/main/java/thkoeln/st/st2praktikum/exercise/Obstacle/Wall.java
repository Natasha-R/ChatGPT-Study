package thkoeln.st.st2praktikum.exercise.Obstacle;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Space.Space;
import thkoeln.st.st2praktikum.exercise.Space.Tile;

import java.util.UUID;


public class Wall implements Obstacle {
    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    private final Coordinate start, end;

    public Wall (Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;

        if (start.equals(end))
            throw new RuntimeException("Die Mauer hat keine Ausdehnung");

        if (!isHorizontal() && !isVertical())
            throw new RuntimeException("Diagonale Mauern werden nicht unterstützt");
    }

    public Boolean isHorizontal () { return start.getY() == end.getY(); }
    public Boolean isVertical () { return start.getX() == end.getX(); }

    private void toggleBarriers (Space space, boolean barrier) {
        /*
            Erzeugt eine Barriere indem es die jeweils 2 verknüpften Tiles
            im Netz voneinander trennt. Verknüft sie wieder um die Barriere
            zu zerstören.
         */
        if (isHorizontal()) {
            int y = start.getY();
            for (int x = start.getX(); x < end.getX(); x++) {
                Tile tileNorth = space.getTile(x, y);
                Tile tileSouth = space.getTile(x, y-1);
                if (tileNorth != null)
                    tileNorth.setSouthNeighbor(barrier ? null : tileSouth);
                if (tileSouth != null)
                    tileSouth.setNorthNeighbor(barrier ? null : tileNorth);
            }
        }
        else {
            int x = start.getX();
            for (int y = start.getY(); y < end.getY(); y++) {
                Tile tileEast = space.getTile(x, y);
                Tile tileWest = space.getTile(x-1, y);
                if (tileEast != null)
                    tileEast.setWestNeighbor(barrier ? null : tileWest);
                if (tileWest != null)
                    tileWest.setEastNeighbor(barrier ? null : tileEast);
            }
        }
    }

    @Override
    public void buildUp (Space destinationSpace) {
        toggleBarriers(destinationSpace,true);
    }

    @Override
    public void tearDown (Space sourceSpace) {
        toggleBarriers(sourceSpace,false);
    }
}
