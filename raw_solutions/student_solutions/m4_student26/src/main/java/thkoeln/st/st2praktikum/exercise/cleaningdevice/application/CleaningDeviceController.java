package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.WrongFormatException;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Transactional
public class CleaningDeviceController {

    private final CleaningDeviceService cleaningDeviceService;

    @Autowired
    public CleaningDeviceController( CleaningDeviceService cleaningDeviceService ) {
        this.cleaningDeviceService = cleaningDeviceService;
    }


    /**
     * Get all
     */
    @GetMapping("/cleaningDevices")
    public Iterable<CleaningDevice> getAllCleaningDevices() {
        Iterable<CleaningDevice> allCleaningDevices = cleaningDeviceService.findAll();
        return allCleaningDevices;
    }

    /**
     *
     * Get one
     */
    @GetMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDevice> getCleaningDevice( @PathVariable UUID id ) {
        CleaningDevice cleaningDevice = cleaningDeviceService.findById( id );
        return new ResponseEntity( cleaningDevice, HttpStatus.OK );
    }

    /**
     * Delete one Cleaning Device
     */
    @DeleteMapping("/cleaningDevices/{id}")
    public ResponseEntity<?> deleteCleaningDevice( @PathVariable UUID id ) {
        cleaningDeviceService.deleteById( id );
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    /**
     * Create New CleaningDevice
     */
    @PostMapping("/cleaningDevices")
    public ResponseEntity<CleaningDevice> postCleaningDevice(@RequestBody CleaningDevice cleaningDevice )
            throws WrongFormatException {
        CleaningDevice cleaningDevice1 = cleaningDeviceService.save(cleaningDevice);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand( cleaningDevice.getUuid() )
                .toUri();

        return ResponseEntity
                .created(returnURI)
                .body( cleaningDevice1 );
    }

    /**
     * Patch
     *
     */
    @PatchMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDevice> replaceEmployee(@RequestBody CleaningDevice newCleaningDevice, @PathVariable UUID id) {
        return new ResponseEntity(newCleaningDevice,HttpStatus.OK);



    }

    /**
     * Delete all Cleaning Device Commands
     */
    @DeleteMapping("/cleaningDevices/{id}/commands")
    public ResponseEntity<?> deleteCleaningDevice() {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    /**
     * Give a specific cleaning device a command
     */
    @PostMapping("/cleaningDevices/{id}/commands")
    public ResponseEntity<Command> postCleaningDeviceCommand(@PathVariable UUID id, @RequestBody Command command)
            throws WrongFormatException {
        cleaningDeviceService.executeCommand(id, command);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand( id )
                .toUri();
        System.out.println(returnURI);
        return ResponseEntity
                .created(returnURI)
                .body( command );
    }


    /**
     * Get all CleaningDeviceCommands
     */

    @GetMapping("/cleaningDevices/{id}/commands")
    public Iterable<Command> getAllCleaningDeviceCommands(@PathVariable UUID id) {
        Iterable<Command> allCleaningDevicesCommand = cleaningDeviceService.findById(id).getCommands();
        System.out.println(allCleaningDevicesCommand);
        return allCleaningDevicesCommand;
    }








}
