package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.Zone;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;


import java.net.URI;
import java.util.Optional;
import java.util.UUID;

//@RepositoryRestController
@RestController
public class TidyUpRobotController {

    @Autowired
    private TidyUpRepository tidyUpRobotRepository;
    @Autowired
    private  Zone zone;
    @Autowired
    private TidyUpRobotService tidyUpRobotService;

    /*@Autowired
    public TidyUpRobotController(TidyUpRepository cleaningDeviceRepository, Zone zone){
        this.tidyUpRobotRepository =cleaningDeviceRepository;
        this.zone = zone;
    }*/

    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobot> getAllTidyUpRobots(){
        return tidyUpRobotRepository.findAll();
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> createNewCleaningDevice(@RequestBody TidyUpRobot cleaningDevice){
        try{
            tidyUpRobotRepository.save(cleaningDevice);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{tidyUpRobot-id}")
                    .buildAndExpand(cleaningDevice.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(cleaningDevice);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> getTidyUpRobot(@PathVariable UUID id){
        return new ResponseEntity(tidyUpRobotRepository.findById(id),HttpStatus.OK);
    }

    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> deleteTidyUpRobot(@PathVariable UUID id ) {

        tidyUpRobotRepository.deleteById(id);
        return new ResponseEntity( HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<?> changeNameOfCleaningDevice(@RequestBody TidyUpRobot cleaningDevice){
        try{
            tidyUpRobotRepository.save(cleaningDevice);
            return ResponseEntity.ok(cleaningDevice);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/tidyUpRobots/{id}/orders")
    public ResponseEntity<?> executeCommand(@PathVariable UUID id, @RequestBody Order command){

        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            TidyUpRobot cleaningDevice = found.get();
            cleaningDevice.addCommand(command);
            tidyUpRobotService.executeCommand(id,command);
            new ModelMapper().map(cleaningDevice, TidyUpRobot.class);
            tidyUpRobotRepository.save(cleaningDevice);

            return new ResponseEntity<Order>(cleaningDevice.getOrders().get(cleaningDevice.getOrders().size()-1), HttpStatus.CREATED);
        }

    }

    @GetMapping("/tidyUpRobots/{id}/orders")
    public Iterable<Order> getAllCommandsOfCleaningDevice(@PathVariable UUID id){
        return tidyUpRobotRepository.findById(id).get().getOrders();
    }

    @DeleteMapping("/tidyUpRobots/{id}/orders")
    public ResponseEntity<TidyUpRobot> deleteCommandHistoryOfCleaningDevice(@PathVariable UUID id) {
       // TidyUpRobot cleaningDevice = tidyUpRobotRepository.findCleaningDeviceById(id);
        TidyUpRobot cleaningDevice = tidyUpRobotRepository.findTidyUpRobotById(id);
        cleaningDevice.deleteCommands();
        tidyUpRobotRepository.save(cleaningDevice);
        return new ResponseEntity( HttpStatus.NO_CONTENT);
    }

}
