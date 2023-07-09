package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;

public class CleaningDeviceDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public CleaningDeviceDto mapToDto(CleaningDevice maintenanceDroid) {
        CleaningDeviceDto maintenanceDroidDto = modelMapper.map(maintenanceDroid, CleaningDeviceDto.class);

        return maintenanceDroidDto;
    }

    public CleaningDevice mapToEntity(CleaningDeviceDto cleaningDeviceDto) {
        CleaningDevice cleaningDevice = new CleaningDevice(cleaningDeviceDto.getName());
        modelMapper.map(cleaningDeviceDto, cleaningDevice);

        return cleaningDevice;
    }

    public void mapToExistingEntity(CleaningDeviceDto cleaningDeviceDto, CleaningDevice toEntity, boolean ignoreNull) {
        ModelMapper localModelMapper = new ModelMapper();
        localModelMapper.getConfiguration().setSkipNullEnabled(ignoreNull);
        localModelMapper.map(cleaningDeviceDto, toEntity);
    }
}
