package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@Transactional
public class TidyUpRobotController {
    private final TidyUpRobotService tidyUpRobotService;
    private TidyUpRobotDtoMapper tidyUpRobotDtoMapper = new TidyUpRobotDtoMapper();

    @Autowired
    public TidyUpRobotController( TidyUpRobotService tidyUpRobotService) {
        this.tidyUpRobotService = tidyUpRobotService;
    }

    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobotDto> getAllRobots() {
        Iterable<TidyUpRobot> allRobots = tidyUpRobotService.findAll();
        List<TidyUpRobotDto> allDtos = new ArrayList<>();
        for (TidyUpRobot tidyUpRobot : allRobots) {
            allDtos.add(tidyUpRobotDtoMapper.mapToDto(tidyUpRobot));
        }
        return allDtos;
    }

    @GetMapping("/tidyUpRobots/{tidyUpRobotId}")
    public ResponseEntity<TidyUpRobotDto> getOneRobot(@PathVariable UUID tidyUpRobotId) {
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findByID(tidyUpRobotId);
        TidyUpRobotDto tidyUpRobotDto = tidyUpRobotDtoMapper.mapToDto( tidyUpRobot );
        return new ResponseEntity( tidyUpRobotDto, OK );
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<TidyUpRobotDto> createNewRobot (@RequestBody TidyUpRobotDto tidyUpRobotDto) {
        TidyUpRobot tidyUpRobot = tidyUpRobotService.createRobotFromDto(tidyUpRobotDto);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{tidyUpRobotId}")
                .buildAndExpand( tidyUpRobot.getId())
                .toUri();
        TidyUpRobotDto createdTidyUpRobotDto =tidyUpRobotDtoMapper.mapToDto( tidyUpRobot );
        return ResponseEntity
                .created(returnURI)
                .body( createdTidyUpRobotDto);
    }

    @DeleteMapping("/tidyUpRobots/{tidyUpRobotId}")
    public ResponseEntity<?> deleteRobot( @PathVariable UUID tidyUpRobotId) {
        tidyUpRobotService.deleteById(tidyUpRobotId);
        return new ResponseEntity( NO_CONTENT );
    }

    @PatchMapping("/tidyUpRobots/{tidyUpRobotId}")
    public ResponseEntity<TidyUpRobotDto> changeName (@PathVariable UUID tidyUpRobotId, @RequestBody TidyUpRobotDto tidyUpRobotDto) {
        TidyUpRobot tidyUpRobot = tidyUpRobotService.changeName( tidyUpRobotId, tidyUpRobotDto );
        TidyUpRobotDto createdRobotDto = tidyUpRobotDtoMapper.mapToDto( tidyUpRobot);
        return new ResponseEntity( createdRobotDto, OK );
    }

    @PostMapping("/tidyUpRobots/{tidyUpRobotId}/tasks")
    public ResponseEntity<Task> executeTask(@PathVariable UUID tidyUpRobotId, @RequestBody Task task) {
        TidyUpRobot tidyUpRobot =tidyUpRobotService.findByID(tidyUpRobotId);
        tidyUpRobotService.executeCommand(tidyUpRobot.getId(), task);
        return new ResponseEntity(task, CREATED );
    }

    @GetMapping("/tidyUpRobots/{tidyUpRobotId}/tasks")
    public List<Task> getAllTasks (@PathVariable UUID tidyUpRobotId){
        return tidyUpRobotService.findAllTasks(tidyUpRobotId);
    }

    @DeleteMapping("/tidyUpRobots/{tidyUpRobotId}/tasks")
    public ResponseEntity<?> deleteTaskHistory(@PathVariable UUID tidyUpRobotId) {
        tidyUpRobotService.deleteTaskHistory(tidyUpRobotId);
        return new ResponseEntity( NO_CONTENT );
    }

}
