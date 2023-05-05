package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;

public class CleaningDeviceDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public CleaningDeviceDto mapToDto(CleaningDevice cleaningDevice)
    {
        CleaningDeviceDto cleaningDeviceDto = modelMapper.map(cleaningDevice, CleaningDeviceDto.class);
        return cleaningDeviceDto;
    }

    public CleaningDevice mapToEntity(CleaningDeviceDto cleaningDeviceDto)
    {
        CleaningDevice cleaningDevice = new CleaningDevice(cleaningDeviceDto.getUuid(), cleaningDeviceDto.getName());
        return cleaningDevice;
    }
}
