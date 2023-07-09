package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;

import java.net.URI;
import java.util.*;

@RestController
public class TidyUpRobotController {

    private TidyUpRobotService tidyUpRobotService;
    private TidyUpRobotDtoMapper tidyUpRobotDtoMapper = new TidyUpRobotDtoMapper();

    @Autowired
    public TidyUpRobotController(TidyUpRobotRepository tidyUpRobotRepository, TidyUpRobotService tidyUpRobotService){
        this.tidyUpRobotService = tidyUpRobotService;
    }

    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobotDto> getAllTidyUpRobots(){
        Iterable<TidyUpRobot> tidyUpRobots = tidyUpRobotService.findAll();
        List<TidyUpRobotDto> allDtos = new ArrayList<>();
        for(TidyUpRobot tidyUpRobot : tidyUpRobots){
            allDtos.add(tidyUpRobotDtoMapper.mapToDto(tidyUpRobot));
        }
        return allDtos;
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> createNewTidyUpRobot(@RequestBody TidyUpRobotDto tidyUpRobotDto){
        TidyUpRobot tidyUpRobot = tidyUpRobotDtoMapper.mapToEntity(tidyUpRobotDto);
        tidyUpRobotService.save(tidyUpRobot);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tidyUpRobot.getUuid())
                .toUri();
        TidyUpRobotDto createdTidyUpRobotDto = tidyUpRobotDtoMapper.mapToDto(tidyUpRobot);
        return ResponseEntity
                .created(returnURI)
                .body(createdTidyUpRobotDto);
    }

    @GetMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobotDto> getOneTidyUpRobot(@PathVariable UUID id){
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findByUuid(id);
        TidyUpRobotDto tidyUpRobotDto = tidyUpRobotDtoMapper.mapToDto(tidyUpRobot);
        return new ResponseEntity<>(tidyUpRobotDto, HttpStatus.OK);
    }

    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> deleteOneTidyUpRobot(@PathVariable UUID id){
        tidyUpRobotService.deleteByUuid(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<?> createTidyUpRobotTask(@PathVariable UUID id, @RequestBody Task task){
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findByUuid(id);
        tidyUpRobotService.executeCommand(id, task);
        tidyUpRobotService.save(tidyUpRobot);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tidyUpRobot.getUuid())
                .toUri();
        return ResponseEntity.created(returnURI).body(task);
    }

    @GetMapping("/tidyUpRobots/{id}/tasks")
    public Iterable<Task> getTidyUpRobotTasks(@PathVariable UUID id){
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findByUuid(id);
        return tidyUpRobot.getExecutedTasks();
    }

    @DeleteMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<TidyUpRobot> deleteAllTidyUpRobotTasks(@PathVariable UUID id){
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findByUuid(id);
        tidyUpRobot.deleteExecutedTasks();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobotDto> patchTidyUpRobot(@PathVariable UUID id, @RequestBody TidyUpRobotDto tidyUpRobotDto){
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findByUuid(id);
        tidyUpRobot.setName(tidyUpRobotDto.getName());
        tidyUpRobotService.save(tidyUpRobot);
        TidyUpRobotDto changedTidyUpRobotDto = tidyUpRobotDtoMapper.mapToDto(tidyUpRobot);
        return new ResponseEntity<>(changedTidyUpRobotDto, HttpStatus.OK);
    }
}
