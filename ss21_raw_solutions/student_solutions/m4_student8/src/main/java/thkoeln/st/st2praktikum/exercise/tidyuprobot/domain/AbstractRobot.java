package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class AbstractRobot
{
    @Id
    protected UUID robotID;

    @Embedded
    protected Coordinate coordinate;

    @ManyToOne
    protected Room jobRoom;


    protected AbstractRobot()
    {
        this.robotID = UUID.randomUUID();
    }
}
