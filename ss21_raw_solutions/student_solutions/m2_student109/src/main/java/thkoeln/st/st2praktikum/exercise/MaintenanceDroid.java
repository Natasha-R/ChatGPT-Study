package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;


public class MaintenanceDroid {

    private String maintenceDroidsName;
    private UUID maintenanceDroidsId;
    private UUID spaceShipDeskId;
   private Vector2D position;

    public MaintenanceDroid(String name, UUID maintenanceDroidsId) {
        this.maintenceDroidsName = name;
        this.maintenanceDroidsId = maintenanceDroidsId;
    }



    public UUID getMaintenanceDroidsId() {
        return maintenanceDroidsId;
    }



    public UUID getSpaceShipDeskId() {
        return spaceShipDeskId;
    }

    public void setSpaceShipDeskId(UUID spaceShipDeskId) {
        this.spaceShipDeskId = spaceShipDeskId;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
}
