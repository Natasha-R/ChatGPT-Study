package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotDto;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TidyUpRobotController {

    private TidyUpRobotService tidyUpRobotService;

    private TidyUpRobotRepository tidyUpRobotRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public TidyUpRobotController(TidyUpRobotRepository tidyUpRobotRepository, TidyUpRobotService tidyUpRobotService) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.tidyUpRobotService = tidyUpRobotService;
    }

    @GetMapping("/tidyUpRobots")
    public @ResponseBody Iterable<TidyUpRobotDto> getAllTidyUpRobots() {
        Iterable<TidyUpRobot> found = tidyUpRobotRepository.findAll();
        List foundDtos = new ArrayList<TidyUpRobotDto>();
        for (TidyUpRobot sg : found) {
            foundDtos.add(modelMapper.map(sg, TidyUpRobotDto.class));
        }
        return foundDtos;
    }

    @GetMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobotDto> getOneTidyUpRobot(@PathVariable("tidyUpRobot-id") String parameter) {
        UUID robotId = UUID.fromString(parameter);
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(robotId);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<TidyUpRobotDto>(
                    modelMapper.map(found.get(), TidyUpRobotDto.class), HttpStatus.OK);
        }
    }


    @DeleteMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobotDto> deleteOneTidyUpRobot(@PathVariable("tidyUpRobot-id") String parameter) {
        UUID robotId = UUID.fromString(parameter);
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById( robotId );
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            tidyUpRobotRepository.delete( found.get() );
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> createNewTidyUpRobot( @RequestBody TidyUpRobotDto tidyUpRobotDto) {
        try {
            // create new so that the ID has been set, then transfer the received DTO
            TidyUpRobot tidyUpRobot = new TidyUpRobot();
            modelMapper.map( tidyUpRobotDto, tidyUpRobot );
            tidyUpRobotRepository.save( tidyUpRobot );
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(tidyUpRobot.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body( tidyUpRobotDto );
        }
        catch( Exception e ) {
            // something bad happened -
            return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

    @PatchMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<?> tidyUpRobotChangeName(
            @RequestBody TidyUpRobotDto tidyUpRobotDto, @PathVariable("tidyUpRobot-id") String parameterId) {

        UUID robotId = UUID.fromString(parameterId);
        tidyUpRobotRepository.findById(robotId).get().setName(tidyUpRobotDto.getName());
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(robotId).get();
        return ResponseEntity
                .ok( tidyUpRobotDto );
    }


    @PostMapping("/tidyUpRobots/{tidyUpRobot-id}/orders")
    public ResponseEntity<?> createNewOrder(@PathVariable("tidyUpRobot-id") String parameter, @RequestBody Order order){
        System.out.println("order from PostMapping: " + order + " -?- " + order.toString());
        System.out.println("order specs: " + order.getOrderType() );
        UUID robotId = UUID.fromString(parameter);
        tidyUpRobotService.executeCommand(robotId, order);

        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order)
                .toUri();
        return ResponseEntity
                .created(returnURI)
                .body(order);
    }

    @GetMapping("/tidyUpRobots/{tidyUpRobot-id}/orders")
    public @ResponseBody List<Order> getOrderFromTidyUpRobot(@PathVariable("tidyUpRobot-id") String parameter) {
        UUID robotId = UUID.fromString(parameter);
        tidyUpRobotService.getOrderHistory(robotId).forEach(it -> System.out.println("order: " + it.getOrderType()));
        System.out.println(robotId + " -------------- ");
        return tidyUpRobotService.getOrderHistory(robotId);
    }

    @DeleteMapping("/tidyUpRobots/{tidyUpRobot-id}/orders")
    public ResponseEntity<?> deleteTidyUpRobotHistory(@PathVariable("tidyUpRobot-id") String parameter) {
        UUID robotId = UUID.fromString(parameter);
        tidyUpRobotService.getOrderHistory(robotId).clear();
        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }
}