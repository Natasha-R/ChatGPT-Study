package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class CleaningDeviceController {
    private CleaningDeviceService cleaningDeviceService;
    private CleaningDeviceDtoMapper cleaningDeviceDtoMapper = new CleaningDeviceDtoMapper();

    @Autowired
    public CleaningDeviceController(CleaningDeviceService cleaningDeviceService)
    {
        this.cleaningDeviceService = cleaningDeviceService;
    }

    @GetMapping("/cleaningDevices")
    public Iterable<CleaningDeviceDto> getAllCleaningDevices(){
        Iterable<CleaningDevice> allCleaningDevices = cleaningDeviceService.findAllCleaningDevices();
        List<CleaningDeviceDto> allDtos = new ArrayList<>();
        for (CleaningDevice cleaningDevice : allCleaningDevices)
        {
            allDtos.add(cleaningDeviceDtoMapper.mapToDto(cleaningDevice));
        }
        return allDtos;
    }

    @PostMapping("/cleaningDevices")
    public ResponseEntity<CleaningDeviceDto> createCleaningDevice(@RequestBody CleaningDevice cleaningDevice){
        CleaningDevice createdCleaningDevice = cleaningDeviceService.createCleaningDevice(cleaningDevice);

        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCleaningDevice.getUuid())
                .toUri();

        CleaningDeviceDto cleaningDeviceDto = cleaningDeviceDtoMapper.mapToDto(cleaningDevice);

        return ResponseEntity
                .created(returnURI)
                .body(cleaningDeviceDto);
    }

    @GetMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDeviceDto> getCleaningDeviceById(@PathVariable UUID id){
        CleaningDevice cleaningDevice = cleaningDeviceService.getCleaningDeviceById(id);
        CleaningDeviceDto cleaningDeviceDto = cleaningDeviceDtoMapper.mapToDto(cleaningDevice);
        return new ResponseEntity(cleaningDeviceDto, HttpStatus.OK);
    }

    @DeleteMapping("/cleaningDevices/{id}")
    public ResponseEntity<?> deleteOneCleaningDevice(@PathVariable UUID id){
        if (cleaningDeviceService.deleteById(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/cleaningDevices/{id}")
    public ResponseEntity changeNameOfCleaningDevice(@PathVariable UUID id, @RequestBody String name){
        cleaningDeviceService.changeName(id,name);
        return new ResponseEntity(name,HttpStatus.OK);
    }

    @PostMapping("/cleaningDevices/{id}/commands")
    public ResponseEntity<Command> giveCommandToCleaningDevice(@PathVariable UUID id, @RequestBody Command command){
        cleaningDeviceService.executeCommand(id,command);
        return new ResponseEntity(command,HttpStatus.CREATED);
    }

    @GetMapping("/cleaningDevices/{id}/commands")
    public ResponseEntity<List<Command>> listCommands(@PathVariable UUID id){
        return new ResponseEntity(cleaningDeviceService.getCommandList(id),HttpStatus.OK);
    }

    @DeleteMapping("/cleaningDevices/{id}/commands")
    public ResponseEntity deleteCommands(@PathVariable UUID id){
        cleaningDeviceService.deleteCommands(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
