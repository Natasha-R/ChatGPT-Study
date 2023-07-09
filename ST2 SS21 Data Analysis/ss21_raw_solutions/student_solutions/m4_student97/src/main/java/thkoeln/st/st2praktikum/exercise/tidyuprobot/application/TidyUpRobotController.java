package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.dto.CommandDto;
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

    @PostMapping("/tidyUpRobots/{tidyUpRobot-id}/commands")
    public ResponseEntity<?> addCommandToTidyUpRobotById(@PathVariable("tidyUpRobot-id") UUID id, @RequestBody CommandDto newCommandDto) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            Command command = Command.fromDto(newCommandDto);
            tidyUpRobotService.executeCommand(id, command);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{tidyUpRobot-id}")
                    .buildAndExpand(id)
                    .toUri();
            return ResponseEntity.created(returnURI).body(modelMapper.map(command, CommandDto.class));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/tidyUpRobots/{tidyUpRobot-id}/commands")
    @ResponseBody
    public Iterable<CommandDto> getAllCommandsOfTidyUpRobotById(@PathVariable("tidyUpRobot-id") UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
        List<CommandDto> commandDtos = new ArrayList<>();
        if (found.isEmpty()) {
            return commandDtos;
        }
        List<Command> commands = found.get().getCommands();
        for (Command command : commands) {
            commandDtos.add(modelMapper.map(command, CommandDto.class));
        }
        return commandDtos;
    }

    @DeleteMapping("/tidyUpRobots/{tidyUpRobot-id}/commands")
    public ResponseEntity<?> deleteCommandHistoryOfTidyUpRobotById(@PathVariable("tidyUpRobot-id") UUID id) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TidyUpRobot updatedTidyUpRobot = found.get();
        updatedTidyUpRobot.deleteCommandHistory();
        tidyUpRobotService.updateTidyUpRobot(updatedTidyUpRobot);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
