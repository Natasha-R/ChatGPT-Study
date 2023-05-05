package thkoeln.st.st2praktikum.exercise.domainprimitives;

import org.modelmapper.ModelMapper;


public class TaskDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public TaskDto mapToDto (Task task ) {
        TaskDto taskDto = modelMapper.map( task, TaskDto.class );
        return taskDto;
    }

}
