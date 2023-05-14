package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.modelmapper.ModelMapper;

public class TidyUpRobotDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public TidyUpRobotDto mapToDto(TidyUpRobot tidyUpRobot){
        TidyUpRobotDto tidyUpRobotDto = modelMapper.map(tidyUpRobot, TidyUpRobotDto.class);
        return tidyUpRobotDto;
    }

    public TidyUpRobot mapToEntity(TidyUpRobotDto tidyUpRobotDto){
        TidyUpRobot tidyUpRobot = new TidyUpRobot(tidyUpRobotDto.getName());
        modelMapper.map(tidyUpRobotDto, tidyUpRobot);
        return tidyUpRobot;
    }

    public void mapToExistingEntity (TidyUpRobotDto tidyUpRobotDto, TidyUpRobot toEntity, boolean ignoreNULL){
        ModelMapper localModelMapper = new ModelMapper();
        localModelMapper.getConfiguration().setSkipNullEnabled(ignoreNULL);
        localModelMapper.map(tidyUpRobotDto, toEntity);
    }
}
