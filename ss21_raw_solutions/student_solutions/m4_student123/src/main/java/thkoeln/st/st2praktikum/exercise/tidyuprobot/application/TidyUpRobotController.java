package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TidyUpRobotController {

    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;

    private ModelMapper modelMapper = new ModelMapper();
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private TidyUpRobotService tidyUpRobotService;


    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobot> getAllTidyUpRobots() {
        return tidyUpRobotRepository.findAll();
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<TidyUpRobot> createNewTidyUpRobot(@RequestBody String tidyUpRobot) {
        System.out.println(tidyUpRobot);
        try {
            TidyUpRobot robot = objectMapper.readValue(tidyUpRobot, TidyUpRobot.class);
            TidyUpRobot newRobot = new TidyUpRobot(robot.getRobotName());
            System.out.println(robot.getRobotName());
            tidyUpRobotRepository.save(newRobot);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newRobot.getTidyUpRobotID())
                    .toUri();
            return ResponseEntity.created(returnURI).body(newRobot);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("Help");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> getOneTidyUpRobot(@PathVariable UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<TidyUpRobot>(
                    modelMapper.map(found.get(), TidyUpRobot.class), HttpStatus.OK);
        }
    }

    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> deleteOneTidyUpRobot(@PathVariable UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            tidyUpRobotRepository.delete(found.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> patchOneTidyUpRobot(@PathVariable UUID id, @RequestBody String tidyUpRobot) {
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            TidyUpRobot robot = null;
            try {
                robot = objectMapper.readValue(tidyUpRobot, TidyUpRobot.class);
                found.get().setRobotName(robot.getRobotName());
                return new ResponseEntity<TidyUpRobot>(
                        modelMapper.map(found.get(), TidyUpRobot.class), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }

    @PostMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<Task> postOneTaskForRobot(@PathVariable UUID id, @RequestBody String task) {
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            try {
                Task newTask = objectMapper.readValue(task, Task.class);
                tidyUpRobotService.executeCommand(found.get().getTidyUpRobotID(), newTask);
                return new ResponseEntity<Task>(modelMapper.map(newTask, Task.class), HttpStatus.CREATED);
            } catch (JsonProcessingException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    @GetMapping("/tidyUpRobots/{id}/tasks")
    public List<Task> getAllTidyUpRobotTasks(@PathVariable UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        return found.get().getTaskHistory();
    }

    @DeleteMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<TidyUpRobot> deleteAllRobotTasks(@PathVariable UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            found.get().deleteTasks();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
