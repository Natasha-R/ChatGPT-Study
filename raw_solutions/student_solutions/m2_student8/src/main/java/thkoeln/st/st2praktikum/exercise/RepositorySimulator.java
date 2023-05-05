package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.robot.Transportable;
import thkoeln.st.st2praktikum.exercise.room.Roomable;

import java.util.HashMap;
import java.util.UUID;

public class RepositorySimulator
{
    private HashMap<UUID, Connectable> connections;

    private HashMap<UUID, Roomable> rooms;

    private HashMap<UUID, Transportable> robots;


    public RepositorySimulator()
    {
        this.connections = new HashMap<>();
        this.robots = new HashMap<>();
        this.rooms = new HashMap<>();
    }

    public Connectable getConnectionByID(UUID connectionID)
    {
        return connections.get(connectionID);
    }

    public void addConnection(Connectable connection)
    {
        this.connections.put(connection.identify(),connection);
    }

    public Roomable getRoomByID(UUID roomID)
    {
        return rooms.get(roomID);
    }

    public void addRoom(Roomable room)
    {
        this.rooms.put(room.identify(),room);
    }

    public void addRobot(Transportable robot)
    {
        this.robots.put(robot.identify(),robot);
    }

    public Transportable getRobotByID(UUID robotID)
    {
        return robots.get(robotID);
    }
}
