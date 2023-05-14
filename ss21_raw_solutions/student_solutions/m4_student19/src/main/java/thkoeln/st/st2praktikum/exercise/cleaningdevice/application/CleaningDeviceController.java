package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.apache.catalina.util.ResourceSet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import java.lang.annotation.Repeatable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CleaningDeviceController {

    @Autowired
    private CleaningDeviceService cleaningDeviceService;

    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/cleaningDevices")
    public Iterable<CleaningDevice> getAllCleaningDevices() {
        Iterable<CleaningDevice> found = cleaningDeviceService.getAllDevices();
        List<CleaningDevice> foundDtos = new ArrayList<>();
        for (CleaningDevice cleaningDevice : found) {
            foundDtos.add(modelMapper.map(cleaningDevice, CleaningDevice.class));
        }
        return foundDtos;
    }

    @PostMapping("/cleaningDevices")
    public ResponseEntity<?> createNewCleaningDevice(@RequestBody String cleaningDeviceName) {
        try {
            UUID newCleaningDeviceId = cleaningDeviceService.addCleaningDevice(cleaningDeviceName);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newCleaningDeviceId)
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(cleaningDeviceName);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDevice> getSpecificCleaningDevice(@PathVariable UUID id) {
        Optional<CleaningDevice> found = cleaningDeviceService.getCleaningDevice(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<CleaningDevice>(
                modelMapper.map(found.get(), CleaningDevice.class), HttpStatus.OK);
        }
    }

    @DeleteMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDevice> deleteSpecificCleaningDevice(@PathVariable UUID id) {
        Optional<CleaningDevice> found = cleaningDeviceService.getCleaningDevice(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            cleaningDeviceService.deleteCleaningDevice(found.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PatchMapping("/cleaningDevices/{id}")
    public ResponseEntity<String> changeCleaningDeviceName(@PathVariable UUID id, @RequestBody String cleaningDeviceName) {
        try {
            Optional<CleaningDevice> found = cleaningDeviceService.getCleaningDevice(id);
            if (found.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                CleaningDevice cleaningDevice = found.get();
                cleaningDevice.setName(cleaningDeviceName);
                return new ResponseEntity<>(cleaningDevice.getName(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/cleaningDevices/{id}/tasks")
    public ResponseEntity<?> giveCleaningDeviceATask(@PathVariable UUID id, @RequestBody Task task) {
        try {
            Optional<CleaningDevice> found = cleaningDeviceService.getCleaningDevice(id);
            if (found.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                var success = cleaningDeviceService.addTaskToCleaningDevice(id, task);
                return new ResponseEntity<>(task, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/cleaningDevices/{id}/tasks")
    public Iterable<Task> getCleaningDeviceTasks(@PathVariable UUID id) {
        Optional<CleaningDevice> cleaningDevice = cleaningDeviceService.getCleaningDevice(id);
        if (cleaningDevice.isEmpty()) {
            return new ArrayList<>();
        } else {
            Iterable<Task> foundTasks = cleaningDevice.get().getTasks();
            List<Task> foundDtos = new ArrayList<Task>();
            for (Task t : foundTasks) {
                foundDtos.add(modelMapper.map(t, Task.class));
            }

            return foundDtos;
        }
    }

    @DeleteMapping("/cleaningDevices/{id}/tasks")
    public ResponseEntity<Task> deleteCleaningDeviceTaskHistory(@PathVariable UUID id) {
        Optional<CleaningDevice> cleaningDevice = cleaningDeviceService.getCleaningDevice(id);
        if (cleaningDevice.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            cleaningDevice.get().clearTasks();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
