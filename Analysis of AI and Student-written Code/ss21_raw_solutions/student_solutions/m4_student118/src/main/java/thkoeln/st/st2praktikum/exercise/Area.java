package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Barrier;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.ConnectionRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.UUID;


/** This is a class which contains many Spaces and also the cleaning devices that stands in every Space */

@Service
@Transactional
public class Area implements Createable, Commandable {


    @Autowired
    private CleaningDeviceRepository cleaningDeviceRepository;
    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;
    @Autowired
    private SpaceRepository spaceRepository;
    @Autowired
    private ConnectionRepository connectionRepository;


    private HashMap<UUID, Space> spaces = new HashMap<>();
    private HashMap<UUID, CleaningDevice> cleaningDevices = new HashMap<>();
    private HashMap<UUID, TransportTechnology> transportTechnologies = new HashMap<>();


    public UUID getCleaningDeviceSpaceId(UUID cleaningDevice){
        return fetchCleaningDeviceById(cleaningDevice).getSpaceId();
    }
    public CleaningDevice getCleaningDeviceById(UUID cleaningDevice){
        return cleaningDeviceRepository.getCleaningDeviceById(cleaningDevice);
    }
    public Coordinate getCoordinates(UUID cleaningDeviceId){
        return fetchCleaningDeviceById(cleaningDeviceId).getCoordinate();
    }
    private Space fetchSpaceById(UUID spaceId){
        return spaces.get(spaceId);
    }
    private CleaningDevice fetchCleaningDeviceById(UUID cleaningDevice){
        return cleaningDevices.get(cleaningDevice);
    }


    @Override
    public UUID createConnection(UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {
        Connection createdConnection =  new Connection(sourceSpaceId,destinationSpaceId,sourceCoordinate,destinationCoordinate);
        Space souSpace = fetchSpaceById(sourceSpaceId);
        Space desSpace = fetchSpaceById(destinationSpaceId);
        // check if the start and end Coordinate of the Connection inside th space
        if (sourceCoordinate.getX()<=souSpace.getWidth() && sourceCoordinate.getY()<=souSpace.getHeight()){

            if (destinationCoordinate.getX()<=desSpace.getWidth() && destinationCoordinate.getY()<= desSpace.getHeight()) {
                fetchSpaceById(sourceSpaceId).addConnection(createdConnection);
                return createdConnection.getId();
            }else throw new RuntimeException();
        }else throw new RuntimeException();
    }

    @Override
    public UUID createSpace(Integer height, Integer width) {
        Space space = new Space(width-1,height-1);
        spaces.put(space.getId(), space);
        spaceRepository.save(space);
        return space.getId();
    }

    @Override
    public UUID createCleaningDevice(String name) {
        CleaningDevice cleaningDevice = new CleaningDevice();

        cleaningDevice.setName(name);
        cleaningDevices.put(cleaningDevice.getId(),cleaningDevice);
        cleaningDeviceRepository.save(cleaningDevice);
        return cleaningDevice.getId();    }

    @Override
    public void createBarrier(UUID space, Barrier barrier) {
        fetchSpaceById(space).addBarrier(barrier);
    }

    public UUID createTransportTechnology(String technology){
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologies.put(transportTechnology.getId(),transportTechnology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getId();
    }
    public boolean canTheCommandExecute(UUID cleaningDevice, Command command){
        CleaningDevice cleaningDevice1 = cleaningDeviceRepository.findById(cleaningDevice).get();
        if (command.getCommandType().equals(CommandType.ENTER)){
            boolean returnedValue = initialize(cleaningDevice,command);
            cleaningDevice1 = cleaningDevices.get(cleaningDevice);
            cleaningDevice1.addCommand(command);
            cleaningDeviceRepository.save(cleaningDevice1);
            return returnedValue;
        }
        else if (command.getCommandType().equals(CommandType.TRANSPORT)){
            boolean returnedValue = transport(cleaningDevice,command);
            cleaningDevice1 = cleaningDevices.get(cleaningDevice);
            cleaningDevice1.addCommand(command);
            cleaningDeviceRepository.save(cleaningDevice1);
            return returnedValue;
        }
        else{
            boolean returnedValue = movement(cleaningDevice,command);
            cleaningDevice1 = cleaningDevices.get(cleaningDevice);
            cleaningDevice1.addCommand(command);
            cleaningDeviceRepository.save(cleaningDevice1);
            return returnedValue;
        }
    }

    @Override
    public boolean movement(UUID cleaningDevice, Command command) {
        int steps = command.getNumberOfSteps();
        if (command.getCommandType().equals(CommandType.NORTH)){
            cleaningDevices.get(cleaningDevice).moveNorth(steps);
        }
        else if (command.getCommandType().equals(CommandType.SOUTH)){
            cleaningDevices.get(cleaningDevice).moveSouth(steps);
        }
        else if (command.getCommandType().equals(CommandType.WEST)){

            cleaningDevices.get(cleaningDevice).moveWest(steps);
        }
        else if (command.getCommandType().equals(CommandType.EAST)){
            cleaningDevices.get(cleaningDevice).moveEast(steps);
        }

        return false;
    }

    @Override
    public boolean transport(UUID cleaningDevice, Command command) {
        UUID destinationSpaceId = command.getGridId();
        CleaningDevice device = fetchCleaningDeviceById(cleaningDevice);
        Connection Connection = fetchSpaceById(device.getSpaceId()).fetchConnectionByDestinationSpaceId(destinationSpaceId);
        Space desSpace = fetchSpaceById(destinationSpaceId);

        // check if the coordinate of the cleaning device equals the source coordinate of the connection.
        if (Connection.getSourceCoordinate().equals(device.getCoordinate())) {
            if (!desSpace.getCleaningDevices().isEmpty()){
                for (int i = 0; i<desSpace.getCleaningDevices().size(); i++){
                    // check the destination position if its occupied.
                    if (desSpace.getCleaningDevices().get(i).getCoordinate().equals(Connection.getDestinationCoordinate())){
                        throw new  RuntimeException();
                    }
                }
            } else {

                // remove the cleaning device form the source space and add it in the destination space
                fetchSpaceById(device.getSpaceId()).getCleaningDevices().remove(cleaningDevices.get(cleaningDevice));
                device.setXY(Connection.getDestinationCoordinate().getX(), Connection.getDestinationCoordinate().getY());
                device.setSpaceId(destinationSpaceId);
                device.setCurrentSpace(fetchSpaceById(destinationSpaceId));
                return true;
            }
        }
        return false;
    }

    private void setCleaningDevice(UUID cleaningDevice, UUID space){
        fetchCleaningDeviceById(cleaningDevice).setSpaceId(space);
        fetchCleaningDeviceById(cleaningDevice).setXY(0,0);
        fetchCleaningDeviceById(cleaningDevice).setCurrentSpace(fetchSpaceById(space));
        fetchSpaceById(space).getCleaningDevices().add(fetchCleaningDeviceById(cleaningDevice));
    }

    @Override
    public boolean initialize(UUID cleaningDevice, Command command) {
        UUID space = command.getGridId();

        if (fetchSpaceById(space).getCleaningDevices().isEmpty()) {
            setCleaningDevice(cleaningDevice, space);
            return true;
        }else{
            for (int i = 0; i<fetchSpaceById(space).getCleaningDevices().size(); i++) {
                if (fetchSpaceById(space).getCleaningDevices().get(i).getCoordinate().equals(new Coordinate(0,0))) return false;
                else {
                    setCleaningDevice(cleaningDevice, space);
                    return true;
                }
            }
        }
        return false;
    }

}


