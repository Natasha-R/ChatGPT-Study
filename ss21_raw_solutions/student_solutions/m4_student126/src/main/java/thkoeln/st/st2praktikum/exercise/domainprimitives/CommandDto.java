package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommandDto {

    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;
}
