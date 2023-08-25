package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.util.UUID;

@Entity
public class TidyUpRobot extends AbstractEntity {

    @Getter
    private String name;

    @Getter
    @Setter
    @Embedded
    private Vector2D robotPosition;

    @Getter
    @Setter
    private UUID roomId;


    public TidyUpRobot(String name) {
        this.name = name;
        this.robotPosition = new Vector2D(0,0);
        this. roomId = null;
    }

    protected TidyUpRobot(){}

    public Vector2D getVector2D() { return robotPosition; }

}
