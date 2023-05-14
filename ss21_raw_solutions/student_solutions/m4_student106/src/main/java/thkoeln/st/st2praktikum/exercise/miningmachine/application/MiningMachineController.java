package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.util.UUID;

@RestController
public class MiningMachineController {
    @Autowired
    MiningMachineService service;

    @GetMapping(value = "/miningMachines")
    public Iterable<MiningMachine> getAllMiningMachines() {
        return service.findAll();
    }

    @PostMapping(value = "/miningMachines")
    public ResponseEntity<MiningMachine> createNewMiningMachine(@RequestBody MiningMachine miningMachine ) {
        UUID id = service.addMiningMachine(miningMachine);
        return new ResponseEntity(service.findById(id), HttpStatus.CREATED);
    }

    @GetMapping(value = "/miningMachines/{id}")
    public MiningMachine getMiningMachine(@PathVariable UUID id) {
        return service.findById(id);
    }

    @DeleteMapping(value = "/miningMachines/{id}")
    public ResponseEntity deleteMinigMachine(@PathVariable UUID id) {
        service.deleteById(id);
        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }

    @PatchMapping(value = "/miningMachines/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public MiningMachine patchMiningMachine(@PathVariable UUID id, @RequestBody MiningMachine miningMachine) {
        service.changeName(miningMachine);
        return miningMachine;
    }

    @PostMapping(value = "/miningMachines/{id}/tasks")
    public ResponseEntity<Task> giveMiningMachineATask(@PathVariable UUID id, @RequestBody Task task) {
        service.executeCommand(id, task);
        return new ResponseEntity(task, HttpStatus.CREATED);
    }

    @GetMapping(value = "/miningMachines/{id}/tasks")
    public Iterable<Task> getTaskHistoryOfMiningMachine(@PathVariable UUID id) {
        return service.getTaskHistory(id);
    }

    @DeleteMapping(value = "/miningMachines/{id}/tasks")
    public ResponseEntity deleteTaskHistoryOfMiningMachine(@PathVariable UUID id) {
        service.deleteTaskHistory(id);
        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }
}
