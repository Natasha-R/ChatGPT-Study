package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;


import java.util.UUID;


@Service
public class TidyUpRobotService {
    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    private RoomService roomService;
    @Autowired
    private TransportTechnologyService transportTechnologyService;
    
    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot robot = new TidyUpRobot(name);
        this.tidyUpRobotRepository.save(robot);
        return robot.getTidyUpRobotID();
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

        TidyUpRobot robot = this.tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(()->new IllegalArgumentException("This TidyUpRobot does not Exist."));
        Room room = null;
        if(robot.getRoomId() != null) room = roomService.getRoom(robot.getRoomId());
        var success = false;
        switch (task.getTaskType()){
            case NORTH:
                success = robot.moveNorth(room, task.getNumberOfSteps());
                break;
            case SOUTH:
                success = robot.moveSouth(room,task.getNumberOfSteps());
                break;
            case EAST:
                success = robot.moveEast(room,task.getNumberOfSteps());
                break;
            case WEST:
                success = robot.moveWest(room,task.getNumberOfSteps());
                break;
            case TRANSPORT:
                success = robot.traverseRobot( transportTechnologyService.findConnection(task.getGridId(),robot.getRoomId(),robot.getCoordinates()));
                break;
            case ENTER:
                if (roomService.getRoom(task.getGridId()) != null){
                    success = robot.placeRobot(this.tidyUpRobotRepository,task.getGridId());
                }else throw new IllegalArgumentException("This Room does not Exist.") ;

                break;
            default:
                throw new UnsupportedOperationException("Fail");
        }
        robot.addTaskToHistory(task);
        return success;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return this.tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(()->new IllegalArgumentException("This TidyUpRobot does not Exist.")).getRoomId();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId){
        return this.tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(()->new IllegalArgumentException("This TidyUpRobot does not Exist.")).getVector2D();
    }
}
