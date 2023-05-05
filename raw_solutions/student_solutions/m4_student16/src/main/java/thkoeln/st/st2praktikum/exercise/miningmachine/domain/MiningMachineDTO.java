package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MiningMachineDTO {
    private String name;
    private Coordinate coordinate;
}
