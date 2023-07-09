package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;


@RestController
@Transactional
public class MaintenanceDroidController {
    private final MaintenanceDroidService maintenanceDroidService;
    private final MaintenanceDroidDtoMapper maintenanceDroidDtoMapper = new MaintenanceDroidDtoMapper();

    @Autowired
    public MaintenanceDroidController( MaintenanceDroidService MaintenanceDroidService ) {
        this.maintenanceDroidService = MaintenanceDroidService;
    }

    /**
     * Get all droids
     */
    @GetMapping("/maintenanceDroids")
    public Iterable<MaintenanceDroidDto> getAllMaintenanceDroids() {
        Iterable<MaintenanceDroid> allDroids = this.maintenanceDroidService.findAll();
        List<MaintenanceDroidDto> allDtos = new ArrayList<>();
        for ( MaintenanceDroid droid : allDroids ) {
            allDtos.add( this.maintenanceDroidDtoMapper.mapToDto( droid ) );
        }
        return allDtos;
    }

    /**
     * Create New droid
     */
    @PostMapping("/maintenanceDroids")
    public ResponseEntity<MaintenanceDroidDto> createNewDungeon( @RequestBody MaintenanceDroidDto droidDto ) {
        MaintenanceDroid droid = this.maintenanceDroidService.createDroidFromDto( droidDto );
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand( droid.getUuid() )
                .toUri();
        MaintenanceDroidDto newDroidDto = this.maintenanceDroidDtoMapper.mapToDto( droid );
        return ResponseEntity
                .created(returnURI)
                .body( newDroidDto );
    }

    /**
     * Get one droid
     */
    @GetMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDto> getOneMaintenanceDroid(@PathVariable UUID id ) {
        MaintenanceDroid droid = this.maintenanceDroidService.getMaintenanceDroidByUUID(id);
        MaintenanceDroidDto droidDto = this.maintenanceDroidDtoMapper.mapToDto(droid);
        return new ResponseEntity<>(droidDto, OK);
    }

    /**
     * Delete one droid
     */
    @DeleteMapping("/maintenanceDroids/{id}")
    public ResponseEntity<?> deleteOneMaintenanceDroid(@PathVariable UUID id ) {
        this.maintenanceDroidService.deleteMaintenanceDroidByUUID(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    /**
     * Rename one droid
     */
    @PatchMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDto> changeNameOnOneMaintenanceDroid(@PathVariable UUID id, @RequestBody MaintenanceDroidDto droidDto ) {
        MaintenanceDroid droid = this.maintenanceDroidService.getMaintenanceDroidByUUID(id);
        droid.setName(droidDto.getName());
        MaintenanceDroidDto newDroidDto = this.maintenanceDroidDtoMapper.mapToDto(droid);
        return new ResponseEntity<>(newDroidDto, OK);
    }


    /**
     * Get Tasks for one droid
     */
    @GetMapping("/maintenanceDroids/{id}/tasks")
    public ResponseEntity<List<Task>> getTasksForDroid(@PathVariable UUID id ) {
        MaintenanceDroid droid = this.maintenanceDroidService.getMaintenanceDroidByUUID(id);
        List<Task> tasks = droid.getReceivedTasks();
        return new ResponseEntity<>(tasks, OK);
    }

    /**
     * New Task for one droid
     */
    @PostMapping("/maintenanceDroids/{id}/tasks")
    public ResponseEntity<Task> newTaskForDroid(@PathVariable UUID id, @RequestBody Task task ) {
        this.maintenanceDroidService.executeCommand(id, task);

        return new ResponseEntity<>(task, CREATED);
    }

    /**
     * New Task for one droid
     */
    @DeleteMapping("/maintenanceDroids/{id}/tasks")
    public ResponseEntity<?> deleteTasksDorDroid(@PathVariable UUID id) {
        MaintenanceDroid droid = this.maintenanceDroidService.getMaintenanceDroidByUUID(id);

        droid.deleteTaskList();

        return new ResponseEntity<>(NO_CONTENT);
    }
}
