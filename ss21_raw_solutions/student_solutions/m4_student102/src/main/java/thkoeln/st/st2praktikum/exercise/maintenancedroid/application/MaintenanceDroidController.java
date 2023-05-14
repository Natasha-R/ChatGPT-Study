package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CommandDto;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;

import java.util.*;

@RestController
@Transactional
@RepositoryRestController
public class MaintenanceDroidController {
    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;
    @Autowired
    private MaintenanceDroidService maintenanceDroidService;


    //Get specific Droid
    @GetMapping("/maintenanceDroids/{id}")
    public MaintenanceDroid getSpecificMaintenanceDroids(@PathVariable UUID id ) {

        return maintenanceDroidRepository.findById( id ).get();
    }
    //Get all MaintenanceDroid
    @GetMapping("/maintenanceDroids")
    public Iterable<MaintenanceDroid> getAllMaintenanceDroids() {

        return maintenanceDroidRepository.findAll();
    }
    //Create a new MaintenanceDroid
    @PostMapping("/maintenanceDroids")
    public ResponseEntity<MaintenanceDroid> createMaintenanceDroid(@RequestBody MaintenanceDroid maintenanceDroid ) {
        maintenanceDroidRepository.save( maintenanceDroid );
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceDroid);
    }
    //Delete a specific MaintenanceDroid
    @DeleteMapping("/maintenanceDroids/{id}")
    public ResponseEntity<?> deleteOneDungeon( @PathVariable UUID id ) {
        maintenanceDroidRepository.deleteById( id );
        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }
    //Change the name of a specific maintenance droid
    @PatchMapping("/maintenanceDroids/{id}")
    public ResponseEntity<?> changeName( @RequestBody MaintenanceDroid maintenanceDroid, @PathVariable UUID id ) {
        System.out.println( "*******"+ maintenanceDroid.getName() + "**********");
        System.out.println( "*******"+ id + "**********");
        MaintenanceDroid tmp=maintenanceDroidRepository.findById( id ).get();
        System.out.println( "******* Tmp Nom: "+tmp.getName() +" Id "+ tmp.getDroidID() + "**********");
        tmp.setName(  maintenanceDroid.getName() );
        maintenanceDroidRepository.save( tmp );
        return ResponseEntity.status(HttpStatus.OK).body(tmp);
    }
    //Give a specific maintenance droid a command
    @PostMapping("/maintenanceDroids/{id}/commands")
    public ResponseEntity<?> giveCommand( @PathVariable UUID id  , @RequestBody Command command ) {
        System.out.println("***+***********ID******* "+command.getGridId());

        maintenanceDroidService.executeCommand(id , command);
        MaintenanceDroid droid=maintenanceDroidRepository.findById(id).get();
        return ResponseEntity.status(HttpStatus.CREATED).body(command);
    }
    //List of all Command
    @GetMapping("/maintenanceDroids/{id}/commands")
    public Iterable<Command> getAllCommand(@PathVariable UUID id  ) {
        ArrayList<Command> iterableCommand=new ArrayList<>();
        for ( Command tmp : maintenanceDroidService.getAllCommand()){
            if( id.equals( tmp.getMaintenanceDroidId() )  )
                iterableCommand.add( tmp);
        }
        return iterableCommand;
    }
    @DeleteMapping("/maintenanceDroids/{id}/commands")
    public ResponseEntity<?> deleteCommand( @PathVariable UUID id ) {
        //maintenanceDroidRepository.deleteById( id );
        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }
}
