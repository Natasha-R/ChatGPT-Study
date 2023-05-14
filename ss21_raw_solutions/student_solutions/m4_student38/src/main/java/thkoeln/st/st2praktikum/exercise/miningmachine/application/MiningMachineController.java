package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineDto;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineDtoMapper;

import java.util.List;
import java.util.UUID;


@RestController
public class MiningMachineController {
    private final MiningMachineService miningMachineService;
    private MiningMachineDtoMapper miningMachineMapper = new MiningMachineDtoMapper();

    @Autowired
    public MiningMachineController(MiningMachineService miningMachineService) {
        this.miningMachineService = miningMachineService;
    }

    @GetMapping("/miningMachines")
    public ResponseEntity<List<MiningMachine>> getAllMiningMachines(){
        Iterable<MiningMachine> miningMachines = miningMachineService.getMiningMachines();
        return new ResponseEntity(miningMachines,HttpStatus.OK);
    }

    @PostMapping("/miningMachines")
    public ResponseEntity<MiningMachineDto> createMiningMachine(@RequestBody MiningMachine miningMachine){
        MiningMachine createdMiningMachine = miningMachineService.createMiningMachine(miningMachine);
        return new ResponseEntity(createdMiningMachine,HttpStatus.CREATED);
    }

    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> getMiningMachineById(@PathVariable UUID id){
        MiningMachine miningMachine = miningMachineService.getMiningMachineById(id);
        return new ResponseEntity(miningMachine, HttpStatus.OK);
    }

    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<?> deleteOneMiningMachine(@PathVariable UUID id){
        if (miningMachineService.deleteById(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity changeNameOfMiningMachine(@PathVariable UUID id, @RequestBody String name){
        miningMachineService.changeName(id,name);
        return new ResponseEntity(name,HttpStatus.OK);
    }

    @PostMapping("/miningMachines/{id}/commands")
    public ResponseEntity<Command> giveCommandToMiningMachine(@PathVariable UUID id, @RequestBody Command command){
        miningMachineService.giveCommand(id,command);
        return new ResponseEntity(command,HttpStatus.CREATED);
    }

    @GetMapping("/miningMachines/{id}/commands")
    public ResponseEntity<List<Command>> listCommands(@PathVariable UUID id){
        return new ResponseEntity(miningMachineService.getCommandList(id),HttpStatus.OK);
    }

    @DeleteMapping("/miningMachines/{id}/commands")
    public ResponseEntity deleteCommands(@PathVariable UUID id){
        miningMachineService.deleteCommands(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
