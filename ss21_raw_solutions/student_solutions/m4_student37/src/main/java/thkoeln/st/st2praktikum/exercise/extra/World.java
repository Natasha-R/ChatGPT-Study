package thkoeln.st.st2praktikum.exercise.extra;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.Obstacle;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connector;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class World {
    private static World w;

    private List<SpaceShipDeck> spaces;

    private List<MaintenanceDroid> maintenanceDroids;

    private List<TransportTechnology> transportTechnologies;

    public World(){
        spaces = new ArrayList<>();
        maintenanceDroids = new ArrayList<>();
        transportTechnologies = new ArrayList<>();
        w=this;
    }

    public static World getInstance() {
        return w==null?new World():w;
    }


    public void addSpace(SpaceShipDeck space) {
        spaces.add(space);
    }

    public void addObstacleToSpace(UUID spaceId, Obstacle obstacle) {
        for(SpaceShipDeck sp:spaces){
            if(sp.getId()== spaceId){
                if(obstacle.isVertical()){
                    if(obstacle.getEnd().getY()<sp.getheight()){
                        sp.addObstacle(obstacle);
                    }else{
                        throw new IllegalArgumentException();
                    }
                }else{
                    if(obstacle.getEnd().getX()<sp.getwidth()){
                        sp.addObstacle(obstacle);
                    }else{
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
    }

    public void addObstacleToSpace(SpaceShipDeck space, Obstacle obstacle) {
        addObstacleToSpace(space.getId(),obstacle);
    }

    public void addConnectors(UUID sourceSpaceId, Connector connector){
        for(SpaceShipDeck sp:spaces){
            if(sp.getId() .equals(sourceSpaceId)){
                if(connector.getLocation1().getX()<sp.getwidth()&&connector.getLocation1().getY()<sp.getheight()){
                    sp.addConnector(connector);
                }else{
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public SpaceShipDeck getSpace(UUID spaceId) throws IllegalArgumentException {
        for(SpaceShipDeck sp:spaces){
            if(sp.getId()== spaceId){
                return sp;
            }
        }
        throw new IllegalArgumentException();
    }

    public void addMaintenanceDroid(MaintenanceDroid maintenanceDroid){
        maintenanceDroids.add(maintenanceDroid);
    }

    public Boolean sendcommand(MaintenanceDroid maintenanceDroid, Task task) {
        boolean results = false;
        switch(task.getTaskType()){
            case TRANSPORT:
                results = transport(maintenanceDroid,task.getGridId());
                break;
            case ENTER:
                results= enable(maintenanceDroid,task.getGridId());
                break;
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                move(maintenanceDroid,task);
                results = true;
                break;
        }
        updateMaintenanceDroid(maintenanceDroid);
        return results;
    }

    private boolean transport(MaintenanceDroid maintenanceDroid, UUID spaceid) {
        boolean result = false;
        for(SpaceShipDeck sp:spaces){
            if(sp.getId().equals(maintenanceDroid.getSpaceShipDeckId())){
                result = maintenanceDroid.transport(sp,spaceid);
            }
        }
        return result;
    }

    private void move(MaintenanceDroid maintenanceDroid, Task command) {
        for(SpaceShipDeck sp:spaces){
            if(sp.getId().equals(maintenanceDroid.getSpaceShipDeckId())){
                maintenanceDroid.move(command,sp);
            }
        }
    }

    private boolean enable(MaintenanceDroid maintenanceDroid, UUID spaceid) {
        boolean able=true;
        for (MaintenanceDroid cd : maintenanceDroids) {
            if (cd.getId() != maintenanceDroid.getId()) {
                if (spaceid.equals(cd.getSpaceuuid())) {
                    if (cd.getPosition().equals(new Vector2D(0,0))) {
                        able=false;
                    }
                }
            }
        }
        if(able==true){
            maintenanceDroid.enable(spaceid);
        }
        return able;
    }


    public void addTransportTechnology(TransportTechnology technology){
        transportTechnologies.add(technology);
    }

    public TransportTechnology getTechnology(UUID TechnologyID){
        for(TransportTechnology tt: transportTechnologies){
            if(tt.getTransportId().equals(TechnologyID)){
                return tt;
            }
        }
        return null;
    }

    public void updateMaintenanceDroid(MaintenanceDroid maintenanceDroid) throws IllegalArgumentException {
        int i=0;
        boolean set =false;
        for(MaintenanceDroid cd: maintenanceDroids){
            if(cd.getId()== maintenanceDroid.getId()){
                maintenanceDroids.set(i,maintenanceDroid);
                set=true;
            }
            i++;
        }
        if(!set)
        {
            throw new IllegalArgumentException();
        }
    }

    public UUID addSpace(Integer height, Integer width) {
        SpaceShipDeck space = new SpaceShipDeck(height,width);
        spaces.add(space);
        return space.getId();
    }

    public UUID addConnectors(TransportTechnology transportTechnology, UUID sourceSpaceId, Connector connector) {
        transportTechnology.addConnector(connector);
        addConnectors(sourceSpaceId,connector);
        return connector.getId();
    }
}
