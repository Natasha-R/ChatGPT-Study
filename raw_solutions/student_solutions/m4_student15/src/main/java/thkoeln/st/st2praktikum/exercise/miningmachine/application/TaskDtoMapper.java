package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

public class TaskDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public TaskDto mapToDto ( Task task ) {
        TaskDto taskDto = modelMapper.map( task, TaskDto.class );
        return taskDto;
    }

    public Task mapToEntity ( TaskDto taskDto) throws RuntimeException {
        Task task = new Task(taskDto.getTaskType(),taskDto.getNumberOfSteps(), taskDto.getGridId());
        modelMapper.map( taskDto, task );
        return task;
    }

}

