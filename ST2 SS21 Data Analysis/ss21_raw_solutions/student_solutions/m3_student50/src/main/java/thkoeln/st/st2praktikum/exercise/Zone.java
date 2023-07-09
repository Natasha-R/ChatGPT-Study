package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;

@Component
@Transactional
public class Zone  {

    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    private TransportSystemRepository transportSystemRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ConnectionRepository connectionRepository;

    protected HashMap<UUID, Room> rooms = new HashMap<>();
    protected HashMap<UUID, TidyUpRobot> tidyUpRobots = new HashMap<>();
    private HashMap<UUID, TransportSystem> transportSystems = new HashMap<>();

    public UUID getTidyUpRobotRoomId(UUID tidyUpRobot){
        return fetchTidyUpRobotById(tidyUpRobot).getCurrentRoomUUID();
    }
    public Coordinate getCoordinate(UUID tidyupRobotId){
        return fetchTidyUpRobotById(tidyupRobotId).getCoordinate();
    }
    protected Room fetchRoomById(UUID roomId){
        return rooms.get(roomId);
    }
    protected TidyUpRobot fetchTidyUpRobotById(UUID tidyupRobot){
        return tidyUpRobots.get(tidyupRobot);
    }


    public UUID createConnection(UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate) {
        Connection createdConnection =  new Connection(sourceRoomId,destinationRoomId,sourceCoordinate,destinationCoordinate);
        Room sourceRoom = fetchRoomById(sourceRoomId);
        Room destinationRoom = fetchRoomById(destinationRoomId);
        // check if the start and end Coordinate of the Connection inside th space
        if (sourceCoordinate.getX()<=sourceRoom.getWidth() && sourceCoordinate.getY()<=sourceRoom.getHeight()){

            if (destinationCoordinate.getX() <= destinationRoom.getWidth() && destinationCoordinate.getY()<= destinationRoom.getHeight()) {
                fetchRoomById(sourceRoomId).addConnection(createdConnection);
                return createdConnection.getId();
            }else throw new RuntimeException();
        }else throw new RuntimeException();
    }


    public UUID createRoom(Integer height, Integer width) {
        Room space = new Room(width,height);
        rooms.put(space.getId(), space);
        roomRepository.save(space);
        return space.getId();
    }


    public UUID createTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot();

        tidyUpRobot.setName(name);
        tidyUpRobots.put(tidyUpRobot.getId(),tidyUpRobot);
        tidyUpRobotRepository.save(tidyUpRobot);

        return tidyUpRobot.getId();    }


    public void createWall(UUID space, Wall wall) {
        fetchRoomById(space).addWall(wall);
    }

    public UUID createTransportTechnology(String technology){
        TransportSystem transportTechnology = new TransportSystem(technology);
        transportSystems.put(transportTechnology.getId(),transportTechnology);
        transportSystemRepository.save(transportTechnology);
        return transportTechnology.getId();
    }

    public boolean canTheCommandExecute(UUID tidyUpRobot, Order order){
        TidyUpRobot tidyUpRobot1 = tidyUpRobotRepository.findById(tidyUpRobot).get();
        if (order.getOrderType().equals(OrderType.ENTER)){
            boolean returnedValue = initialize(tidyUpRobot,order);
            tidyUpRobot1 = tidyUpRobots.get(tidyUpRobot);
            tidyUpRobotRepository.save(tidyUpRobot1);
            return returnedValue;
        }
        else if (order.getOrderType().equals(OrderType.TRANSPORT)){
            boolean returnedValue = transport(tidyUpRobot,order);
            tidyUpRobot1 = tidyUpRobots.get(tidyUpRobot);
            tidyUpRobotRepository.save(tidyUpRobot1);
            return returnedValue;
        }
        else{
            boolean returnedValue = movement(tidyUpRobot,order);
            tidyUpRobot1 = tidyUpRobots.get(tidyUpRobot);
            tidyUpRobotRepository.save(tidyUpRobot1);
            return returnedValue;
        }
    }


    public boolean movement(UUID cleaningDevice, Order command) {
        int steps = command.getNumberOfSteps();
        if (command.getOrderType().equals(OrderType.NORTH)){
            tidyUpRobots.get(cleaningDevice).moveNorth(steps);
        }
        else if (command.getOrderType().equals(OrderType.SOUTH)){
            tidyUpRobots.get(cleaningDevice).moveSouth(steps);
        }
        else if (command.getOrderType().equals(OrderType.WEST)){
            tidyUpRobots.get(cleaningDevice).moveWest(steps);
        }
        else if (command.getOrderType().equals(OrderType.EAST)){
            tidyUpRobots.get(cleaningDevice).moveEast(steps);
        }

        return false;
    }

    public boolean transport(UUID tidyUpRobot, Order command) {
        UUID destinationSpaceId = command.getGridId();
        TidyUpRobot robot = fetchTidyUpRobotById(tidyUpRobot);
        Connection Connection = fetchRoomById(robot.getCurrentRoomUUID()).fetchConnectionByDestinationRoomId(destinationSpaceId);
        Room desSpace = fetchRoomById(destinationSpaceId);

        //check if Tidyuprobot's coordinate same as the connection's source coordinate
        if (Connection.getSourceCoordinate().equals(robot.getCoordinate())) {
            if (!desSpace.tidyUpRobots.isEmpty()){
                for (int i=0; i<desSpace.tidyUpRobots.size();i++){
                    if (desSpace.tidyUpRobots.get(i).getCoordinate().equals(Connection.getDestinationCoordinate())){
                        throw new  RuntimeException();
                    }
                }
            } else {
                //remove the robot from Source Room and add it in Destination Room
                fetchRoomById(robot.getCurrentRoomUUID()).tidyUpRobots.remove(tidyUpRobots.get(tidyUpRobot));
                robot.setXY(Connection.getDestinationCoordinate().getX(), Connection.getDestinationCoordinate().getY());
                robot.setCurrentRoomUUID(destinationSpaceId);
                robot.setCurrentRoom(fetchRoomById(destinationSpaceId));
                return true;
            }
        }
        return false;
    }

    private void setTidyUpRobot(UUID tidyUpRobot, UUID room){
        fetchTidyUpRobotById(tidyUpRobot).setCurrentRoomUUID(room);
        fetchTidyUpRobotById(tidyUpRobot).setXY(0,0);
        fetchTidyUpRobotById(tidyUpRobot).setCurrentRoom(fetchRoomById(room));
        fetchRoomById(room).tidyUpRobots.add(fetchTidyUpRobotById(tidyUpRobot));
    }

    public boolean initialize(UUID tidyupRobot, Order command) {
        UUID room = command.getGridId();

        if (fetchRoomById(room).tidyUpRobots.isEmpty()) {
            setTidyUpRobot(tidyupRobot, room);
            return true;
        }else{
            for (int i = 0; i< fetchRoomById(room).tidyUpRobots.size(); i++) {
                if (fetchRoomById(room).tidyUpRobots.get(i).getCoordinate().equals(new Coordinate(0,0))) return false;
                else {
                    setTidyUpRobot(tidyupRobot, room);
                    return true;
                }
            }
        }
        return false;
    }

}

