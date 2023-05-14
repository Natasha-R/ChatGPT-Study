package thkoeln.st.st2praktikum.exercise.robot;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.position.Position;
import thkoeln.st.st2praktikum.exercise.room.Room;

import java.util.UUID;

@Getter
public abstract class AbstractRobot
{
    protected UUID robotID;

    protected Position position;

    protected Room jobRoom;


    protected AbstractRobot()
    {
        this.robotID = UUID.randomUUID();
    }
}
