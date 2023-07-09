package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
public class MiningMachineController {

    private final MiningMachineService miningMachineService;

    @Autowired
    public MiningMachineController(MiningMachineService miningMachineService) {
        this.miningMachineService = miningMachineService;
    }

    @GetMapping("/miningMachines")
    public ResponseEntity<Iterable<MiningMachine>> getAllMiningMachines(){
        return new ResponseEntity<Iterable<MiningMachine>>(miningMachineService.getALlMiningMachine(), HttpStatus.OK);
    }

    @GetMapping("/miningMachines/{id}/commands")
    public ResponseEntity<Iterable<Command>> getAllMiningMachineCommands(@PathVariable UUID id){
        val miningMachine = miningMachineService.getMiningMachine(id);
        if(miningMachine == null){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }else {
            return new ResponseEntity<Iterable<Command>>(miningMachineService.getMiningMachine(id).getCommands(), HttpStatus.OK);
        }
    }

    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> getMiningMachine(@PathVariable UUID id) {
        val miningMachine = miningMachineService.getMiningMachine(id);
        if( miningMachine == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            return new ResponseEntity<>(miningMachine, HttpStatus.OK );
        }
    }

    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> reNameMiningMachine(@RequestBody Map<String, Object> updates, @PathVariable("id") UUID id)  {
        Optional<MiningMachine> found = miningMachineService.getMiningMachineRepository().findById( id );
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            var mi = found.get();
            if(updates.containsKey("name"))
                mi.setName(updates.get("name").toString());
            return new ResponseEntity<MiningMachine>(mi, HttpStatus.OK );
        }
    }

    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity deleteMiningMachine(@PathVariable UUID id){
        if( !miningMachineService.deleteMiningMachine(id))
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else
            return new ResponseEntity( HttpStatus.NO_CONTENT );
    }

    @PostMapping("/miningMachines")
    public ResponseEntity deleteMiningMachine( @RequestBody MiningMachine miningMachine){
        val uuid = miningMachineService.addMiningMachine(miningMachine.getName());
        if( uuid == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            return new ResponseEntity<>(miningMachineService.getMiningMachine(uuid), HttpStatus.CREATED );
        }
    }

    @PostMapping("/miningMachines/{id}/commands")
    public ResponseEntity executeCommandMiningMachine(@PathVariable UUID id, @RequestBody Command command){
        val returnValue = miningMachineService.executeCommand(id, command);
        if( !returnValue )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            val miningMachine = miningMachineService.getMiningMachine(id);
            return new ResponseEntity<>(miningMachine.getCommands().get(miningMachine.getCommands().size()-1), HttpStatus.CREATED );
        }
    }

    @DeleteMapping("/miningMachines/{id}/commands")
    public ResponseEntity deleteMiningMachineCommands(@PathVariable UUID id){
        val returnValue = miningMachineService.getMiningMachine(id);
        if( returnValue == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else{
            returnValue.getCommands().clear();
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }

    }
}
