package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Getter
@MappedSuperclass
public abstract class AbstractRoom implements Roomable
{
    @Id
    protected UUID roomID;

    @OneToMany
    protected List<Connection> roomConnections;

    @OneToMany
    protected List<Wall> walls;
    @OneToMany
    protected List<TidyUpRobot> cleaningRobots;

    protected AbstractRoom()
    {
        this.roomID = UUID.randomUUID();
        this.walls = new ArrayList<>();
        this.roomConnections = new ArrayList<>();
        this.cleaningRobots = new ArrayList<>();
    }

    public TidyUpRobot getRobotByID(UUID robotID)
    {
        for (TidyUpRobot robot : cleaningRobots)
        {
            if (robot.getRobotID()==robotID)
                return robot;
        }

        return null;
    }

    public void addWall(Wall wall)
    {
        this.walls.add(wall);
    }

}
