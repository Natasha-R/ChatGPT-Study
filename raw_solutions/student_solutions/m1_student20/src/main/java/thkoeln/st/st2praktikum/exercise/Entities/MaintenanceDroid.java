package thkoeln.st.st2praktikum.exercise.Entities;

import javax.persistence.Id;
import java.util.UUID;

public class MaintenanceDroid {



    @Id
    protected UUID maintenanceDroid_Id;
    public MaintenanceDroid(){
        maintenanceDroid_Id = UUID.randomUUID();
    }

    public UUID getMaintenanceDroid_Id() {
        return maintenanceDroid_Id;
    }
    private SpaceShipDeck spaceShipDeck;

    public void setSpaceShipDeck(SpaceShipDeck spaceShipDeck) {
        this.spaceShipDeck = spaceShipDeck;
    }

    public SpaceShipDeck getSpaceShipDeck() {
        return spaceShipDeck;
    }

    private String nameOfMaintenanceDroid;

    public void setNameOfMaintenanceDroid(String nameOfMaintenanceDroid) {
        this.nameOfMaintenanceDroid = nameOfMaintenanceDroid;
    }

    public String getNameOfMaintenanceDroid() {
        return nameOfMaintenanceDroid;
    }


    private Coordinates xyCoordinatesOfDroid;

    public void setXYPositionOfDroid(Coordinates xyPosition) {
        this.xyCoordinatesOfDroid = xyPosition;
    }

    public Coordinates getXYCoordinatesOfMaintenanceDroid() {
        return xyCoordinatesOfDroid;
    }






}
