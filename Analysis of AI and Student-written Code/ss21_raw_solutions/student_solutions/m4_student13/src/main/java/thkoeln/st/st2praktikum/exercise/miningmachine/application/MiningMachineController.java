package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.net.URI;
import java.util.*;

@RestController
@RepositoryRestController
public class MiningMachineController {

    private MiningMachineService miningMachineService;
    private MiningMachineRepository miningMachineRepository;

    @Autowired
    public MiningMachineController(MiningMachineService miningMachineService,
                                   MiningMachineRepository miningMachineRepository) {
        this.miningMachineService = miningMachineService;
        this.miningMachineRepository = miningMachineRepository;
    }

    //1. Get all mining machines

    @GetMapping("/miningMachines")
    public ResponseEntity<Iterable<MiningMachine>> getAllMiningMachines() {
        Iterable<MiningMachine> miningMachines = miningMachineService.getAllMiningMachines();
        return new ResponseEntity<> (miningMachines,HttpStatus.OK);
    }

    //2. Create a new mining machine
    @PostMapping("/miningMachines")
    public ResponseEntity<?> createNewMiningMachine(@RequestBody MiningMachine miningMachine) {
        try {
            MiningMachine newMiningMachine = new MiningMachine(miningMachine.getName());
            miningMachineRepository.save(newMiningMachine);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{miningMachine-id}")
                    .buildAndExpand(newMiningMachine.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(miningMachine);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //3. Get a specific mining machine by ID
    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> getOneSpecificMiningMachine(@PathVariable UUID id) {
        Optional<MiningMachine> found = miningMachineRepository.findById(id);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<MiningMachine>(
                    found.get(), HttpStatus.OK);
            //modelMapper.map(found.get(), MiningMachine.class), HttpStatus.OK);
        }
    }

    //4. Delete a specific mining machine
    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> deleteOneMiningMachine(@PathVariable UUID id) {
        Optional<MiningMachine> found = miningMachineRepository.findById(id);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            miningMachineRepository.delete(found.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    //5. Change the name of a specific mining machine
    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<?> changeNameOfMiningMachine(@PathVariable UUID id, @RequestBody
            MiningMachine miningMachine) {
        try {
            Optional<MiningMachine> found = miningMachineRepository.findById(id);
            if (found.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                found.get().setName(miningMachine.getName());
                return new ResponseEntity<MiningMachine>(found.get(),HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


    // 6. Give a specific mining machine a task

    @PostMapping("/miningMachines/{id}/tasks")
    public ResponseEntity<?> giveMiningMachineTask(@PathVariable UUID id,
                                                   @RequestBody Task task) {
        try {
            miningMachineService.executeCommand(id,task);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/")
                    .build()
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(task);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //7. List all the tasks a specific mining machine has received so far

    @GetMapping("/miningMachines/{id}/tasks")
    public Iterable<Task> miningMachineTasks(@PathVariable UUID id) {
        Optional<MiningMachine> found = miningMachineRepository.findById(id);
        if (found.isEmpty())
            return null;
        else {
            return found.get().getHistoryOfTasks();
        }
    }

    //8. Delete the task history of a specific mining machine

    @DeleteMapping("/miningMachines/{id}/tasks")
    public ResponseEntity<MiningMachine> deleteTaskHistoryOfMiningMachine(@PathVariable UUID id) {
        Optional<MiningMachine> found = miningMachineRepository.findById(id);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            found.get().cleanHistoryTasks();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
