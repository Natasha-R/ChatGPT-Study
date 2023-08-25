package thkoeln.st.st2praktikum.exercise;

import java.util.LinkedList;
import java.util.UUID;

public class World {
    private LinkedList<SpaceShipDeck> spaceShipDecks;
    private LinkedList<Droid> droids;


    public World(){
        spaceShipDecks =new LinkedList<>();
        droids =new LinkedList<>();
    }

    public void addSpace(SpaceShipDeck space) {
        spaceShipDecks.add(space);
    }

    public void addObstacletoSpace(UUID spaceId, String obstacleString) {
        for(SpaceShipDeck sp: spaceShipDecks){
            if(sp.getId()== spaceId){
                sp.addObstacle(obstacleString);
            }
        }
    }

    public void addConnectors(UUID sourceSpaceId,Connector connector){
        for(SpaceShipDeck sp: spaceShipDecks){
            if(sp.getId()== sourceSpaceId){
                sp.addConnector(connector);
            }
        }
    }

    public SpaceShipDeck getSpaces(UUID spaceId) throws IllegalArgumentException{
        for(SpaceShipDeck sp: spaceShipDecks){
            if(sp.getId()== spaceId){
                return sp;
            }
        }
        throw new IllegalArgumentException();
    }

    public void addDroids(Droid droid){
        droids.add(droid);
    }

    public boolean sendcommand(UUID cleaningDeviceId, String commandString){
        boolean executed = false;
        String[] command = commandString.split(",");
        switch(command[0].substring(1)){
            case "tr":
                executed = transport(cleaningDeviceId,command[1].substring(0,command[1].length()-1));
                break;
            case "en":
                executed = enable(cleaningDeviceId, command[1].substring(0,command[1].length()-1));
                break;
            default:
                move(cleaningDeviceId, commandString);
        }

        return executed;
    }

    private boolean transport(UUID droidId, String spaceid) {
        boolean result = false;
        for (Droid droid : droids) {
            if (droid.getDroidId() == droidId) {
                for(SpaceShipDeck sp: spaceShipDecks){
                    if(sp.getId().equals(droid.getspaceId())){
                        result = droid.transport(sp,spaceid);
                    }
                }
            }
        }
        return result;
    }

    private void move(UUID droidId, String commandString) {
        for (Droid droid : droids) {
            if (droid.getDroidId() == droidId) {
                for(SpaceShipDeck sp: spaceShipDecks){
                    if(sp.getId().equals(droid.getspaceId())){
                        droid.move(commandString,sp);
                    }
                }
            }
        }
    }

    private boolean enable(UUID cleaningDeviceId, String spaceid) {
        boolean able=true;
        for (Droid droid : droids) {
            if (droid.getDroidId() != cleaningDeviceId) {
                if (UUID.fromString(spaceid).equals(droid.getspaceId())) {
                    if (droid.getposition().equalsIgnoreCase("(0,0)")) {
                        able=false;
                    }
                }
            }
        }
        if(able==true){
            for (Droid droid : droids) {
                if (droid.getDroidId()== cleaningDeviceId) {
                    droid.enable(UUID.fromString(spaceid));
                }
            }
        }
        return able;
    }

    public UUID getcleaningspaceid(UUID cleaningDeviceId) throws IllegalArgumentException {
        for(Droid droid: droids){
            if(droid.getDroidId()== cleaningDeviceId){
                return droid.getspaceId();
            }
        }
        throw new IllegalArgumentException();
    }

    public Droid getcleaningdevice(UUID cleaningDeviceId) throws IllegalArgumentException {
        for(Droid droid: droids){
            if(droid.getDroidId()== cleaningDeviceId){
                return droid;
            }
        }
        throw new IllegalArgumentException();
    }
}