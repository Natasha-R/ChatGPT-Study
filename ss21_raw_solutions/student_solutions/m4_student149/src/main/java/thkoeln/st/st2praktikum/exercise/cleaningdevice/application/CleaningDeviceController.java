package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CleaningDeviceController {

    @Autowired
    private CleaningDeviceService cleaningDeviceService;

    private CleaningDeviceDtoMapper cleaningDeviceDtoMapper = new CleaningDeviceDtoMapper();
    private CommandDtoMapper commandDtoMapper = new CommandDtoMapper();

    @GetMapping("/test")
    public ResponseEntity test() {
        return new ResponseEntity("Hello World!", HttpStatus.OK);
    }

    @GetMapping("/cleaningDevices")
    public Iterable<CleaningDeviceDto> getAllCleaningDevices() {
        return cleaningDeviceService.getCleaningDevices().stream()
                .map(cleaningDevice -> cleaningDeviceDtoMapper.mapToDto(cleaningDevice))
                .collect(Collectors.toList());
    }

    @PostMapping("/cleaningDevices")
    public ResponseEntity createNewCleaningDevice(@RequestBody CleaningDeviceDto cleaningDeviceDto) {
        try {
            CleaningDevice newCleaningDevice = cleaningDeviceService.addCleaningDeviceReturnResult(cleaningDeviceDto.getName());
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newCleaningDevice.getId())
                    .toUri();
            return ResponseEntity.created(returnURI).body(cleaningDeviceDtoMapper.mapToDto(newCleaningDevice));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    @GetMapping("/cleaningDevices/{cleaningDeviceId}")
    public ResponseEntity<CleaningDeviceDto> getCleaningDevice(@PathVariable String cleaningDeviceId) {
        try {
            CleaningDevice cleaningDevice = cleaningDeviceService.getCleaningDevice(UUID.fromString(cleaningDeviceId));
            return new ResponseEntity(cleaningDeviceDtoMapper.mapToDto(cleaningDevice), HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cleaningDevices/{cleaningDeviceId}")
    public ResponseEntity deleteCleaningDevice(@PathVariable String cleaningDeviceId) {
        cleaningDeviceService.getCleaningDevice(UUID.fromString(cleaningDeviceId));
        cleaningDeviceService.deleteCleaningDevice(UUID.fromString(cleaningDeviceId));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/cleaningDevices/{cleaningDeviceId}")
    public ResponseEntity updateCleaningDevice(@PathVariable String cleaningDeviceId, @RequestBody CleaningDeviceDto cleaningDeviceDto) {
        CleaningDevice cleaningDevice = cleaningDeviceService.getCleaningDevice(UUID.fromString(cleaningDeviceId));
        cleaningDevice.setName(cleaningDeviceDto.getName());
        CleaningDevice savedCleaningDevice = cleaningDeviceService.updateCleaningDevice(cleaningDevice);

        return new ResponseEntity(cleaningDeviceDtoMapper.mapToDto(savedCleaningDevice), HttpStatus.OK);
    }

    @PostMapping("/cleaningDevices/{cleaningDeviceId}/commands")
    public ResponseEntity createCleaningDeviceCommand(@PathVariable String cleaningDeviceId, @RequestBody CommandDto commandDto) {
        HttpStatus httpStatus = HttpStatus.CREATED;
        Boolean successfully = cleaningDeviceService.executeCommand(UUID.fromString(cleaningDeviceId), commandDtoMapper.mapToEntity(commandDto));
        if (!successfully) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity(httpStatus);
    }

    @GetMapping("/cleaningDevices/{cleaningDeviceId}/commands")
    public ResponseEntity<Iterable<CleaningDeviceDto>> getAllCleaningDeviceCommands(@PathVariable String cleaningDeviceId) {
        HttpStatus httpStatus = HttpStatus.OK;
        CleaningDevice cleaningDevice = cleaningDeviceService.getCleaningDevice(UUID.fromString(cleaningDeviceId));
        List<CommandDto> commandDtos = cleaningDevice.getCommands().stream()
                .map(s -> commandDtoMapper.mapToDto(Command.fromString(s)))
                .collect(Collectors.toList());

        return new ResponseEntity(commandDtos, httpStatus);
    }

    @DeleteMapping("/cleaningDevices/{cleaningDeviceId}/commands")
    public ResponseEntity<Iterable<CleaningDeviceDto>> deleteAllCleaningDeviceCommands(@PathVariable String cleaningDeviceId) {
        HttpStatus httpStatus = HttpStatus.NO_CONTENT;
        CleaningDevice cleaningDevice = cleaningDeviceService.getCleaningDevice(UUID.fromString(cleaningDeviceId));
        cleaningDevice.getCommands().clear();
        cleaningDeviceService.updateCleaningDevice(cleaningDevice);

        return new ResponseEntity(httpStatus);
    }

}
