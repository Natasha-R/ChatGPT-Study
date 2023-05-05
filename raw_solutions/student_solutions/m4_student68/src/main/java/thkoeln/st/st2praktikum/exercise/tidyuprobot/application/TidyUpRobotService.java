package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.application.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.dto.TidyUpRobotDto;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.Instructable;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.execution.RobotCommand;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.TransportSystemRepository;


import java.util.Optional;
import java.util.UUID;


@Service
public class TidyUpRobotService {

    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TransportSystemRepository transportSystemRepository;

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot robot = new TidyUpRobot(name);  //TODO - Repository mit Instructable
        this.tidyUpRobotRepository.save(robot);
        return robot.getId();

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
        Instructable robot = this.tidyUpRobotRepository.findById(tidyUpRobotId).get();
        //if (robot == null)
        //    return false;
        robot.addTask(task);
        RobotCommand robotCommand = new RobotCommand(task,
                                                    this.roomRepository,
                                                    this.transportSystemRepository,
                                                    robot.getRoom(),
                                                    robot.getCoordinate());


//       if (robot.getRoom() != null) {
//           Room room = roomRepository.findRoomByOccupiers(this.tidyUpRobotRepository.findById(tidyUpRobotId).get());
//           System.out.println("Width: "+room.getWidth()+"  Heigh: "+room.getHeight());
//        }


        Boolean result = robot.readCommand(robotCommand);
        this.tidyUpRobotRepository.save((TidyUpRobot) robot);
        return result;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return this.tidyUpRobotRepository.findById(tidyUpRobotId).get().getRoomId();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId){
        return this.tidyUpRobotRepository.findById(tidyUpRobotId).get().getCoordinate();
    }

    public Iterable<TidyUpRobot> getAllTidyUpRobots() {
        return  this.tidyUpRobotRepository.findAll();
    }

    public Optional<TidyUpRobot> getTidyUpRobotById(UUID id) {
        return  this.tidyUpRobotRepository.findById(id);
    }

    public Boolean deleteTidyUpRobotById(UUID id) {
        if (this.tidyUpRobotRepository.findById(id).isPresent()) {
            this.tidyUpRobotRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteTidyUpRobot(TidyUpRobot robot) {
        this.tidyUpRobotRepository.delete(robot);
    }


    public TidyUpRobot updateTidyUpRobot(TidyUpRobot robot) {
        return this.tidyUpRobotRepository.save(robot);
    }

}
