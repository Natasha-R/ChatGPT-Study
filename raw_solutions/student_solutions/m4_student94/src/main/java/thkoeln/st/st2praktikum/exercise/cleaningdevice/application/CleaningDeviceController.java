package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderDto;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestController
@Transactional
public class CleaningDeviceController {
    private final CleaningDeviceService cleaningDeviceService;
    private final CleaningDeviceDtoMapper mapper= new CleaningDeviceDtoMapper();

    @Autowired
    public CleaningDeviceController(CleaningDeviceService cleaningDeviceService){
        this.cleaningDeviceService=cleaningDeviceService;
    }
    //Get all
    @GetMapping("/cleaningDevices")
    @ResponseBody
    public Iterable<CleaningDeviceDto> getAllCleaningDevices() {
        Iterable<CleaningDevice> found = cleaningDeviceService.findAll();
        // we create a list of Dto
        List foundDtos = new ArrayList<CleaningDeviceDto>();
        // here we loop through our found and add it to our foundDtos
        for ( CleaningDevice cleaningDevice : found ) {
            foundDtos.add( mapper.mapToDto( cleaningDevice) );
        }
        return foundDtos;
    }

    //Get orderList
     @GetMapping("/cleaningDevices/{id}/orders")
     @ResponseBody
     public Iterable<Order> getAllCleaningDeviceOrder(@PathVariable UUID id){
        return cleaningDeviceService.getOrderOfCleaningDeviceById(id);
     }

    //Get one
    @GetMapping("/cleaningDevices/{id}")
    @ResponseBody
    public ResponseEntity<?> getCleaningDevice (@PathVariable UUID id){
        Optional<CleaningDevice> cleaningDevice= cleaningDeviceService.findById(id);
        if (cleaningDevice.isEmpty())
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else
            return new ResponseEntity<>(mapper.mapToDto(cleaningDevice.get()),HttpStatus.OK);
    }

    //POST a new CleaningDevice
    @PostMapping("/cleaningDevices")
    public ResponseEntity<?> givenCleaningDeviceOrders( @RequestBody CleaningDeviceDto cleaningDeviceDto) {
        try {
            UUID cleaningDeviceId= cleaningDeviceService.addCleaningDevice(cleaningDeviceDto.getName());
            // mapping to CleaningDevice Entity
            CleaningDevice newCleaningDevice =mapper.mapToEntity(cleaningDeviceDto);

            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newCleaningDevice.getCleaningDeviceId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body( cleaningDeviceDto );
        }
        catch( Exception e ) {
            return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }


    //POST give an Order to a cleaningDevice
    @PostMapping("/cleaningDevices/{id}/orders")
    public ResponseEntity<?> createCleaningDevice(@PathVariable UUID id,  @RequestBody OrderDto orderDto) {
        try {
            Optional<CleaningDevice> newCleaningDevice=cleaningDeviceService.findById(id);
            if (newCleaningDevice.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else {
                Order order= new Order();
                ModelMapper modelMapper= new ModelMapper();
                modelMapper.map(orderDto,order);
            cleaningDeviceService.executeCommand(id, order);
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/orders")
                    .buildAndExpand(order.toString())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body( orderDto );
        }}
        catch( Exception e ) {
            return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

    //PATCH change name
    @PatchMapping("/cleaningDevices/{id}")
    public ResponseEntity<?> updateCleaningDevice(@PathVariable UUID id, @RequestBody CleaningDeviceDto cleaningDeviceDto) {
        try {
            //Service class anfragen
            Optional<CleaningDevice> found = cleaningDeviceService.findById(id);
            //Optional prüfen
            if (found.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
            CleaningDevice device=found.get();
                mapper.mapToDto(device);
                cleaningDeviceService.updateName(id, cleaningDeviceDto.getName());

                return new ResponseEntity<>(mapper.mapToDto(device), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    //DELETE a cleaningDevice
    @DeleteMapping("/cleaningDevices/{id}")
    public ResponseEntity<?> deleteCleaningDevice(@PathVariable UUID id ) {
        Optional<CleaningDevice> found = cleaningDeviceService.cleaningDeviceRepository.findById( id );
        if ( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            cleaningDeviceService.deleteById(id);
            return new ResponseEntity<CleaningDevice>( HttpStatus.NO_CONTENT );
        }
    }

    //DELETE a order s of cleaningDevice
    @DeleteMapping("/cleaningDevices/{id}/orders")
    public ResponseEntity<CleaningDeviceDto> deleteCleaningDeviceOrderHistory(@PathVariable UUID id ) {
        Optional<CleaningDevice> found = cleaningDeviceService.cleaningDeviceRepository.findById( id );
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            cleaningDeviceService.findById(id).get().getOrders().clear();
            return new ResponseEntity<CleaningDeviceDto>( HttpStatus.NO_CONTENT );
        }
    }

}
