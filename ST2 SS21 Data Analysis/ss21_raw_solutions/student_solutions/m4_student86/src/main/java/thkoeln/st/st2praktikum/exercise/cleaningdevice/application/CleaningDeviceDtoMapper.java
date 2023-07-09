package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.modelmapper.ModelMapper;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;


public class CleaningDeviceDtoMapper {
    private ModelMapper modelMapper = new ModelMapper();

    public CleaningDeviceDto mapToDto (CleaningDevice device) {
        CleaningDeviceDto deviceDto = modelMapper.map( device, CleaningDeviceDto.class );
        deviceDto.setName(device.getName());
        deviceDto.setPosition(device.getPosition());
        deviceDto.setSpace(device.getSpace());
        return deviceDto;
    }

    public CleaningDevice mapToEntity ( CleaningDeviceDto deviceDto ) {
        CleaningDevice device = new CleaningDevice( deviceDto.getName() );
        device.setPosition(deviceDto.getPosition());
        device.setSpace(deviceDto.getSpace());
        modelMapper.map( deviceDto, device );
        return device;
    }

    public void mapToExistingEntity ( CleaningDeviceDto deviceDto, CleaningDevice toEntity, boolean ignoreNull ) {
        ModelMapper localModelMapper = new ModelMapper();
        localModelMapper.getConfiguration().setSkipNullEnabled( ignoreNull );
        localModelMapper.map( deviceDto, toEntity );
    }
}
