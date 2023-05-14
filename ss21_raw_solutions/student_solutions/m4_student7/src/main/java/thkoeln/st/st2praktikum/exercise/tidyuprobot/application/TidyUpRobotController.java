package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import com.fasterxml.jackson.annotation.JsonValue;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.json.patch.Patch;
import org.springframework.data.web.JsonPath;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskDto;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskDtoMapper;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class TidyUpRobotController {
    TidyUpRobotService tidyUpRobotService;
    TransportCategoryService transportCategoryService;
    RoomService roomService;

    TidyUpRobotDtoMapper tidyUpRobotDtoMapper = new TidyUpRobotDtoMapper();
    TaskDtoMapper taskDtoMapper = new TaskDtoMapper();

    @Autowired
    TidyUpRobotController(TidyUpRobotService tidyUpRobotService,
                          TransportCategoryService transportCategoryService,
                          RoomService roomService){
        this.tidyUpRobotService = tidyUpRobotService;
        this.transportCategoryService = transportCategoryService;
        this.roomService = roomService;

        this.transportCategoryService.setRoomService(this.roomService);
        this.transportCategoryService.setTidyUpRobotService(this.tidyUpRobotService);

        this.tidyUpRobotService.setRoomService(this.roomService);
        this.tidyUpRobotService.setTransportCategoryService(this.transportCategoryService);
    }

    //GET /tidyUpRobots
    @GetMapping("/tidyUpRobots")
    public Iterable<TidyUpRobotDto> getAllTidyUpRobots() {
        Iterable<TidyUpRobot> allTidyUpRobots = tidyUpRobotService.getAll();
        List<TidyUpRobotDto> allDtos = new ArrayList<>();
        for ( TidyUpRobot tidyUpRobot : allTidyUpRobots )
            allDtos.add( tidyUpRobotDtoMapper.mapToDto(tidyUpRobot) );
        return allDtos;
    }

    // POST /tidyUpRobots
    @PostMapping("/tidyUpRobots")
    public ResponseEntity<TidyUpRobotDto> createNewTidyUpRobot(@RequestBody TidyUpRobotDto tidyUpRobotDto){
        try{
            TidyUpRobot tidyUpRobot = tidyUpRobotService.addTidyUpRobotFromDto(tidyUpRobotDto);

            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(tidyUpRobot.getId())
                    .toUri();
            TidyUpRobotDto createdTidyUpRobotDto = tidyUpRobotDtoMapper.mapToDto(tidyUpRobot);
            return ResponseEntity
                    .created(returnURI)
                    .body( createdTidyUpRobotDto );

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //GET /tidyUpRobots/{id}
    @GetMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobotDto> getOneTidyUpRobots(@PathVariable UUID id) {
        TidyUpRobot found = tidyUpRobotService.getRobotByRobotId(id);
        if( found == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<TidyUpRobotDto>(
                    tidyUpRobotDtoMapper.mapToDto(found), HttpStatus.OK
            );
        }
    }

    //DELETE /tidyUpRobots/{id}
    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobotDto> deleteOneTidyUpRobots(@PathVariable UUID id) {
        TidyUpRobot found = tidyUpRobotService.getRobotByRobotId(id);
        if( found == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            tidyUpRobotService.deleteOneTidyUpRobot(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


    //PATCH /tidyUpRobots/{id}

    @PatchMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobotDto> patchTidyUpRobots(@PathVariable UUID id, @RequestBody TidyUpRobotDto tidyUpRobotDto){
        TidyUpRobot found = tidyUpRobotService.getRobotByRobotId(id);
        if( found == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            tidyUpRobotService.patchTidyUpRobot(id, tidyUpRobotDto.getName());

            TidyUpRobotDto createdTidyUpRobotDto = tidyUpRobotDtoMapper.mapToDto(tidyUpRobotService.getRobotByRobotId(id));
            return ResponseEntity
                    .ok()
                    .body( createdTidyUpRobotDto );
        }
    }

    //POST /tidyUpRobots/{tidyUpRobot-id}/tasks
    @PostMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<TaskDto> createNewTidyUpRobotTask(@PathVariable UUID id, @RequestBody Task task){
        try{
            TidyUpRobot found = tidyUpRobotService.getRobotByRobotId(id);
            if( found == null )
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else {

                tidyUpRobotService.executeCommand(id,task);

                URI returnURI = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(task.toString())
                        .toUri();
                TaskDto createdTaskDto = taskDtoMapper.mapToDto(task);
                return ResponseEntity
                        .created(returnURI)
                        .body(createdTaskDto);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //GET /tidyUpRobots/{tidyUpRobot-id}/tasks
    @GetMapping("/tidyUpRobots/{id}/tasks")
    public Iterable<TaskDto> getAllTidyUpRobotTask(@PathVariable UUID id) {
        TidyUpRobot found = tidyUpRobotService.getRobotByRobotId(id);
        if( found == null )
            throw new UnsupportedOperationException("No TidyUpRobot with given Id");
        else {
            Iterable<Task> allTasks = found.getTasks();
            List<TaskDto> allDtos = new ArrayList<>();
            for ( Task task : allTasks )
                allDtos.add( taskDtoMapper.mapToDto(task) );
            return allDtos;
        }
    }

    //DELETE /tidyUpRobots/{tidyUpRobot-id}/tasks
    @DeleteMapping("/tidyUpRobots/{id}/tasks")
    public ResponseEntity<TidyUpRobotDto> deleteAllTidyUpRobotTasks(@PathVariable UUID id) {
        TidyUpRobot found = tidyUpRobotService.getRobotByRobotId(id);
        if( found == null )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            found.setTasks(new ArrayList<>());
            tidyUpRobotService.persistNewData();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
