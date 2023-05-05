package thkoeln.st.st2praktikum.exercise.Obstacle;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Space.Space;
import thkoeln.st.st2praktikum.exercise.Space.Tile;

import java.util.UUID;


public class CleaningDevice implements Obstacle {
    @Id
    @Getter
    private UUID id = UUID.randomUUID();

    @Getter
    private String name;

    @Getter
    private Coordinate position;

    @Getter
    private Space space = null;

    public CleaningDevice(String name) {
        this.name = name;
    }

    private void applyStepsToPosition(Tile.Direction direction, int steps) {
        switch (direction) {
            case WEST: position.addToX(-steps); break;
            case EAST: position.addToX(steps); break;
            case SOUTH: position.addToY(-steps); break;
            case NORTH: position.addToY(steps); break;
            default: break;
        }
    }

    public Boolean enterSpace (Space destinationSpace) {
        return transportToSpace(destinationSpace, new Coordinate(0,0));
    }

    public Boolean transportToSpace (Space destinationSpace,
                                     Coordinate destinationPosition) {
        Tile destinationTile = destinationSpace.getTile(destinationPosition);
        if (destinationTile.isOccupied()) {
            return false;
        }
        if (space != null) {
            space.removeObstacle(this);
        }
        position = destinationPosition;
        destinationSpace.addObstacle(this);
        return true;
    }

    public Integer move (Tile.Direction direction, int steps) {
        if (space == null)
            throw new RuntimeException("Device bewegt sich ausserhalb eines Space");

        Tile currentTile = space.getTile(position);
        currentTile.removeObstacle();
        int actualSteps = 0;

        for (; actualSteps < steps; actualSteps++) {
            Tile nextTile = currentTile.getNeighbor(direction);
            if (nextTile == null) break;
            if (nextTile.isOccupied()) break;

            currentTile = nextTile;
        }

        applyStepsToPosition(direction, actualSteps);
        currentTile.setObstacle(this);
        return actualSteps;
    }

    @Override
    public void buildUp (Space destinationSpace) {
        Tile tile = destinationSpace.getTile(position);
        tile.setObstacle(this);
        space = destinationSpace;
    }

    @Override
    public void tearDown (Space sourceSpace) {
        Tile tile = sourceSpace.getTile(position);
        tile.removeObstacle();
        space = null;
    }
}

