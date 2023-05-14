package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity
public class MiningMachine {

    private String name;
    @Id
    private final UUID uuid;
    private Point coordinates;
    private UUID fieldId;

    @ElementCollection(targetClass = Command.class)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Command> commands = new ArrayList<>();

    public MiningMachine(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public MiningMachine() {
        name = null;
        uuid = UUID.randomUUID();
    }

    public Boolean isAtZeroZero() {
        return this.coordinates.getX() == 0 && this.coordinates.getY() == 0;
    }

    public void moveOne(CommandType direction) {
        switch (direction) {
            case EAST:
                this.coordinates = new Point(this.coordinates.getX() + 1, this.coordinates.getY());
                break;
            case WEST:
                this.coordinates = new Point(this.coordinates.getX() - 1, this.coordinates.getY());
                break;
            case NORTH:
                this.coordinates = new Point(this.coordinates.getX(), this.coordinates.getY() + 1);
                break;
            case SOUTH:
                this.coordinates = new Point(this.coordinates.getX(), this.coordinates.getY() - 1);
                break;
        }
    }

    public Point getPoint() {
        return coordinates;
    }
}
