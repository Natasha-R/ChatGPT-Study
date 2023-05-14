package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Transactional
public class MiningMachineController {

    private final MiningMachineApplicationService miningMachineApplicationService;
    private MiningMachineDtoMapper miningMachineMapper = new MiningMachineDtoMapper();
    private TaskDtoMapper taskMapper = new TaskDtoMapper();

    @Autowired
    public MiningMachineController(MiningMachineApplicationService miningMachineApplicationService){
        this.miningMachineApplicationService = miningMachineApplicationService;
    }

    @GetMapping("/miningMachines")
    public Iterable<MiningMachineDto> getAllMiningMachines(){
        Iterable<MiningMachine> found = miningMachineApplicationService.findAll();
        List foundDtos = new ArrayList<MiningMachineDto>();

        for(MiningMachine mm: found){
            foundDtos.add(miningMachineMapper.mapToDto(mm));
        }
        return foundDtos;
    }

    @PostMapping("/miningMachines")
    public ResponseEntity<MiningMachineDto> createNewMiningMachine(@RequestBody MiningMachineDto miningMachineDto){
            MiningMachine newMiningMachine = miningMachineMapper.mapToEntity(miningMachineDto);
            newMiningMachine.setId(UUID.randomUUID());
            miningMachineApplicationService.save(newMiningMachine);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newMiningMachine.getId())
                    .toUri();
            MiningMachineDto miningMachineDto1 = miningMachineMapper.mapToDto(newMiningMachine);
            return ResponseEntity
                    .created(returnURI)
                    .body(miningMachineDto1);
    }
    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDto> getMiningMachine(@PathVariable UUID id){
        MiningMachine found = miningMachineApplicationService.findById(id);
        return new ResponseEntity<MiningMachineDto>(
                miningMachineMapper.mapToDto(found), HttpStatus.OK);
    }

    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDto> deleteMiningMachine(@PathVariable UUID id){
        MiningMachine found = miningMachineApplicationService.findById(id);
            miningMachineApplicationService.delete(found);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/miningMachines/{id}/tasks")
    public Iterable<TaskDto> getAllTaks(@PathVariable UUID id){
        MiningMachine miningMachine = miningMachineApplicationService.findById(id);
        Iterable<Task> found = miningMachine.getTasks();
        List foundDtos = new ArrayList<TaskDto>();

        for(Task t: found){
            foundDtos.add(taskMapper.mapToDto(t));
        }
        return foundDtos;
    }

    @PostMapping("/miningMachines/{id}/tasks")
    public ResponseEntity<TaskDto> giveMMaTask(@PathVariable UUID id, @RequestBody Task task){
        MiningMachine miningMachine = miningMachineApplicationService.findById(id);
        miningMachineApplicationService.executeCommand(id,task);
        TaskDto createdDto = taskMapper.mapToDto(task);
        return new ResponseEntity<TaskDto>(createdDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/miningMachines/{id}/tasks")
    public ResponseEntity<?>deleteTasksFromMM(@PathVariable UUID id){
        MiningMachine miningMachine = miningMachineApplicationService.findById(id);
        miningMachineApplicationService.deleteTasks(miningMachine);
        miningMachineApplicationService.save(miningMachine);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDto> changeNameOfMM(@PathVariable UUID id, @RequestBody MiningMachineDto miningMachineDto){
        MiningMachine miningMachine = miningMachineApplicationService.findById(id);
        miningMachineApplicationService.changeName(miningMachineDto ,miningMachine, miningMachineDto.getName());
        MiningMachineDto createdDto = miningMachineMapper.mapToDto(miningMachine);
        return new ResponseEntity<>(createdDto, HttpStatus.OK);
    }



    /*
    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> changeNameOfMiningMachine(@PathVariable UUID id){
        Optional<MiningMachine> found = miningMachineApplicationService.findById(id);
        if(found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else{
            miningMachineApplicationService.changeName(found.get(),"Fred");
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/miningMachines/{id}/tasks")
    public Iterable<Task> getAllMiningMachineTasks(@PathVariable UUID id){
        Iterable<Task> found = miningMachineApplicationService.findAllTasksByMiningMachine(id);
        return found;
    }

*/
}
