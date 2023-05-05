package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TidyUpRobotService {
    private TidyUpRobotRepository tidyUpRobotRepository;
    private TransportTechnologyService transportTechnologyService;
    private RoomService roomService;

    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository, TransportTechnologyService transportTechnologyService, RoomService roomService) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.transportTechnologyService = transportTechnologyService;
        this.roomService = roomService;
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newTidyUpRobot = new TidyUpRobot(name);
        tidyUpRobotRepository.save(newTidyUpRobot);
        return newTidyUpRobot.getId();
    }

    public UUID addTidyUpRobotObject(TidyUpRobot newTidyUpRobot) {
        //TidyUpRobot newTidyUpRobot = new TidyUpRobot(name);
        tidyUpRobotRepository.save(newTidyUpRobot);
        return newTidyUpRobot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).get();

        switch (task.getTaskType()) {
            case ENTER:
                List<TidyUpRobot> robotsInTargetRoom = tidyUpRobotRepository.findByRoomIdAndRoomIdIsNotNull(task.getGridId());
                System.out.println(tidyUpRobot.getId());
                return tidyUpRobot.placeInInitialRoom(task, robotsInTargetRoom);
            case TRANSPORT:
                TransportTechnology transportTechnology =
                        transportTechnologyService
                                .getTransportTechnologyByConnectionsSourceRoomIdAndConnectionsSourcePoint(tidyUpRobot.getRoomId(), tidyUpRobot.getPoint());
                List<TidyUpRobot> robotsInDestinationRoom = tidyUpRobotRepository.findByRoomIdAndRoomIdIsNotNull(task.getGridId());
                return tidyUpRobot.transport(task, transportTechnology, robotsInDestinationRoom);
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                Room currentRoom = roomService.getRoomByID(tidyUpRobot.getRoomId());
                List<TidyUpRobot> robotsInCurrentRoom = tidyUpRobotRepository.findByRoomIdAndRoomIdIsNotNull(currentRoom.getId());
                tidyUpRobot.walk(task, currentRoom, robotsInCurrentRoom);
                return true;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotRepository
                .findById(tidyUpRobotId)
                .get()
                .getRoomId();
    }

    /**
     * This method returns the point a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the point of the tidy-up robot
     */
    public Point getTidyUpRobotPoint(UUID tidyUpRobotId){
        return tidyUpRobotRepository
                .findById(tidyUpRobotId)
                .get()
                .getPoint();
    }

    public Optional<TidyUpRobot> getTidyUpRobotById(UUID tidyUpRobotId){
        return tidyUpRobotRepository
                .findById(tidyUpRobotId);
    }

    public Iterable<TidyUpRobot> getAllTidyUpRobots() {
        return tidyUpRobotRepository.findAll();
    }

    public void deleteTidyUpRobot(TidyUpRobot tidyUpRobot) {
        tidyUpRobotRepository.delete(tidyUpRobot);
    }
}
