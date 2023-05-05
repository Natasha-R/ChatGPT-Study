package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRoboterList;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.UUID;


@Service
public class TidyUpRobotService {


    TidyUpRoboterList tidyUpRobotList = new TidyUpRoboterList();
    CoordinatesForRobots coordinatesForRobots = new CoordinatesForRobots();

    @Autowired
    TidyUpRobotRepository TidyUpRobotRepository;

    @Autowired
    RoomRepository RoomRepository;

    @Autowired
    TransportCategoryRepository TransportCategoryRepository;

    public TidyUpRobot findById( UUID id ) {
        return TidyUpRobotRepository.findById( id ).orElseThrow( () ->
                new InvalidStringError( "No dungeon with ID " + id + " can be found.") );
    }

    /**
     * This method adds a new tidy-up robot
     *
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        UUID tidyRoboterUUID = UUID.randomUUID();
        TidyUpRobot newTidyRobot = new TidyUpRobot(name, tidyRoboterUUID);
        TidyUpRobotRepository.save(newTidyRobot);
        tidyUpRobotList.add(newTidyRobot);


        return tidyRoboterUUID;
    }



    /**
     * This method lets the tidy-up robot execute a task.
     *
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task          the given task
     *                      NORTH, WEST, SOUTH, EAST for movement
     *                      TRANSPORT for transport - only works on squares with a connection to another room
     *                      ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     * @return true if the command was executed successfully. Else false.
     * (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     * another tidy-up robot, and can move only a part of the steps, or even none at all.
     * tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        boolean canDoAction = true;
        for (TidyUpRobot robot : tidyUpRobotList.getTidyUpRobotsList()) {
            if (robot.getId().equals(tidyUpRobotId)) {
                UUID roomWhereRobotIs = robot.getRoomId();
                TaskType moveCommand = task.getTaskType();
                Integer steps = task.getNumberOfSteps();
                UUID UUID = task.getGridId();
                robot.getTaskList().add(task);
                robot.setCoordinate(robot.executeCommand(moveCommand, steps, UUID, tidyUpRobotList, TidyUpRobotRepository, roomWhereRobotIs, robot.getId(), coordinatesForRobots,RoomRepository,TransportCategoryRepository));
                TidyUpRobotRepository.save(robot);
                canDoAction = robot.isCanDoAction();
            }
        }

        return canDoAction;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     *
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId) {
        return TidyUpRobotRepository.findById(tidyUpRobotId).get().getRoomId();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     *
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getCoordinate(UUID tidyUpRobotId) {
        Coordinate coordinates = null;
        for (TidyUpRobot roboter : tidyUpRobotList.getTidyUpRobotsList()) {
            if (roboter.getId() == tidyUpRobotId) {
                coordinates = roboter.getCoordinate();
            }
        }
        return coordinates;
    }
}
