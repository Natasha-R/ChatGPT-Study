package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.repo.CleaningDeviceRepositry;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CleaningDeviceController {
    @Autowired
    CleaningDeviceRepositry cleaningDeviceRepositry;
    @Autowired
    CleaningDeviceService cleaningDeviceService;

    @RequestMapping(value = "/cleaningDevices", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})

    public List<CleaningDevice> getAllCleaningDevices() {
        return (List<CleaningDevice>) cleaningDeviceRepositry.findAll();
    }

    @RequestMapping(value = "/cleaningDevices", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public CleaningDevice saveCleaningDevices(@RequestBody CleaningDevice cleaningDevice) {
        CleaningDevice cleaningDevice1 = new CleaningDevice(cleaningDevice.getName());
        cleaningDevice1 = cleaningDeviceRepositry.save(cleaningDevice1);
        return cleaningDevice1;
    }

    @RequestMapping(value = "/cleaningDevices/{cleaningDevice-id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CleaningDevice getCleaningDeviceById(@PathVariable("cleaningDevice-id") UUID cleaningDeviceId) {
        Optional<CleaningDevice> cleaningDevice1 = cleaningDeviceRepositry.findById(cleaningDeviceId);
        if (cleaningDevice1.isPresent()) return cleaningDevice1.get();
        return null;
    }


    @DeleteMapping(value = "/cleaningDevices/{cleaningDevice-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCleaningDeviceById(@PathVariable("cleaningDevice-id") UUID cleaningDeviceId) {
        cleaningDeviceRepositry.deleteById(cleaningDeviceId);
    }

    @RequestMapping(value = "/cleaningDevices/{cleaningDevice-id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public CleaningDevice patchCleaningDevice(@PathVariable("cleaningDevice-id") UUID cleaningDeviceId, @RequestBody CleaningDevice cleaningDevice) {
        cleaningDevice.setCleaningDeviceId(cleaningDeviceId);
        return cleaningDeviceRepositry.save(cleaningDevice);
    }

    @RequestMapping(value = "/cleaningDevices/{cleaningDevice-id}/tasks", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Task postCleaningDeviceMoveTask(@PathVariable("cleaningDevice-id") UUID cleaningDeviceId,
                                           @RequestBody Task task) {
        cleaningDeviceService.executeCommand(cleaningDeviceId, task);

        return task;
    }

    @RequestMapping(value = "/cleaningDevices/{cleaningDevice-id}/tasks", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Task> getAllCleaningDeviceTasks(@PathVariable("cleaningDevice-id") UUID cleaningDeviceId) {
        Optional<CleaningDevice> cleaningDevice = cleaningDeviceRepositry.findById(cleaningDeviceId);
        if (cleaningDevice.isPresent()) return cleaningDevice.get().getTasks();
        return null;
    }

    @DeleteMapping(value = "/cleaningDevices/{cleaningDevice-id}/tasks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCleaningDeviceTasks(@PathVariable("cleaningDevice-id") UUID cleaningDeviceId) {
        Optional<CleaningDevice> cleaningDevice = cleaningDeviceRepositry.findById(cleaningDeviceId);
        if (cleaningDevice.isPresent()) {
            cleaningDevice.get().getTasks().clear();
            cleaningDeviceRepositry.save(cleaningDevice.get());
        }
    }

}
