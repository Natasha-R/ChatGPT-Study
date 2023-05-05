package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.dto.TaskDto;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.dto.TidyUpRobotDto;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestController
public class TidyUpRobotController {

    private final ModelMapper modelMapper;
    private final TidyUpRobotService tidyUpRobotService;

    @Autowired
    public TidyUpRobotController(TidyUpRobotService tidyUpRobotService) {
        this.tidyUpRobotService = tidyUpRobotService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping("/tidyUpRobots")
    @ResponseBody
    public Iterable<TidyUpRobotDto> getAllTidyUpRobots() {
        Iterable<TidyUpRobot> tidyUpRobots = tidyUpRobotService.getAllTidyUpRobots();
        List<TidyUpRobotDto> tidyUpRobotDtos = new ArrayList<>();
        for (TidyUpRobot tidyUpRobot: tidyUpRobots) {
            tidyUpRobotDtos.add(modelMapper.map(tidyUpRobot, TidyUpRobotDto.class));
        }
        return tidyUpRobotDtos;
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> makeTidyUpRobot(@RequestBody TidyUpRobotDto tidyUpRobotDto) {
        try {
            UUID tidyUpRobotId = tidyUpRobotService.addTidyUpRobot(tidyUpRobotDto.getName());
            Optional<TidyUpRobot> optionalTidyUpRobot = tidyUpRobotService.getTidyUpRobotById(tidyUpRobotId);
            if (optionalTidyUpRobot.isEmpty()) {
                throw new RuntimeException();
            }
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{tidyUpRobot-id}")
                    .buildAndExpand(tidyUpRobotId)
                    .toUri();
            return ResponseEntity.created(returnURI).body(modelMapper.map(optionalTidyUpRobot.get(), TidyUpRobotDto.class));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobotDto> getTidyUpRobotById(@PathVariable("tidyUpRobot-id") UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(modelMapper.map(found.get(), TidyUpRobotDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<?> deleteTidyUpRobotById(@PathVariable("tidyUpRobot-id") UUID id) {
        if (tidyUpRobotService.deleteTidyUpRobotById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobotDto> patchTidyUpRobotById(@PathVariable("tidyUpRobot-id") UUID id, @RequestBody TidyUpRobotDto updatedTidyUpRobotDto) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TidyUpRobot currentTidyUpRobot = found.get();
        currentTidyUpRobot.updateFromDto(updatedTidyUpRobotDto);
        TidyUpRobot newTidyUpRobot = tidyUpRobotService.updateTidyUpRobot(currentTidyUpRobot);
        return new ResponseEntity<>(modelMapper.map(newTidyUpRobot, TidyUpRobotDto.class), HttpStatus.OK);
    }

    @PostMapping("/tidyUpRobots/{tidyUpRobot-id}/tasks")
    public ResponseEntity<?> addTaskToTidyUpRobotById(@PathVariable("tidyUpRobot-id") UUID id, @RequestBody TaskDto newTaskDto) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            Task task = Task.fromDto(newTaskDto);
            tidyUpRobotService.executeCommand(id, task);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{tidyUpRobot-id}")
                    .buildAndExpand(id)
                    .toUri();
            return ResponseEntity.created(returnURI).body(modelMapper.map(task, TaskDto.class));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/tidyUpRobots/{tidyUpRobot-id}/tasks")
    @ResponseBody
    public Iterable<TaskDto> getAllTasksOfTidyUpRobotById(@PathVariable("tidyUpRobot-id") UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
        List<TaskDto> taskDtos = new ArrayList<>();
        if (found.isEmpty()) {
            return taskDtos;
        }
        List<Task> tasks = found.get().getTasks();
        for (Task task : tasks) {
            taskDtos.add(modelMapper.map(task, TaskDto.class));
        }
        return taskDtos;
    }

    @DeleteMapping("/tidyUpRobots/{tidyUpRobot-id}/tasks")
    public ResponseEntity<?> deleteTaskHistoryOfTidyUpRobotById(@PathVariable("tidyUpRobot-id") UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TidyUpRobot updatedTidyUpRobot = found.get();
        updatedTidyUpRobot.deleteTaskHistory();
        tidyUpRobotService.updateTidyUpRobot(updatedTidyUpRobot);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
