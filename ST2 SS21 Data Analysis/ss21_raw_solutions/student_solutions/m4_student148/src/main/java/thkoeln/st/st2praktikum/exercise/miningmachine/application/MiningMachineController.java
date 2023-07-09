package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class MiningMachineController {
    private final MiningMachineRepository miningMachineRepository;
    private final MiningMachineService miningMachineService;

    private MiningMachineController(MiningMachineRepository miningMachineRepository,
                                    MiningMachineService miningMachineService) {
        this.miningMachineRepository = miningMachineRepository;
        this.miningMachineService = miningMachineService;
    }

    //1. Get all mining machines ✔
    @GetMapping("/miningMachines")
    public Iterable<MiningMachine> getAllMiningMachines() {
        return miningMachineRepository.findAll();
    }

    //2. Create a new mining machine ✔
    @PostMapping("/miningMachines")
    public ResponseEntity<MiningMachine> postMiningMachine(@RequestBody MiningMachine miningMachine) {
        ModelMapper modelMapper = new ModelMapper();
        return new ResponseEntity<>(modelMapper.map(miningMachineRepository.save(miningMachine),
                MiningMachine.class), HttpStatus.CREATED);
    }

    //3. Get a specific mining machine by ID ✔
    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> getMiningMachine(@PathVariable UUID id) {
        Optional<MiningMachine> found = miningMachineRepository.findById(id);
        if (found.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            ModelMapper modelMapper = new ModelMapper();
            return new ResponseEntity<>(modelMapper.map(found.get(), MiningMachine.class), HttpStatus.OK);
        }
    }

    // 4. Delete a specific mining machine ✔
    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> deleteMiningMachine(@PathVariable UUID id) {
        Optional<MiningMachine> found = miningMachineRepository.findById(id);
        if (found.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            miningMachineRepository.delete(found.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    // 5. Change the name of a specific mining machine ✔
    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<?> patchMiningMachine(@PathVariable UUID id, @RequestBody String newName)
            throws JsonProcessingException {
        Optional<MiningMachine> found = miningMachineRepository.findById(id);
        if (found.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            ObjectMapper objectMapper = new ObjectMapper();
            MiningMachine newMachine = objectMapper.readValue(newName, MiningMachine.class);
            MiningMachine ms = found.get();
            ms.setName(newMachine.getName());
            ModelMapper modelMapper = new ModelMapper();
            return new ResponseEntity<>(modelMapper.map(miningMachineRepository.save(ms), MiningMachine.class),
                    HttpStatus.OK);
        }
    }

    // 6. Give a specific mining machine a command ✔
    @PostMapping("/miningMachines/{id}/commands")
    public ResponseEntity<?> postMiningMachineCommand(@PathVariable UUID id, @RequestBody Command command) {
        Optional<MiningMachine> found = miningMachineRepository.findById(id);
        if (found.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            miningMachineService.executeCommand(id, command);
            ModelMapper modelMapper = new ModelMapper();
            return new ResponseEntity<>(modelMapper.map(command, Command.class), HttpStatus.CREATED);
        }
    }

    // 7. List all the commands a specific mining machine has received so far ✔
    @GetMapping("/miningMachines/{id}/commands")
    public ResponseEntity<?> getMiningMachineCommands(@PathVariable UUID id) {
        Optional<MiningMachine> found = miningMachineRepository.findById(id);
        if (found.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<>(found.get().getCommands(), HttpStatus.OK);
        }
    }


    // 8. Delete the command history of a specific mining machine ✔
    @DeleteMapping("/miningMachines/{id}/commands")
    public ResponseEntity<MiningMachine> deleteMiningMachineCommands(@PathVariable UUID id) {
        Optional<MiningMachine> found = miningMachineRepository.findById(id);
        if (found.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            MiningMachine miningMachine = found.get();
            miningMachine.getCommands().clear();
            miningMachineRepository.save(miningMachine);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
