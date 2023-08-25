package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import org.modelmapper.ModelMapper;

public class MaintenanceDroidDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public MaintenanceDroidDto mapToDto ( MaintenanceDroid maintenanceDroid ) {
        MaintenanceDroidDto maintenanceDroidDto = modelMapper.map( maintenanceDroid, MaintenanceDroidDto.class );
        return maintenanceDroidDto;
    }

    public MaintenanceDroid mapToEntity ( MaintenanceDroidDto maintenanceDroidDto ) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(maintenanceDroidDto.getName());
        modelMapper.map( maintenanceDroidDto, maintenanceDroid );
        return maintenanceDroid;
    }


}
