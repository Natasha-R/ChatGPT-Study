package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainapplication.DomainService;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.repository.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.movement.TidyUpRobotTaskService;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.repository.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;


import java.util.Optional;
import java.util.UUID;


@Service
public class TidyUpRobotService {

    private final TidyUpRobotRepository tidyUpRobotRepository;
    private final RoomRepository roomRepository;
    private final TransportCategoryService transportCategoryService;

    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository,
                              RoomRepository roomRepository,
                              TransportCategoryService transportCategoryService) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
        this.transportCategoryService = transportCategoryService;
    }
    
    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        return tidyUpRobotRepository.save(tidyUpRobot).getId();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).get();
        TidyUpRobotTaskService tidyUpRobotTaskService = new TidyUpRobotTaskService(tidyUpRobotRepository, roomRepository, transportCategoryService);
        Boolean taskResponse = tidyUpRobotTaskService.processTask(tidyUpRobot, task);
        tidyUpRobotRepository.save(tidyUpRobot);
        return taskResponse;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId) {
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getRoomId();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId) {
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getVector2D();
    }

    public Iterable<TidyUpRobot> getAllTidyUpRobots() {
        return tidyUpRobotRepository.findAll();
    }

    public Optional<TidyUpRobot> getTidyUpRobotById(UUID id) {
        return tidyUpRobotRepository.findById(id);
    }

    public Boolean deleteTidyUpRobotById(UUID id) {
        if (getTidyUpRobotById(id).isPresent()) {
            tidyUpRobotRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TidyUpRobot updateTidyUpRobot(TidyUpRobot updatedTidyUpRobot) {
        return tidyUpRobotRepository.save(updatedTidyUpRobot);
    }
}
