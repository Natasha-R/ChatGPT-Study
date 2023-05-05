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

    public void addObstacletoSpace(UUID spaceId, Obstacle obstacle ) {
        for(SpaceShipDeck sp: spaceShipDecks){
            if(sp.getId()== spaceId){
                sp.addObstacle(obstacle);
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


    public void addDroids(Droid droid){
        droids.add(droid);
    }

    public boolean sendcommand(UUID maintenanceDroidId, Task task){
        boolean executed = false;
        switch (task.getTaskType()) {
            case TRANSPORT:
                executed=transport(maintenanceDroidId,task.getGridId());
                break;
            case ENTER:
                executed=enter(maintenanceDroidId,task.getGridId());
                break;
            default:
                move(maintenanceDroidId,task);
        }
        return executed;

    }

    private boolean transport(UUID droidId, UUID spaceId) {
        boolean result = false;
        for (Droid droid : droids) {
            if (droid.getDroidId() == droidId) {
                for(SpaceShipDeck sp: spaceShipDecks){
                    if(sp.getId().equals(droid.getspaceId())){
                        result = droid.transport(sp,spaceId);
                    }
                }
            }
        }
        return result;
    }

    private void move(UUID droidId, Task task) {
        for (Droid droid : droids) {
            if (droid.getDroidId() == droidId) {
                for(SpaceShipDeck sp: spaceShipDecks){
                    if(sp.getId().equals(droid.getspaceId())){
                        droid.move(task,sp);
                    }
                }
            }
        }
    }

    private boolean enter(UUID maintenanceDroidId, UUID gridId) {
        boolean able=true;
        for (Droid droid : droids) {
            if (droid.getDroidId()!=maintenanceDroidId) {
                if (gridId.equals(droid.getspaceId())) {
                    if (droid.getPosition().equals(new Vector2D(0,0))) {
                        able=false;
                    }
                }
            }
        }
        if (able==true) {
            for (Droid droid:droids) {
                if (droid.getDroidId()==maintenanceDroidId) {
                    droid.enable(gridId);
                }
            }
        }
        return able;

    }

    public UUID getMaintenanceDroidId(UUID maintenanceDroidId) throws IllegalArgumentException {
        for(Droid droid: droids){
            if(droid.getDroidId()== maintenanceDroidId){
                return droid.getspaceId();
            }
        }
        throw new IllegalArgumentException();
    }

    public Droid getMaintenanceDroid(UUID maintenanceDroidId) throws IllegalArgumentException {
        for(Droid droid: droids){
            if(droid.getDroidId()== maintenanceDroidId){
                return droid;
            }
        }
        throw new IllegalArgumentException();
    }
}
