package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MaintenanceDroid {
    private UUID id;
    private String name;
    private UUID spaceShipDeckId;
    private Vector2D vector2D;

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Vector2D getVector2D() {
        return vector2D;
    }

    public void setVector2D(Vector2D vector2D) {
        this.vector2D = vector2D;
    }

    public void setVector2D(Integer x, Integer y) {
        this.vector2D = new Vector2D(x, y);
    }


    public UUID getSpaceShipDeckId() {
        return spaceShipDeckId;
    }

    public void setSpaceShipDeckId(UUID spaceShipDeckId) {
        this.spaceShipDeckId = spaceShipDeckId;
    }
}
