package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;


public class MaintenanceDroid {

    private String maintenceDroidsName;
    private UUID maintenanceDroidsId;
    private UUID spaceShipDeskId;
    private Integer maintenceDroidsXPosition ;
    private Integer maintenceDroidsYPosition ;

    public MaintenanceDroid(String name, UUID maintenanceDroidsId) {
        this.maintenceDroidsName = name;
        this.maintenanceDroidsId = maintenanceDroidsId;
    }

    public Integer getMaintenceDroidsXPosition() {
        return maintenceDroidsXPosition;
    }

    public Integer getMaintenceDroidsYPosition() {
        return maintenceDroidsYPosition;
    }

    public UUID getMaintenanceDroidsId() {
        return maintenanceDroidsId;
    }


    public String getMaintenceDroidsPosition() {
        return "(" + maintenceDroidsXPosition + "," + maintenceDroidsYPosition +")";
    }

    public UUID getSpaceShipDeskId() {
        return spaceShipDeskId;
    }

    public void setSpaceShipDeskId(UUID spaceShipDeskId) {
        this.spaceShipDeskId = spaceShipDeskId;
    }

    public void setMaintenceDroidsXPosition(Integer maintenceDroidsXPosition) {
        this.maintenceDroidsXPosition = maintenceDroidsXPosition;
    }

    public void setMaintenceDroidsYPosition(Integer maintenceDroidsYPosition) {
        this.maintenceDroidsYPosition = maintenceDroidsYPosition;
    }
}
