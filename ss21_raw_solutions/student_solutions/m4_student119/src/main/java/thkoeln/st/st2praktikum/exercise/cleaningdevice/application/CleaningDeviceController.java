package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.apache.coyote.Response;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.notFound;

@RestController()
@BasePathAwareController
public class CleaningDeviceController {

    private final CleaningDeviceRepository cleaningDeviceRepository;
    private final CleaningDeviceService cleaningDeviceService;

    public CleaningDeviceController(CleaningDeviceRepository cleaningDeviceRepository, CleaningDeviceService cleaningDeviceService) {
        this.cleaningDeviceRepository = cleaningDeviceRepository;
        this.cleaningDeviceService = cleaningDeviceService;
    }

    @GetMapping(path = "/cleaningDevices")
    Iterable<CleaningDevice> all() {
        final Iterable<CleaningDevice> devices = cleaningDeviceRepository.findAll();
        return devices;
    }

    @PostMapping(path = "/cleaningDevices")
    ResponseEntity<CleaningDevice> addDevice(@RequestBody CleaningDevice device) {
        if (device.getName().isEmpty())
            return ResponseEntity.badRequest().build();

        cleaningDeviceRepository.save(device);
        return new ResponseEntity<>(device, HttpStatus.CREATED);
    }

    @GetMapping(path = "/cleaningDevices/{deviceId}")
    ResponseEntity<CleaningDevice> getDevice(@PathVariable UUID deviceId) {
        final Optional<CleaningDevice> device = cleaningDeviceRepository.findById(deviceId);
        if (device.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(device.get());
    }


    @DeleteMapping(path = "/cleaningDevices/{deviceId}")
    ResponseEntity deleteDevice(@PathVariable UUID deviceId) {
        final Optional<CleaningDevice> device = cleaningDeviceRepository.findById(deviceId);
        if (device.isEmpty())
            return ResponseEntity.notFound().build();

        cleaningDeviceRepository.deleteById(deviceId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path = "/cleaningDevices/{deviceId}")
    ResponseEntity<CleaningDevice> patchDevice(@RequestBody CleaningDevice device, @PathVariable UUID deviceId) {

        final Optional<CleaningDevice> optionalCleaningDevice = cleaningDeviceRepository.findById(deviceId);
        if(optionalCleaningDevice.isEmpty())
            return ResponseEntity.notFound().build();

        if (device.getName().isEmpty())
            return ResponseEntity.badRequest().build();

        final CleaningDevice cleaningDevice = optionalCleaningDevice.get();

        cleaningDevice.setName(device.getName());

        cleaningDeviceRepository.save(cleaningDevice);
        return ResponseEntity.ok(device);
    }

    @PostMapping(path = "/cleaningDevices/{deviceId}/commands")
    ResponseEntity<Command> addDeviceCommand(@PathVariable UUID deviceId, @RequestBody Command command) {
        final Optional<CleaningDevice> device = cleaningDeviceRepository.findById(deviceId);
        if (device.isEmpty())
            return ResponseEntity.notFound().build();

        final CleaningDevice cleaningDevice = device.get();
        cleaningDevice.getCommands().add(command);

        cleaningDeviceRepository.save(cleaningDevice);
        cleaningDeviceService.executeCommand(deviceId, command);

        return new ResponseEntity<Command>(command, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/cleaningDevices/{deviceId}/commands")
    ResponseEntity deleteAllCleaningDeviceCommands(@PathVariable UUID deviceId) {
        final Optional<CleaningDevice> device = cleaningDeviceRepository.findById(deviceId);
        if (device.isEmpty())
            return ResponseEntity.notFound().build();

        final CleaningDevice cleaningDevice = device.get();

        cleaningDevice.getCommands().clear();

        cleaningDeviceRepository.save(cleaningDevice);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/cleaningDevices/{deviceId}/commands")
    ResponseEntity<Iterable<Command>> getAllCleaningDeviceCommands(@PathVariable UUID deviceId) {
        final Optional<CleaningDevice> device = cleaningDeviceRepository.findById(deviceId);
        if (device.isEmpty())
            return ResponseEntity.notFound().build();

        final CleaningDevice cleaningDevice = device.get();

        return ResponseEntity.ok(cleaningDevice.getCommands());
    }
}
