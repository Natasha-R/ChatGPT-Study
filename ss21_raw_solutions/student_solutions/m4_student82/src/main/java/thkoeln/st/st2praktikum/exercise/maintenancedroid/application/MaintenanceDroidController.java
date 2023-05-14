package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Transactional
public class MaintenanceDroidController {
    private MaintenanceDroidService maintenanceDroidService;
    private MaintenanceDroidRepository maintenanceDroidRepository;

    private MaintenanceDroidDtoMapper maintenanceDroidDtoMapper = new MaintenanceDroidDtoMapper();

    @Autowired
    MaintenanceDroidController(MaintenanceDroidService maintenanceDroidService, MaintenanceDroidRepository maintenanceDroidRepository){
        this.maintenanceDroidService = maintenanceDroidService;
        this.maintenanceDroidRepository = maintenanceDroidRepository;
    }



    @GetMapping("/maintenanceDroids")
        public Iterable<MaintenanceDroidDto> getAllDroids(){
        System.out.println(maintenanceDroidRepository);
            Iterable<MaintenanceDroid> allDroids = maintenanceDroidRepository.findAll();
            List<MaintenanceDroidDto> allDtos = new ArrayList<>();
            for(MaintenanceDroid maintenanceDroid : allDroids){
                allDtos.add(maintenanceDroidDtoMapper.mapToDto(maintenanceDroid));
            }
        return allDtos;
    }

    @PostMapping("/maintenanceDroids")
    public ResponseEntity<MaintenanceDroidDto> createNewDroid(@RequestBody MaintenanceDroidDto maintenanceDroidDto){
        UUID maintenanceDroid = maintenanceDroidService.addMaintenanceDroid(maintenanceDroidDto.getName());
        URI returnURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(maintenanceDroid).toUri();
        MaintenanceDroidDto createMaintenanceDroidDto = maintenanceDroidDtoMapper.mapToDto(maintenanceDroidService.getDroids().findById(maintenanceDroid).get());
        return ResponseEntity.created(returnURI).body(createMaintenanceDroidDto);
    }



    @GetMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDto> getOneDroid(@PathVariable UUID id) {
        Optional<MaintenanceDroid> found = maintenanceDroidRepository.findById(id);
        if(found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else{
            return new ResponseEntity<MaintenanceDroidDto>(maintenanceDroidDtoMapper.mapToDto(found.get()), OK );
        }
//        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findById(id).get();
//        MaintenanceDroidDto createMaintenanceDroidDto = maintenanceDroidDtoMapper.mapToDto(maintenanceDroid);
//        return new ResponseEntity( createMaintenanceDroidDto, OK);
    }


    @DeleteMapping("/maintenanceDroids/{id}")
    public ResponseEntity<?> deleteDroid(@PathVariable UUID id) {
        maintenanceDroidService.getDroids().deleteById(id);
        return new ResponseEntity( NO_CONTENT );
    }

    @PatchMapping("/maintenanceDroids/{id}")
    public ResponseEntity<MaintenanceDroidDto> changeDroidName(@PathVariable UUID id, @RequestBody MaintenanceDroidDto maintenanceDroidDto){
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.getDroids().findById(id).get();
        maintenanceDroid.setName(maintenanceDroidDto.getName());
        maintenanceDroidRepository.save(maintenanceDroid);
        return new ResponseEntity(maintenanceDroidDtoMapper.mapToDto(maintenanceDroid), OK);
    }

    @PostMapping("/maintenanceDroids/{id}/orders")
    public ResponseEntity<?> giveOrders(@PathVariable UUID id, @RequestBody Order order){
        MaintenanceDroid maintenanceDroid = maintenanceDroidService.getDroids().findById(id).get();
        maintenanceDroidService.executeCommand(id, order);
        maintenanceDroidRepository.save(maintenanceDroid);
        URI returnURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(maintenanceDroid.getMaintenanceDroidId()).toUri();
        return ResponseEntity.created(returnURI).body(order);
    }

    @GetMapping("/maintenanceDroids/{id}/orders")
    public Iterable<Order> getAllOrders(@PathVariable UUID id){
        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findById(id).get();

        return maintenanceDroid.getOrders();
    }

    @DeleteMapping("/maintenanceDroids/{id}/orders")
    public ResponseEntity<?> deleteOrders(@PathVariable UUID id){
        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findById(id).get();
        maintenanceDroid.getOrders().clear();
        return new ResponseEntity(NO_CONTENT);
    }

}
