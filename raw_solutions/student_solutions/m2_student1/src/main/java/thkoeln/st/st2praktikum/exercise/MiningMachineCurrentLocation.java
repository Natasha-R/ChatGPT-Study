package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMachineCurrentLocation {
    private final UUID machineID;  //Maschinen ID
    private UUID currentFieldID;    //auf welchem Feld Sie sich aufhält
    private Point point; //siehe unten und wieder final machen

    public MiningMachineCurrentLocation(UUID machineID, UUID fieldID, Point point) {
        this.machineID = machineID;
        this.currentFieldID = fieldID;
        this.point = point;
    }

    public UUID getMachineID(){return machineID;}

    public UUID getCurrentFieldID(){return currentFieldID;}

    public Point getPoint(){return point;}

    public void setCurrentFieldID(UUID uuid){
        this.currentFieldID = uuid;
    }

    public void setCoordinate(Point point){
        this.point = point;
    } //hier Eintrag löschen und neu eintragen statt setter

}
