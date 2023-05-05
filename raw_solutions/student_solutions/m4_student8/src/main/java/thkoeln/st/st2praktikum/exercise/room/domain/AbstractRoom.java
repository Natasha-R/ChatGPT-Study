package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Getter;
import org.hibernate.annotations.Cascade;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.Transportable;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connectable;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@MappedSuperclass
public abstract class AbstractRoom implements Roomable
{
    @Id
    protected UUID roomID;


    @Transient
    protected List<FieldBlockable> walls;

    @Transient
    protected List<Transportable> cleaningRobots;

    protected AbstractRoom()
    {
        this.roomID = UUID.randomUUID();
        this.walls = new ArrayList<>();
        this.cleaningRobots = new ArrayList<>();
    }


    public void addWall(Wall wall)
    {
        this.walls.add(wall);
    }

}
