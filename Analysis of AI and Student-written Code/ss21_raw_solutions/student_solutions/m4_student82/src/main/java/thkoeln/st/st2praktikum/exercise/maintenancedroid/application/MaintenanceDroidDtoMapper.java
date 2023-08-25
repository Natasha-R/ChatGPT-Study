package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.jboss.jandex.Main;
import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

public class MaintenanceDroidDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public MaintenanceDroidDto mapToDto(MaintenanceDroid maintenanceDroid){
        MaintenanceDroidDto maintenanceDroidDto = new MaintenanceDroidDto(maintenanceDroid.getName());
        return maintenanceDroidDto;
    }

    public MaintenanceDroid mapToEntity(MaintenanceDroidDto maintenanceDroidDto){
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(maintenanceDroidDto.getName());
        modelMapper.map(maintenanceDroidDto, maintenanceDroid);
        return maintenanceDroid;
    }
}
