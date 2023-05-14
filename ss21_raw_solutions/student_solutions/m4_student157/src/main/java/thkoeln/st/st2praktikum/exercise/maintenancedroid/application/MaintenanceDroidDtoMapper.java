package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

public class MaintenanceDroidDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public MaintenanceDroidDto mapToDto(MaintenanceDroid maintenanceDroid) {
        MaintenanceDroidDto maintenanceDroidDto = modelMapper.map(maintenanceDroid, MaintenanceDroidDto.class);
        return maintenanceDroidDto;
    }

    public MaintenanceDroid mapToEntity(MaintenanceDroidDto maintenanceDroidDto) {
        MaintenanceDroid maintenanceDroid = new MaintenanceDroid(maintenanceDroidDto.getName());
        modelMapper.map(maintenanceDroidDto, maintenanceDroid);
        return maintenanceDroid;
    }

    public void mapToExistingEntity(MaintenanceDroidDto maintenanceDroidDto, MaintenanceDroid toEntity, boolean ignoreNull) {
        ModelMapper localModelMapper = new ModelMapper();
        localModelMapper.getConfiguration().setSkipNullEnabled(ignoreNull);
        localModelMapper.map(maintenanceDroidDto, toEntity);
    }
}
