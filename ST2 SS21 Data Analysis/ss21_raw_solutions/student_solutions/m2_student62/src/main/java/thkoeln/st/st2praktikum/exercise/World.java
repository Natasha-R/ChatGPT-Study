package thkoeln.st.st2praktikum.exercise;

import java.util.LinkedList;
import java.util.UUID;

public class World {
    private LinkedList<Space> spaces;
    private LinkedList<CleaningDevice> cleaners;


    public World(){
        spaces=new LinkedList<>();
        cleaners=new LinkedList<>();
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
            if(sp.getId()== sourceSpaceId){
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
                    if(sp.getId().equals(cd.getspaceId())){
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
                    if(sp.getId().equals(cd.getspaceId())){
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
                if (spaceid.equals(cd.getspaceId())) {
                    if (cd.getposition().equals(new Point(0,0))) {
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
                return cd.getspaceId();
            }
        }
        throw new SpaceNotFoundExeption(cleaningDeviceId);
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
