package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class World {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long WorldId;

    @OneToMany
    private List<Space> spaces;
    @OneToMany
    private List<CleaningDevice> cleaners;

    @OneToMany
    private List<TransportTechnology> transportTechnologies;


    public World(){
        spaces=new ArrayList<>();
        cleaners=new ArrayList<>();
        transportTechnologies = new ArrayList<>();
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

    public void addConnectors(UUID sourceSpaceId,Connector connector){
        for(Space sp:spaces){
            if(sp.getId().equals(sourceSpaceId)){
                if(connector.getLocation1().getX()<sp.getwidth()&&connector.getLocation1().getY()<sp.getheight()){
                    sp.addConnector(connector);
                }else{
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public Space getSpaces(UUID spaceId) throws SpaceNotFoundExeption {
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

    public Boolean sendcommand(UUID cleaningDeviceId, Command command) {
        boolean results = false;
        switch(command.getCommandType()){
            case TRANSPORT:
                results = transport(cleaningDeviceId,command.getGridId());
                break;
            case ENTER:
                results= enable(cleaningDeviceId,command.getGridId());
                break;
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                move(cleaningDeviceId,command);
                results = true;
                break;
        }
        return results;
    }

    private boolean transport(UUID cleaningDeviceId, UUID spaceid) {
        boolean result = false;
        for (CleaningDevice cd : cleaners) {
            if (cd.getId() == cleaningDeviceId) {
                for(Space sp:spaces){
                    if(sp.getId().equals(cd.getSpaceId())){
                        result = cd.transport(sp,spaceid);
                    }
                }
            }
        }
        return result;
    }

    private void move(UUID cleaningDeviceId, Command command) {
        for (CleaningDevice cd : cleaners) {
            if (cd.getId() == cleaningDeviceId) {
                for(Space sp:spaces){
                    if(sp.getId().equals(cd.getSpaceId())){
                        cd.move(command,sp);
                    }
                }
            }
        }
    }

    private boolean enable(UUID cleaningDeviceId, UUID spaceid) {
        boolean able=true;
        for (CleaningDevice cd : cleaners) {
            if (cd.getId() != cleaningDeviceId) {
                if (spaceid.equals(cd.getSpaceId())) {
                    if (cd.getPoint().equals(new Point(0,0))) {
                        able=false;
                    }
                }
            }
        }
        if(able==true){
            for (CleaningDevice cd : cleaners) {
                if (cd.getId() == cleaningDeviceId) {
                    cd.enable(spaceid);

                }
            }
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

    public CleaningDevice getcleaningdevice(UUID cleaningDeviceId) throws CleaningDeviceNotFoundExeption {
        for(CleaningDevice cd:cleaners){
            if(cd.getId()== cleaningDeviceId){
                return cd;
            }
        }
        throw new CleaningDeviceNotFoundExeption(cleaningDeviceId);
    }
}
