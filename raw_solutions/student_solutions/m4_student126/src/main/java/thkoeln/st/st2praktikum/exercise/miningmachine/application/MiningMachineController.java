package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandDto;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineDto;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/miningMachines")
public class MiningMachineController {

    private final MiningMachineService miningMachineService;
    private final MiningMachineRepository miningMachineRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public MiningMachineController(MiningMachineService miningMachineService,
                                   MiningMachineRepository miningMachineRepository) {
        this.miningMachineService = miningMachineService;
        this.miningMachineRepository = miningMachineRepository;
    }

    @GetMapping
    public Iterable<MiningMachineDto> getAllMiningMachines() {
        Iterable<MiningMachine> miningMachines = miningMachineRepository.findAll();
        List<MiningMachineDto> dtos = new ArrayList<>();
        for(MiningMachine mm : miningMachines){
            dtos.add(modelMapper.map(mm, MiningMachineDto.class));
        }
        return dtos;
    }

    @PostMapping
    public ResponseEntity<?> createMiningmachine(@RequestBody MiningMachineDto miningMachineDto) {
        try {
            MiningMachine miningMachine = new MiningMachine();
            modelMapper.map(miningMachineDto, miningMachine);
            miningMachineRepository.save(miningMachine);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(miningMachine.getId())
                    .toUri();
            return ResponseEntity.created(returnURI).body(miningMachineDto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @GetMapping("/{miningMachineId}")
    public ResponseEntity<MiningMachineDto> getMiningMachineById(@PathVariable UUID miningMachineId) {
        Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(modelMapper.map(found.get(), MiningMachineDto.class), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{miningMachineId}")
    public ResponseEntity<?> deleteMiningMachineById(@PathVariable UUID miningMachineId) {
        Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            miningMachineRepository.delete(found.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/{miningMachineId}")
    public ResponseEntity<MiningMachineDto> renameMiningMachine(@PathVariable UUID miningMachineId, @RequestBody MiningMachineDto miningMachineDto) {
        Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            MiningMachine miningMachine = found.get();
            miningMachine.setName(miningMachineDto.getName());
            miningMachineRepository.save(miningMachine);
            return new ResponseEntity<>(modelMapper.map(miningMachine, MiningMachineDto.class), HttpStatus.OK);
        }
    }

    @PostMapping("/{miningMachineId}/commands")
    public ResponseEntity<Command> executeCommand(@PathVariable UUID miningMachineId, @RequestBody Command command) {
        Optional<MiningMachine> found = miningMachineRepository.findById(miningMachineId);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            miningMachineService.executeCommand(miningMachineId, command);
            return new ResponseEntity<>(command, HttpStatus.CREATED);
        }
    }

    @GetMapping("/{miningMachineId}/commands")
    public Iterable<CommandDto> getAllCommands(@PathVariable UUID miningMachineId) {
        MiningMachine miningMachine = miningMachineRepository.findById(miningMachineId).orElseThrow();
        Iterable<Command> commands = miningMachine.getCommands();
        List<CommandDto> commandDtos = new ArrayList<>();
        for (Command c : commands) {
            commandDtos.add(modelMapper.map(c, CommandDto.class));
        }
        return commandDtos;
    }

    @DeleteMapping("/{miningMachineId}/commands")
    public ResponseEntity<?> deleteAllCommands(@PathVariable UUID miningMachineId) {
        MiningMachine miningMachine = miningMachineRepository.findById(miningMachineId).orElseThrow();
        miningMachine.deleteCommandHistory();
        miningMachineRepository.save(miningMachine);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
