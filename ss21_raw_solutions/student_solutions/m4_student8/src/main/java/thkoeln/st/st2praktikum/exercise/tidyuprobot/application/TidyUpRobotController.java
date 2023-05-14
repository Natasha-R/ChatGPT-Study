package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.room.application.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyRepository;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;

import java.net.URI;
import java.util.*;

@RepositoryRestController
public class TidyUpRobotController {

    private TidyUpRobotRepository tidyUpRobotRepository;

    private TidyUpRobotService tidyUpRobotService;


    @Autowired
    public TidyUpRobotController(TidyUpRobotRepository tidyUpRobotRepository,TidyUpRobotService tidyUpRobotService) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.tidyUpRobotService = tidyUpRobotService;
    }


    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> createNewTidyUpRobot(@RequestBody TidyUpRobotDto tidyUpRobotDto) {
        try {
            TidyUpRobot tidyUpRobot = new TidyUpRobot();
            ModelMapper mapper = new ModelMapper();
            mapper.map(tidyUpRobotDto, tidyUpRobot);
            this.tidyUpRobotRepository.save(tidyUpRobot);
            URI resultURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{tidyUpRobot-id}")
                    .buildAndExpand(tidyUpRobot.identify())
                    .toUri();
            return ResponseEntity.created(resultURI)
                    .body(tidyUpRobotDto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @GetMapping("/tidyUpRobots")
    @ResponseBody
    public Iterable<TidyUpRobotDto> getAllTidyUpRobots() {
        ModelMapper mapper = new ModelMapper();
        Iterable<TidyUpRobot> tidyUpRobots = tidyUpRobotRepository.findAll();
        List<TidyUpRobotDto> tidyUpRobotDtos = new ArrayList<TidyUpRobotDto>();
        for (TidyUpRobot robot : tidyUpRobots) {
            tidyUpRobotDtos.add(mapper.map(robot, TidyUpRobotDto.class));
        }

        return tidyUpRobotDtos;
    }

    @GetMapping("/tidyUpRobots/{id}")
    @ResponseBody
    public ResponseEntity<TidyUpRobotDto> getRobotByID(@PathVariable UUID id) {
        ModelMapper mapper = new ModelMapper();
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<TidyUpRobotDto>(mapper.map(found.get(), TidyUpRobotDto.class), HttpStatus.OK);
        }
    }

    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobotDto> deleteRobot(@PathVariable UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            tidyUpRobotRepository.delete(found.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/tidyUpRobots/{id}")
    @ResponseBody
    public ResponseEntity<TidyUpRobotDto> changeRobotAttribs(@RequestBody TidyUpRobotDto tidyUpRobotDto, @PathVariable UUID id) {
        ModelMapper mapper = new ModelMapper();
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            TidyUpRobot robot = found.get();
            mapper.map(tidyUpRobotDto, robot);
            tidyUpRobotRepository.save(robot);
            return new ResponseEntity<>(tidyUpRobotDto, HttpStatus.OK);
        }

    }

    @PostMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<?> createNewTidyUpRobotTask(@PathVariable UUID id, @RequestBody TaskDto taskDto) {

        ModelMapper mapper = new ModelMapper();
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);

        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
        {
              TidyUpRobot robot = found.get();
              Task task = new Task();

              mapper.map(taskDto, task);
              tidyUpRobotService.executeCommand(robot.getRobotID(),task);

            URI resultURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/tasks")
                    .buildAndExpand(task.getTaskType())
                    .toUri();

            return ResponseEntity.created(resultURI).
                    body(taskDto);

        }


    }

    @GetMapping("/tidyUpRobots/{id}/tasks")
    @ResponseBody
    public Iterable<TaskDto> getAllTidyUpRobotTasks(@PathVariable UUID id) {
        ModelMapper mapper = new ModelMapper();
        Iterable<Task> tidyUpRobots = tidyUpRobotRepository.findById(id).get().getTasks();
        List<TaskDto> taskDtos = new ArrayList<TaskDto>();
        for (Task task: tidyUpRobots) {
            taskDtos.add(mapper.map(task, TaskDto.class));
        }

        return taskDtos;
    }

    @DeleteMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<TidyUpRobotDto> deleteAllRobotTasks(@PathVariable UUID id)
    {
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            tidyUpRobotRepository.findById(id).get().removeAllTasks();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}