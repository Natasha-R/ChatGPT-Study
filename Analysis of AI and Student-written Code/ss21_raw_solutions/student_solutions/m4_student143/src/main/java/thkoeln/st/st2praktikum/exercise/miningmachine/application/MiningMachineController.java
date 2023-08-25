package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import com.jayway.jsonpath.internal.function.numeric.Min;
import org.modelmapper.ModelMapper;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.net.URI;
import java.util.*;


@RestController
public class MiningMachineController {

    MiningMachineService miningMachineService;
    MiningMachineDtoMapper miningMachineDtoMapper;

    public MiningMachineController(MiningMachineService miningMachineService){
        this.miningMachineService = miningMachineService;
        this.miningMachineDtoMapper = new MiningMachineDtoMapper();
    }


    @GetMapping ("/miningMachines")
    Iterable<MiningMachineDto> getAllMachines(){
        List<MiningMachineDto> machines = new ArrayList<>();
        for(MiningMachine m : miningMachineService.findAll()){
            machines.add(miningMachineDtoMapper.mapToDto(m));
        }
        return machines;
    }

    @PostMapping("/miningMachines")
    ResponseEntity<?> addMachine(@RequestBody MiningMachineDto miningMachineDto){
        UUID id = miningMachineService.addMiningMachine(miningMachineDto.getName());
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();

        return ResponseEntity.created(returnURI).body(miningMachineDto);
    }

    @GetMapping("/miningMachines/{id}")
    ResponseEntity<MiningMachineDto> getMiningMachineById( @PathVariable UUID id ){
        Optional<MiningMachine> machine = miningMachineService.getMiningMachineById(id);
        if(machine.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<MiningMachineDto>(
                    miningMachineDtoMapper.mapToDto(machine.get()),
                    HttpStatus.OK
            );
        }
    }

    @DeleteMapping("/miningMachines/{id}")
    ResponseEntity<MiningMachineDto> deleteMiningMachineById(@PathVariable UUID id){
        Optional<MiningMachine> m = miningMachineService.getMiningMachineById(id);
        if(m.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            miningMachineService.deleteMachine(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/miningMachines/{id}")
    ResponseEntity<?> updateMiningMachineName(@PathVariable UUID id, @RequestBody MiningMachineDto machineDto){
        try {
            Optional<MiningMachine> maybeMachine =  miningMachineService.getMiningMachineById(id);
            if(maybeMachine.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                MiningMachine miningMachine = maybeMachine.get();
                miningMachine.setName(machineDto.getName());
                return new ResponseEntity<>(miningMachineDtoMapper.mapToDto(miningMachine),HttpStatus.OK);
            }
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/miningMachines/{id}/commands")
    ResponseEntity<?> giveMiningMachineCommand(@PathVariable UUID id, @RequestBody Command command ){
        try {
            miningMachineService.executeCommand(id,command);
            return new ResponseEntity<>(command,HttpStatus.CREATED);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(command, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/miningMachines/{id}/commands")
    ResponseEntity<?> listAllCommandsOfMiningMachine(@PathVariable UUID id){
        Optional<MiningMachine> maybeMachine =
                miningMachineService.getMiningMachineById(id);
        if(maybeMachine.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            MiningMachine machine = maybeMachine.get();
            System.out.println(machine.getCommands());
            return new ResponseEntity<>(machine.getCommands(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/miningMachines/{id}/commands")
    ResponseEntity<?> deleteAllCommandsOfMiningMachine(@PathVariable UUID id){
        Optional<MiningMachine> maybeMachine = miningMachineService.getMiningMachineById(id);
        if(maybeMachine.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            MiningMachine machine = maybeMachine.get();
            machine.clearCommands();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }



}
