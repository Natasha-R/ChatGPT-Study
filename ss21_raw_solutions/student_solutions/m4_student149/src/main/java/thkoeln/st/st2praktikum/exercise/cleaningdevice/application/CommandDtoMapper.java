package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import thkoeln.st.st2praktikum.exercise.AbstractDtoMapper;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

/**
 * javaDoc
 *
 * @author gloewen
 */
public class CommandDtoMapper extends AbstractDtoMapper<Command, CommandDto> {

    @Override
    public CommandDto mapToDto(Command command) {
        return map(command, CommandDto.class);
    }

    @Override
    public Command mapToEntity(CommandDto dto) {
        String argument = dto.getGridId() == null ? String.valueOf(dto.getNumberOfSteps()) : dto.getGridId();
        return new Command(dto.getCommandType(), argument);
    }

}
