package thkoeln.st.st2praktikum.exercise.room;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.robot.Transportable;
import thkoeln.st.st2praktikum.exercise.Wall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


@Getter
public abstract class AbstractRoom implements Roomable
{
    protected UUID roomID;

    protected HashMap<UUID,Connectable> roomConnections;

    protected ArrayList<Wall> walls;

    protected HashMap<UUID, Transportable> cleaningRobots;

    protected AbstractRoom()
    {
        this.roomID = UUID.randomUUID();
        this.walls = new ArrayList<>();
        this.roomConnections = new HashMap<>();
        this.cleaningRobots = new HashMap<>();
    }


}
