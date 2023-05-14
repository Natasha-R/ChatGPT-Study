package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.repository.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.repository.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.repository.ConnectionRepository;


import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Service
public class TidyUpRobotService {
    private final HashMap<UUID, Room> rooms;

    private final RoomRepository roomRepository;
    private final TidyUpRobotRepository tidyUpRobotRepository;

    private TidyUpRobotDtoMapper tidyUpRobotDtoMapper = new TidyUpRobotDtoMapper();

    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository){
        rooms = new HashMap<>();

        this.roomRepository = roomRepository;
        this.tidyUpRobotRepository = tidyUpRobotRepository;
    }
    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        roomRepository.findAll().iterator().forEachRemaining(room -> rooms.put(room.getId(), room));
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name, rooms);
        UUID id = tidyUpRobot.getId();

        tidyUpRobotRepository.save(tidyUpRobot);

        return id;
    }

    /**
     * This method adds a new tidy-up robot
     * @param tidyUpRobot as an object TidyUpRobot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(TidyUpRobot tidyUpRobot) {
        roomRepository.findAll().iterator().forEachRemaining(room -> rooms.put(room.getId(), room));
        tidyUpRobot.setId(UUID.randomUUID());
        tidyUpRobotRepository.save(tidyUpRobot);

        return tidyUpRobot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Order order) {
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(EntityNotFoundException::new);

        Boolean status = tidyUpRobot.execute(order);
        tidyUpRobotRepository.save(tidyUpRobot);

        return status;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(EntityNotFoundException::new);
        return tidyUpRobot.getRoomId();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(EntityNotFoundException::new);
        return tidyUpRobot.getCoordinate();
    }

    /**
     * This method clears the order history of a tidy-up robot
     * @param tidyUpRobotId the ID of the tidy-up robot
     */
    public void clearTidyUpRobotOrderHistory(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(EntityNotFoundException::new);
        tidyUpRobot.clearOrderHistory();
    }

    /**
     * This method returns all orders of a tidy-up robot
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return all orders of a tidy-up robot
     */
    public List<Order> getOrdersById(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(EntityNotFoundException::new);
        return tidyUpRobot.getOrders();
    }

    /**
     * This method returns all orders of a tidy-up robot
     * @param tidyUpRobotId the ID of the tidy-up robot#
     * @param tidyUpRobotName the name of the tidy-up robot
     * @return the changed tidyUpRobot
     */
    public TidyUpRobot changeTidyUpRobotName(UUID tidyUpRobotId, String tidyUpRobotName) {
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(EntityNotFoundException::new);

        tidyUpRobot.setName(tidyUpRobotName);

        return tidyUpRobot;
    }

    /**
     * This method returns the dto version from tidyUpRobot
     * @param tidyUpRobot
     * @return tidyUpRobotDto
     */
    public TidyUpRobotDto covertToTidyUpRobotDto(TidyUpRobot tidyUpRobot) {
        return tidyUpRobotDtoMapper.mapToDto(tidyUpRobot);
    }
}
