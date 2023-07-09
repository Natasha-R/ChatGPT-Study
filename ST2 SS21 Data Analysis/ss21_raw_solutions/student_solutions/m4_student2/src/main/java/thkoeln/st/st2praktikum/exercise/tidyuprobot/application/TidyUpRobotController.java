package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.Place;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.room.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.transportsystem.TransportSystemRepository;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestController
@RestController
public class TidyUpRobotController {
    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;

    @Autowired
    private TidyUpRobotService tidyUpRobotService;

    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobot> getAllTidyUpRobot(){
        return tidyUpRobotRepository.findAll();
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> createNewTidyUpRobot(@RequestBody TidyUpRobot tidyUpRobot){
        try{
            TidyUpRobot newTidyUpRobot = new TidyUpRobot();
            new ModelMapper().map(tidyUpRobot, newTidyUpRobot);
            tidyUpRobotRepository.save(newTidyUpRobot);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newTidyUpRobot.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(tidyUpRobot);
        }
        catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> getTidyUpRobotById(@PathVariable UUID id){
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<TidyUpRobot>(new ModelMapper().map(found.get(), TidyUpRobot.class), HttpStatus.OK);
        }

    }

    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> deleteTidyUpRobotById(@PathVariable UUID id){
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            tidyUpRobotRepository.delete(found.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/tidyUpRobots/{id}")
    public ResponseEntity<?> changeNameTidyUpRobot(@PathVariable UUID id, @RequestBody TidyUpRobot tidyUpRobot){
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            TidyUpRobot robot = found.get();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.map(tidyUpRobot, robot);
            tidyUpRobotRepository.save(robot);
            return new ResponseEntity<TidyUpRobot>(new ModelMapper().map(robot, TidyUpRobot.class), HttpStatus.OK);
        }
    }

    @PostMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity<?> giveCommandToTidyUpRobot(@PathVariable UUID id, @RequestBody Command command){
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            TidyUpRobot robot = found.get();
            robot.addCommand(command);
            tidyUpRobotService.executeCommand(id,command);
            new ModelMapper().map(robot, TidyUpRobot.class);
            tidyUpRobotRepository.save(robot);

            return new ResponseEntity<Command>(robot.getCommands().get(robot.getCommands().size()-1), HttpStatus.CREATED);
        }
    }

    @GetMapping("/tidyUpRobots/{id}/commands")
    public Iterable<Command> getAllCommandFromOneTidyUpRobot(@PathVariable UUID id){
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if(found.isEmpty()){
            throw new RuntimeException();
        }
        else {
            return tidyUpRobotRepository.findById(id).get().getCommands();
        }
    }

    @DeleteMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity<TidyUpRobot> deleteCommandFromOneTidyUpRobot(@PathVariable UUID id){
        Optional<TidyUpRobot> found = tidyUpRobotRepository.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            found.get().deleteCommand();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
