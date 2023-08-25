package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Wall;

import thkoeln.st.st2praktikum.exercise.transportsystem.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystem;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.TransportSystemRepository;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.UUID;


/** This is a class which contains many Spaces and also the cleaning devices that stands in every Space */

@Service
@Transactional
public class Zone implements Createable {


    @Autowired
    private TidyUpRepository tidyuUpRobotRepository;
    @Autowired
    private TransportSystemRepository transportSystemRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ConnectionRepository connectionRepository;


    private HashMap<UUID, Room> rooms = new HashMap<>();
    private HashMap<UUID, TidyUpRobot> tidyUpRobots = new HashMap<>();
    private HashMap<UUID, TransportSystem> transportSystem = new HashMap<>();

    public UUID getTidyUpRobotRoomId(UUID cleaningDevice){
        return fetchTidyUpRobotById(cleaningDevice).getRoomId();
    }
    public TidyUpRobot getTidyUpRobotById(UUID cleaningDevice){
        return tidyuUpRobotRepository.getTidyUpRobotById(cleaningDevice);
    }
    public Coordinate getCoordinates(UUID cleaningDeviceId){
        return fetchTidyUpRobotById(cleaningDeviceId).getCoordinate();
    }
    private Room fetchRoomById(UUID spaceId){
        return rooms.get(spaceId);
    }
    private TidyUpRobot fetchTidyUpRobotById(UUID cleaningDevice){
        return tidyUpRobots.get(cleaningDevice);
    }


    @Override
    public UUID createConnection(UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {
        Connection createdConnection =  new Connection(sourceSpaceId,destinationSpaceId,sourceCoordinate,destinationCoordinate);
        Room souSpace = fetchRoomById(sourceSpaceId);
        Room desSpace = fetchRoomById(destinationSpaceId);
        // check if the start and end Coordinate of the Connection inside th space
        if (sourceCoordinate.getX()<=souSpace.getWidth() && sourceCoordinate.getY()<=souSpace.getHeight()){

            if (destinationCoordinate.getX()<=desSpace.getWidth() && destinationCoordinate.getY()<= desSpace.getHeight()) {
                fetchRoomById(sourceSpaceId).addConnection(createdConnection);
                return createdConnection.getId();
            }else throw new RuntimeException();
        }else throw new RuntimeException();
    }

    @Override
    public UUID createSpace(Integer height, Integer width) {
        Room room = new Room(width,height);
        rooms.put(room.getId(), room);
        roomRepository.save(room);
        return room.getId();
    }


    @Override
    public UUID createCleaningDevice(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot();

        tidyUpRobot.setName(name);
        tidyUpRobots.put(tidyUpRobot.getId(),tidyUpRobot);
        tidyuUpRobotRepository.save(tidyUpRobot);
        return tidyUpRobot.getId();    }

    @Override
    public void createWall(UUID space, Wall wall) {
        fetchRoomById(space).addWall(wall);
    }

    public UUID createTransportTechnology(String technology){
        TransportSystem transportSystem = new TransportSystem(technology);
        this.transportSystem.put(transportSystem.getId(),transportSystem);
        transportSystemRepository.save(transportSystem);
        return transportSystem.getId();
    }

    public boolean canTheCommandExecute(UUID cleaningDevice, Order order){
        TidyUpRobot tidyUpRobot = tidyuUpRobotRepository.findById(cleaningDevice).get();
        if (order.getCommandType().equals(OrderType.ENTER)){
            boolean returnedValue = initialize(cleaningDevice,order);
            tidyUpRobot = tidyUpRobots.get(cleaningDevice);
            tidyUpRobot.addCommand(order);
            tidyuUpRobotRepository.save(tidyUpRobot);
            return returnedValue;
        }
        else if (order.getCommandType().equals(OrderType.TRANSPORT)){
            boolean returnedValue = transport(cleaningDevice,order);
            tidyUpRobot = tidyUpRobots.get(cleaningDevice);
            tidyUpRobot.addCommand(order);
            tidyuUpRobotRepository.save(tidyUpRobot);
            return returnedValue;
        }
        else{
            boolean returnedValue = movement(cleaningDevice,order);
            tidyUpRobot = tidyUpRobots.get(cleaningDevice);
            tidyUpRobot.addCommand(order);
            tidyuUpRobotRepository.save(tidyUpRobot);
            return returnedValue;
        }
    }

    public boolean movement(UUID cleaningDevice, Order order) {
        int steps = order.getNumberOfSteps();
        if (order.getCommandType().equals(OrderType.NORTH)){
            tidyUpRobots.get(cleaningDevice).moveNorth(steps);
        }
        else if (order.getCommandType().equals(OrderType.SOUTH)){
            tidyUpRobots.get(cleaningDevice).moveSouth(steps);
        }
        else if (order.getCommandType().equals(OrderType.WEST)){

            tidyUpRobots.get(cleaningDevice).moveWest(steps);
        }
        else if (order.getCommandType().equals(OrderType.EAST)){
            tidyUpRobots.get(cleaningDevice).moveEast(steps);
        }

        return false;
    }

    public boolean transport(UUID cleaningDevice, Order order) {
        UUID destinationRoomId = order.getGridId();
        TidyUpRobot tidyUpRobot = fetchTidyUpRobotById(cleaningDevice);
        Connection Connection = fetchRoomById(tidyUpRobot.getRoomId()).fetchConnectionByDestinationRoomId(destinationRoomId);
        Room desSpace = fetchRoomById(destinationRoomId);

        // check if the coordinate of the cleaning tidyUpRobot equals the source coordinate of the connection.
        if (Connection.getSourceCoordinate().equals(tidyUpRobot.getCoordinate())) {
            if (!desSpace.getTidyUpRobots().isEmpty()){
                for (int i = 0; i<desSpace.getTidyUpRobots().size(); i++){
                    // check the destination position if its occupied.
                    if (desSpace.getTidyUpRobots().get(i).getCoordinate().equals(Connection.getDestinationCoordinate())){
                        throw new  RuntimeException();
                    }
                }
            } else {

                // remove the cleaning tidyUpRobot form the source space and add it in the destination space
                fetchRoomById(tidyUpRobot.getRoomId()).getTidyUpRobots().remove(tidyUpRobots.get(cleaningDevice));
                tidyUpRobot.setXY(Connection.getDestinationCoordinate().getX(), Connection.getDestinationCoordinate().getY());
                tidyUpRobot.setRoomUUID(destinationRoomId);
                //tidyUpRobot.setRoomId(destinationRoomId)
                tidyUpRobot.setCurrentRoom(fetchRoomById(destinationRoomId));
                return true;
            }
        }
        return false;
    }

    private void setTidyUpRobot(UUID tidyUpRobot, UUID room){
        fetchTidyUpRobotById(tidyUpRobot).setRoomUUID(room);
        //fetchTidyUpRobotById(tidyUpRobot).setRoomId(room);
        fetchTidyUpRobotById(tidyUpRobot).setXY(0,0);
        fetchTidyUpRobotById(tidyUpRobot).setCurrentRoom(fetchRoomById(room));
        fetchRoomById(room).getTidyUpRobots().add(fetchTidyUpRobotById(tidyUpRobot));
    }

    public boolean initialize(UUID cleaningDevice, Order order) {
        UUID space = order.getGridId();

        if (fetchRoomById(space).getTidyUpRobots().isEmpty()) {
            setTidyUpRobot(cleaningDevice, space);
            return true;
        }else{
            for (int i = 0; i< fetchRoomById(space).getTidyUpRobots().size(); i++) {
                if (fetchRoomById(space).getTidyUpRobots().get(i).getCoordinate().equals(new Coordinate(0,0))) return false;
                else {
                    setTidyUpRobot(cleaningDevice, space);
                    return true;
                }
            }
        }
        return false;
    }



}

