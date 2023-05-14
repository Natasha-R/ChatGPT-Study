package thkoeln.st.st2praktikum.exercise.HE_DOES_SPEAK_ENGLISH;

import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.Wall;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connector;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class World {

    private static World w;

    private List<Space> spaces;

    private List<CleaningDevice> cleaners;

    private List<TransportTechnology> transportTechnologies;

    public World(){
        spaces = new ArrayList<>();
        cleaners = new ArrayList<>();
        transportTechnologies = new ArrayList<>();
        w=this;
    }

    public static World getInstance() {
        return w==null?new World():w;
    }


    public void addSpace(Space space) {
        spaces.add(space);
    }

    public void addWalltoSpace(UUID spaceId, Wall wall) {
        for(Space sp:spaces){
            if(sp.getId()== spaceId){
                if(wall.isVertical()){
                    if(wall.getEnd().getY()<sp.getheight()){
                        sp.addWall(wall);
                    }else{
                        throw new IllegalArgumentException();
                    }
                }else{
                    if(wall.getEnd().getX()<sp.getwidth()){
                        sp.addWall(wall);
                    }else{
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
    }

    public void addWalltoSpace(Space space, Wall wall) {
        addWalltoSpace(space.getId(),wall);
    }

    public void addConnectors(UUID sourceSpaceId, Connector connector){
        for(Space sp:spaces){
            if(sp.getId() .equals(sourceSpaceId)){
                if(connector.getLocation1().getX()<sp.getwidth()&&connector.getLocation1().getY()<sp.getheight()){
                    sp.addConnector(connector);
                }else{
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public Space getSpace(UUID spaceId) throws SpaceNotFoundExeption {
        for(Space sp:spaces){
            if(sp.getId()== spaceId){
                return sp;
            }
        }
        throw new SpaceNotFoundExeption(spaceId);
    }

    public void addCleaningDevice(CleaningDevice cleaner){
        cleaners.add(cleaner);
    }

    public Boolean sendcommand(CleaningDevice cleaner, Command command) {
        boolean results = false;
        switch(command.getCommandType()){
            case TRANSPORT:
                results = transport(cleaner,command.getGridId());
                break;
            case ENTER:
                results= enable(cleaner,command.getGridId());
                break;
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                move(cleaner,command);
                results = true;
                break;
        }
        updateCleaningDevice(cleaner);
        return results;
    }

    private boolean transport(CleaningDevice cleaner, UUID spaceid) {
        boolean result = false;
        for(Space sp:spaces){
            if(sp.getId().equals(cleaner.getSpaceId())){
                result = cleaner.transport(sp,spaceid);
            }
        }
        return result;
    }

    private void move(CleaningDevice cleaner, Command command) {
        for(Space sp:spaces){
            if(sp.getId().equals(cleaner.getSpaceId())){
                cleaner.move(command,sp);
            }
        }
    }

    private boolean enable(CleaningDevice cleaningDevice, UUID spaceid) {
        boolean able=true;
        for (CleaningDevice cd : cleaners) {
            if (cd.getId() != cleaningDevice.getId()) {
                if (spaceid.equals(cd.getSpaceuuid())) {
                    if (cd.getPosition().equals(new Point(0,0))) {
                        able=false;
                    }
                }
            }
        }
        if(able==true){
            cleaningDevice.enable(spaceid);
        }
        return able;
    }

    public UUID getcleaningspaceid(UUID cleaningDeviceId) throws SpaceNotFoundExeption {
        for(CleaningDevice cd:cleaners){
            if(cd.getId()== cleaningDeviceId){
                return cd.getSpaceId();
            }
        }
        throw new SpaceNotFoundExeption(cleaningDeviceId);
    }

    public void addTransportTechnology(TransportTechnology technology){
        transportTechnologies.add(technology);
    }

    public TransportTechnology getTechnology(UUID TechnologyID){
        for(TransportTechnology tt: transportTechnologies){
            if(tt.getTechnologyId().equals(TechnologyID)){
                return tt;
            }
        }
        return null;
    }

    public void updateCleaningDevice(CleaningDevice cleaner) throws CleaningDeviceNotFoundExeption {
        int i=0;
        boolean set =false;
        for(CleaningDevice cd:cleaners){
            if(cd.getId()== cleaner.getId()){
                cleaners.set(i,cleaner);
                set=true;
            }
            i++;
        }
        if(!set)
        {
            throw new CleaningDeviceNotFoundExeption(cleaner.getUuid());
        }
    }

    public UUID addSpace(Integer height, Integer width) {
        Space space = new Space(height,width);
        spaces.add(space);
        return space.getId();
    }

    public UUID addConnectors(TransportTechnology transportTechnology, UUID sourceSpaceId, Connector connector) {
        transportTechnology.addConnector(connector);
        addConnectors(sourceSpaceId,connector);
        return connector.getId();
    }
}
