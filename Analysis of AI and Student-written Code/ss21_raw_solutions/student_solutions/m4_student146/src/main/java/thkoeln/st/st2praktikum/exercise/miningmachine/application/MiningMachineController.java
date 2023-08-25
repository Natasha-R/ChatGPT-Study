package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class MiningMachineController {
    private final MiningMachineService service;

    public MiningMachineController(MiningMachineService service) {
        this.service = service;
    }

    @RequestMapping(name = "GET", value = "/miningMachines")
    public @ResponseBody
    Iterable<MiningMachine> getAllMiningMachines() {
        return service.miningMachineRepository.findAll();
    }

    @RequestMapping(name = "GET", value = "/miningMachines/{miningMachine-id}")
    public @ResponseBody
    MiningMachine getMiningMachineByName(@PathVariable("miningMachine-id") String id) {
        return service.miningMachineRepository.findById(UUID.fromString(id)).get();
    }

    @PostMapping("/miningMachines")
    public ResponseEntity<?> createNewMiningMachine(@RequestBody MiningMachine miningMachine) {
        try {
            MiningMachine miningMachine1 = new MiningMachine(UUID.randomUUID(), miningMachine.getName());
            service.miningMachineRepository.save(miningMachine1);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand(miningMachine.getName())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(miningMachine);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/miningMachines/{miningMachine-id}")
    public ResponseEntity<?> deleteMiningMachine(@PathVariable("miningMachine-id") String id) {
        try {
            MiningMachine miningMachine = service.miningMachineRepository.findById(UUID.fromString(id)).get();
            service.miningMachineRepository.delete(miningMachine);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PatchMapping("/miningMachines/{miningMachine-id}")
    public ResponseEntity<?> renameMiningMachine(@PathVariable("miningMachine-id") String id, @RequestBody MiningMachine miningMachine) {
        try {
            MiningMachine oldMachine = service.miningMachineRepository.findById(UUID.fromString(id)).get();
            oldMachine.setName(miningMachine.getName());
            service.miningMachineRepository.save(oldMachine);
            return ResponseEntity
                    .ok(oldMachine);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/miningMachines/{miningMachine-id}/orders")
    public ResponseEntity<?> giveOrdersToMachine(@RequestBody Order order, @PathVariable("miningMachine-id") String id) {
        try {
            MiningMachine machine = service.miningMachineRepository.findById(UUID.fromString(id)).get();
            service.executeCommand(machine.getId(), order);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .buildAndExpand()
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(order);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @RequestMapping(name = "GET", value = "/miningMachines/{miningMachine-id}/orders")
    public @ResponseBody
    List<Order> getAllOrders(@PathVariable("miningMachine-id") String id) {
        return service.miningMachineRepository.findById(UUID.fromString(id)).get().getOrders();
    }

    @DeleteMapping("/miningMachines/{miningMachine-id}/orders")
    public ResponseEntity<?> deleteAllOrders(@PathVariable("miningMachine-id") String id) {
        try {
            MiningMachine machine = service.miningMachineRepository.findById(UUID.fromString(id)).get();
            if(!machine.getOrders().isEmpty()) {
                machine.getOrders().clear();
                service.miningMachineRepository.save(machine);
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
