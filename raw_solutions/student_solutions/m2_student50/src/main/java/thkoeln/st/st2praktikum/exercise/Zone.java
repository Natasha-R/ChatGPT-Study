package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Zone implements Creatable {
    protected HashMap<UUID, Room> rooms = new HashMap<>();
    protected HashMap<UUID, TidyUpRobot> cleaningDevices = new HashMap<>();

    public UUID getTidyUpRobotRoomId(UUID cleaningDevice){
        return fetchTidyUpRobotById(cleaningDevice).getCurrentRoomUUID();
    }
    public Coordinate getCoordinate(UUID cleaningDeviceId){
        return fetchTidyUpRobotById(cleaningDeviceId).getCoordinate();
    }
    protected Room fetchRoomWithId(UUID roomId){
        return rooms.get(roomId);
    }
    protected TidyUpRobot fetchTidyUpRobotById(UUID cleaningDevice){
        return cleaningDevices.get(cleaningDevice);
    }

    @Override
    public UUID createConnection(UUID sourceSpaceId, Coordinate sourceCoordinate, UUID destinationSpaceId, Coordinate destinationCoordinate) {
        Connection createdConnection =  new Connection(sourceSpaceId,destinationSpaceId,sourceCoordinate,destinationCoordinate);
        Room souSpace = fetchRoomWithId(sourceSpaceId);
        Room desSpace = fetchRoomWithId(destinationSpaceId);

        if (sourceCoordinate.getX() <= souSpace.getWidth() && sourceCoordinate.getY()<=souSpace.getHeight()){
            if (destinationCoordinate.getX()<=desSpace.getWidth() && destinationCoordinate.getY()<= desSpace.getHeight()) {
                fetchRoomWithId(sourceSpaceId).addConnection(createdConnection);
                return createdConnection.getId();
            }else throw new RuntimeException();
        }else throw new RuntimeException();
    }

    @Override
    public UUID createRoom(Integer height, Integer width) {
        Room space = new Room(width,height);
        rooms.put(space.getId(), space);
        return space.getId();
    }

    @Override
    public UUID createTidyUpRobot(String name) {
        TidyUpRobot cleaningDevice = new TidyUpRobot();

        cleaningDevice.setName(name);
        cleaningDevices.put(cleaningDevice.getId(),cleaningDevice);

        return cleaningDevice.getId();    }

    @Override
    public void createWall(UUID space, Wall wall) {
        fetchRoomWithId(space).addWall(wall);
    }

    public boolean command(UUID tidyUpRobotId, Order order){
        if (order.getOrderType().equals(OrderType.ENTER)){
            return initialize(tidyUpRobotId,order);
        }
        else if (order.getOrderType().equals(OrderType.TRANSPORT)){
            return transport(tidyUpRobotId,order);
        }
        else {
            return movement(tidyUpRobotId,order);
        }
    }

    public boolean movement(UUID cleaningDevice, Order command) {
        int steps = command.getNumberOfSteps();
        if (command.getOrderType().equals(OrderType.NORTH)){
            cleaningDevices.get(cleaningDevice).moveNorth(steps);
        }
        else if (command.getOrderType().equals(OrderType.SOUTH)){
            cleaningDevices.get(cleaningDevice).moveSouth(steps);
        }
        else if (command.getOrderType().equals(OrderType.WEST)){
            cleaningDevices.get(cleaningDevice).moveWest(steps);
        }
        else if (command.getOrderType().equals(OrderType.EAST)){
            cleaningDevices.get(cleaningDevice).moveEast(steps);
        }

        return false;
    }

    public boolean transport(UUID cleaningDevice, Order command) {
        UUID destinationSpaceId = command.getGridId();
        TidyUpRobot device = fetchTidyUpRobotById(cleaningDevice);
        Connection Connection = fetchRoomWithId(device.getCurrentRoomUUID()).fetchConnectionByDestinationRoomId(destinationSpaceId);
        Room desSpace = fetchRoomWithId(destinationSpaceId);

        //check if Tidyuprobot's coordinate same as the connection's source coordinate
        if (Connection.getSourceCoordinate().equals(device.getCoordinate())) {
            if (!desSpace.tidyUpRobots.isEmpty()){
                for (int i=0; i<desSpace.tidyUpRobots.size();i++){
                    if (desSpace.tidyUpRobots.get(i).getCoordinate().equals(Connection.getDestinationCoordinate())){
                        throw new  RuntimeException();
                    }
                }
            } else {
                //remove the robot from Source Room and add it in Destination Room
                fetchRoomWithId(device.getCurrentRoomUUID()).tidyUpRobots.remove(cleaningDevices.get(cleaningDevice));
                device.setXY(Connection.getDestinationCoordinate().getX(), Connection.getDestinationCoordinate().getY());
                device.setCurrentRoomUUID(destinationSpaceId);
                device.setCurrentRoom(fetchRoomWithId(destinationSpaceId));
                return true;
            }
        }
        return false;
    }

    private void setCleaningDevice(UUID cleaningDevice, UUID space){
        fetchTidyUpRobotById(cleaningDevice).setCurrentRoomUUID(space);
        fetchTidyUpRobotById(cleaningDevice).setXY(0,0);
        fetchTidyUpRobotById(cleaningDevice).setCurrentRoom(fetchRoomWithId(space));
        fetchRoomWithId(space).tidyUpRobots.add(fetchTidyUpRobotById(cleaningDevice));
    }

    public boolean initialize(UUID cleaningDevice, Order command) {
        UUID space = command.getGridId();

        if (fetchRoomWithId(space).tidyUpRobots.isEmpty()) {
            setCleaningDevice(cleaningDevice, space);
            return true;
        }else{
            for (int i = 0; i< fetchRoomWithId(space).tidyUpRobots.size(); i++) {
                if (fetchRoomWithId(space).tidyUpRobots.get(i).getCoordinate().equals(new Coordinate(0,0))) return false;
                else {
                    setCleaningDevice(cleaningDevice, space);
                    return true;
                }
            }
        }
        return false;
    }

}

