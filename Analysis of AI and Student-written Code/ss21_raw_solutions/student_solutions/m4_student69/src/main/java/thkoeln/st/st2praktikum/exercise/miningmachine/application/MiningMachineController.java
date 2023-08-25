package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RepositoryRestController
public class MiningMachineController {
    public MiningMachineService miningMachineService;

    public MiningMachineController(MiningMachineService miningMachineService) {
        this.miningMachineService = miningMachineService;
    }

    @GetMapping("/miningMachines")
    public @ResponseBody
    Iterable<MiningMachine> all() {
        return miningMachineService.miningMachineRepo.findAll();
    }

    @GetMapping("/miningMachines/{id}")
    public @ResponseBody
    MiningMachine byName(@PathVariable("id") String id) {
        return miningMachineService.miningMachineRepo.findById(UUID.fromString(id)).get();
    }

    @PostMapping("/miningMachines")
    public ResponseEntity<?> create(@RequestBody MiningMachine miningMachine) {
        MiningMachine miningMachine1 = new MiningMachine(UUID.randomUUID(), miningMachine.getName(), new Coordinate(0, 0), null, new ArrayList<>());
        miningMachineService.miningMachineRepo.save(miningMachine1);
        URI returnURI = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(miningMachine.getName()).toUri();
        return ResponseEntity.created(returnURI).body(miningMachine);
    }

    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        MiningMachine miningMachine = miningMachineService.miningMachineRepo.findById(UUID.fromString(id)).get();
        miningMachineService.miningMachineRepo.delete(miningMachine);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<?> rename(@PathVariable("id") String id, @RequestBody MiningMachine miningMachine) {
        MiningMachine oldMachine = miningMachineService.miningMachineRepo.findById(UUID.fromString(id)).get();
        oldMachine.setName(miningMachine.getName());
        miningMachineService.miningMachineRepo.save(oldMachine);
        return ResponseEntity.ok(oldMachine);
    }

    @PostMapping("/miningMachines/{id}/tasks")
    public ResponseEntity<?> addOrder(@RequestBody Task task, @PathVariable("id") String id) {
        MiningMachine machine = miningMachineService.miningMachineRepo.findById(UUID.fromString(id)).get();
        miningMachineService.executeCommand(machine.getMiningMachine_ID(), task);
        URI returnURI = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();
        return ResponseEntity.created(returnURI).body(task);
    }

    @GetMapping("/miningMachines/{id}/tasks")
    public @ResponseBody
    Iterable<Task> allOrders(@PathVariable("id") String id) {
        return miningMachineService.miningMachineRepo.findById(UUID.fromString(id)).get().getTasks();
    }

    @DeleteMapping("/miningMachines/{id}/tasks")
    public ResponseEntity<?> deleteAllOrders(@PathVariable("id") String id) {
        MiningMachine machine = miningMachineService.miningMachineRepo.findById(UUID.fromString(id)).get();
        if (!machine.getTasks().isEmpty()) {
            machine.getTasks().clear();
            miningMachineService.miningMachineRepo.save(machine);
        }
        return ResponseEntity.noContent().build();
    }
}
