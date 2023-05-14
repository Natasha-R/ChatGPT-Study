package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.*;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Transactional
public class TidyUpRobotController {
    private  final TidyUpRobotService tidyUpRobotService;
    private TidyUpRobotDtoMapper tidyUpRobotDtoMapper = new TidyUpRobotDtoMapper();

    @Autowired
    public TidyUpRobotController(TidyUpRobotService tidyUpRobotService){
        this.tidyUpRobotService = tidyUpRobotService;
    }

    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobot> getAllTidyUpRobots(){
        Iterable<TidyUpRobot> allRobots = tidyUpRobotService.findAll();
        return allRobots;
    }

    @GetMapping("/tidyUpRobots/{tidyUpRobotId}")
    public TidyUpRobot getOneRobot(@PathVariable UUID tidyUpRobotId){
        return tidyUpRobotService.findById(tidyUpRobotId);
    }

    @GetMapping("/tidyUpRobots/{id}/commands")
    public Iterable<Command> getAllCommands(@PathVariable UUID id){
        //TidyUpRobot tidyUpRobot = tidyUpRobotService.findById(id);
        Iterable<Command> allCommands = tidyUpRobotService.getAllCommands(id);
        return allCommands;
    }

    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<?> deleteOneRobot(@PathVariable UUID id){
        tidyUpRobotService.deleteById(id);
        return new ResponseEntity(NO_CONTENT);

    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<TidyUpRobotDto> createRobot(@RequestBody TidyUpRobotDto tidyUpRobotDto){
       TidyUpRobot tidyUpRobot = tidyUpRobotService.createNewRobotFromDto(tidyUpRobotDto);
       URI returnURI = ServletUriComponentsBuilder
               .fromCurrentRequest()
               .path("/{id}")
               .buildAndExpand(tidyUpRobot.getTidyUpRobotId())
               .toUri();
       TidyUpRobotDto createdRobot = tidyUpRobotDtoMapper.mapToDto(tidyUpRobot);
       return ResponseEntity
               .created(returnURI)
               .body(createdRobot);
    }

    @PatchMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> patchNameOfRobot(@PathVariable UUID id, @RequestBody String name){
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findById(id);
        String parsedName = parseName(name);
        tidyUpRobot.setName(parsedName);
        return new ResponseEntity(tidyUpRobot,OK);
    }
    private String parseName(String name){
        String parsedName = name.substring(name.indexOf(":")+2,name.lastIndexOf("}")-1);
        return parsedName;
    }

    @PostMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity<Command> addCommand(@PathVariable UUID id, @RequestBody Command command){
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findById(id);
        tidyUpRobotService.executeCommand(id, command);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(command.getCommandId())
                .toUri();
        return ResponseEntity
                .created(returnURI)
                .body(command);
    }
    @DeleteMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity<?> deleteAllCommands(@PathVariable UUID id){
        tidyUpRobotService.deleteAllCommands(id);
        return new ResponseEntity(NO_CONTENT);

    }
}
