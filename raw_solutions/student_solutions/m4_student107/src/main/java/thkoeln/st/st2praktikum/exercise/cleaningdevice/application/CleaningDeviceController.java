package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.modelmapper.ModelMapper;

import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@RepositoryRestController
@RestController
public class CleaningDeviceController {

    private final CleaningDeviceRepository cleaningDeviceRepository;
    private final CleaningDeviceService cleaningDeviceService;
    @Autowired
    public CleaningDeviceController(
            CleaningDeviceRepository cleaningDeviceRepository,
            CleaningDeviceService cleaningDeviceService
    ){
        this.cleaningDeviceRepository = cleaningDeviceRepository;
        this.cleaningDeviceService = cleaningDeviceService;
    }

    @GetMapping("/cleaningDevices")
    public Iterable<CleaningDevice>getAllCleaningDevices(){
        return cleaningDeviceRepository.findAll();
    }
    @PostMapping("/cleaningDevices")
    public ResponseEntity<?> createNewCleaningDevice( @RequestBody CleaningDevice cleaningDevice){
        try {
            CleaningDevice device = new CleaningDevice(cleaningDevice.getName());

            cleaningDeviceRepository.save(device);
            URI returnUri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{cleaningDesvice-id}")
                    .buildAndExpand(device.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnUri)
                    .body(cleaningDevice);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }


    @GetMapping("/cleaningDevices/{cleaningDeviceid}")
    public ResponseEntity<CleaningDevice> getOneCleaningDevice (@PathVariable UUID cleaningDeviceid){
        Optional<CleaningDevice> found = cleaningDeviceRepository.findById(cleaningDeviceid);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<CleaningDevice>(
              found.get(),HttpStatus.OK
            );
        }
    }

    @DeleteMapping("/cleaningDevices/{cleaningDeviceid}")
    public ResponseEntity<CleaningDevice> deleteCleaningDeviceById (@PathVariable UUID cleaningDeviceid){
        Optional<CleaningDevice> found = cleaningDeviceRepository.findById(cleaningDeviceid);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            cleaningDeviceRepository.delete(found.get());
            return new ResponseEntity<CleaningDevice>(HttpStatus.NO_CONTENT);
        }
    }
    @PatchMapping("/cleaningDevices/{cleaningDeviceid}")
    public ResponseEntity<CleaningDevice> patchCleaningDeviceNameById(@PathVariable UUID cleaningDeviceid , @RequestBody CleaningDevice cleaningDevice){
        Optional<CleaningDevice> found = cleaningDeviceRepository.findById(cleaningDeviceid);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            found.get().setName(cleaningDevice.getName());
            return new ResponseEntity<CleaningDevice>(
                    found.get(),HttpStatus.OK
            );
        }
    }

    @PostMapping("/cleaningDevices/{cleaningDeviceid}/orders")
    public ResponseEntity<?> giveCleaningDeviceAnOrder(@PathVariable UUID cleaningDeviceid ,@RequestBody Order order){
        Optional<CleaningDevice> found = cleaningDeviceRepository.findById(cleaningDeviceid);
        try {

            cleaningDeviceService.executeCommand(found.get().getId(),order);

            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/")
                    .build()
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(order);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }


        @DeleteMapping("/cleaningDevices/{cleaningDeviceid}/orders")
        public ResponseEntity<?> deleteAllCleaningDeviceOrders (@PathVariable UUID cleaningDeviceid) {
            Optional<CleaningDevice> found = cleaningDeviceRepository.findById(cleaningDeviceid);
            if(found.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else {
                found.get().clearOrderList();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        }
    @GetMapping("/cleaningDevices/{cleaningDeviceid}/orders")
    public Iterable<Order> getAllCleaningDeviceOrders (@PathVariable UUID cleaningDeviceid) {
        Optional<CleaningDevice> found = cleaningDeviceRepository.findById(cleaningDeviceid);
        if(found.isEmpty()){
            return null;
        }else {
            return found.get().getOrderList();
        }
    }


}
