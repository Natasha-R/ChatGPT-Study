package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.RobotDTO;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@RestController
public class TidyUpRobotController {

    private final TidyUpRobotService tidyUpRobotService;
    private final TidyUpRobotRepo tidyUpRobotRepo;

    public TidyUpRobotController(TidyUpRobotService tidyUpRobotService, TidyUpRobotRepo tidyUpRobotRepo) {
        this.tidyUpRobotService = tidyUpRobotService;

        this.tidyUpRobotRepo = tidyUpRobotRepo;
    }

    @PostMapping("/tidyUpRobots/{tidyUpRobotId}/tasks")
    @ResponseStatus(code = HttpStatus.CREATED)
    private Task postTidyUpRobotMoveTask(@PathVariable UUID tidyUpRobotId, @RequestBody Task task){
        tidyUpRobotService.executeCommand(tidyUpRobotId,task);
        return task;
    }

    @PostMapping("/tidyUpRobots")
    @ResponseStatus(HttpStatus.CREATED)
    private RobotDTO addTidyUpRobot(@RequestBody TidyUpRobot body){
        UUID id = tidyUpRobotService.addTidyUpRobot(body.getName());
        RobotDTO robotdto = new RobotDTO();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(tidyUpRobotService.findTidyUpRobot(id),robotdto);
        return robotdto;


    }

    @GetMapping("/tidyUpRobots")
    @ResponseStatus(value = HttpStatus.OK)
    public List<RobotDTO> getAllTidyUpRobots() {
        List<TidyUpRobot> tidyUpRobotList = tidyUpRobotService.getAllTidyUpRobots();
        List<RobotDTO> robotDTOList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        for(TidyUpRobot tidyUpRobot : tidyUpRobotList){
            RobotDTO robotDTO = new RobotDTO();
            modelMapper.map(tidyUpRobot,robotDTO);
            robotDTOList.add(robotDTO);
        }
        return robotDTOList;
    }

    @GetMapping("/tidyUpRobots/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RobotDTO getOneTidyUpRobot(@PathVariable UUID id){
        TidyUpRobot tidyUpRobot = tidyUpRobotService.findTidyUpRobot(id);
        return new RobotDTO(tidyUpRobot.getName());
    }

    @PatchMapping("/tidyUpRobots/{id}")
    public RobotDTO patchTidyUpRobot(@PathVariable UUID id, @RequestBody TidyUpRobot tidyUpRobot){
        return new RobotDTO(tidyUpRobot.getName());
    }


    @DeleteMapping("/tidyUpRobots/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTidyUpRobot(@PathVariable UUID id){
        tidyUpRobotRepo.deleteTidyUpRobotById(id);
    }

    @DeleteMapping("/tidyUpRobots/{id}/tasks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTasksFromRobot(@PathVariable UUID id){
        tidyUpRobotService.findTidyUpRobot(id).setTasks(null);
    }

    @GetMapping("/tidyUpRobots/{id}/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllTasksFromRobot(@PathVariable UUID id){
        return tidyUpRobotService.findTidyUpRobot(id).getTasks();
    }



}
