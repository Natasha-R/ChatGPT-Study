package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.room.repository.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.repository.SquareRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.repository.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;
import thkoeln.st.st2praktikum.exercise.transporttechnology.repository.TransportTechnologyRepository;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class TidyUpRobotController {

    RoomService roomService;
    TidyUpRobotService tidyUpRobotService;
    TransportTechnologyService transportTechnologyService;

    RoomRepository roomRepository;
    SquareRepository squareRepository;
    TidyUpRobotRepository tidyUpRobotRepository;
    TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    public TidyUpRobotController(RoomService roomService, TidyUpRobotService tidyUpRobotService, TransportTechnologyService transportTechnologyService, RoomRepository roomRepository, SquareRepository squareRepository, TidyUpRobotRepository tidyUpRobotRepository, TransportTechnologyRepository transportTechnologyRepository) {
        this.roomService = roomService;
        this.tidyUpRobotService = tidyUpRobotService;
        this.transportTechnologyService = transportTechnologyService;

        this.roomRepository = roomRepository;
        this.squareRepository = squareRepository;
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
    }

    @PostMapping(value = "/tidyUpRobots")
    public ResponseEntity<TidyUpRobotDto> postTidyUpRobot(@RequestBody TidyUpRobot tidyUpRobot) {
        try {
            tidyUpRobotService.addTidyUpRobot(tidyUpRobot);

            TidyUpRobotDto tidyUpRobotDto = tidyUpRobotService.covertToTidyUpRobotDto(tidyUpRobot);

            URI returnURI = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(tidyUpRobot.getId())
                    .toUri();
            return ResponseEntity.created(returnURI).body(tidyUpRobotDto);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping(value = "/tidyUpRobots/{id}/orders")
    public ResponseEntity<Order> postTidyUpRobotMoveOrder(@PathVariable UUID id, @RequestBody Order order) {
        try {
            tidyUpRobotService.executeCommand(id, order);
            return new ResponseEntity(order, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping(value = "/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobotDto> getTidyUpRobot(@PathVariable UUID id) {
        try {
            TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(id).orElseThrow(EntityNotFoundException::new);

            TidyUpRobotDto tidyUpRobotDto = tidyUpRobotService.covertToTidyUpRobotDto(tidyUpRobot);

            return new ResponseEntity(tidyUpRobotDto, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/tidyUpRobots")
    public Iterable<TidyUpRobotDto> getAllTidyUpRobots() {
        Iterable<TidyUpRobot> tidyUpRobots = tidyUpRobotRepository.findAll();
        List<TidyUpRobotDto> tidyUpRobotDtos = new ArrayList<>();

        for (TidyUpRobot tidyUpRobot : tidyUpRobots)
            tidyUpRobotDtos.add(tidyUpRobotService.covertToTidyUpRobotDto(tidyUpRobot));

        return tidyUpRobotDtos;
    }

    @GetMapping(value = "/tidyUpRobots/{id}/orders")
    public ResponseEntity getAllTidyUpRobotOrders(@PathVariable UUID id) {
        try {
            List<Order> orders = tidyUpRobotService.getOrdersById(id);
            return new ResponseEntity(orders, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobotDto> deleteTidyUpRobot(@PathVariable UUID id) {
        try {
            TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            tidyUpRobotRepository.delete(tidyUpRobot);

            TidyUpRobotDto tidyUpRobotDto = tidyUpRobotService.covertToTidyUpRobotDto(tidyUpRobot);

            return new ResponseEntity(tidyUpRobotDto, HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/tidyUpRobots/{id}/orders")
    public ResponseEntity<TidyUpRobotDto> deleteAllTidyUpRobotOrders(@PathVariable UUID id) {
        try {
            TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(id).orElseThrow(EntityNotFoundException::new);
            tidyUpRobotService.clearTidyUpRobotOrderHistory(id);

            TidyUpRobotDto tidyUpRobotDto = tidyUpRobotService.covertToTidyUpRobotDto(tidyUpRobot);

            return new ResponseEntity(tidyUpRobotDto, HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobotDto> patchTidyUpRobot(@PathVariable UUID id, @RequestBody TidyUpRobot tidyUpRobot) {
        try {
            TidyUpRobot changedTidyUpRobot = tidyUpRobotService.changeTidyUpRobotName(id, tidyUpRobot.getName());

            TidyUpRobotDto tidyUpRobotDto = tidyUpRobotService.covertToTidyUpRobotDto(changedTidyUpRobot);

            return new ResponseEntity(tidyUpRobotDto, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
