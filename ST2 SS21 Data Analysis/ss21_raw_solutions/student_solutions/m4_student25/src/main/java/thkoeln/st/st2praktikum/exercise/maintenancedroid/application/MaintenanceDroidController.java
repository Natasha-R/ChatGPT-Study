package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class MaintenanceDroidController {
    public MaintenanceDroidController(MaintenanceDroidService maintenanceDroidService) {
        this.maintenanceDroidService = maintenanceDroidService;
    }

    private final MaintenanceDroidService maintenanceDroidService;
    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/maintenanceDroids")
    public Iterable<MaintenanceDroidDto> getAllMaintenanceDroids() {
        Iterable<MaintenanceDroid> found =  maintenanceDroidService.getMaintenanceDroids().findAll();
        List foundDtos = new ArrayList<MaintenanceDroidDto>();
        for ( MaintenanceDroid sg : found ) {
            foundDtos.add( modelMapper.map( sg, MaintenanceDroidDto.class ) );
        }
        return foundDtos;
    }

    @PostMapping("/maintenanceDroids")
    public ResponseEntity<MaintenanceDroidDto> postMaintenanceDroid( @RequestBody MaintenanceDroidDto maintenanceDroidDto) {
        try{
            UUID miningMachine = this.maintenanceDroidService.addMaintenanceDroid(maintenanceDroidDto.getName());
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand( miningMachine )
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body( maintenanceDroidDto );
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDto> getOneMaintenanceDroid( @PathVariable UUID id ) {
        Optional<MaintenanceDroid> found = maintenanceDroidService.getMaintenanceDroids().findById( id );
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            return new ResponseEntity<MaintenanceDroidDto>(
                    modelMapper.map( found.get(), MaintenanceDroidDto.class ), HttpStatus.OK );
        }
    }


    @DeleteMapping("/maintenanceDroids/{id}")
    public ResponseEntity deleteOneMaintenanceDroid(@PathVariable UUID id ) {
        Optional<MaintenanceDroid> found = maintenanceDroidService.getMaintenanceDroids().findById( id );
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            maintenanceDroidService.getMaintenanceDroids().delete( found.get() );
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }
    }

    @PatchMapping(path = "/maintenanceDroids/{id}", consumes = "application/json")
    public ResponseEntity<MaintenanceDroidDto> patchMaintenanceDroid( @PathVariable UUID id, @RequestBody MaintenanceDroid patch){
        Optional<MaintenanceDroid> found = this.maintenanceDroidService.getMaintenanceDroids().findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            found.get().setName(patch.getName());
            return new ResponseEntity<MaintenanceDroidDto>(
                    this.modelMapper.map(found.get(), MaintenanceDroidDto.class), HttpStatus.OK
            );
        }
    }

    @PostMapping("/maintenanceDroids/{id}/orders")
    public ResponseEntity<?> postMaintenanceDroid(@RequestBody Order order, @PathVariable UUID id) {
        try {
// create new so that the ID has been set, then transfer the received DTO
            Optional<MaintenanceDroid> found = maintenanceDroidService.getMaintenanceDroids().findById(id);
            if (found.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                maintenanceDroidService.executeCommand(found.get().getMaintenanceDroidID(), order);
                maintenanceDroidService.getMaintenanceDroids().save(found.get());
                URI returnURI = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(found.get().getMaintenanceDroidID())
                        .toUri();
                return ResponseEntity
                        .created(returnURI)
                        .body(order);
            }
        } catch (Exception e) {
// something bad happened -
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    @GetMapping("/maintenanceDroids/{id}/orders")
    public Iterable<Order> getAllOrders(@PathVariable UUID id){
        return this.maintenanceDroidService.getMaintenanceDroids().findById(id).get().getOrders();
    }

    @DeleteMapping("/maintenanceDroids/{id}/orders")
    public ResponseEntity<?> deleteAllOrders(@PathVariable UUID id){
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.getMaintenanceDroids().findById(id).get();
        maintenanceDroid.getOrders().clear();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
