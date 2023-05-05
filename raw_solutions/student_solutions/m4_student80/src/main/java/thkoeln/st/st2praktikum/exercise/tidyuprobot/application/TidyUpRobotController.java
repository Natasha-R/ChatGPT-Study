package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TidyUpRobotController {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    TidyUpRobotRepository tidyUpRobotRepository;

    @Autowired
    TransportCategoryRepository transportCategoryRepository;

    private final TidyUpRobotService tidyUpRobotService;


    public TidyUpRobotController(TidyUpRobotService tidyUpRobotService) {
        this.tidyUpRobotService = tidyUpRobotService;
    }


    @GetMapping("/tidyUpRobots/{id}")
    public Optional<TidyUpRobot> getTidyUpRobot(@PathVariable UUID id) {
        Optional<TidyUpRobot> tidyUpRobot = tidyUpRobotRepository.findById(id);
        return tidyUpRobot;

    }


    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobot> getAllTidyUpRobots() {
        return tidyUpRobotRepository.findAll();
    }

    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<?> deleteTidyUpRobotsTest(@PathVariable UUID id) {
        tidyUpRobotRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> postTidyUpRobot(@RequestBody TidyUpRobot tidyUpRobot) {
        tidyUpRobot.setRoboterUUID(UUID.randomUUID());
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tidyUpRobot.getId())
                .toUri();
        tidyUpRobotRepository.save(tidyUpRobot);
        return ResponseEntity
                .created(returnURI)
                .body(tidyUpRobot);
    }

    @PatchMapping("/tidyUpRobots/{id}")
    public ResponseEntity<?> PatchTidyUpRobot(@RequestBody TidyUpRobot tidyUpRobot) {
        tidyUpRobot.setRoboterUUID(UUID.randomUUID());
        tidyUpRobotRepository.save(tidyUpRobot);
        return new ResponseEntity(tidyUpRobot, HttpStatus.OK);
    }

    @GetMapping("/tidyUpRobots/{id}/tasks")
    public List<Task> getAllTidyUpRobotTasks(@PathVariable UUID id) {
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findById(id);
        return tidyUpRobot.getTaskList();
    }

    @DeleteMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<?> deleteAllMiningMachineOrders(@PathVariable UUID id) {
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findById(id);
        List<Task> tasks = tidyUpRobot.getTaskList();
        tasks.clear();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<?> postTidyUpRobotMoveTask(@PathVariable UUID id, @RequestBody Task task) {
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findById(id);
        tidyUpRobot.getTaskList().add(task);
        tidyUpRobotService.executeCommand(id,task);
        return new ResponseEntity<>(task, HttpStatus.CREATED);
    }


}



