package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.execution.EnterCommand;
import thkoeln.st.st2praktikum.exercise.execution.MoveCommand;
import thkoeln.st.st2praktikum.exercise.execution.TransportCommand;
import thkoeln.st.st2praktikum.exercise.space.domain.SpaceRepository;


import java.util.Optional;
import java.util.UUID;


@Service
public class CleaningDeviceService {
    @Autowired
    CleaningDeviceRepository cleaningDeviceRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    EnterCommand enterCommand= new EnterCommand();

    @Autowired
    MoveCommand moveCommand= new MoveCommand();

    @Autowired
    TransportCommand transportCommand= new TransportCommand();
    
    /**
     * This method adds a new cleaning device
     * @param name the name of the cleaning device
     * @return the UUID of the created cleaning device
     */
    public UUID addCleaningDevice(String name) {

        CleaningDevice cleaningDevice = new CleaningDevice(name);
        cleaningDeviceRepository.save(cleaningDevice);
        return cleaningDevice.getCleaningDeviceId();
    }

    /**
     * This method lets the cleaning device execute a order.
     * @param cleaningDeviceId the ID of the cleaning device
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another space
     * ENTER for setting the initial space where a cleaning device is placed. The cleaning device will always spawn at (0,0) of the given space.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the cleaning device hits a obstacle or
     *      another cleaning device, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID cleaningDeviceId, Order order) {
        CleaningDevice cleaningDevice= cleaningDeviceRepository.findById(cleaningDeviceId).get();
        cleaningDevice.addOrder(order);

        switch (order.getOrderType()){
            case ENTER:return enterCommand.initialisingCommand(order,cleaningDeviceId);
            case NORTH:
                moveCommand.movingNorth(order,cleaningDeviceId);
                return true;
            case SOUTH:
                moveCommand.movingSouth(order,cleaningDeviceId);
                return true;
            case EAST:
                moveCommand.movingEast(order,cleaningDeviceId);
                return true;
            case WEST:
                moveCommand.movingWest(order,cleaningDeviceId);
                return true;
            case TRANSPORT:return transportCommand.transportingCommand(order,cleaningDeviceId);

            default:return false;
        }
    }

    /**
     * This method returns the space-ID a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the UUID of the space the cleaning device is located on
     */
    public UUID getCleaningDeviceSpaceId(UUID cleaningDeviceId){
        return cleaningDeviceRepository.findById(cleaningDeviceId).get().getSpaceId();

    }

    /**
     * This method returns the coordinate a cleaning device is standing on
     * @param cleaningDeviceId the ID of the cleaning device
     * @return the coordinate of the cleaning device
     */
    public Coordinate getCleaningDeviceCoordinate(UUID cleaningDeviceId){
        return cleaningDeviceRepository.findById(cleaningDeviceId).get().getCoordinate();

    }

    public Iterable<CleaningDevice> findAll(){
        return cleaningDeviceRepository.findAll();
    }

    public Optional<CleaningDevice> findById(UUID id){
        return Optional.ofNullable(cleaningDeviceRepository.findById(id).orElseThrow(() -> new CleaningDeviceNotFoundException("Not CleaningDevice with ID" + id + "can be found")));
    }

    public void deleteById(UUID id){
        CleaningDevice cleaningDevice= cleaningDeviceRepository.findById(id).orElseThrow(()-> new CleaningDeviceNotFoundException("Not CleaningDevice with ID" + id+ "can be found" ));
        cleaningDeviceRepository.delete(cleaningDevice);
    }

    public Iterable<Order>getOrderOfCleaningDeviceById(UUID id){
        CleaningDevice cleaningDevice= cleaningDeviceRepository.findById(id).orElseThrow(()-> new CleaningDeviceNotFoundException("Not CleaningDevice with ID" + id+ "can be found" ));
        return cleaningDevice.getOrders();
    }

    public void updateName(UUID id, String name){
        CleaningDevice cleaningDevice= cleaningDeviceRepository.findById(id).orElseThrow(()-> new CleaningDeviceNotFoundException("Not CleaningDevice with ID" + id+ "can be found" ));
        cleaningDevice.setName(name);
        save(cleaningDevice);
    }

    public void save(CleaningDevice cleaningDevice){
        cleaningDeviceRepository.save(cleaningDevice);
    }
}
