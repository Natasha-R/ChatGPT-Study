package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotDto;

public class TidyUpRobotDtoMapper
{
    private ModelMapper modelMapper = new ModelMapper();

    public TidyUpRobotDto mapToDto (TidyUpRobot tidyUpRobot)
    {
        TidyUpRobotDto mappedTidyUpRobot = modelMapper.map(tidyUpRobot, TidyUpRobotDto.class);
        return mappedTidyUpRobot;
    }

    public TidyUpRobot mapToEntity (TidyUpRobotDto tidyUpRobotDto)
    {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(tidyUpRobotDto.getId(), tidyUpRobotDto.getName());
        modelMapper.map(tidyUpRobotDto, tidyUpRobot);
        return tidyUpRobot;
    }
}