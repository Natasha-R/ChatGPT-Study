package thkoeln.st.st2praktikum.exercise.domainprimitives;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.TidyUpRobotDto;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;


public class TaskDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public TaskDto mapToDto (Task task ) {
        TaskDto taskDto = modelMapper.map( task, TaskDto.class );
        return taskDto;
    }
}
