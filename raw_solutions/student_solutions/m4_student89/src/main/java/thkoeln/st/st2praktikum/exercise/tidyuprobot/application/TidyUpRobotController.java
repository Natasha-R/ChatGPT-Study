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
import thkoeln.st.st2praktikum.exercise.tidyuprobot.dto.TaskDto;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.dto.TidyUpRobotDto;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestController
public class TidyUpRobotController {




    private final TidyUpRobotService tidyUpRobotService;
    @Autowired
    public TidyUpRobotController(TidyUpRobotService tidyUpRobotService) {
        this.tidyUpRobotService = tidyUpRobotService;
    }



    @RequestMapping(method = RequestMethod.GET, value ="/tidyUpRobots")
    public @ResponseBody Iterable<TidyUpRobotDto> getAllTidyUpRobots() {
        Iterable<TidyUpRobot> found = tidyUpRobotService.finAll();
        List foundDtos = new ArrayList<TidyUpRobotDto>();
        ModelMapper modelMapper = new ModelMapper();
        for ( TidyUpRobot sg : found ) {
            foundDtos.add( modelMapper.map( sg, TidyUpRobotDto.class ) );
        }
        return foundDtos;
    }



    @PostMapping("/tidyUpRobots")
    public ResponseEntity<?> createNewTidyUprobot(@RequestBody TidyUpRobotDto tidyUpRobotDto) {
        try {
            TidyUpRobot newTidyUpRobot = new TidyUpRobot();
            ModelMapper modelMapper=new ModelMapper();
            modelMapper.map( tidyUpRobotDto, newTidyUpRobot );
            tidyUpRobotService.addTidyUpRobot( newTidyUpRobot.getName() );
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newTidyUpRobot.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body( tidyUpRobotDto );
        }
        catch( Exception e ) {
            return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }
    @PostMapping("/tidyUpRobots/{tidyUpRobot-id}/tasks")
    public ResponseEntity<?> giveTidyUpRobotNewTask(@PathVariable("tidyUpRobot-id") UUID id , @RequestBody TaskDto taskDto) {
        try {
            Optional<TidyUpRobot> found = Optional.ofNullable(tidyUpRobotService.getTidyUpRobotByID(id));

            if( found.isEmpty())
                return new ResponseEntity<>( HttpStatus.NOT_FOUND );
            else {
                ModelMapper modelMapper=new ModelMapper();
                Task taskent = modelMapper.map( taskDto, Task.class );
                tidyUpRobotService.executeCommand(id,taskent);
                URI returnURI = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(found.get().getId())
                        .toUri();
                return ResponseEntity
                        .created(returnURI)
                        .body( taskDto );
            }
        }


        catch( Exception e ) {
            return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

    @RequestMapping(method = RequestMethod.GET, value ="/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobotDto> getOneTidyUpRobot( @PathVariable("tidyUpRobot-id") UUID id) {
        Optional<TidyUpRobot> found = Optional.ofNullable(tidyUpRobotService.getTidyUpRobotByID(id));
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            ModelMapper modelMapper= new ModelMapper();
            return new ResponseEntity<TidyUpRobotDto>(
                    modelMapper.map( found.get(), TidyUpRobotDto.class ), HttpStatus.OK );
        }
    }

    @RequestMapping(method = RequestMethod.GET, value ="/tidyUpRobots/{tidyUpRobot-id}/tasks")
    public  @ResponseBody Iterable<TaskDto> getTasksFromTidyUpRobot(@PathVariable("tidyUpRobot-id") UUID id ) {
        Optional<TidyUpRobot> found = Optional.ofNullable(tidyUpRobotService.getTidyUpRobotByID(id));
        if( found.isEmpty() )
            throw new UnsupportedOperationException();
        else {
            Iterable<Task> foundTasks = found.get().getTaskList();
            List foundTasksDto = new ArrayList<TaskDto>();
            ModelMapper modelMapper= new ModelMapper();

            for ( Task sg : foundTasks ) {
                foundTasksDto.add( modelMapper.map( sg, TaskDto.class ) );
            }
            return foundTasksDto;


        }
    }

    @RequestMapping(method = RequestMethod.PATCH,value = "/tidyUpRobots/{tidyUpRobot-id}")
    public ResponseEntity<TidyUpRobotDto> updateTidyUpRobotName( @PathVariable("tidyUpRobot-id") UUID id , @RequestBody TidyUpRobotDto tidyUpRobotDto) {

        Optional<TidyUpRobot> found = Optional.ofNullable(tidyUpRobotService.getTidyUpRobotByID(id));
        if( found.isEmpty())
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            found.get().setName(tidyUpRobotDto.getName());
            tidyUpRobotService.updateRobots(found.get());
            ModelMapper modelMapper= new ModelMapper();
            return new ResponseEntity<>(
                    modelMapper.map( found.get(), TidyUpRobotDto.class ), HttpStatus.OK );
        }
    }


    @RequestMapping(method = RequestMethod.DELETE,value = "/tidyUpRobots/{tidyUpRobot-id}/tasks")
    public ResponseEntity<TaskDto> deleteTaskListFromTidyUpRobot( @PathVariable("tidyUpRobot-id") UUID id ) {

        Optional<TidyUpRobot> found = Optional.ofNullable(tidyUpRobotService.getTidyUpRobotByID(id));
        if( found.isEmpty())
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            if (found.get().clearTaskHistory()) {
                ModelMapper modelMapper = new ModelMapper();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
    }

}
