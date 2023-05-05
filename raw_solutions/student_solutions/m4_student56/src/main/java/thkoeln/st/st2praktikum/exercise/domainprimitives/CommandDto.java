package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Setter;

public class CommandDto {

    @Setter
    private CommandType commandType;

    @Setter
    private Integer numberOfSteps;
}
