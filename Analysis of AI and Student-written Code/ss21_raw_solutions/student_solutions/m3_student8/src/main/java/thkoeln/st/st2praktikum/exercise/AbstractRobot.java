package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class AbstractRobot
{
    @Id
    protected UUID robotID;

    @OneToOne
    protected Coordinate coordinate;

    @ManyToOne
    protected Room jobRoom;


    protected AbstractRobot()
    {
        this.robotID = UUID.randomUUID();
    }
}
