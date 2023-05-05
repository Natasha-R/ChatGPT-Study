package thkoeln.st.st2praktikum.exercise.exception;

import java.util.UUID;

public class RobotNotFound extends Exception
{
    public RobotNotFound(UUID uuid)
    {
        super("Robot not found width UUID:" + uuid);
    }
}
