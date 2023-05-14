package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineDTO;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class MiningMachineController {
    @Autowired
    private MiningMachineService miningMachineService;
    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/miningMachines")
    @ResponseBody
    public Iterable<MiningMachineDTO> getAllMiningMachines(){
        Iterable<MiningMachine> found = miningMachineService.getMiningMachineRepository().findAll();
        List foundDTOs = new ArrayList<MiningMachineDTO>();
        for(MiningMachine miningMachine : found){
            foundDTOs.add(modelMapper.map(miningMachine,MiningMachineDTO.class));
        }
        return foundDTOs;
    }

    @PostMapping("/miningMachines")
    public ResponseEntity<?> createNewMiningMachine(@RequestBody MiningMachineDTO miningMachineDTO){
        try{
            MiningMachine newMiningMachine = new MiningMachine();
            modelMapper.map(miningMachineDTO,newMiningMachine);
            miningMachineService.getMiningMachineRepository().save(newMiningMachine);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newMiningMachine.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(miningMachineDTO);
        }
        catch ( Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDTO> getOneMiningMachine(@PathVariable UUID id){
        Optional<MiningMachine> found = miningMachineService.getMiningMachineRepository().findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<MiningMachineDTO>(
                    modelMapper.map(found.get(),MiningMachineDTO.class),HttpStatus.OK );
        }
    }

    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDTO> deleteOneMiningMachine(@PathVariable UUID id){
        Optional<MiningMachine> found = miningMachineService.getMiningMachineRepository().findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            miningMachineService.getMiningMachineRepository().delete(found.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<?> changeOneMiningMachine(@PathVariable UUID id, @RequestBody MiningMachineDTO miningMachineDTO){
        try{
            Optional<MiningMachine> oldMiningMachine = miningMachineService.getMiningMachineRepository().findById(id);
            if(oldMiningMachine.isPresent()){
                MiningMachine newMiningMachine = new MiningMachine();
                modelMapper.map(miningMachineDTO, newMiningMachine);
                miningMachineService.getMiningMachineRepository().delete(oldMiningMachine.get());
                miningMachineService.getMiningMachineRepository().save(newMiningMachine);
                URI returnURI = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(newMiningMachine.getId())
                        .toUri();
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(miningMachineDTO);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch ( Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/miningMachines/{id}/tasks")
    public ResponseEntity<?> giveMiningMachinesTask(@PathVariable UUID id, @RequestBody Task task){
        Optional<MiningMachine> found = miningMachineService.getMiningMachineRepository().findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            miningMachineService.executeCommand(found.get().getId(), task);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/tasks")
                    .buildAndExpand(task)
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(task);
        }
    }

    @GetMapping("/miningMachines/{id}/tasks")
    @ResponseBody
    public Iterable<Task> getAllMiningMachineTasks(@PathVariable UUID id){
        List<Task> found = new ArrayList<>();
        if(miningMachineService.getMiningMachineRepository().findById(id).isPresent()){
            found = miningMachineService.getMiningMachineRepository().findById(id).get().getTasks();
        }
        return found;
    }

    @DeleteMapping("/miningMachines/{id}/tasks")
    public ResponseEntity<Task> deleteAllMiningMachineTasks(@PathVariable UUID id){
        Optional<MiningMachine> miningMachine = miningMachineService.getMiningMachineRepository().findById(id);
        if(miningMachine.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            miningMachineService.getMiningMachineRepository().findById(id).get().deleteAllTasks();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
