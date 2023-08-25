package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private UUID id;
    private UUID sourceSpaceShipDeckId;
    private Vector2D sourceVector2D;
    private UUID destinationSpaceShipDeckId;
    private Vector2D destinationVector2D;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Vector2D getSourceVector2D() {
        return sourceVector2D;
    }

    public void setSourceVector2D(Vector2D sourceVector2D) {
        this.sourceVector2D = sourceVector2D;
    }


    public Vector2D getDestinationVector2D() {
        return destinationVector2D;
    }

    public void setDestinationVector2D(Vector2D destinationVector2D) {
        this.destinationVector2D = destinationVector2D;
    }


    public UUID getDestinationSpaceShipDeckId() {
        return destinationSpaceShipDeckId;
    }

    public void setDestinationSpaceShipDeckId(UUID destinationSpaceShipDeckId) {
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
    }


    public UUID getSourceSpaceShipDeckId() {
        return sourceSpaceShipDeckId;
    }

    public void setSourceSpaceShipDeckId(UUID sourceSpaceShipDeckId) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
    }
}
