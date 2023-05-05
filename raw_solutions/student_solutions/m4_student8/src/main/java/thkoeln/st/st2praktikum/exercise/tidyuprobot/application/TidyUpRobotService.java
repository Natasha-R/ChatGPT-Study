package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.application.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.Roomable;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.Transportable;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;


import java.util.UUID;


@Service
public class TidyUpRobotService
{


    private TidyUpRobotRepository tidyUpRobotRepository;

    private RoomRepository roomRepository;

    private ConnectionRepository connectionRepository;

    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository,
                              RoomRepository roomRepository,ConnectionRepository connectionRepository)
    {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
        this.connectionRepository = connectionRepository;
    }


    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name)
    {
        TidyUpRobot robot = new TidyUpRobot(name);
        this.tidyUpRobotRepository.save(robot);
        return robot.identify();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task)
    {
        Transportable robot = tidyUpRobotRepository.findById(tidyUpRobotId).get();
        UUID roomID = task.getGridId();
        Room sourceRoom = (Room) robot.showRoom();

        Connection connection = null;

        tidyUpRobotRepository.findById(tidyUpRobotId).get().setTask(task);

        if (sourceRoom != null)
            if(roomID!=null)
            {
                connection = connectionRepository.findById(sourceRoom.hasConnectionToRoom(roomID)).get();
                connection = roomRepository.findById(sourceRoom.identify()).get().getConnectionByID(connection.identify());
            }


        Roomable room = null;
        if (roomID != null)
        {
            this.roomRepository.findById(roomID).get().addRobotToRoom(robot);
            room = roomRepository.findById(roomID).get();
        }

        return this.tidyUpRobotRepository.findById(tidyUpRobotId).get().moveInDirection(task.getTaskType(),task.getNumberOfSteps(),connection,room);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId)
    {
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getRoomId();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId)
    {
       return tidyUpRobotRepository.findById(tidyUpRobotId).get().getCoordinate();
    }
}
