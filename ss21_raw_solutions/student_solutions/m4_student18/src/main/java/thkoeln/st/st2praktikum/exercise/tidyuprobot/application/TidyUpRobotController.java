package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.ApplicationController;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TidyUpRobotController {

    ApplicationController applicationController;

    @Autowired
    public TidyUpRobotController(ApplicationController applicationController){
        this.applicationController = applicationController;
    }


    // 1. Get all tidy-up robots
    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobot> getAllTidyUpRobots() {
        return applicationController.getTidyUpRobotRepository().findAll();
    }


    // 2. Create a new tidy-up robot
    @PostMapping("/tidyUpRobots")
    public ResponseEntity<TidyUpRobot> createNewTidyUpRobots(@RequestBody TidyUpRobot tidyUpRobot ) {
        TidyUpRobot newTidyUpRobot = applicationController.getTidyUpRobotRepository().save(tidyUpRobot);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTidyUpRobot.getId())
                .toUri();
        return ResponseEntity
                .created(returnURI)
                .body( tidyUpRobot );
    }


    // 3. Get a specific tidy-up robot by ID
    @GetMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> getOneTidyUpRobot(@PathVariable UUID id ) {
        Optional<TidyUpRobot> tidyUpRobot = applicationController.getTidyUpRobotRepository().findById(id);
        if (tidyUpRobot.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity(tidyUpRobot, HttpStatus.OK);
        }
    }


    // 4. Delete a specific tidy-up robot
    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> deleteOneTidyUpRobot( @PathVariable UUID id ) {
        Optional<TidyUpRobot> tidyUpRobot = applicationController.getTidyUpRobotRepository().findById( id );
        if( tidyUpRobot.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            applicationController.getTidyUpRobotRepository().delete( tidyUpRobot.get() );
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }
    }


    // 5. Change the name of a specific tidy-up robot
    @PatchMapping("/tidyUpRobots/{id}")
    ResponseEntity<?> postCommand(@RequestBody String input, @PathVariable UUID id) throws JSONException {

        TidyUpRobot tidyUpRobot = applicationController.getTidyUpRobotRepository().findById(id).get();
        String name = new JSONObject(input).get("name").toString();

        tidyUpRobot.setName(name);
        applicationController.getTidyUpRobotRepository().save(tidyUpRobot);

        return ResponseEntity
                .ok()
                .body( tidyUpRobot );
    }


    // 6. Give a specific tidy-up robot a command
    @PostMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity<Command> postNewCommand(@PathVariable UUID id, @RequestBody Command command) {

        TidyUpRobot tidyUpRobot = applicationController.getTidyUpRobotRepository().findById(id).get();

        applicationController.execute(tidyUpRobot.getId(), command);
        applicationController.getTidyUpRobotRepository().save(tidyUpRobot);

        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/commands")
                .buildAndExpand(command)
                .toUri();
        return ResponseEntity
                .created(returnURI)
                .body( command );
    }


    // 7. List all the commands a specific tidy-up robot has received so far
    @GetMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity<Command> getAllTidyUpRobotCommands(@PathVariable UUID id ) {
        List<Command> commands = applicationController.getTidyUpRobotRepository().findById(id).get().getCommands();
        if (commands.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity(commands, HttpStatus.OK);
        }
    }


    // 8. Delete the command history of a specific tidy-up robot
    @DeleteMapping("/tidyUpRobots/{id}/commands")
    public ResponseEntity<Command> deleteAllCommandsOfOneTidyUpRobot( @PathVariable UUID id ) {
        Optional<TidyUpRobot> tidyUpRobot = applicationController.getTidyUpRobotRepository().findById( id );
        if( tidyUpRobot.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            tidyUpRobot.get().setCommandsNull();
            applicationController.getTidyUpRobotRepository().save( tidyUpRobot.get() );
            return new ResponseEntity( HttpStatus.NO_CONTENT );
        }
    }



}
