package thkoeln.st.st2praktikum.exercise;

import org.hibernate.ObjectNotFoundException;

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

    public void addWalltoSpace(UUID spaceId, String wallString) {
        for(Space sp:spaces){
            if(sp.getId()== spaceId){
                sp.addWall(wallString);
            }
        }
    }

    public void addConnectors(UUID sourceSpaceId,Connector connector){
        for(Space sp:spaces){
            if(sp.getId()== sourceSpaceId){
                sp.addConnector(connector);
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

    private boolean transport(UUID cleaningDeviceId, String spaceid) {
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

    private void move(UUID cleaningDeviceId, String commandString) {
        for (CleaningDevice cd : cleaners) {
            if (cd.getId() == cleaningDeviceId) {
                for(Space sp:spaces){
                    if(sp.getId().equals(cd.getspaceId())){
                        cd.move(commandString,sp);
                    }
                }
            }
        }
    }

    private boolean enable(UUID cleaningDeviceId, String spaceid) {
        boolean able=true;
        for (CleaningDevice cd : cleaners) {
            if (cd.getId() != cleaningDeviceId) {
                if (UUID.fromString(spaceid).equals(cd.getspaceId())) {
                    if (cd.getposition().equalsIgnoreCase("(0,0)")) {
                        able=false;
                    }
                }
            }
        }
        if(able==true){
            for (CleaningDevice cd : cleaners) {
                if (cd.getId() == cleaningDeviceId) {
                    cd.enable(UUID.fromString(spaceid));
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
