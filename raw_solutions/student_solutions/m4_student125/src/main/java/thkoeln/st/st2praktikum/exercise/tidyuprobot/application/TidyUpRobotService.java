package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Connection;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


import java.util.*;


@Service
public class TidyUpRobotService
{
    @Autowired
    public TidyUpRobotRepository tidyUpRobotRepository;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public TransportTechnologyRepository transportTechnologyRepository;

    public HashMap<UUID, TidyUpRobot> robots = new HashMap<>();
    public HashMap<UUID, Room> rooms = new HashMap<>();
    public List<Connection> connections = new LinkedList<>();

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name)
    {
        TidyUpRobot robot = new TidyUpRobot(name);
        robots.put(robot.getRobotId(), robot);
        tidyUpRobotRepository.save(robot);
        return robot.getRobotId();
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Order order)
    {
        Iterable<Room> roomIterator = roomRepository.findAll();
        Iterable<TransportTechnology> transportTechnologyIterator = transportTechnologyRepository.findAll();

        for (Room room : roomIterator)
        {
            rooms.put(room.getRoomId(), room);
        }
        for (TransportTechnology transportTechnology : transportTechnologyIterator)
        {
            connections.addAll(transportTechnology.getConnections());
        }

        boolean executionSuccessful = robots.get(tidyUpRobotId).executeCommand(order, rooms, connections);
        tidyUpRobotRepository.deleteById(tidyUpRobotId);
        tidyUpRobotRepository.save(robots.get(tidyUpRobotId));
        return executionSuccessful;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId)
    {
        return robots.get(tidyUpRobotId).getCurrentRoom();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId)
    {
        return robots.get(tidyUpRobotId).getCoordinate();
    }

    public TidyUpRobot getTidyUpRobotById(UUID robotId)
    {
        Optional<TidyUpRobot> tidyUpRobotOptional = tidyUpRobotRepository.findById(robotId);

        if (tidyUpRobotOptional.isPresent())
        {
            return tidyUpRobotOptional.get();
        }
        else
        {
            throw new IllegalArgumentException("TidyUpRobot not found!");
        }
    }

    public Iterable<TidyUpRobot> getTidyUpRobots()
    {
        return tidyUpRobotRepository.findAll();
    }

    public TidyUpRobot createTidyUpRobot(TidyUpRobot tidyUpRobot)
    {
        tidyUpRobot.setRobotId(UUID.randomUUID());
        tidyUpRobotRepository.save(tidyUpRobot);
        return tidyUpRobot;
    }

    public boolean deleteById(UUID robotId)
    {
        if (tidyUpRobotRepository.findById(robotId).isPresent())
        {
            tidyUpRobotRepository.deleteById(robotId);
            return true;
        }
        return false;
    }

    public TidyUpRobot changeName(UUID robotId, String newName)
    {
        Optional<TidyUpRobot> tidyUpRobot = tidyUpRobotRepository.findById(robotId);
        if (tidyUpRobot.isEmpty())
        {
            throw new IllegalArgumentException("Name not valid!");
        }
        tidyUpRobot.get().setName(newName);
        tidyUpRobotRepository.save(tidyUpRobot.get());
        return tidyUpRobot.get();
    }

    public void giveOrder(UUID robotId, Order order)
    {
        Optional<TidyUpRobot> tidyUpRobot = tidyUpRobotRepository.findById(robotId);

        Iterable<Room> roomIterator = roomRepository.findAll();

        for (Room room : roomIterator)
        {
            rooms.put(room.getRoomId(), room);
        }

        if (tidyUpRobot.isPresent())
        {
            tidyUpRobot.get().orderList.add(order);

            if (order.getOrderType() == OrderType.ENTER)
            {
                tidyUpRobot.get().spawnAtPosition(new Coordinate(0, 0), order.getGridId(), rooms);
            }
            else
            {
                tidyUpRobot.get().move(order, rooms);
            }
        }
        else
        {
            throw new IllegalArgumentException("Invalid order!");
        }
    }

    public List<Order> getOrderList(UUID robotId)
    {
        Optional<TidyUpRobot> tidyUpRobot = tidyUpRobotRepository.findById(robotId);

        if (!tidyUpRobot.isEmpty())
        {
            return tidyUpRobot.get().orderList;
        }
        else
        {
            return new LinkedList<>();
        }
    }

    public void deleteOrderList(UUID robotId)
    {
        Optional<TidyUpRobot> tidyUpRobot = tidyUpRobotRepository.findById(robotId);
        tidyUpRobot.ifPresent(robot -> robot.orderList.clear());
    }
}
