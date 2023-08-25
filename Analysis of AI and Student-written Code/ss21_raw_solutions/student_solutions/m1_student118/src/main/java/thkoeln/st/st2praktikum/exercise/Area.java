package thkoeln.st.st2praktikum.exercise;

import javassist.NotFoundException;

import java.util.HashMap;
import java.util.UUID;


/** This is a class which contains many Spaces and also the cleaning devices that stands in every Space */

public class Area implements Createable, Commandable{
    protected HashMap<UUID, Space> spaces = new HashMap<>();
    protected HashMap<UUID, CleaningDevice> cleaningDevices = new HashMap<>();


    protected Space fetchSpaceById(UUID spaceId){
        return spaces.get(spaceId);
    }
    protected CleaningDevice fetchCleaningDeviceById(UUID cleaningDevice){
        return cleaningDevices.get(cleaningDevice);
    }

    @Override
    public UUID createConnection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        Connection createdConnection =  new Connection(sourceSpaceId,destinationSpaceId,sourceCoordinate,destinationCoordinate);

        fetchSpaceById(sourceSpaceId).connections.add(createdConnection);

        return createdConnection.getId();
    }

    @Override
    public UUID createSpace(Integer height, Integer width) {
        Space space = new Space(width-1,height-1);

        spaces.put(space.getId(), space);

        return space.getId();
    }

    @Override
    public UUID createCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice();

        cleaningDevice.setName(name);
        cleaningDevices.put(cleaningDevice.getId(),cleaningDevice);

        return cleaningDevice.getId();    }

    @Override
    public void createBarrier(UUID space, String barrier) {
        fetchSpaceById(space).barrierrs.add(barrier);
    }




    protected boolean canTheCommandExecute(UUID cleaningDevice, String command){
        if (command.charAt(2)=='n'){
            return initialize(cleaningDevice,command);
        }
        else if (command.charAt(1)=='t'){
            return transport(cleaningDevice,command);
        }
        else {
            return movement(cleaningDevice,command);
        }
    }

    protected UUID getCleaningDeviceSpaceId(UUID cleaningDevice){
        return fetchCleaningDeviceById(cleaningDevice).getCurrentSpaceUUID();
    }
    protected String getCoordinates(UUID cleaningDeviceId){
        return fetchCleaningDeviceById(cleaningDeviceId).getCoordinate();
    }

    @Override
    public boolean movement(UUID cleaningDevice, String command) {
        int steps = Integer.parseInt(String.valueOf(command.charAt(4)));
        if (command.charAt(1)=='n'){
            cleaningDevices.get(cleaningDevice).moveNorth(steps);
        }
        else if (command.charAt(1)=='s'){
            cleaningDevices.get(cleaningDevice).moveSouth(steps);
        }
        else if (command.charAt(1)=='w'){
            cleaningDevices.get(cleaningDevice).moveWest(steps);
        }
        else if (command.charAt(1)=='e'){
            cleaningDevices.get(cleaningDevice).moveEast(steps);
        }

        return false;
}

    @Override
    public boolean transport(UUID cleaningDevice, String command) {
        UUID destinationSpaceId = UUID.fromString(command.substring(command.indexOf(',') + 1, command.length() - 1));



        if (fetchSpaceById(fetchCleaningDeviceById(cleaningDevice).getCurrentSpaceUUID()).fetchConnectionByDestinationSpaceId(destinationSpaceId)==null) {
            throw new NullPointerException("this Space doesn't contains a connection with the entered Space ID");
        }else {

            // Check if the cleaning device is on the connection coordinate.
            if (fetchSpaceById(fetchCleaningDeviceById(cleaningDevice).getCurrentSpaceUUID()).fetchConnectionByDestinationSpaceId(destinationSpaceId).getSourceCoordinate().equals(fetchCleaningDeviceById(cleaningDevice).getCoordinate())) {

                // get the x and y coordinate from destination coordinate String.
                int x = Integer.parseInt(String.valueOf(fetchSpaceById(fetchCleaningDeviceById(cleaningDevice).getCurrentSpaceUUID()).fetchConnectionByDestinationSpaceId(destinationSpaceId).getDestinationCoordinate().charAt(1)));
                int y = Integer.parseInt(String.valueOf(fetchSpaceById(fetchCleaningDeviceById(cleaningDevice).getCurrentSpaceUUID()).fetchConnectionByDestinationSpaceId(destinationSpaceId).getDestinationCoordinate().charAt(3)));

                // set the new coordinate.
                fetchCleaningDeviceById(cleaningDevice).setXY(x, y);
                fetchCleaningDeviceById(cleaningDevice).setCoordinate("(" + x + "," + y + ")");

                // remove the cleaning device form the source space and add it in the destination space
                fetchSpaceById(fetchCleaningDeviceById(cleaningDevice).getCurrentSpaceUUID()).cleaningDevices.remove(cleaningDevices.get(cleaningDevice));
                fetchCleaningDeviceById(cleaningDevice).setCurrentSpaceUUID(destinationSpaceId);
                fetchCleaningDeviceById(cleaningDevice).setCurrentSpace(fetchSpaceById(destinationSpaceId));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean initialize(UUID cleaningDevice, String command) {
        UUID space = UUID.fromString(command.substring(command.indexOf(',')+1,command.length()-1));

        if (fetchSpaceById(space).cleaningDevices.isEmpty()) {

            // initialize
            fetchCleaningDeviceById(cleaningDevice).setCurrentSpaceUUID(space);
            fetchCleaningDeviceById(cleaningDevice).setCurrentSpace(fetchSpaceById(space));
            fetchSpaceById(space).cleaningDevices.add(fetchCleaningDeviceById(cleaningDevice));

            return true;
        }else{
            for (int i=0;i<fetchSpaceById(space).cleaningDevices.size();i++) {

                if (fetchSpaceById(space).cleaningDevices.get(i).coordinate.equals("(0,0)")) return false;

                else {

                    // initialize
                    fetchCleaningDeviceById(cleaningDevice).setCurrentSpaceUUID(space);
                    fetchCleaningDeviceById(cleaningDevice).setCurrentSpace(fetchSpaceById(space));
                    fetchSpaceById(space).cleaningDevices.add(fetchCleaningDeviceById(cleaningDevice));

                    return true;
                }
            }
        }
        return false;
    }
}
