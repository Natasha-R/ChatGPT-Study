package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MiningMachine {

    @Id
    private UUID miningMachineId;
    private String name;
    private Coordinate coordinate;
    private UUID fieldId;

    public MiningMachine(UUID miningMachineId, String name) {
        this.miningMachineId = miningMachineId;
        this.name = name;
    }

    public void moveNorth() {
        coordinate.setY(coordinate.getY()+1);
    }

    public void moveEast() {
        coordinate.setX(coordinate.getX()+1);
    }

    public void moveSouth() {
        coordinate.setY(coordinate.getY()-1);
    }

    public void moveWest() {
        coordinate.setX(coordinate.getX()-1);
    }
}
