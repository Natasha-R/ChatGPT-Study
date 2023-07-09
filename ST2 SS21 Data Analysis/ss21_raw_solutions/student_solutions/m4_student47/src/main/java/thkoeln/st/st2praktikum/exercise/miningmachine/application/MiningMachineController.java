package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.Repositories.FieldRepository;
import thkoeln.st.st2praktikum.exercise.Repositories.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.Repositories.TransportSystemRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.util.*;

@RestController
public class MiningMachineController {

    MachineCommander commander;
    private final MiningMachineRepository miningMachineRepository;
    private final FieldRepository fieldRepository;
    private final TransportSystemRepository transportSystemRepository;
    MiningMachineService service;

    MiningMachineController(MiningMachineRepository miningMachineRepository,FieldRepository fieldRepository,TransportSystemRepository transportSystemRepository){
        this.miningMachineRepository = miningMachineRepository;
        this.fieldRepository = fieldRepository;
        this.transportSystemRepository = transportSystemRepository;
        this.commander = new MachineCommander(miningMachineRepository,fieldRepository,transportSystemRepository);
    }

    @GetMapping("/miningMachines")
    private List<MiningMachine> all(){
        return (List<MiningMachine>) miningMachineRepository.findAll();
    }


    @PostMapping("/miningMachines")
    @ResponseStatus(value = HttpStatus.CREATED)
    Object machine(@RequestBody MiningMachine machine){
        return miningMachineRepository.save(new MiningMachine(machine.getName()));
    }

    @GetMapping("/miningMachines/{id}")
    MiningMachine one (@PathVariable UUID id){
         return miningMachineRepository.findById(id).orElseThrow(() -> new RuntimeException("machine not found"));
    }

    @DeleteMapping("/miningMachines/{id}")
    Object deleteMachine(@PathVariable UUID id){
        miningMachineRepository.deleteMiningMachineByMiningMachineId(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/miningMachines/{id}")
    private MiningMachine machine(@PathVariable UUID id, @RequestBody MiningMachine machine){
        MiningMachine eMachine = miningMachineRepository.findById(id).orElseThrow(() -> new RuntimeException("machine not found"));
        eMachine.setName(machine.getName());
        return miningMachineRepository.save(eMachine);
    }



    @PostMapping("/miningMachines/{id}/commands")
    @ResponseStatus(value = HttpStatus.CREATED)
    Command postCommand(@RequestBody Command command, @PathVariable UUID id ){
        MiningMachine eMachine = miningMachineRepository.findById(id).orElseThrow(() -> new RuntimeException("machine not found"));
        List <Command> commands = eMachine.getCommands();
        commands.add(command);
        commander.executeCommand(eMachine.getMiningMachineId(),command);
        miningMachineRepository.save(eMachine);
        return command;
    }



    //not working
    @GetMapping("/miningMachines/{id}/commands")
    private List<Command> getCommands (@PathVariable UUID id){
         MiningMachine machine = miningMachineRepository.findById(id).orElseThrow(() -> new RuntimeException("machine not found"));
         return machine.getCommands();
    }


    @DeleteMapping("/miningMachines/{id}/commands")
    Object deleteCommands(@PathVariable UUID id){
        MiningMachine eMachine = miningMachineRepository.findById(id).orElseThrow(() -> new RuntimeException("machine not found"));
        eMachine.getCommands().clear();
        miningMachineRepository.save(eMachine);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
