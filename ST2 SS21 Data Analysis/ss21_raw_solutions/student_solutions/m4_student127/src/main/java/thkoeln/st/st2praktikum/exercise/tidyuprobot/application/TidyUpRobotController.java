package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RepositoryRestController
public class TidyUpRobotController {
    TidyUpRobotService tidyUpRobotService;

    @Autowired
    public TidyUpRobotController(TidyUpRobotService tidyUpRobotService) {
        this.tidyUpRobotService = tidyUpRobotService;
    }

    // 1. Get all tidy-up robots
    @GetMapping("/tidyUpRobots")
    @ResponseBody
    public Iterable<TidyUpRobot> getAllTidyUpRobots() {
        return tidyUpRobotService.getAllTidyUpRobots();
    }

    // 2. Create a new tidy-up robot
    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> createNewTidyUpRobot(@RequestBody TidyUpRobot tidyUpRobot) {
        try {
            UUID robotId = tidyUpRobotService.addTidyUpRobotObject(tidyUpRobot);
            TidyUpRobot newTidyUpRobot = tidyUpRobotService.getTidyUpRobotById(robotId).get();
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newTidyUpRobot.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(newTidyUpRobot);
        }
        catch( Exception e ) {
            return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }


    // 3. Get a specific tidy-up robot by ID
    @GetMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobot> getOneTidyUpRobot(@PathVariable("tidyUpRobot-id") String parameter) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(UUID.fromString(parameter));
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            return new ResponseEntity<>(found.get(), HttpStatus.OK);
        }
    }


    // 4. Delete a specific tidy-up robot
    @DeleteMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobot> deleteOneTidyUpRobotById(@PathVariable("tidyUpRobot-id") String parameter) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(UUID.fromString(parameter));
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            tidyUpRobotService.deleteTidyUpRobot(found.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    // 5. Change the name of a specific tidy-up robot
    @PatchMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public @ResponseBody ResponseEntity<?> patchOneTidyUpRobotName(@PathVariable("tidyUpRobot-id") String parameter, @RequestBody TidyUpRobot tempTidyUpRobot) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(UUID.fromString(parameter));
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            tidyUpRobotService
                    .getTidyUpRobotById(UUID.fromString(parameter))
                    .get()
                    .setName(tempTidyUpRobot.getName());
            TidyUpRobot patched = tidyUpRobotService
                    .getTidyUpRobotById(UUID.fromString(parameter))
                    .get();

            return new ResponseEntity<>(patched, HttpStatus.OK);
        }
    }

    // 6. Give a specific tidy-up robot a task
    @PostMapping("/tidyUpRobots/{tidyUpRobot-id}/tasks")
    public ResponseEntity<?> giveTidyUpRobotTask(@PathVariable("tidyUpRobot-id") String parameter, @RequestBody Task task) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(UUID.fromString(parameter));
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (tidyUpRobotService.executeCommand(UUID.fromString(parameter), task)) {
                return new ResponseEntity<>(task, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(task, HttpStatus.METHOD_NOT_ALLOWED);
            }
        }
    }

    // 7. List all the tasks a specific tidy-up robot has received so far
    @GetMapping("/tidyUpRobots/{tidyUpRobot-id}/tasks")
    public ResponseEntity<Iterable<Task>> getTidyUpRobotTasksByID(@PathVariable("tidyUpRobot-id") String parameter) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(UUID.fromString(parameter));
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(found.get().getTasks(), HttpStatus.OK);
        }
    }

    // 8. Delete the task history of a specific tidy-up robot
    @DeleteMapping("/tidyUpRobots/{tidyUpRobot-id}/tasks")
    public ResponseEntity<Iterable<Task>> deleteTidyUpRobotTasksByID(@PathVariable("tidyUpRobot-id") String parameter) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(UUID.fromString(parameter));
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            found.get().setTasks(new ArrayList<>());
            return new ResponseEntity<>(found.get().getTasks(), HttpStatus.NO_CONTENT);
        }
    }
}
