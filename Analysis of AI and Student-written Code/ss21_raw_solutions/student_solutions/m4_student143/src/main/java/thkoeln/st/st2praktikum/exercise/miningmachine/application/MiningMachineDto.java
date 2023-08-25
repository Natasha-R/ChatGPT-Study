package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MiningMachineDto {
    private String name;
    private Coordinate coordinate;
}
