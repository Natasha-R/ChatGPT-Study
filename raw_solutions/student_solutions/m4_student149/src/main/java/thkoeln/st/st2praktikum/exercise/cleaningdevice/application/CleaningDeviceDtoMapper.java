package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import thkoeln.st.st2praktikum.exercise.AbstractDtoMapper;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;

/**
 * javaDoc
 *
 * @author gloewen
 */
public class CleaningDeviceDtoMapper extends AbstractDtoMapper<CleaningDevice, CleaningDeviceDto> {

    @Override
    public CleaningDeviceDto mapToDto(CleaningDevice cleaningDevice) {
        return map(cleaningDevice, CleaningDeviceDto.class);
    }

    @Override
    public CleaningDevice mapToEntity(CleaningDeviceDto dto) {
        return map(dto, CleaningDevice.class);
    }

}
