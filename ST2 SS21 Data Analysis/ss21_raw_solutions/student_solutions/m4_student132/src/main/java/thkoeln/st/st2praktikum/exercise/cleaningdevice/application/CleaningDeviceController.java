package thkoeln.st.st2praktikum.exercise.cleaningdevice.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDevice;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceDTO;
import thkoeln.st.st2praktikum.exercise.cleaningdevice.domain.CleaningDeviceRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderDTO;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Transactional
public class CleaningDeviceController {

    private final CleaningDeviceService cleaningDeviceService;
    private final CleaningDeviceRepository cleaningDeviceRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public CleaningDeviceController(CleaningDeviceService cleaningDeviceService, CleaningDeviceRepository cleaningDeviceRepository) {
        this.cleaningDeviceService = cleaningDeviceService;
        this.cleaningDeviceRepository = cleaningDeviceRepository;
        for (CleaningDevice m : cleaningDeviceRepository.findAll()) {
            System.out.println(m.getName());
        }
    }

    /**
     * Get one
     */
    @GetMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDeviceDTO> getCleaningDevice(@PathVariable UUID id) {
        Optional<CleaningDevice> found = this.cleaningDeviceRepository.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<CleaningDeviceDTO>(
                    this.modelMapper.map(found.get(), CleaningDeviceDTO.class), HttpStatus.OK
            );
        }
    }

    /**
     * Get all
     */
    @GetMapping("/cleaningDevices")
    public Iterable<CleaningDeviceDTO> getAllCleaningDevices() {
        Iterable<CleaningDevice> found = this.cleaningDeviceRepository.findAll();
        List foundDTO = new ArrayList<CleaningDeviceDTO>();
        for (CleaningDevice cleaningDevice : found) {
            foundDTO.add(modelMapper.map(cleaningDevice, CleaningDeviceDTO.class));
        }
        return foundDTO;
    }

    /**
     * Create New
     */
    @PostMapping("/cleaningDevices")
    public ResponseEntity<?> postCleaningDevice(@RequestBody CleaningDeviceDTO cleaningDeviceDTO) {
        try {
            UUID cleaningDevice = this.cleaningDeviceService.addCleaningDevice(cleaningDeviceDTO.getName());
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{cleaningDevice-id}")
                    .buildAndExpand(cleaningDevice)
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body(cleaningDeviceDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Delete one
     */
    @DeleteMapping("/cleaningDevices/{id}")
    public ResponseEntity<CleaningDeviceDTO> deleteCleaningDevices(@PathVariable UUID id) {
        Optional<CleaningDevice> found = this.cleaningDeviceRepository.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            this.cleaningDeviceRepository.delete(found.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Patch one
     */
    @PatchMapping(path = "/cleaningDevices/{id}", consumes = "application/json")
    public ResponseEntity<CleaningDeviceDTO> patchCleaningDevice(@PathVariable UUID id, @RequestBody CleaningDevice patch) {
        Optional<CleaningDevice> found = this.cleaningDeviceRepository.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            found.get().setSpaceId(patch.getSpaceId());
            found.get().setCoordinate(patch.getCoordinate());
            found.get().setName(patch.getName());
            return new ResponseEntity<CleaningDeviceDTO>(
                    this.modelMapper.map(found.get(), CleaningDeviceDTO.class), HttpStatus.OK
            );
        }
    }

    /**
     * Post order to cleaningdevice
     */
    @PostMapping(path = "/cleaningDevices/{id}/orders", consumes = "application/json")
    public ResponseEntity<?> postCleaningDeviceEntryOrderTest(@PathVariable UUID id, @RequestBody OrderDTO order) {
        try {
            Optional<CleaningDevice> found = this.cleaningDeviceRepository.findById(id);
            if (found.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                this.cleaningDeviceService.executeCommand(found.get().getCleaningMachineId(), Order.fromOrderDTO(order));
                return new ResponseEntity<OrderDTO>(
                        order, HttpStatus.CREATED
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Get all orders from cleaningdevice
     */
    @GetMapping(path = "/cleaningDevices/{id}/orders")
    public Iterable<Order> getAllCleaningDeviceOrders(@PathVariable UUID id) {
        return this.cleaningDeviceRepository.findById(id).get().getOrders();
    }

    /**
     * Delete all orders from cleaningdevice
     */
    @DeleteMapping(path = "/cleaningDevices/{id}/orders")
    public ResponseEntity<OrderDTO> deleteAllCleaningDeviceOrders(@PathVariable UUID id) {
        Optional<CleaningDevice> found = this.cleaningDeviceRepository.findById(id);
        if (found.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            found.get().setOrders(new ArrayList<>());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
