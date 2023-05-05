package thkoeln.st.st2praktikum.exercise;

import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.Task;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.TidyUpRobotService;

import java.util.List;
import java.util.UUID;

@RestController
public class TidyUpRobotController {

    private final TidyUpRobotService tidyUpRobotService;

    public TidyUpRobotController(TidyUpRobotService tidyUpRobotService) {
        this.tidyUpRobotService = tidyUpRobotService;
    }

    @GetMapping("/tidyUpRobots")
    public List<TidyUpRobot> getAllTidyUpRobots(){
        return tidyUpRobotService.getAllTidyUpRobots();
    }

    @GetMapping("/tidyUpRobots/get/{tidyUpRobot_id}")
    public TidyUpRobot getTidyUpRobotById(@PathVariable UUID tidyUpRobot_id){
        return tidyUpRobotService.findTidyUpRobot(tidyUpRobot_id);
    }

    @PostMapping("/tidyUpRobots")
    public void createTidyUpRobot(@RequestParam String name){
        tidyUpRobotService.addTidyUpRobot(name);
    }

    @DeleteMapping("tidyUpRobots/delete/{tidyUpRobot_id}")
    public void deleteTidyUpRobot(@PathVariable UUID tidyUpRobot_id){
        tidyUpRobotService.deleteTidyUpRobot(tidyUpRobot_id);
    }

    @PatchMapping("tidyUpRobots/change/name/{tidyUpRobot_id}")
    public void tidyUpRobotChangeName(@PathVariable UUID tidyUpRobot_id,@RequestParam String name){
        tidyUpRobotService.changeNameOfTidyUpRobot(tidyUpRobot_id,name);
    }


    @PostMapping("tidyUpRobots/newTask/{tidyUpRobot_id}/{taskString}")
    public void giveNewTaskToTidyUpRobot(@PathVariable UUID tidyUpRobot_id, @PathVariable String taskString){
        tidyUpRobotService.executeCommand(tidyUpRobot_id,new Task(taskString));
    }

    @GetMapping("tidyUpRobots/taskList/{tidyUpRobot_id}")
    public List<Task> listAllTasks(@PathVariable UUID tidyUpRobot_id){
        return tidyUpRobotService.getReceivedTasksList(tidyUpRobot_id);
    }

    @DeleteMapping("tidyUpRobots/deleteTaskList/{tidyUpRobot_id}")
    public void deleteTaskListofTidyUpRobot(@PathVariable UUID tidyUpRobot_id){
        tidyUpRobotService.deleteReceivedTasksList(tidyUpRobot_id);
    }


}
