package thkoeln.st.st2praktikum.exercise.utility;

import thkoeln.st.st2praktikum.exercise.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.RobotNotFound;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RobotManager
{
    private final Map<UUID, TidyUpRobot> robotList = new HashMap<>();

    public Vector2D getRobotPosition(final UUID uuid) throws RobotNotFound
    {
        final TidyUpRobot robot = getRobot(uuid);

        return robot.getCurrentPosition();
    }

    public UUID addRobot(final String name)
    {
        final TidyUpRobot robot = new TidyUpRobot(name);

        robotList.put(robot.getUUID(), robot);

        return robot.getUUID();
    }

    public TidyUpRobot getRobot(final UUID robotID) throws RobotNotFound
    {
        final TidyUpRobot robot = robotList.get(robotID);

        if(robot == null)
        {
            throw new RobotNotFound(robotID);
        }

        return robot;
    }
}
