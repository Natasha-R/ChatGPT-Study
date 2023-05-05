package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//@RepositoryRestController
@RestController
@Transactional
public class CleaningDeviceController {

    private CleaningDeviceService cleaningDeviceService;
    private final CleaningDeviceDtoMapper deviceMapper = new CleaningDeviceDtoMapper();
    private final TaskDtoMapper taskMapper = new TaskDtoMapper();

    @Autowired
    public CleaningDeviceController (CleaningDeviceService cleaningDeviceService) {
        this.cleaningDeviceService = cleaningDeviceService;
    }

    @GetMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDeviceDto> getCleaningDeviceById (@PathVariable UUID id ) {
        CleaningDevice device = cleaningDeviceService.findById(id);
        CleaningDeviceDto dto = deviceMapper.mapToDto(device);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDeviceDto> deleteDevice (@PathVariable UUID id ) {
        CleaningDevice device = cleaningDeviceService.findById(id);
        cleaningDeviceService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/cleaningDevices")
    public Iterable<CleaningDeviceDto> getAllCleaningDevices () {
        Iterable<CleaningDevice> allDevices = cleaningDeviceService.findAll();
        List<CleaningDeviceDto> allDtos = new ArrayList<>();
        for (CleaningDevice device : allDevices) {
            allDtos.add(deviceMapper.mapToDto(device));
        }
        return allDtos;
    }

    @PostMapping("/cleaningDevices")
    public ResponseEntity<CleaningDeviceDto> createNewCleaningDevice( @RequestBody CleaningDeviceDto deviceDto ) {
        //CleaningDevice device = modelMapper.mapToEntity(deviceDto);
        UUID id = cleaningDeviceService.addCleaningDevice(deviceDto.getName());
        CleaningDevice device = cleaningDeviceService.findById(id);
        //device.enterSpace(deviceDto.getSpace());
        //device.move(...
        device.setSpace(deviceDto.getSpace());
        device.setPosition(deviceDto.getPosition());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(uri).body(deviceDto);
    }

    @PatchMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDeviceDto> changeCleaningDeviceName (
            @PathVariable UUID id, @RequestBody CleaningDeviceDto deviceDto ) {
        CleaningDevice device = cleaningDeviceService.findById(id);
        device.setName(deviceDto.getName());
        CleaningDeviceDto dto = deviceMapper.mapToDto(device);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/cleaningDevices/{cleaningDeviceId}/tasks")
    public ResponseEntity<TaskDto> executeTaskOnDevice (
            @PathVariable UUID cleaningDeviceId, @RequestBody TaskDto taskDto ) {
        Task task = taskMapper.mapToEntity(taskDto);
        CleaningDevice device = cleaningDeviceService.findById(cleaningDeviceId);
        device.executeTask(task, cleaningDeviceService);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{cleaningDevice-id}/tasks")
                .buildAndExpand(cleaningDeviceId)
                .toUri();

        return ResponseEntity.created(uri).body(taskDto);
    }

    @GetMapping("/cleaningDevices/{cleaningDeviceId}/tasks")
    public Iterable<TaskDto> getTaskHistory (@PathVariable UUID cleaningDeviceId) {
        CleaningDevice device = cleaningDeviceService.findById(cleaningDeviceId);
        Iterable<Task> taskHistory = device.getTaskHistory();
        List<TaskDto> allDtos = new ArrayList<>();
        for (Task task : taskHistory) {
            allDtos.add(taskMapper.mapToDto(task));
        }
        return allDtos;
    }

    @DeleteMapping("/cleaningDevices/{cleaningDeviceId}/tasks")
    public ResponseEntity<TaskDto> clearTaskHistory (@PathVariable UUID cleaningDeviceId ) {
        CleaningDevice device = cleaningDeviceService.findById(cleaningDeviceId);
        device.deleteTaskHistory();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
