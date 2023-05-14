package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.dom4j.rule.Mode;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;

import java.net.URI;
import java.util.*;

@RepositoryRestController
@RestController
public class MaintenanceDroidController {

    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;

    @Autowired
    private MaintenanceDroidService maintenanceDroidService;

    @GetMapping("/maintenanceDroids")
    public Iterable<MaintenanceDroidDto> getAllMaintenanceDroids() {
        Iterable<MaintenanceDroid> found = maintenanceDroidRepository.findAll();
        List foundDtos = new ArrayList<MaintenanceDroidDto>();
        ModelMapper modelMapper = new ModelMapper();
        for (MaintenanceDroid md : found) {
            foundDtos.add(modelMapper.map(md, MaintenanceDroidDto.class));
        }
        return foundDtos;
    }

    @GetMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDto> getOneMaintenanceDroid( @PathVariable UUID id ) {
        Optional<MaintenanceDroid> found = maintenanceDroidRepository.findById( id );
        if ( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            ModelMapper modelMapper = new ModelMapper();
            return new ResponseEntity<MaintenanceDroidDto>(
                    modelMapper.map( found.get(), MaintenanceDroidDto.class ), HttpStatus.OK );
        }
    }

    @GetMapping("/maintenanceDroids/{id}/tasks")
    public ResponseEntity<List<Task>> getMaintenanceDroidTask (@PathVariable UUID id) {
        Optional<MaintenanceDroid> found = maintenanceDroidRepository.findById(id);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            MaintenanceDroid md = found.get();
            List<Task> t = md.getTasks();
            return new ResponseEntity<List<Task>>(t, HttpStatus.OK);
        }
    }

    @DeleteMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDto> deleteOneMaintenanceDroid( @PathVariable UUID id ) {
        Optional<MaintenanceDroid> found = maintenanceDroidRepository.findById( id );
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            maintenanceDroidRepository.delete( found.get() );
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }
    }

    @DeleteMapping("/maintenanceDroids/{id}/tasks")
    public ResponseEntity<MaintenanceDroidDto> deleteMaintenanceDroidTask( @PathVariable UUID id ) {
        Optional<MaintenanceDroid> found = maintenanceDroidRepository.findById( id );
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            found.get().removeTasks();
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }
    }

    @PostMapping("/maintenanceDroids")
    public ResponseEntity<?> createNewMaintenanceDroid( @RequestBody MaintenanceDroidDto maintenanceDroidDto) {
         try {
            // create new so that the ID has been set, then transfer the received DTO
            MaintenanceDroid newMaintenanceDroid = new MaintenanceDroid();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map( maintenanceDroidDto, newMaintenanceDroid );
            maintenanceDroidRepository.save( newMaintenanceDroid );
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/id")
                    .buildAndExpand(newMaintenanceDroid.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body( maintenanceDroidDto );
        }
        catch( Exception e ) {
            //something bad happened -
            return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

    @PostMapping("/maintenanceDroids/{id}/tasks")
    public ResponseEntity<MaintenanceDroidDto> executeTask(@PathVariable UUID id, @RequestBody Task task ) {
        maintenanceDroidService.executeCommand(id, task);
        return new ResponseEntity(task, HttpStatus.CREATED);
    }

    @PatchMapping("maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDto> patchMaintenanceDroid( @PathVariable UUID id, @RequestBody MaintenanceDroidDto maintenanceDroidDto ) {
        try {
            ModelMapper modelMapper = new ModelMapper();
            MaintenanceDroid found = maintenanceDroidRepository.findById(id).orElseThrow(NoSuchElementException::new);

            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(maintenanceDroidDto, found);

            maintenanceDroidRepository.save(found);

            return new ResponseEntity<MaintenanceDroidDto>(modelMapper.map(found, MaintenanceDroidDto.class), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }



}
