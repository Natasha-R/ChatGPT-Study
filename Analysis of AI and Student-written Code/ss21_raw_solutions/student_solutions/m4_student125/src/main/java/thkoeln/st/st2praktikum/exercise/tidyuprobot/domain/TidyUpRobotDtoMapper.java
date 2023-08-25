package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.modelmapper.ModelMapper;

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
        TidyUpRobot tidyUpRobot = new TidyUpRobot(tidyUpRobotDto.getName(), tidyUpRobotDto.getId());
        modelMapper.map(tidyUpRobotDto, tidyUpRobot);
        return tidyUpRobot;
    }
}
