package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.application.CleaningDeviceDto;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceCreationException;

public class CleaningDeviceDtoMapper {
    private final ModelMapper modelMapper= new ModelMapper();


    public CleaningDeviceDto mapToDto(CleaningDevice cleaningDevice){
        CleaningDeviceDto cleaningDeviceDto=modelMapper.map(cleaningDevice,CleaningDeviceDto.class);
        return cleaningDeviceDto;
    }

    public CleaningDevice mapToEntity(CleaningDeviceDto cleaningDeviceDto) throws CleaningDeviceCreationException {
        CleaningDevice cleaningDevice= new CleaningDevice(cleaningDeviceDto.getName());
        modelMapper.map(cleaningDeviceDto, cleaningDevice);
        return cleaningDevice;
    }
}
