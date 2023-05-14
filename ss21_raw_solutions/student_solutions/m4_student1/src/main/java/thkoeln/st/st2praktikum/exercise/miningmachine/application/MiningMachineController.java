package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
public class MiningMachineController {

    private final MiningMachineService miningMachineService;
    private final FieldService fieldService;
    private MiningMachineDtoMapper miningMachineDtoMapper = new MiningMachineDtoMapper();

    @Autowired
    public MiningMachineController(MiningMachineService miningMachineService, FieldService fieldService) {
        this.miningMachineService = miningMachineService;
        this.fieldService = fieldService;
    }

    @GetMapping("/miningMachines")
    List<MiningMachineDto> getAllMiningMachines() {
        Iterable<MiningMachine> allMiningMachines = miningMachineService.findAll();
        List<MiningMachineDto> allDtos = new ArrayList<>();
        for (MiningMachine monster : allMiningMachines) {
            allDtos.add(miningMachineDtoMapper.mapToDto(monster));
        }
        return allDtos;
    }

    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDto> getOneMiningMachine(@PathVariable UUID id) {
        MiningMachine miningMachine = miningMachineService.findById(id);
        MiningMachineDto createdMiningMachineDto = miningMachineDtoMapper.mapToDto(miningMachine);
        return new ResponseEntity(createdMiningMachineDto, OK);
    }

    @PostMapping("/miningMachines")
    public ResponseEntity<MiningMachineDto> createMiningMachine(@RequestBody MiningMachineDto monsterDto) {
        MiningMachine miningMachine = miningMachineService.createMiningMachineFromDto(monsterDto);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(miningMachine.getUuid())
                .toUri();
        MiningMachineDto createdMiningMachineDto = miningMachineDtoMapper.mapToDto(miningMachine);
        return ResponseEntity
                .created(returnURI)
                .body(createdMiningMachineDto);
    }

    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<?> deleteMiningMachine(@PathVariable UUID id) {
        miningMachineService.deleteById(id);
        return new ResponseEntity(NO_CONTENT);
    }

    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDto> updateMiningMachine(
            @PathVariable UUID id, @RequestBody MiningMachineDto miningMachineDto) {
        MiningMachine miningMachine = miningMachineService.findById(id);
        miningMachine.setName(miningMachineDto.getName());
        MiningMachineDto newMachineDto = this.miningMachineDtoMapper.mapToDto(miningMachine);
        return new ResponseEntity<>(newMachineDto, OK);
    }

    @GetMapping("/miningMachines/{id}/commands")
    List<Command> getAllCommandsForMiningMachine(@PathVariable String id) {
        MiningMachine miningMachine = miningMachineService.findById(UUID.fromString(id));
        return miningMachine.getCommands();
    }

    @PostMapping("/miningMachines/{id}/commands")
    public ResponseEntity<MiningMachine> createCommandForMiningMachine(@PathVariable String id, @RequestBody Command command) {
        miningMachineService.executeCommand(UUID.fromString(id), command);
        return new ResponseEntity(command, CREATED);
    }

    @DeleteMapping("/miningMachines/{id}/commands")
    public ResponseEntity<?> deleteCommandsDorMachine(@PathVariable UUID id) {
        MiningMachine miningMachine = miningMachineService.findById(id);

        return new ResponseEntity<>(NO_CONTENT);
    }

}
