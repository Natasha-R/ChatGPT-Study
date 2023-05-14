package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.jboss.jandex.Main;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.World;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;


import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Transactional
public class MaintenanceDroidController {

    @Autowired
    private final MaintenanceDroidRepository maintenanceDroidRepository;
    @Autowired
    private final World world;
    @Autowired
    private MaintenanceDroidService maintenanceDroidService;

    @Autowired
    public MaintenanceDroidController(MaintenanceDroidRepository maintenanceDroidRepository, World world){
        this.maintenanceDroidRepository =maintenanceDroidRepository;
        this.world = world;
    }

    @GetMapping("/maintenanceDroids")
    public Iterable<MaintenanceDroid> getAllMaintenanceDroids(){
        return maintenanceDroidRepository.findAll();
    }

    @PostMapping("/maintenanceDroids")
    public ResponseEntity<?> createNewMaintenanceDroid(@RequestBody MaintenanceDroid maintenanceDroid){
        try{
            maintenanceDroidRepository.save(maintenanceDroid);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{maintenanceDroid-id}")
                    .buildAndExpand(maintenanceDroid.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(maintenanceDroid);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroid> getOneMaintenanceDroid(@PathVariable UUID id){
        return new ResponseEntity(maintenanceDroidRepository.findById(id),HttpStatus.OK);
    }

    @DeleteMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroid> deleteOneMaintenanceDroid( @PathVariable UUID id ) {

        maintenanceDroidRepository.deleteById(id);
        return new ResponseEntity( HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/maintenanceDroids/{maintenanceDroid-id}")
    public ResponseEntity<?> changeNameOfMaintenanceDroid(@RequestBody MaintenanceDroid maintenanceDroid){
        try{
            maintenanceDroidRepository.save(maintenanceDroid);
            return ResponseEntity.ok(maintenanceDroid);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/maintenanceDroids/{id}/commands")
    public ResponseEntity<?> executeCommand(@PathVariable UUID id, @RequestBody Command command){

        Optional<MaintenanceDroid> found = maintenanceDroidRepository.findById(id);
        if (found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            MaintenanceDroid maintenanceDroid = found.get();
            maintenanceDroid.addCommand(command);
            maintenanceDroidService.executeCommand(id,command);
            new ModelMapper().map(maintenanceDroid, MaintenanceDroid.class);
            maintenanceDroidRepository.save(maintenanceDroid);

            return new ResponseEntity<Command>(maintenanceDroid.getCommands().get(maintenanceDroid.getCommands().size()-1), HttpStatus.CREATED);
        }

    }

    @GetMapping("/maintenanceDroids/{id}/commands")
    public Iterable<Command> getAllCommandsOfMaintenanceDroid(@PathVariable UUID id){
        return maintenanceDroidRepository.findById(id).get().getCommands();
    }

    @DeleteMapping("/maintenanceDroids/{id}/commands")
    public ResponseEntity<MaintenanceDroid> deleteCommandHistoryOfMaintenanceDroid(@PathVariable UUID id) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findMaintenanceDroidById(id);
        maintenanceDroid.deleteCommands();
        maintenanceDroidRepository.save(maintenanceDroid);
        return new ResponseEntity( HttpStatus.NO_CONTENT);
    }

}
