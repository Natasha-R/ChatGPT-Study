package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
public class MiningMachine {
    @Id
    private final UUID id = UUID.randomUUID();

    private String machineName;

    @Embedded
    @Getter
    private Coordinate coordinate;

    private MachineDeploymentState state = MachineDeploymentState.HIBERNATING;

    @ManyToOne
    private Field currentField;

    public UUID getFieldId() {
        return currentField.getId();
    }

    public MiningMachine(String machineName) {
        this.machineName = machineName;
    }

    public void setPosition(int x, int y) {
        this.coordinate = new Coordinate(x, y);
    }

    public void setPosition(Coordinate p) {
        this.coordinate = p;
    }

    public void setField(Field field) {
        this.currentField = field;
    }

    public void setState(MachineDeploymentState s) {
        this.state = s;
    }

    protected MiningMachine() {
    }
}