package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiningMachineDto {

    private String name;
    private Coordinate coordinate;
    private Field field;

}
