package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;


public class TaskDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public TaskDto mapToDto (Task task) {
        TaskDto taskDto = modelMapper.map( task, TaskDto.class );
        taskDto.setTaskType(task.getTaskType());
        taskDto.setNumberOfSteps(task.getNumberOfSteps());
        taskDto.setGridId(task.getGridId());
        return taskDto;
    }

    public Task mapToEntity ( TaskDto taskDto ) {
        Task task = new Task(taskDto.getTaskType(), taskDto.getNumberOfSteps(), taskDto.getGridId());
        //modelMapper.map( taskDto, task );
        return task;
    }

    public void mapToExistingEntity ( TaskDto taskDto, Task toEntity, boolean ignoreNull ) {
        ModelMapper localModelMapper = new ModelMapper();
        localModelMapper.getConfiguration().setSkipNullEnabled( ignoreNull );
        localModelMapper.map( taskDto, toEntity );
    }
}
