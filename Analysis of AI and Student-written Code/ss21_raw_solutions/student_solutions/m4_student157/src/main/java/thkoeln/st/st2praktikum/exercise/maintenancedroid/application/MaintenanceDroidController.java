package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.SpaceShipDeck;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
public class MaintenanceDroidController {

    private final MaintenanceDroidService maintenanceDroidService;
    private final SpaceShipDeckService spaceShipDeckService;
    private MaintenanceDroidDtoMapper maintenanceDroidDtoMapper = new MaintenanceDroidDtoMapper();

    @Autowired
    public MaintenanceDroidController(MaintenanceDroidService maintenanceDroidService, SpaceShipDeckService spaceShipDeckService) {
        this.maintenanceDroidService = maintenanceDroidService;
        this.spaceShipDeckService = spaceShipDeckService;
    }

    @GetMapping("/maintenanceDroids")
    List<MaintenanceDroidDto> getAllMaintenanceDroids() {
        Iterable<MaintenanceDroid> allMaintenanceDroids = maintenanceDroidService.findAll();
        List<MaintenanceDroidDto> allDtos = new ArrayList<>();
        for (MaintenanceDroid monster : allMaintenanceDroids) {
            allDtos.add(maintenanceDroidDtoMapper.mapToDto(monster));
        }
        return allDtos;
    }

    @GetMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDto> getOneMaintenanceDroid(@PathVariable UUID id) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.findById(id);
        MaintenanceDroidDto createdMaintenanceDroidDto = maintenanceDroidDtoMapper.mapToDto(maintenanceDroid);
        return new ResponseEntity(createdMaintenanceDroidDto, OK);
    }

    @PostMapping("/maintenanceDroids")
    public ResponseEntity<MaintenanceDroidDto> createMaintenanceDroid(@RequestBody MaintenanceDroidDto monsterDto) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.createMaintenanceDroidFromDto(monsterDto);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(maintenanceDroid.getUuid())
                .toUri();
        MaintenanceDroidDto createdMaintenanceDroidDto = maintenanceDroidDtoMapper.mapToDto(maintenanceDroid);
        return ResponseEntity
                .created(returnURI)
                .body(createdMaintenanceDroidDto);
    }

    @DeleteMapping("/maintenanceDroids/{id}")
    public ResponseEntity<?> deleteMaintenanceDroid(@PathVariable UUID id) {
        maintenanceDroidService.deleteById(id);
        return new ResponseEntity(NO_CONTENT);
    }

    @PatchMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDto> updateMaintenanceDroid(
            @PathVariable UUID id, @RequestBody MaintenanceDroidDto maintenanceDroidDto) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.findById(id);
        maintenanceDroid.setName(maintenanceDroidDto.getName());
        MaintenanceDroidDto newDroidDto = this.maintenanceDroidDtoMapper.mapToDto(maintenanceDroid);
        return new ResponseEntity<>(newDroidDto, OK);
    }

    @GetMapping("/maintenanceDroids/{id}/orders")
    List<Order> getAllOrdersForMaintenanceDroids(@PathVariable String id) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.findById(UUID.fromString(id));
        return maintenanceDroid.getOrders();
    }

    @PostMapping("/maintenanceDroids/{id}/orders")
    public ResponseEntity<MaintenanceDroid> createOrderForMaintenanceDroid(@PathVariable String id, @RequestBody Order order) {
        maintenanceDroidService.executeCommand(UUID.fromString(id), order);
        return new ResponseEntity(order, CREATED);
    }

    @DeleteMapping("/maintenanceDroids/{id}/orders")
    public ResponseEntity<?> deleteTasksDorDroid(@PathVariable UUID id) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.findById(id);

        return new ResponseEntity<>(NO_CONTENT);
    }

}
