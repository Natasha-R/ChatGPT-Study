package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractDto;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandType;

/**
 * javaDoc
 *
 * @author gloewen
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommandDto extends AbstractDto<Command> {
    private CommandType commandType;
    private String gridId;
    private Integer numberOfSteps;
}
