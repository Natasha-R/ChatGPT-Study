package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TidyUpRobotService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    TransportTechnologyRepository transportTechnologyRepository;
    @Autowired
    TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    ConnectionRepository connectionRepository;

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(width, height);
        roomRepository.save(room);
        return room.getId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        for(Room room : roomRepository.findAll()) {
            if(room.getId().equals(roomId)) {
                room.addBarrier(barrier);
                break;
            }
        }
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceRoomId, Point sourcePoint, UUID destinationRoomId, Point destinationPoint) {
        Connection connection = new Connection(sourceRoomId, sourcePoint, destinationRoomId, destinationPoint);

        connectionRepository.save(connection);

        return connection.getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot robot = new TidyUpRobot(name);

        tidyUpRobotRepository.save(robot);

        return robot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Order order) {
        Optional<TidyUpRobot> robotToExecuteOn = tidyUpRobotRepository.findById(tidyUpRobotId);

        if(robotToExecuteOn.isEmpty()) {
            return false;
        }

        try {
            switch (order.getOrderType()) {
                case TRANSPORT:
                    executeTransportOrder(robotToExecuteOn.get(), order);
                    break;
                case ENTER:
                    executeEnterOrder(robotToExecuteOn.get(), order);
                    break;
                default:
                    executeMoveCommand(robotToExecuteOn.get(), order);
                    break;
            }

            return true;
        } catch (Exception e) {
            System.out.println("Error in command execution " + e.getMessage());
            return false;
        }
    }

    private void executeTransportOrder(TidyUpRobot robot, Order order) {
        List<Connection> usableConnections = connectionRepository.findBySourceRoomIdAndSourceCoordinate(robot.getRoomId(), robot.getCurrentPosition());

        System.out.println("Going to transport from " + robot.getRoomId());
        if(usableConnections.isEmpty()) {
            throw new RuntimeException("No connection found");
        } else if(!usableConnections.get(0).getDestinationRoomId().equals(order.getGridId())) {
            throw new RuntimeException("Cant reach that room via this connection");
        } else if(isDestinationOccupied(usableConnections.get(0).getDestinationRoomId(), usableConnections.get(0).getDestinationCoordinate())) {
            throw new RuntimeException("Destination already occupied");
        } else {
            System.out.println("Found connection to use: " + usableConnections.get(0).getDestinationRoomId());
            robot.setRoomId(usableConnections.get(0).getDestinationRoomId());
            robot.setCurrentPosition(usableConnections.get(0).getDestinationCoordinate());
        }
    }

    private void executeEnterOrder(TidyUpRobot robot, Order order) {
        if (robot.getRoomId() != null){
            throw new RuntimeException("Robot already has a room");
        } else if(isDestinationOccupied(order.getGridId(), new Point(0,0))) {
            throw new RuntimeException("Destination coordinates already blocked");
        } else {
            robot.setRoomId(order.getGridId());
            robot.setCurrentPosition(new Point(0, 0));
        }
    }

    private void executeMoveCommand(TidyUpRobot robot, Order order) {
        Point newPosition = new Point(robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY());

        if(robot.getCurrentPosition() == null) {
            throw new RuntimeException("Robot must be initialized before you can move it!");
        }

        switch (order.getOrderType()) {
            case NORTH:
                newPosition.increaseY(order.getNumberOfSteps());
                break;
            case EAST:
                newPosition.increaseX(order.getNumberOfSteps());
                break;
            case SOUTH:
                newPosition.decreaseY(order.getNumberOfSteps());
                break;
            case WEST:
                newPosition.decreaseX(order.getNumberOfSteps());
                break;
            default:
                throw new RuntimeException("Unknown command type");
        }

        Optional<Room> roomWhereRobotIsPlacedOn = roomRepository.findById(robot.getRoomId());

        if(roomWhereRobotIsPlacedOn.isEmpty()) {
            throw new RuntimeException("Robot must have a room before you can move it!");
        }

        Point wallCoordinateBetween = roomWhereRobotIsPlacedOn.get().coordinateOfBarrierBetween(robot.getCurrentPosition(), newPosition);

        if(wallCoordinateBetween != null) {
            newPosition = wallCoordinateBetween;
            if (order.getOrderType() == OrderType.EAST) {
                newPosition.decreaseX(1);
            } else if (order.getOrderType() == OrderType.NORTH) {
                newPosition.decreaseY(1);
            }
        }

        robot.setCurrentPosition(newPosition);

        System.out.println("Set robot position to " + robot.getCurrentPosition().toString());
    }

    private Boolean isDestinationOccupied(UUID destinationRoomId, Point destinationPoint) {
        List<TidyUpRobot> robotsOnDestination = tidyUpRobotRepository.findByRoomIdAndCurrentPosition(destinationRoomId, destinationPoint);

        return !robotsOnDestination.isEmpty();
    }
}
