package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.RobotNotFoundException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;


import java.util.List;
import java.util.UUID;


@Service
public class TidyUpRobotService {
    private TidyUpRobotRepository tidyUpRobotRepository;
    private RoomRepository roomRepository;
    private TidyUpRobotDtoMapper tidyUpRobotDtoMapper = new TidyUpRobotDtoMapper();

    @Autowired
    public TidyUpRobotService (TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
    }
    
    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        tidyUpRobotRepository.save(tidyUpRobot);
        return tidyUpRobot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().commandDecider(task, roomRepository);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getCurrentRoom();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getVector2D();
    }

    public Iterable<TidyUpRobot> findAll() {
        return tidyUpRobotRepository.findAll();
    }

    public TidyUpRobot findByID(UUID id) {
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(id).get();
        return tidyUpRobot;
    }

    public TidyUpRobot createRobotFromDto ( TidyUpRobotDto tidyUpRobotDto) {
        TidyUpRobot tidyUpRobot = tidyUpRobotDtoMapper.mapToEntity( tidyUpRobotDto);
        tidyUpRobotRepository.save(tidyUpRobot);
        return tidyUpRobot;
    }

    public void deleteById( UUID id) {
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(id).orElseThrow( () ->
                new RobotNotFoundException( "No Robot with ID " + id + " can be found.") );
        //roomRepository.findById(tidyUpRobot.getRoomId()).get().getCoordinate(tidyUpRobot.getVector2D().getX(), tidyUpRobot.getVector2D().getY()).setHasRobot();
        tidyUpRobotRepository.delete(tidyUpRobot);
    }

    public TidyUpRobot changeName( UUID id, TidyUpRobotDto tidyUpRobotDto) {
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(id).orElseThrow( () ->
            new RobotNotFoundException("No Robot with ID " + id + " can be found.") );
        tidyUpRobotDtoMapper.mapToExistingEntity(tidyUpRobotDto, tidyUpRobot, true);
        tidyUpRobotRepository.save( tidyUpRobot );
        return tidyUpRobot;
    }

    public List<Task> findAllTasks(UUID id) {
        return tidyUpRobotRepository.findById(id).get().getTasks();
    }

    public void deleteTaskHistory (UUID id) {
        tidyUpRobotRepository.findById(id).get().deleteTaskHistory();
    }
}
