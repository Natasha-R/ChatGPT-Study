package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.RobotNotFound;
import thkoeln.st.st2praktikum.exercise.exception.RoomNotFound;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.utility.World;


import java.util.UUID;


@Service
public class TidyUpRobotService
{
    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;

    private TidyUpRobotController robotManager;
    private final World world = World.getInstance();

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name)
    {
        final TidyUpRobot robot = getRobotManager().saveRobot(name);

        return robot.getUUID();
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Order order)
    {
        boolean result = false;

        try
        {
            final TidyUpRobot robot = getRobotManager().getRobot(tidyUpRobotId);

            result = robot.move(order);

            tidyUpRobotRepository.save(robot);

            getRobotManager().orderHistoryAdd(robot.getUUID(),order );
        }
        catch (RoomNotFound exception)
        {
            exception.printStackTrace();
        }

        return result;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId)
    {
        try
        {
            return getWorld().getRoomUUIDFromRoboter(tidyUpRobotId);
        }
        catch (RoomNotFound roomNotFound)
        {
            roomNotFound.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId)
    {
        try
        {
            return getRobotManager().getRobotPosition(tidyUpRobotId);
        }
        catch (RobotNotFound robotNotFound)
        {
            robotNotFound.printStackTrace();
            return null;
        }
    }

    private World getWorld()
    {
        return world;
    }

    private TidyUpRobotController getRobotManager()
    {
        if(robotManager == null)
        {
            robotManager = new TidyUpRobotController(tidyUpRobotRepository);
        }

        return robotManager;
    }
}
