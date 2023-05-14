package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.core.Identifiable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public abstract class AbstractRobot implements Identifiable {

    @Id
    protected UUID id;
    @Getter
    protected String name;
    @OneToOne (cascade = CascadeType.ALL)
    protected Vector2D position;

    @Override
    public UUID getId() {
        return id;
    }

    public Vector2D getPosition() {
        return position;
    }

    public UUID getRoomId() {
        return position.getRoomID();
    }

    public Vector2D getVector2D() {
        return position;
    }

    abstract public Boolean processTask(Task task, MapInstance map);
}
