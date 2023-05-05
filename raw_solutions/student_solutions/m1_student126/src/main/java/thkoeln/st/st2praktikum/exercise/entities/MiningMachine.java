package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.abstractions.abstractClasses.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.valueObjects.Coordinate;

@NoArgsConstructor
@Getter
public class MiningMachine extends AbstractEntity {

    @Setter
    private String name;
    @Setter
    private Coordinate coordinate;

    public MiningMachine(String name) {
        this.name = name;
        coordinate = new Coordinate(-1, -1);
    }

}
