package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

public class TidyUpRobotDtoMapper{
    private ModelMapper modelMapper = new ModelMapper();

    public TidyUpRobotDto mapToDto(TidyUpRobot tidyUpRobot) {
        return modelMapper.map(tidyUpRobot, TidyUpRobotDto.class);
    }

    public TidyUpRobot mapToEntity(TidyUpRobotDto tidyUpRobotDto) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(tidyUpRobotDto.getName());
        modelMapper.map(tidyUpRobotDto, tidyUpRobot);
        return tidyUpRobot;
    }
}
