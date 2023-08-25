package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.dto.CleaningDeviceDto;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CleaningDeviceController {

    @Autowired
    private CleaningDeviceService service;

    private ModelMapper modelMapper = new ModelMapper();



    @GetMapping("/cleaningDevices")
    public List<CleaningDevice> getAllCleaningDevices() {
        return service.cleaningDeviceRepo.findAll();
    }

    @PostMapping("/cleaningDevices")
    public ResponseEntity<?> addCleaningDevice(@RequestBody CleaningDeviceDto cleaningDeviceDto) {
        CleaningDevice newCleaningDevice = new CleaningDevice();
        modelMapper.map(cleaningDeviceDto, newCleaningDevice);
        service.cleaningDeviceRepo.save(newCleaningDevice);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{cleaningDevice-id}")
                .buildAndExpand(newCleaningDevice.getCleaningDeviceId())
                .toUri();
        return new ResponseEntity<>(cleaningDeviceDto,HttpStatus.CREATED);
    }


    @GetMapping("/cleaningDevices/{cleaningDevice-id}")
    public ResponseEntity<CleaningDeviceDto> getCleaningDevice(@PathVariable(value = "cleaningDevice-id") UUID cleaningDeviceId) {
        Optional<CleaningDevice> found = service.cleaningDeviceRepo.findById( cleaningDeviceId );
        if( found.isEmpty() )
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<CleaningDeviceDto>(
                    modelMapper.map( found.get(), CleaningDeviceDto.class),HttpStatus.OK);
        }
    }


    @DeleteMapping("/cleaningDevices/{cleaningDevice-id}")
    @ResponseBody
    public ResponseEntity<CleaningDevice> deleteCleaningDevice(@PathVariable(value = "cleaningDevice-id") UUID cleaningDeviceId) {

        CleaningDevice cleaningDevice = service.cleaningDeviceRepo.findById( cleaningDeviceId )
                .orElseThrow( () -> new EntityNotFoundException());

        service.cleaningDeviceRepo.delete( cleaningDevice );

        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }


    @PatchMapping("/cleaningDevices/{cleaningDevice-id}")
    public ResponseEntity<CleaningDevice> updateCleaningDevice(@PathVariable(value = "cleaningDevice-id") UUID cleaningDeviceId,@RequestBody CleaningDevice cleaningDevice) {

        CleaningDevice newCleaningDevice = service.cleaningDeviceRepo.findById( cleaningDeviceId ).orElseThrow( () -> new EntityNotFoundException());

        newCleaningDevice.setName(cleaningDevice.getName());

        service.cleaningDeviceRepo.save( newCleaningDevice );

        return new ResponseEntity( newCleaningDevice , HttpStatus.OK );
    }



    @PostMapping("/cleaningDevices/{cleaningDevice-id}/tasks")
    public ResponseEntity<?> addCleaningDeviceTask(@PathVariable(value = "cleaningDevice-id") UUID cleaningDeviceId, @RequestBody Task task) {

        CleaningDevice newCleaningDevice = service.cleaningDeviceRepo.findById( cleaningDeviceId ).orElseThrow( () -> new EntityNotFoundException());

        newCleaningDevice.setTasks( task );

        service.cleaningDeviceRepo.save( newCleaningDevice );

        service.executeCommand( newCleaningDevice.getCleaningDeviceId() , task );

        Task addedTask = null;

        if(newCleaningDevice.getTasks().size() == 1)
            addedTask = newCleaningDevice.getTasks().get(0);
        else if(newCleaningDevice.getTasks().size() > 1)
            addedTask = newCleaningDevice.getTasks().get(newCleaningDevice.getTasks().size() - 1);

        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{taskType}")
                .buildAndExpand(addedTask.getTaskType())
                .toUri();
        return ResponseEntity
                .created(returnURI)
                .body(task);
    }




    @GetMapping("/cleaningDevices/{cleaningDevice-id}/tasks")
    @ResponseBody
    public List<Task> getAllCleaningDeviceTasks(@PathVariable(value = "cleaningDevice-id" ) UUID cleaningDeviceId) {
        CleaningDevice newCleaningDevice = service.cleaningDeviceRepo.findById( cleaningDeviceId )
                .orElseThrow(() -> new EntityNotFoundException());

        return newCleaningDevice.getTasks();
    }


    @DeleteMapping("/cleaningDevices/{cleaningDevice-id}/tasks")
    public ResponseEntity<?> deleteAllCleaningDeviceTasks(@PathVariable(value = "cleaningDevice-id") UUID cleaningDeviceId) {

        CleaningDevice newCleaningDevice = service.cleaningDeviceRepo.findById( cleaningDeviceId )
                .orElseThrow(() -> new EntityNotFoundException());

        newCleaningDevice.getTasks().clear();
        service.cleaningDeviceRepo.save(newCleaningDevice);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
