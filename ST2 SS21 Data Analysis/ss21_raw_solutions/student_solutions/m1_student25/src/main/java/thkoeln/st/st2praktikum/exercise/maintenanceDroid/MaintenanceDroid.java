package thkoeln.st.st2praktikum.exercise.maintenanceDroid;

import thkoeln.st.st2praktikum.exercise.exception.MaintenanceDroidPositionIsNull;

import java.util.UUID;

public class MaintenanceDroid implements MaintenanceDroidInterface {
    private final UUID maintenanceDroidID = UUID.randomUUID();
    private final String name;
    private MaintenanceDroidPosition maintenanceDroidPosition;


    public MaintenanceDroid(String name){
        this.name = name;
    }

    public UUID getMaintenanceDroidID() {
        return maintenanceDroidID;
    }

    public MaintenanceDroidPosition getMaintenanceDroidPosition() throws MaintenanceDroidPositionIsNull {
        if(maintenanceDroidPosition != null) return maintenanceDroidPosition;
        else throw new MaintenanceDroidPositionIsNull("MaintenanceDroid position is NULL");
    }

    public void setMaintenanceDroidPosition(MaintenanceDroidPosition maintenanceDroidPosition) {
        this.maintenanceDroidPosition = maintenanceDroidPosition;
    }
}
