package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@RestController
public class TidyUpRobotController {
    private final TidyUpRobotRepository repository;
    private final TidyUpRobotService service;

    @Autowired
    public TidyUpRobotController(TidyUpRobotRepository repo, TidyUpRobotService service) {
        repository = repo;
        this.service = service;
    }

    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobot> getAllTidyUpRobots() {
        return repository.findAll();
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<TidyUpRobot> createNewTidyUpRobot( @RequestBody TidyUpRobot tidyUpRobot) {
        TidyUpRobot newRobot = new TidyUpRobot(tidyUpRobot.getName());

        repository.save(newRobot);

        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newRobot.getId())
                .toUri();

        return ResponseEntity
                .created(returnURI)
                .body(newRobot);
    }

    @GetMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> getOneTidyUpRobot(@PathVariable UUID id) {
        Optional<TidyUpRobot> found = repository.findById(id);

        if(found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> deleteOneTidyUpRobot(@PathVariable UUID id) {
        Optional<TidyUpRobot> found = repository.findById(id);

        if(found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            repository.delete(found.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> updateOneTidyUpRobot(@PathVariable UUID id, @RequestBody TidyUpRobot tidyUpRobot) {
        Optional<TidyUpRobot> found = repository.findById(id);

        if(found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            TidyUpRobot updatedRobot = found.get();
            updatedRobot.setName(tidyUpRobot.getName());
            return new ResponseEntity<>(tidyUpRobot, HttpStatus.OK);
        }
    }

    @PostMapping("/tidyUpRobots/{id}/orders")
    public ResponseEntity<Order> giveRobotAOrder(@PathVariable UUID id, @RequestBody Order order) {
        Optional<TidyUpRobot> found = repository.findById(id);

        if(found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            TidyUpRobot robot = found.get();
            this.service.executeCommand(id, order);

            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
    }

    @GetMapping("/tidyUpRobots/{id}/orders")
    public Iterable<Order> getAllOrdersForRobot(@PathVariable UUID id) {
        Optional<TidyUpRobot> found = repository.findById(id);

        if(found.isEmpty()) {
            return new ArrayList<Order>();
        } else {
            TidyUpRobot robot = found.get();
            return robot.getOrders();
        }
    }

    @DeleteMapping("/tidyUpRobots/{id}/orders")
    public ResponseEntity<Order> deleteAllOrdersForRobot(@PathVariable UUID id) {
        Optional<TidyUpRobot> found = repository.findById(id);

        if(found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            TidyUpRobot robot = found.get();
            robot.setOrders(new ArrayList<>());

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
