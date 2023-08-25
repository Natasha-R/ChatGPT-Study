package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class TidyUpRobot extends AbstractEntity {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @Embedded
    private Vector2D robotPosition;

    @Getter
    @Setter
    private UUID roomId;

    @Getter
    @ElementCollection(targetClass = Command.class, fetch = FetchType.EAGER)
    private List<Command> commands = new ArrayList<>();

    public TidyUpRobot(String name) {
        this.name = name;
        this.robotPosition = new Vector2D(0,0);
        this. roomId = null;
    }

    protected TidyUpRobot(){}

    public void setCommandsNull() { this.commands = null; }

    public Vector2D getVector2D() { return robotPosition; }

}
