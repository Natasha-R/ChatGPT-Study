package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Connection;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.RobotRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotDto;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Service
public class TidyUpRobotService {

    @Autowired
    private RobotRepository robotRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TransportCategoryRepository transportCategoryRepository;

    private HashMap<UUID, Room> rooms = new HashMap<>();
    private HashMap<UUID, TidyUpRobot> robots;
    private List<Connection> connections;


    public TidyUpRobotService()
    {
        connections = new ArrayList<>();
        robots = new HashMap<>();
    }


    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public java.util.UUID addTidyUpRobot(String name)
    {
        if(name == null)
        {
            throw new IllegalArgumentException();
        }
        UUID uuid = java.util.UUID.randomUUID();
        TidyUpRobot tidyUpRobot = new TidyUpRobot(uuid, name);
        robots.put(uuid, tidyUpRobot);
        robotRepository.save(tidyUpRobot);
        return uuid;
    }

    public TidyUpRobot addTidyUpRobot(TidyUpRobot tidyUpRobot)
    {
        if(tidyUpRobot == null)
        {
            throw new IllegalArgumentException();
        }
        TidyUpRobot robot = new TidyUpRobot(UUID.randomUUID(), tidyUpRobot.getName());
        robots.put(robot.getId(), robot);
        robotRepository.save(robot);
        return robot;
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
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(java.util.UUID tidyUpRobotId, Task task)
    {
        if(tidyUpRobotId == null || task == null || robots.get(tidyUpRobotId) == null)
        {
            throw new IllegalArgumentException();
        }

        var x = transportCategoryRepository.findAll();
        //Iterable<TransportCategory> transportCategories = transportCategoryRepository.findAll();
        for(TransportCategory transportCategory : x)
        {
            connections.addAll(transportCategory.getConnections());
        }
        var y = roomRepository.findAll();
        for(Room room : y)
        {
            rooms.put(room.getId(), room);
        }


        boolean terminated = robots.get(tidyUpRobotId).executeCommand(task, rooms);
        robotRepository.deleteById(tidyUpRobotId);
        robotRepository.save(robots.get(tidyUpRobotId));
        return terminated;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public java.util.UUID getTidyUpRobotRoomId(java.util.UUID tidyUpRobotId)
    {
        if(tidyUpRobotId == null || robots.get(tidyUpRobotId) == null)
        {
            throw new IllegalArgumentException();
        }

        return robots.get(tidyUpRobotId).getRoomId();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getVector2D(java.util.UUID tidyUpRobotId)
    {
        if(tidyUpRobotId == null || robots.get(tidyUpRobotId) == null)
        {
            throw new IllegalArgumentException();
        }

        return robots.get(tidyUpRobotId).getPosition();
    }

    public Iterable<TidyUpRobot> getTidyUpRobots()
    {
        return robotRepository.findAll();
    }

    public TidyUpRobot getTidyUpRobotById(UUID id)
    {
        var robot = robotRepository.findById(id).get();
        if(!(robot instanceof TidyUpRobot))
            throw new RuntimeException("No TidyUpRobot was found!");
        else
            return robot;
    }

    public boolean deleteById(UUID id)
    {
        if(robotRepository.findById(id).isPresent())
        {
            robotRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    public void changeName(UUID id, String name)
    {
        if(robotRepository.findById(id).isPresent())
        {
            TidyUpRobot robot = robotRepository.findById(id).get();
            robot.setName(name);
        }
        else throw new IllegalArgumentException("There is no robot with that id!");
    }

    public void giveTask(UUID id, Task task)
    {
        if(robotRepository.findById(id).isPresent())
        {
            TidyUpRobot robot = robotRepository.findById(id).get();
            robot.getTasks().add(task);
            executeCommand(id, task);
        }
        else throw new IllegalArgumentException("There is no robot with that id!");
    }

    public List<Task> getTaskHistory(UUID id)
    {
        if(robotRepository.findById(id).isPresent())
        {
            TidyUpRobot robot = robotRepository.findById(id).get();
            return robot.getTasks();
        }
        else throw new IllegalArgumentException("There is no robot with that id!");
    }

    public void deleteTaskHistory(UUID id)
    {
        if(robotRepository.findById(id).isPresent())
        {
            TidyUpRobot robot = robotRepository.findById(id).get();
            robot.getTasks().clear();
        }
        else throw new IllegalArgumentException("There is no robot with that id!");
    }


}
