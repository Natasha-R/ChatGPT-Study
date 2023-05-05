package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

/**
 * This mapper class might not be complete yet! Enhance it if necessary.
 */

public class MaintenanceDroidDtoMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public MaintenanceDroidDto mapToDto (MaintenanceDroid droid) {
        return modelMapper.map(droid, MaintenanceDroidDto.class);
    }

    public MaintenanceDroid mapToEntity (MaintenanceDroidDto droidDto) {
        MaintenanceDroid droid = new MaintenanceDroid(droidDto.getName());
        modelMapper.map(droidDto, droid);
        return droid;
    }
}
