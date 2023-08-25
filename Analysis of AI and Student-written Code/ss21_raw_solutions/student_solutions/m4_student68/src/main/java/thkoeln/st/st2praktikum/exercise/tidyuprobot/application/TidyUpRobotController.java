package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.dto.TaskDto;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.dto.TidyUpRobotDto;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestController
public class TidyUpRobotController {

    @Autowired
    private TidyUpRobotService tidyUpRobotService;



// ===============================================[POST CREATE NEW ROBOT]================================================
    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> createNewTidyUpRobot( @RequestBody TidyUpRobotDto tidyUpRobotDto) {
        try {
            UUID robotId = tidyUpRobotService.addTidyUpRobot(tidyUpRobotDto.getName());

            URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{tidyUpRobot-id}")
                .buildAndExpand(robotId)
                .toUri();

            return ResponseEntity
                .created(returnURI)
                .body( tidyUpRobotDto);
        }
        catch( Exception e ) {
            // something bad happened -
             return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

// ===============================================[CREATE NEW ROBOT]====================================================
// ==================================================[ALTERNATIVE]======================================================
//    @PostMapping("/tidyUpRobotss")
//    public ResponseEntity<?> createNewTidyUpRobot2( @RequestBody TidyUpRobotDto tidyUpRobotDto) {
//        try {
//            TidyUpRobot tidyUpRobot = new TidyUpRobot();
//            ModelMapper mapper = new ModelMapper();
//            mapper.map(tidyUpRobotDto, tidyUpRobot);
//
//            UUID robotID = tidyUpRobotService.addTidyUpRobot(tidyUpRobot.getName());
//
//            URI resultURI = ServletUriComponentsBuilder
//                    .fromCurrentRequest()
//                    .path("/{tidyUpRobot-id}")
//                    .buildAndExpand(robotID)
//                    .toUri();
//
//            return ResponseEntity.created(resultURI)
//                    .body(tidyUpRobotDto);
//        }
//        catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
//        }
//
//    }

// ===================================================[GET ONE ROBOT]===================================================
    @GetMapping("/tidyUpRobots/{id}")
    @ResponseBody
    public ResponseEntity<TidyUpRobotDto> getOneTidyUpRobot( @PathVariable UUID id ) {
        // ServiceClass abfragen
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
        // Optional überprüfen
        if( found.isEmpty() ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            // Mapper-Klasse
            ModelMapper modelMapper = new ModelMapper();
            return new ResponseEntity<TidyUpRobotDto>( modelMapper.map( found.get(), TidyUpRobotDto.class ), HttpStatus.OK );
        }
    }


// ===================================================[GET ALL ROBOTS]==================================================
    @GetMapping("/tidyUpRobots")
    @ResponseBody
    public Iterable<TidyUpRobotDto> getAllTidyUpRobots() {
        // ServiceClass abfragen
        Iterable<TidyUpRobot> found = tidyUpRobotService.getAllTidyUpRobots();
        // Neue DTO Liste
        List foundDtos = new ArrayList<TidyUpRobotDto>();
        // Mapper-Klasse
        ModelMapper modelMapper = new ModelMapper();
        // Liste auf DTO-Liste mappen
        for ( TidyUpRobot robot : found )
            foundDtos.add( modelMapper.map( robot, TidyUpRobotDto.class ) );
        return foundDtos;
    }




// =================================================[DELETE ROBOT]======================================================
    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<?> deleteTidyUpRobot( @PathVariable UUID id ) {
        // Delete auswerten
        if( tidyUpRobotService.deleteTidyUpRobotById(id) ) {
            return new ResponseEntity( HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

// ==================================================[ALTERNATIVE]======================================================
// =================================================[DELETE ROBOT]======================================================
//    @DeleteMapping("/tidyUpRobots/{id}")
//    @ResponseBody
//    public ResponseEntity<TidyUpRobotDto> deleteTidyUpRobot( @PathVariable UUID id ) {
//        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
//        // Optional überprüfen
//        if( found.isEmpty() )
//            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
//        else {
//            tidyUpRobotService.deleteTidyUpRobot( found.get() );
//            return new ResponseEntity( HttpStatus.NO_CONTENT );
//        }
//    }


// =================================================[PATCH UPDATE ROBOT]=================================================
    @PatchMapping("/tidyUpRobots/{id}")
    public ResponseEntity<?> UpdateTidyUpRobot(@PathVariable UUID id, @RequestBody TidyUpRobotDto tidyUpRobotDto) {
        try {
            // ServiceClass abfragen
            Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
            // Optional überprüfen
            if (found.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                TidyUpRobot robot = found.get();
                // Mapper-Klasse
                ModelMapper modelMapper = new ModelMapper();
                modelMapper.map(tidyUpRobotDto, robot);
                // Update ausführen
                tidyUpRobotService.updateTidyUpRobot(robot);
                return new ResponseEntity<TidyUpRobotDto>( modelMapper.map(robot, TidyUpRobotDto.class),  HttpStatus.OK);
            }
        }
        catch( Exception e ) {
            // something bad happened -
             return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }



// ===============================================[POST CREATE NEW TASK]================================================
    @PostMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<?> createNewTask(@PathVariable UUID id, @RequestBody TaskDto taskDto) {
       try {
            Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
            if (found.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                Task task = new Task();
                //Mapper-Klasse
                ModelMapper modelMapper = new ModelMapper();
                modelMapper.map(taskDto, task);
                //Execute Task
                tidyUpRobotService.executeCommand(id, task);

                URI returnURI = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/tasks")
                        .buildAndExpand(task.toString())
                        .toUri();

                return ResponseEntity
                        .created(returnURI)
                        .body(taskDto);
            }
        }
        catch( Exception e ) {
            // something bad happened -
             return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }


// ===================================================[GET ALL ROBOT TASKS]=============================================
    @GetMapping("/tidyUpRobots/{id}/tasks")
    @ResponseBody
    public Iterable<TaskDto> getAllTidyUpRobotTasks(@PathVariable UUID id) {
        Iterable<Task> found = tidyUpRobotService.getTidyUpRobotById(id).get().getTasks();
        List foundDtos = new ArrayList<TaskDto>();
        // Mapper-Klasse
        ModelMapper modelMapper = new ModelMapper();
        for ( Task task : found )
            foundDtos.add( modelMapper.map( task, TaskDto.class ) );
        return foundDtos;
    }


// ======================================================[ TEST ]======================================================
// ===================================================[GET ALL ROBOT TASKS]=============================================
//    @GetMapping("/tidyUpRobots/{id}/tasks")
//    @ResponseBody
//    public Iterable<TaskDto> getAllTidyUpRobotTasks(@PathVariable UUID id) {
//        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
//        // Optional überprüfen
//        if( found.isEmpty() ) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        else {
//            Iterable<Task> foundTasks = found.get().getTasks();
//            List foundDtos = new ArrayList<TaskDto>();
//            // Mapper-Klasse
//            ModelMapper modelMapper = new ModelMapper();
//            for ( Task task : foundTasks )
//                foundDtos.add( modelMapper.map( task, TaskDto.class ) );
//            return foundDtos;
//        }
//    }


// =================================================[DELETE ROBOT]======================================================
    @DeleteMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<?> deleteTidyUpRobotTasks( @PathVariable UUID id ) {
        Optional<TidyUpRobot> found = tidyUpRobotService.getTidyUpRobotById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            found.get().getTasks().clear();
            return new ResponseEntity( HttpStatus.NO_CONTENT);
        }
    }


}
