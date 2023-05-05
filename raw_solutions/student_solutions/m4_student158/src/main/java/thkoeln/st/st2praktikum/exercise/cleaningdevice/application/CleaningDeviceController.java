package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cleaningDevices")
public class CleaningDeviceController {

    @Autowired
    CleaningDeviceService deviceService;

    @PostMapping("")
    public ResponseEntity<CleaningDevice> postCleaningDevice(@RequestBody CleaningDevice device){
        deviceService.addCleaningDevice(device);
        try{deviceService.getAllCleaningDevices();}catch (Exception e){};
        return ResponseEntity.status(201).body(device);
    }

    @GetMapping("")
    public ResponseEntity<List<CleaningDevice>> getCleaningDevices(){
        try{deviceService.getAllCleaningDevices();}catch (Exception e){};
        return ResponseEntity.status(200).body(deviceService.getAllCleaningDevices());
    }

    @GetMapping(value = "/{cleaningdevice-id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CleaningDevice> getCleaningDevice(@PathVariable(value="cleaningdevice-id")String id){
        return ResponseEntity.status(200).body(deviceService.getCleaningDevice(id));
    }

    @PatchMapping("/{cleaningdevice-id}")
    public ResponseEntity<CleaningDevice> patchCleaningDevice(@PathVariable(value="cleaningdevice-id")String id, @RequestBody CleaningDevice givenDevice){
        return ResponseEntity.status(200).body(deviceService.changeName(id, givenDevice));
    }

    @DeleteMapping("/{cleaningdevice-id}")
    public ResponseEntity deleteCleaningDevice(@PathVariable(value="cleaningdevice-id")String id){
        deviceService.deleteCleaningDevice(id);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("")
    public ResponseEntity deleteCleaningDevices(){
        deviceService.deleteCleaningDevices();
        return ResponseEntity.status(204).build();
    }

    @PostMapping(value = "/{cleaningdevice-id}/tasks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> postDeviceTask(@PathVariable(value="cleaningdevice-id")String id, @RequestBody Task task){
        deviceService.executeCommand(UUID.fromString(id), task);
        return ResponseEntity.status(201).body(task);
    }

    @DeleteMapping("/{cleaningdevice-id}/tasks")
    public ResponseEntity deleteDeviceTasks(@PathVariable(value="cleaningdevice-id")String id){
        deviceService.deleteTasks(id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/{cleaningdevice-id}/tasks")
    public ResponseEntity<List<Task>> getDeviceTasks(@PathVariable(value="cleaningdevice-id") String id){
        return ResponseEntity.status(200).body(deviceService.getAllTasks(id));
    }




}
