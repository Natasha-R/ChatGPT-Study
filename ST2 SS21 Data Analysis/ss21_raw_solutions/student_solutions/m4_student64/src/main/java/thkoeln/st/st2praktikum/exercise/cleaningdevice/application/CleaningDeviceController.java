package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;


import static org.springframework.http.HttpStatus.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


import javax.transaction.Transactional;
import java.util.UUID;

@RestController
@Transactional
public class CleaningDeviceController {
    private final CleaningDeviceService cleaningDeviceService;
    private final CleaningDeviceDtoMapper cleaningDeviceDtoMapper = new CleaningDeviceDtoMapper();

    @Autowired
    public CleaningDeviceController(CleaningDeviceService cleaningDeviceService)
    {
        this.cleaningDeviceService = cleaningDeviceService;
    }

    @GetMapping("/cleaningDevices")
    public List<CleaningDeviceDto> getAllCleaningDevices()
    {
        Iterable<CleaningDevice> allCleaningDevices = this.cleaningDeviceService.findAll();
        List<CleaningDeviceDto> allDtos = new ArrayList<>();

        for (CleaningDevice cleaningDevice : allCleaningDevices) {
            allDtos.add(cleaningDeviceDtoMapper.mapToDto(cleaningDevice));
        }

        return allDtos;

    }

    @GetMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDevice> getOneCleaningDevice(@PathVariable UUID id)
    {
        CleaningDevice CleaningDevice = this.cleaningDeviceService.findById(id);
        CleaningDeviceDto createdCleaningDeviceDto = this.cleaningDeviceDtoMapper.mapToDto(CleaningDevice);

        return new ResponseEntity(createdCleaningDeviceDto, OK);
    }


    @PostMapping("/cleaningDevices")
    public ResponseEntity<CleaningDeviceDto> createCleaningDevice(@RequestBody CleaningDeviceDto monsterDto) {
        CleaningDevice CleaningDevice = this.cleaningDeviceService.createCleaningDeviceFromDto(monsterDto);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(CleaningDevice.getId())
                .toUri();
        CleaningDeviceDto createdCleaningDeviceDto = this.cleaningDeviceDtoMapper.mapToDto(CleaningDevice);
        return ResponseEntity
                .created(returnURI)
                .body(createdCleaningDeviceDto);
    }

    @DeleteMapping("/cleaningDevices/{id}")
    public ResponseEntity<?> deleteCleaningDevice(@PathVariable UUID id) {
        this.cleaningDeviceService.deleteById(id);

        return new ResponseEntity(NO_CONTENT);
    }

    @PatchMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDeviceDto> updateCleaningDevice(
            @PathVariable UUID id, @RequestBody CleaningDeviceDto CleaningDeviceDto) {
        CleaningDevice CleaningDevice = this.cleaningDeviceService.findById(id);
        CleaningDevice.setName(CleaningDeviceDto.getName());
        CleaningDeviceDto cleaningDeviceDto = this.cleaningDeviceDtoMapper.mapToDto(CleaningDevice);
        return new ResponseEntity<>(cleaningDeviceDto, OK);
    }


    @GetMapping("/cleaningDevices/{id}/tasks")
    List<Task> getAllOrdersForCleaningDevices(@PathVariable String id) {
        CleaningDevice cleaningDevice = this.cleaningDeviceService.findById(UUID.fromString(id));

        return cleaningDevice.getTasks();
    }

    @PostMapping("/cleaningDevices/{id}/tasks")
    public ResponseEntity<CleaningDeviceDto> createOrderForCleaningDevice(@PathVariable String id, @RequestBody Task task) {
        this.cleaningDeviceService.executeCommand(UUID.fromString(id), task);

        return new ResponseEntity(task, CREATED);
    }

    @DeleteMapping("/cleaningDevices/{id}/tasks")
    public ResponseEntity<?> deleteTasksForCleaningDevice(@PathVariable UUID id) {
        CleaningDevice cleaningDevice = this.cleaningDeviceService.findById(id);

        return new ResponseEntity<>(NO_CONTENT);
    }

}
