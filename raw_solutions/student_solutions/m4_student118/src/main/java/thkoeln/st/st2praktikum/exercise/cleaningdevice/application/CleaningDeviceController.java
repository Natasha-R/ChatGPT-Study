package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.Area;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;


import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Transactional
public class CleaningDeviceController {

    @Autowired
    private final CleaningDeviceRepository cleaningDeviceRepository;
    @Autowired
    private final Area area;
    @Autowired
    private CleaningDeviceService cleaningDeviceService;

    @Autowired
    public CleaningDeviceController(CleaningDeviceRepository cleaningDeviceRepository, Area area){
        this.cleaningDeviceRepository =cleaningDeviceRepository;
        this.area = area;
    }

    @GetMapping("/cleaningDevices")
    public Iterable<CleaningDevice> getAllCleaningDevices(){
        return cleaningDeviceRepository.findAll();
    }

    @PostMapping("/cleaningDevices")
    public ResponseEntity<?> createNewCleaningDevice(@RequestBody CleaningDevice cleaningDevice){
        try{
            cleaningDeviceRepository.save(cleaningDevice);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{cleaningDevice-id}")
                    .buildAndExpand(cleaningDevice.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(cleaningDevice);
        }
        catch (Exception e){
         return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDevice> getOneCleaningDevice(@PathVariable UUID id){
        return new ResponseEntity(cleaningDeviceRepository.findById(id),HttpStatus.OK);
    }

    @DeleteMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDevice> deleteOneCleaningDevice( @PathVariable UUID id ) {

        cleaningDeviceRepository.deleteById(id);
        return new ResponseEntity( HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/cleaningDevices/{cleaningDevice-id}")
    public ResponseEntity<?> changeNameOfCleaningDevice(@RequestBody CleaningDevice cleaningDevice){
        try{
            cleaningDeviceRepository.save(cleaningDevice);
            return ResponseEntity.ok(cleaningDevice);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/cleaningDevices/{id}/commands")
    public ResponseEntity<?> executeCommand(@PathVariable UUID id, @RequestBody Command command){

        Optional<CleaningDevice> found = cleaningDeviceRepository.findById(id);
        if (found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            CleaningDevice cleaningDevice = found.get();
            cleaningDevice.addCommand(command);
            cleaningDeviceService.executeCommand(id,command);
            new ModelMapper().map(cleaningDevice, CleaningDevice.class);
            cleaningDeviceRepository.save(cleaningDevice);

            return new ResponseEntity<Command>(cleaningDevice.getCommands().get(cleaningDevice.getCommands().size()-1), HttpStatus.CREATED);
        }

    }

    @GetMapping("/cleaningDevices/{id}/commands")
    public Iterable<Command> getAllCommandsOfCleaningDevice(@PathVariable UUID id){
        return cleaningDeviceRepository.findById(id).get().getCommands();
    }

    @DeleteMapping("/cleaningDevices/{id}/commands")
    public ResponseEntity<CleaningDevice> deleteCommandHistoryOfCleaningDevice(@PathVariable UUID id) {
        CleaningDevice cleaningDevice = cleaningDeviceRepository.findCleaningDeviceById(id);
        cleaningDevice.deleteCommands();
        cleaningDeviceRepository.save(cleaningDevice);
        return new ResponseEntity( HttpStatus.NO_CONTENT);
    }

}
