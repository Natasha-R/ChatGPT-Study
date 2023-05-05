package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskException;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;


import java.util.Optional;
import java.util.UUID;


@Service
public class TidyUpRobotService {

    @Getter
    private TidyUpRobotRepository tidyUpRobotRepository;
    @Getter
    private RoomService roomService;

    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository, RoomService roomService){
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomService = roomService;
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newRobot = new TidyUpRobot(name);
        return tidyUpRobotRepository.save(newRobot).getUuid();
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
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot tidyUpRobot = findByUuid(tidyUpRobotId);
        switch (task.getTaskType()) {
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                tidyUpRobot.go(task);
                return true;
            case ENTER:
                Room initialRoom = roomService.getRoomRepository().findByUuid(task.getGridId());
                if (initialRoom == null)
                    throw new TaskException("Dont know a room with UUID: " + task.getGridId());
                return tidyUpRobot.init(initialRoom, task);
            case TRANSPORT:
                Room destinationRoom = roomService.getRoomRepository().findByUuid(task.getGridId());
                if (destinationRoom == null)
                    throw new TaskException("Dont know a room with UUID: " + task.getGridId());
                return tidyUpRobot.transport(destinationRoom, task);
            default:
                throw new TaskException("Wrong command.");
        }
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return findByUuid(tidyUpRobotId).getRoomId();
    }

    /**
     * This method returns the point a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the point of the tidy-up robot
     */
    public Point getTidyUpRobotPoint(UUID tidyUpRobotId){
        return findByUuid(tidyUpRobotId).getPoint();
    }

    public TidyUpRobot findByUuid(UUID uuid){
        return tidyUpRobotRepository.findByUuid(uuid).orElseThrow(() ->
                new TidyUpRobotNotFoundException("There is no robot with ID: " + uuid));
    }

    public Iterable<TidyUpRobot> findAll() {
        return tidyUpRobotRepository.findAll();
    }

    public void deleteByUuid(UUID uuid) {
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findByUuid(uuid).orElseThrow(() ->
                new TidyUpRobotNotFoundException("There is no robot with ID: " + uuid));
        tidyUpRobotRepository.delete(tidyUpRobot);
    }

    public void save(TidyUpRobot tidyUpRobot) {
        tidyUpRobotRepository.save(tidyUpRobot);
    }
}
