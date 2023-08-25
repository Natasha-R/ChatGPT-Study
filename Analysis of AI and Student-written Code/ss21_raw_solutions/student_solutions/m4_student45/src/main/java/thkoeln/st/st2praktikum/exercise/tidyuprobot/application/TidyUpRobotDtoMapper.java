package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;


public class TidyUpRobotDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public TidyUpRobotDto mapToDto(TidyUpRobot tidyUpRobot) {
        TidyUpRobotDto tidyUpRobotDto = modelMapper.map(tidyUpRobot, TidyUpRobotDto.class);
        return tidyUpRobotDto;
    }

    public TidyUpRobot mapToEntity(TidyUpRobotDto tidyUpRobotDto) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(tidyUpRobotDto.getName(), tidyUpRobotDto.getRooms(), tidyUpRobotDto.getOrders());
        modelMapper.map(tidyUpRobotDto, tidyUpRobot);
        return tidyUpRobot;
    }
}
