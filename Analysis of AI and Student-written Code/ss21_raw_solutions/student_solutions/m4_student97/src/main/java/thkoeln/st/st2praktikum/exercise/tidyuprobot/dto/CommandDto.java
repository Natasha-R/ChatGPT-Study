package thkoeln.st.st2praktikum.exercise.tidyuprobot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommandDto {
    private UUID id;
    private CommandType commandType;
    private Integer numberOfSteps;
    private UUID gridId;
}
