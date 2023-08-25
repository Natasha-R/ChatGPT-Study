package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MaintenanceDroid {

    private static List<MaintenanceDroid> maintenanceDroidList = new ArrayList<>();

    UUID maintenanceDroidId;
    UUID maintenanceDroidSpaceShipDeckId;
    String name;
    int xPosition = -1;
    int yPosition = -1;

    public MaintenanceDroid(UUID maintenanceDroidId, String name) {
        this.maintenanceDroidId = maintenanceDroidId;
        this.name = name;
        maintenanceDroidList.add(this);

    }

    public static MaintenanceDroid getMaintenanceDroid(UUID uuid) {
        MaintenanceDroid maintenanceDroid = null;
        for (MaintenanceDroid md : maintenanceDroidList) {
            if (md.getMaintenanceDroidId().equals(uuid)) {
                maintenanceDroid = md;
            }
        }
        return maintenanceDroid;
    }

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public UUID getMaintenanceDroidId() {
        return maintenanceDroidId;
    }

    public String getPosition() {
        return "(" + getXPosition() + "," + getYPosition() + ")";
    }

    public UUID getMaintenanceDroidSpaceShipDeckId() {
        return maintenanceDroidSpaceShipDeckId;
    }

    public void setMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidSpaceShipDeckId) {
        this.maintenanceDroidSpaceShipDeckId = maintenanceDroidSpaceShipDeckId;
    }
}
