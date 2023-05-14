package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskDto;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskDtoMapper;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidDto;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidDtoMapper;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class MaintenanceDroidController {

    private final MaintenanceDroidService maintenanceDroidService;
    private MaintenanceDroidDtoMapper maintenanceDroidDtoMapper = new MaintenanceDroidDtoMapper();
    private TaskDtoMapper taskDtoMapper = new TaskDtoMapper();

    @Autowired
    public MaintenanceDroidController( MaintenanceDroidService maintenanceDroidService ) {
        this.maintenanceDroidService = maintenanceDroidService;
    }

    // 1. Get all maintenance droids
    ///maintenanceDroids
    //GET
    @GetMapping("/maintenanceDroids")
    public Iterable<MaintenanceDroidDto> getAllMaintenanceDroids(){
        Iterable<MaintenanceDroid> allMaintenanceDroids = maintenanceDroidService.findAll();
        List<MaintenanceDroidDto> allDtos = new ArrayList<>();
        for ( MaintenanceDroid maintenanceDroid : allMaintenanceDroids){
            allDtos.add(maintenanceDroidDtoMapper.mapToDto(maintenanceDroid));
        }
        return allDtos;
    }

    //2. Create a new maintenance droid
    ///maintenanceDroids
    //POST
    @PostMapping("/maintenanceDroids")
    public ResponseEntity<MaintenanceDroidDto> createNewMaintenanceDroid(@RequestBody MaintenanceDroidDto maintenanceDroidDto )  {
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.createMaintenanceDroidFromDto( maintenanceDroidDto );

        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{maintenanceDroid-id}")
                .buildAndExpand( maintenanceDroid.getMaintenanceDroidsId() )
                .toUri();
        MaintenanceDroidDto createdMaintenanceDroidDto = maintenanceDroidDtoMapper.mapToDto( maintenanceDroid );

        return ResponseEntity
                .created(returnURI)
                .body( createdMaintenanceDroidDto );
    }


    //3. Get a specific maintenance droid by ID
    ///maintenanceDroids/{maintenanceDroid-id}
    //GET
    @GetMapping("/maintenanceDroids/{maintenanceDroid-id}")
    public ResponseEntity<MaintenanceDroidDto> getOneMaintenanceDroid(@PathVariable("maintenanceDroid-id") UUID id) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.MDfindById( id );
        MaintenanceDroidDto createdMaintenanceDroidDto = maintenanceDroidDtoMapper.mapToDto( maintenanceDroid );
        return new ResponseEntity( createdMaintenanceDroidDto, OK );
    }

    //4. Delete a specific maintenance droid
    ///maintenanceDroids/{maintenanceDroid-id}
    //DELETE
    @DeleteMapping("/maintenanceDroids/{maintenanceDroid-id}")
    public ResponseEntity<?> deleteOneMaintenanceDroid( @PathVariable("maintenanceDroid-id") UUID id ) {
        maintenanceDroidService.deleteById( id );
        return new ResponseEntity( NO_CONTENT );
    }

    //5. Change the name of a specific maintenance droid
    ///maintenanceDroids/{maintenanceDroid-id}
    //PATCH
    @PatchMapping("/maintenanceDroids/{maintenanceDroid-id}")
    public ResponseEntity<?> changeMaintenanceDroidsName (@RequestBody MaintenanceDroid maintenanceDroidDto, @PathVariable("maintenanceDroid-id") UUID id){
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.MDfindById(id);
        maintenanceDroid.setName(maintenanceDroidDto.getName());
        MaintenanceDroidDto createdMaintenanceDroidDto = maintenanceDroidDtoMapper.mapToDto(maintenanceDroid);
        return new ResponseEntity(createdMaintenanceDroidDto, OK );
    }

    //6. Give a specific maintenance droid a task
    ///maintenanceDroids/{maintenanceDroid-id}/tasks
    //POST
    @PostMapping("/maintenanceDroids/{maintenanceDroid-id}/tasks")
    public ResponseEntity<TaskDto> giveMaintenanceDroidTask(@RequestBody Task task, @PathVariable("maintenanceDroid-id") UUID id)  {
        maintenanceDroidService.executeCommand(id, task);

        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{maintenanceDroid-id}")
                .buildAndExpand( task )
                .toUri();
        TaskDto createdTaskDto = taskDtoMapper.mapToDto( task );

        return ResponseEntity
                .created(returnURI)
                .body( createdTaskDto );
    }

    // 7. List all the tasks a specific maintenance droid has received so far
    ///maintenanceDroids/{maintenanceDroid-id}/tasks
    //GET
    @GetMapping("/maintenanceDroids/{maintenanceDroid-id}/tasks")
    public Iterable<Task> getAllMaintenanceDroidTasks(@PathVariable("maintenanceDroid-id") UUID id) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.MDfindById( id );
        Iterable<Task> allMaintenanceDroidsTasks = maintenanceDroid.getTasks();
        return allMaintenanceDroidsTasks;
    }

    // 8. Delete the task history of a specific maintenance droid
    ///maintenanceDroids/{maintenanceDroid-id}/tasks
    //DELETE
    @DeleteMapping("/maintenanceDroids/{maintenanceDroid-id}/tasks")
    public ResponseEntity<?> deleteOneMaintenanceDroidsTasks( @PathVariable("maintenanceDroid-id") UUID id ) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.MDfindById( id );
        maintenanceDroid.setTasks(new ArrayList<>());
        return new ResponseEntity( NO_CONTENT );
    }






}
