package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import thkoeln.st.st2praktikum.exercise.Exception.ObstacleException;

import java.util.UUID;


public class Obstacle implements Blocking {
    @Id
    @Getter
    private final UUID id = UUID.randomUUID();

    @Getter
    private Vector2D start, end;

    private void init (Vector2D start, Vector2D end) {
        if (start.length() > end.length()) {
            this.start = end;
            this.end = start;
        }
        else {
            this.start = start;
            this.end = end;
        }

        if (start.equals(end))
            throw new ObstacleException("Die Mauer hat keine Ausdehnung");

        if (!isHorizontal() && !isVertical())
            throw new ObstacleException("Diagonale Mauern werden nicht unterstützt");
    }

    public Obstacle (Vector2D start, Vector2D end) {
        init(start, end);
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle (String obstacleString) {
        if (!obstacleString.matches("^.+-.+$"))
            throw new ObstacleException("Formatfehler: " + obstacleString);

        String[] vectorStrings = obstacleString.split("-", 2);

        Vector2D start = new Vector2D(vectorStrings[0]);
        Vector2D end = new Vector2D(vectorStrings[1]);

        init(start, end);
    }

    @Override
    public String toString() {
        return start.toString() + " - " + end.toString();
    }

    public Boolean isHorizontal () { return start.getY() == end.getY(); }
    public Boolean isVertical () { return start.getX() == end.getX(); }

    /*
        Erzeugt eine Barriere indem es die jeweils 2 verknüpften Tiles
        im Netz voneinander trennt oder verknüft sie um die Barriere
        wieder zu zerstören.
     */
    private void toggleBarriers (Space space, boolean buildUpBarrier) {
        Vector2D iteratorVector = start, endVector = end;
        Vector2D stepVector, highNeighborVector;
        Tile.Direction barrierSideLow, barrierSideHigh;

        // Workaround weil keine negativen Vektoren erlaubt sind :(
        if (isHorizontal()) {
            iteratorVector.addToY(-1);
            endVector.addToY(-1);
        }
        else {
            iteratorVector.addToX(-1);
            endVector.addToX(-1);
        }

        if (isHorizontal()) {
            stepVector = new Vector2D(1,0);
            highNeighborVector = new Vector2D(0,1);
            barrierSideLow = Tile.Direction.SOUTH;
            barrierSideHigh = Tile.Direction.NORTH;
        }
        else {
            stepVector = new Vector2D(0,1);
            highNeighborVector = new Vector2D(1,0);
            barrierSideLow = Tile.Direction.WEST;
            barrierSideHigh = Tile.Direction.EAST;
        }

        while (!iteratorVector.equals(endVector)) {
            Tile tileHigh = space.getTile(iteratorVector.add(highNeighborVector));
            Tile tileLow = space.getTile(iteratorVector);

            if (buildUpBarrier && (tileLow == null || tileHigh == null))
                throw new ObstacleException(
                        "Die Mauer schneidet sich parallel einer anderen Barriere");

            if (tileLow != null)
                tileLow.setNeighbor(barrierSideHigh,
                        buildUpBarrier ? null : tileHigh);
            if (tileHigh != null)
                tileHigh.setNeighbor(barrierSideLow,
                        buildUpBarrier ? null : tileLow);

            iteratorVector = iteratorVector.add(stepVector);
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
