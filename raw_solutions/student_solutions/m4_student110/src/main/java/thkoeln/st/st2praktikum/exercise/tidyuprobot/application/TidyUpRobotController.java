package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;

import javax.persistence.Tuple;
import java.net.URI;
import java.util.*;


@RestController
public class TidyUpRobotController {



    @Autowired
    public TidyUpRobotController(TidyUpRobotService tidyUpRobotService){
        this.tidyUpRobotService = tidyUpRobotService;
    }

    public TidyUpRobotController(){}

    TidyUpRobotService tidyUpRobotService;
    private ModelMapper mapper = new ModelMapper();
 


    //Get All . OK
    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobot> getAllTidyUpRobots(){
        return tidyUpRobotService.tidyUpRobotRepository.findAll();
    }

    //Delete ONE. OK
    @DeleteMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobot> deleteOneTidyUpRobot(@PathVariable("tidyUpRobot-id") UUID id){
        Optional<TidyUpRobot> found = tidyUpRobotService.tidyUpRobotRepository.findById(id);
        if (found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            tidyUpRobotService.tidyUpRobotRepository.delete(found.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

    }


    //Get One.OK
    @GetMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobot> getOneTidyUpRobot(@PathVariable("tidyUpRobot-id") UUID id){
        Optional<TidyUpRobot> found = tidyUpRobotService.tidyUpRobotRepository.findById(id);
        if (found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<TidyUpRobot>(
                    mapper.map(found.get(), TidyUpRobot.class), HttpStatus.OK);
        }
    }


    //Create New Robot. OK
    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> createNewTidyUpRobot(@RequestBody TidyUpRobot tidyUpRobot){
        try {
            TidyUpRobot newRobot = new TidyUpRobot();
           mapper.map(tidyUpRobot, newRobot);
            tidyUpRobotService.tidyUpRobotRepository.save(newRobot);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newRobot.getTidyUpRobotId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(tidyUpRobot);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //Create new Order for Robot . OK
    @PostMapping("/tidyUpRobots/{tidyUpRobot-id}/orders")
    public ResponseEntity<?> createNewOrderForTidyUpRobot(@PathVariable("tidyUpRobot-id") UUID id, @RequestBody Order order){
        Optional<TidyUpRobot> tidyUpRobot = tidyUpRobotService.tidyUpRobotRepository.findById(id);
        tidyUpRobotService.executeCommand(id,order);
        mapper.map(tidyUpRobot,order);
        return new ResponseEntity<>(tidyUpRobot,HttpStatus.CREATED);
    }

    //Patch Robot . OK
    @PatchMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobot> patchRobot( @PathVariable("tidyUpRobot-id") UUID id, @RequestBody TidyUpRobot tidyUpRobot){
        Optional<TidyUpRobot> oldTidyUpRobot = tidyUpRobotService.tidyUpRobotRepository.findById(id);
        mapper.map(tidyUpRobot, oldTidyUpRobot);
        return new ResponseEntity<>(tidyUpRobot,HttpStatus.OK);

    }

    //Get Orders from Robot
    @GetMapping("/tidyUpRobots/{tidyUpRobot-id}/orders")
    public Iterable<Order> getOrdersFromRobot(@PathVariable("tidyUpRobot-id") UUID uuid){

        Optional<TidyUpRobot> actingRobot = tidyUpRobotService.tidyUpRobotRepository.findById(uuid);
        List<Order> robotOrders = tidyUpRobotService.getRobotersOrders(uuid);


        return robotOrders;
    }


    //Delete all Robot Orders
    @DeleteMapping("/tidyUpRobots/{tidyUpRobot-id}/orders")
    public ResponseEntity<?> deleteAllOrders(@PathVariable("tidyUpRobot-id") UUID id) {

        tidyUpRobotService.deleteRobotersOrders(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }










}
