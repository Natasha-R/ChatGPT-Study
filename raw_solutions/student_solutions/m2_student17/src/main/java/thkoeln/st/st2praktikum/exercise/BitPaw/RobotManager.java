package thkoeln.st.st2praktikum.exercise.BitPaw;

import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RobotManager
{
    private final Map<UUID, Robot> robotList = new HashMap<>();

    public Vector2D getRobotPosition(final UUID uuid) throws RobotNotFound
    {
        final Robot robot = getRobot(uuid);

        return robot.getCurrentPosition();
    }

    public UUID addRobot(final String name)
    {
        final Robot robot = new Robot(name);

        robotList.put(robot.getUUID(), robot);

        return robot.getUUID();
    }

    public Robot getRobot(final UUID robotID) throws RobotNotFound
    {
        final Robot robot = robotList.get(robotID);

        if(robot == null)
        {
            throw new RobotNotFound(robotID);
        }

        return robot;
    }
}
