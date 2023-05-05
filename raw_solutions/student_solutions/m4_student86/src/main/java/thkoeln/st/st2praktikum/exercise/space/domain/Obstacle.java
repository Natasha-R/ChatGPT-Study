package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import thkoeln.st.st2praktikum.exercise.space.domain.ObstacleException;


@Entity
@Getter
public class Obstacle extends Blocking { //implements Blocking {
    //@Id
    //private final UUID id = UUID.randomUUID();

    @Embedded
    @AttributeOverride(name = "x", column = @Column(name = "startX"))
    @AttributeOverride(name = "y", column = @Column(name = "startY"))
    private Vector2D start;

    @Embedded
    @AttributeOverride(name = "x", column = @Column(name = "endX"))
    @AttributeOverride(name = "y", column = @Column(name = "endY"))
    private Vector2D end;


    protected Obstacle () {}

    public Obstacle (Vector2D start, Vector2D end) {
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

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public static Obstacle fromString(String obstacleString) {

        if (!obstacleString.matches("^.+-.+$"))
            throw new ObstacleException("Formatfehler: " + obstacleString);

        String[] vectorStrings = obstacleString.split("-", 2);

        Vector2D start = Vector2D.fromString(vectorStrings[0]);
        Vector2D end = Vector2D.fromString(vectorStrings[1]);

        return new Obstacle(start, end);
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
