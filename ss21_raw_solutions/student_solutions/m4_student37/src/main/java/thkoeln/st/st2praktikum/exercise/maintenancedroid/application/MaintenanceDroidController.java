package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.extra.World;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.DroidRepository;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class MaintenanceDroidController {
    private static Map<UUID, List<Task>> TaskHistory;

    private DroidRepository maintenanceDroidRepository;

    static
    {
        TaskHistory = new HashMap<>();
    }

    public MaintenanceDroidController(DroidRepository repository) {
        maintenanceDroidRepository = repository;
    }

    public void addTask(Task task, UUID id) {
        List<Task> list = TaskHistory.get(id);
        if (list==null) {
            list = new ArrayList<>();
            list.add(task);
            TaskHistory.put(id,list);
        }
        else {
            list.add(task);
            TaskHistory.replace(id,list);
        }
    }

    public List<Task> getTaskHistory(UUID id) {
        return TaskHistory.get(id);
    }

    public void deleteAllTasksFromDroid(UUID droidId) {
        TaskHistory.remove(droidId);
    }

    public void deleteTask(UUID id, Task task) {
        List<Task> list = TaskHistory.get(id);
        if (list.size()<=1) {
            list= null;
        }
        else {
            list.remove(task);
        }
        TaskHistory.replace(id,list);
    }
    public UUID addMaintenanceDroid(String name){
        MaintenanceDroid droid = new MaintenanceDroid(name);
        World.getInstance().addMaintenanceDroid(droid);
        saveMaintenanceDroid(droid);
        return droid.getId();
    }

    public Boolean executeTask(UUID maintenanceDroidId, Task task)
    {
        MaintenanceDroid maintenanceDroid = findMaintenanceDroid(maintenanceDroidId);
        boolean result = World.getInstance().sendcommand(maintenanceDroid,task);
        addTask(task,maintenanceDroidId);
        saveMaintenanceDroid(maintenanceDroid);
        return result;
    }

    public void saveMaintenanceDroid(MaintenanceDroid droid)
    {
        maintenanceDroidRepository.save(droid);
    }

    public MaintenanceDroid findMaintenanceDroid(UUID droidID)
    {
        return maintenanceDroidRepository.findById(droidID).orElseThrow(()->new RuntimeException());
    }


    @GetMapping("/maintenanceDroids")
    public @ResponseBody Iterable<MaintenanceDroid> getAllMaintenanceDroids(){return maintenanceDroidRepository.findAll();}

    @PostMapping("/maintenanceDroids")
    public @ResponseBody MaintenanceDroid createMaintenanceDroid(@RequestBody MaintenanceDroid maintenanceDroid, HttpServletResponse response) {
        UUID droid = addMaintenanceDroid(maintenanceDroid.getName());
        response.setStatus(201);
        return findMaintenanceDroid(droid);
    }

    @GetMapping("/maintenanceDroids/{maintenanceDroid-id}")
    public @ResponseBody MaintenanceDroid getMaintenanceDroid(@PathVariable("maintenanceDroid-id") String maintenanceDroidId){
        return findMaintenanceDroid(UUID.fromString(maintenanceDroidId));
    }

    @DeleteMapping("/maintenanceDroids/{maintenanceDroid-id}")
    public void deleteMaintenanceDroid(@PathVariable("maintenanceDroid-id")String maintenanceDroidId,HttpServletResponse response){
        maintenanceDroidRepository.deleteById(UUID.fromString(maintenanceDroidId));
        response.setStatus(204);
    }

    @PatchMapping("/maintenanceDroids/{maintenanceDroid-id}")
    public @ResponseBody MaintenanceDroid changeMaintenanceDroidName(@PathVariable("maintenanceDroid-id") String maintenanceDroidId,@RequestBody MaintenanceDroid maintenanceDroid){
        MaintenanceDroid droid = findMaintenanceDroid(UUID.fromString(maintenanceDroidId));
        droid.setName(maintenanceDroid.getName());
        saveMaintenanceDroid(droid);
        return droid;
    }

    @PostMapping("/maintenanceDroids/{maintenanceDroid-id}/tasks")
    public Task postTask(@RequestBody Task task,@PathVariable("maintenanceDroid-id") String maintenanceDroidId, HttpServletResponse response){
        executeTask(UUID.fromString(maintenanceDroidId),task);
        response.setStatus(201);
        return task;
    }

    @GetMapping("/maintenanceDroids/{maintenanceDroid-id}/tasks")
    public @ResponseBody Iterable<Task> getAllTasks(@PathVariable("maintenanceDroid-id") String maintenanceDroidId){
        List<Task> tasks = getTaskHistory(UUID.fromString(maintenanceDroidId));
        return tasks;
    }

    @DeleteMapping("/maintenanceDroids/{maintenanceDroid-id}/tasks")
    public @ResponseBody void deleteTask(@PathVariable("maintenanceDroid-id") String maintenanceDroidId, HttpServletResponse response)
    {
        deleteAllTasksFromDroid(UUID.fromString(maintenanceDroidId));

        response.setStatus(204);
    }




}
