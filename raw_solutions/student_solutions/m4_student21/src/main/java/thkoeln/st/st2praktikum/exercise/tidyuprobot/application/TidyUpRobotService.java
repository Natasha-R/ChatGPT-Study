package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;


import java.util.*;


@Service
public class TidyUpRobotService {

    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;

    @Autowired
    private RoomService roomService;

    public void deleteOrderHistory(UUID tidyUpRobotId){
        tidyUpRobotRepository.findById(tidyUpRobotId).get().deleteOrderHistory();
    }

    /**
     * This method returns the OrderHistory for a given Robot
     * @param tidyUpRobotId id of the tidy-up robot
     * @return the OrderHistory for a given Robotid
     */
    public List<Order> getOrderHistory(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getOrderHistory();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        List<Order> orderList = new ArrayList<>();
        return tidyUpRobotRepository.save(tidyUpRobot).getId();
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Order order) {
        tidyUpRobotRepository.findById(tidyUpRobotId).get().addOrdertoHistory(order);

        if(order.getOrderType() == OrderType.ENTER || order.getOrderType() == OrderType.TRANSPORT){
            Room room = roomService.roomRepository.findById(order.getGridId()).get();
            return tidyUpRobotRepository.findById(tidyUpRobotId).get().executeRoomCommand(room, order);
        }
        tidyUpRobotRepository.findById(tidyUpRobotId).get().move(order);
        return true;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getRoomId();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getCoordinate();
    }
}
