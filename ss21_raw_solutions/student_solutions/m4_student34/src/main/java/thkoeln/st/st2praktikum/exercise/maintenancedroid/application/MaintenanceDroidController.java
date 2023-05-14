package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import lombok.AllArgsConstructor;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class MaintenanceDroidController {
    private final MaintenanceDroidService maintenanceDroidService;
    private final MaintenanceDroidDTOMapper maintenanceDroidDTOMapper;
    private final TaskDTOMapper taskDTOMapper;

    @GetMapping("/maintenanceDroids")
    public ResponseEntity<List<MaintenanceDroidDTO>> getMaintenanceDroids() {
        var maintenanceDroids = StreamUtils
                .createStreamFromIterator(this.maintenanceDroidService.findAll().iterator())
                .map(this.maintenanceDroidDTOMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(maintenanceDroids);
    }

    @GetMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDTO> getMaintenanceDroid(@PathVariable UUID id) {
        var maintenanceDroid = this.maintenanceDroidService
                .findById(id)
                .map(maintenanceDroidDTOMapper::mapToDTO);
        return ResponseEntity.of(maintenanceDroid);
    }

    @PostMapping("/maintenanceDroids")
    public ResponseEntity<MaintenanceDroidDTO> postMaintenanceDroid(@RequestBody MaintenanceDroidDTO maintenanceDroidDTO) {
        var maintenanceDroidId = this.maintenanceDroidService.addMaintenanceDroid(maintenanceDroidDTO.getName());
        var createdMaintenanceDroidDTO = this.maintenanceDroidService.findById(maintenanceDroidId)
                .map(this.maintenanceDroidDTOMapper::mapToDTO);
        var returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(maintenanceDroidId)
                .toUri();
        return ResponseEntity
                .created(returnURI)
                .body(createdMaintenanceDroidDTO.orElseThrow(IllegalArgumentException::new));
    }

    @PatchMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDTO> patchMaintenanceDroid(
            @PathVariable UUID id, @RequestBody MaintenanceDroidDTO maintenanceDroidDTO) {
        this.maintenanceDroidService.changeMaintenanceDroidName(id, maintenanceDroidDTO.getName());
        var patchedMaintenanceDroid = this.maintenanceDroidService.findById(id)
                .map(this.maintenanceDroidDTOMapper::mapToDTO);
        if (patchedMaintenanceDroid.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(maintenanceDroidDTO);
    }

    @DeleteMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDTO> deleteMaintenanceDroid(@PathVariable UUID id) {
        this.maintenanceDroidService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/maintenanceDroids/{id}/tasks")
    public ResponseEntity<TaskDTO> postMaintenanceDroidTask(
            @PathVariable UUID id, @RequestBody TaskDTO taskDTO
    ) {
        var task = this.taskDTOMapper.mapToValueObject(taskDTO);
        this.maintenanceDroidService.executeCommand(id, task);
        return new ResponseEntity<>(taskDTO, HttpStatus.CREATED);
    }

    @GetMapping("/maintenanceDroids/{id}/tasks")
    public ResponseEntity<List<TaskDTO>> getMaintenanceDroidTasks(@PathVariable UUID id) {
        var allTaskDTOs = this.maintenanceDroidService.findById(id)
                .stream()
                .flatMap(it -> it.getTaskHistory().stream())
                .map(this.taskDTOMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allTaskDTOs);

    }

    @DeleteMapping("/maintenanceDroids/{id}/tasks")
    public ResponseEntity<?> deleteMaintenanceDroidTasks(@PathVariable UUID id) {
        this.maintenanceDroidService.deleteTaskHistory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
