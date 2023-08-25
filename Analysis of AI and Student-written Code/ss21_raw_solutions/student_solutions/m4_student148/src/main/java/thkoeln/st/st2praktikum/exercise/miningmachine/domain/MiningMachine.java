package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import thkoeln.st.st2praktikum.exercise.field.domain.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;

import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Entity
public class MiningMachine {
    @Getter
    @Id
    private final UUID id = UUID.randomUUID();

    @Getter
    @Setter
    private String name;

    @Embedded
    @Getter
    private Coordinate coordinate;

    @Getter
    private MachineDeploymentState state = MachineDeploymentState.HIBERNATING;

    @Getter
    @ManyToOne
    private Field currentField;

    @ElementCollection(targetClass = Command.class, fetch = FetchType.LAZY)
    private final List<Command> command = new ArrayList<>();

    public MiningMachine(String name) {
        this.name = name;
    }

    public List<Command> getCommands() {
        return this.command;
    }

    public void setField(Field field) {
        this.currentField = field;
    }

    public UUID getFieldId() {
        return this.currentField == null ? null : this.currentField.getFieldId();
    }

    public void setPosition(int x, int y) {
        this.coordinate = new Coordinate(x, y);
    }

    public void setPosition(Coordinate p) {
        this.coordinate = p;
    }

    public void setState(MachineDeploymentState s) {
        this.state = s;
    }

    protected MiningMachine() {
    }
}
