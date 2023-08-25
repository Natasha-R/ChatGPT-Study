package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import javax.persistence.Id;
import thkoeln.st.st2praktikum.exercise.Exception.CleaningDeviceException;
import thkoeln.st.st2praktikum.exercise.Repository.TransportCategoryRepository;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Entity
@Getter
public class CleaningDevice extends Blocking { //implements Blocking {
    //@Id
    //private UUID id = UUID.randomUUID();

    private String name;

    @Embedded
    private Vector2D position;

    @ManyToOne
    private Space space = null;

    protected CleaningDevice () {}

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

    private void enterSpace (Space destinationSpace, Vector2D destinationPosition) {
        Tile destinationTile = destinationSpace.getTile(destinationPosition);

        if (destinationTile.isOccupied())
            throw new CleaningDeviceException(
                "Eintrittspunkt " + destinationPosition +
                        " des Ziel-Space ist blockiert");

        if (space != null)
            space.removeBlocker(this);

        position = destinationPosition;
        destinationSpace.addBlocker(this);
    }

    public void enterSpace (Space destinationSpace) {
        enterSpace(destinationSpace, new Vector2D(0,0));
    }

    public void transportToSpace (List<TransportCategory> transportCategoryList, Space destinationSpace) {
        if (space == null)
            throw new CleaningDeviceException(
                "Device muss sich für den Transport in einem Space befinden");

        Vector2D exit = null;

        for (TransportCategory category : transportCategoryList) {
            exit = category.getExit(space, position, destinationSpace);
            if (exit != null) break;
        }

        if (exit == null)
            throw new CleaningDeviceException("Keine passende Verbindung gefunden");

        enterSpace(destinationSpace, exit);
    }

    public Integer move (Tile.Direction direction, int steps) {
        if (space == null)
            throw new CleaningDeviceException("Device bewegt sich ausserhalb eines Space");

        if (steps < 0)
            throw new CleaningDeviceException("Die Anzahl der Schritte muss positiv sein");

        Tile currentTile = space.getTile(position);
        currentTile.removeOccupier();
        int actualSteps = 0;

        for (; actualSteps < steps; actualSteps++) {
            Tile nextTile = currentTile.getNeighbor(direction);
            if (nextTile == null) break;
            if (nextTile.isOccupied()) break;

            currentTile = nextTile;
        }

        applyStepsToPosition(direction, actualSteps);
        currentTile.setOccupier(this);
        return actualSteps;
    }

    @Override
    public void buildUp (Space destinationSpace) {
        Tile tile = destinationSpace.getTile(position);
        tile.setOccupier(this);
        space = destinationSpace;
    }

    @Override
    public void tearDown (Space sourceSpace) {
        Tile tile = sourceSpace.getTile(position);
        tile.removeOccupier();
        space = null;
    }

    public UUID getSpaceId () {
        return space.getId();
    }

    public Vector2D getVector2D () {
        return position;
    }
}

