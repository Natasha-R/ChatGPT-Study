package thkoeln.st.st2praktikum.exercise;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
public class World {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany
    private List<SpaceShipDeck> spaceShipDecks;
    @OneToMany
    private List<MaintenanceDroid> maintenanceDroids;
    @OneToMany
    private List<TransportTechnology> transportTechnologies;


    public World(){
        spaceShipDecks =new ArrayList<>();
        maintenanceDroids =new ArrayList<>();
        transportTechnologies= new ArrayList<>();
    }

    public void addSpace(SpaceShipDeck space) {
        spaceShipDecks.add(space);
    }

    public void addObstacletoSpace(UUID spaceId, Obstacle obstacle ) {
        for(SpaceShipDeck sp:spaceShipDecks){
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

    public void addConnectors(UUID sourceSpaceId,Connector connector){
        for(SpaceShipDeck sp: spaceShipDecks){
            if(sp.getId()== sourceSpaceId){
                sp.addConnector(connector);
            }
        }
    }


    public void addDroids(MaintenanceDroid maintenanceDroid){
        maintenanceDroids.add(maintenanceDroid);
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
        for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
            if (maintenanceDroid.getDroidId() == droidId) {
                for(SpaceShipDeck sp: spaceShipDecks){
                    if(sp.getId().equals(maintenanceDroid.getSpaceShipDeckId())){
                        result = maintenanceDroid.transport(sp,spaceId);
                    }
                }
            }
        }
        return result;
    }

    private void move(UUID droidId, Task task) {
        for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
            if (maintenanceDroid.getDroidId() == droidId) {
                for(SpaceShipDeck sp: spaceShipDecks){
                    if(sp.getId().equals(maintenanceDroid.getSpaceShipDeckId())){
                        maintenanceDroid.move(task,sp);
                    }
                }
            }
        }
    }

    private boolean enter(UUID maintenanceDroidId, UUID gridId) {
        boolean able=true;
        for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
            if (maintenanceDroid.getDroidId()!=maintenanceDroidId) {
                if (gridId.equals(maintenanceDroid.getSpaceShipDeckId())) {
                    if (maintenanceDroid.getVector2D().equals(new Vector2D(0,0))) {
                        able=false;
                    }
                }
            }
        }
        if (able==true) {
            for (MaintenanceDroid maintenanceDroid : maintenanceDroids) {
                if (maintenanceDroid.getDroidId()==maintenanceDroidId) {
                    maintenanceDroid.enable(gridId);
                }
            }
        }
        return able;

    }

    public UUID getMaintenanceDroidId(UUID maintenanceDroidId) throws IllegalArgumentException {
        for(MaintenanceDroid maintenanceDroid : maintenanceDroids){
            if(maintenanceDroid.getDroidId()== maintenanceDroidId){
                return maintenanceDroid.getSpaceShipDeckId();
            }
        }
        throw new IllegalArgumentException();
    }

    public MaintenanceDroid getMaintenanceDroid(UUID maintenanceDroidId) throws IllegalArgumentException {
        for(MaintenanceDroid maintenanceDroid : maintenanceDroids){
            if(maintenanceDroid.getDroidId()== maintenanceDroidId){
                return maintenanceDroid;
            }
        }
        throw new IllegalArgumentException();
    }

    public void addTransportTechnology(TransportTechnology technology) {
        transportTechnologies.add(technology);
    }

    public TransportTechnology getTechnology(UUID TransportID) {
        for (TransportTechnology transportTechnology:transportTechnologies) {
            if (transportTechnology.getTransportId().equals(TransportID)) {
                return transportTechnology;
            }
        }
        return null;
    }
}
