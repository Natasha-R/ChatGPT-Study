package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import com.jayway.jsonpath.internal.function.numeric.Min;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.*;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class MiningMachineController {
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    MiningMachineRepository miningMachineRepository;
    @Autowired
    TransportCategoryRepository transportCategoryRepository;


    private final MiningMachineService miningMachineService;
    private MiningMachineDtoMapper miningMachineDtoMapper = new MiningMachineDtoMapper();
    private ModelMapper modelMapper = new ModelMapper();

    public MiningMachineController(MiningMachineService miningMachineService) {
        this.miningMachineService = miningMachineService;


    }
    /**
     * Get all
     */

    /*@GetMapping("/miningMachines")
    public Iterable<MiningMachineDto> getAllMiningMachine() {
        Iterable<MiningMachine> allMiningMachine = miningMachineRepository.findAll();
        List<MiningMachineDto> allDtos = new ArrayList<>();
        for ( MiningMachine miningMachine : allMiningMachine ) {
            allDtos.add( miningMachineDtoMapper.mapToDto(  miningMachine) );
        }
        return allDtos;
    }

     */

    @GetMapping("/miningMachines")
    public Iterable<MiningMachine> getAllMiningMachine() {
      return miningMachineRepository.findAll();
    }

    @GetMapping("/miningMachines/{id}")
    public Optional<MiningMachine> getMiningMachine(@PathVariable UUID id){
        Optional<MiningMachine> miningMachine = miningMachineRepository.findById(id);
        return miningMachine;

    }
/*
    @PostMapping("/miningMachines")
    public ResponseEntity<MiningMachineDto> createNewMiningMachine( @RequestBody MiningMachineDto miningMachineDto ) {
        MiningMachine miningMachine = miningMachineService.createMiningMachineFromDto( miningMachineDto );
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand( miningMachine.getId() )
                .toUri();
        MiningMachineDto createdMiningMachineDto = miningMachineDtoMapper.mapToDto( miningMachine );
        return ResponseEntity
                .created(returnURI)
                .body( createdMiningMachineDto );
    }

*/


    @PostMapping("/miningMachines")
    public ResponseEntity<?> createNewMiningMachine(@RequestBody MiningMachine miningMachine) {
        miningMachine.setId(UUID.randomUUID());
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(miningMachine.getId())
                .toUri();
        miningMachineRepository.save(miningMachine);
        return ResponseEntity
                .created(returnURI)
                .body( miningMachine );
    }





    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<?> deleteOneMiningMachine( @PathVariable UUID id ) {
        miningMachineRepository.deleteById( id );
        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }


    @PatchMapping("/miningMachines/{id}")
    public  ResponseEntity<?> PatchMiningMachine(@RequestBody MiningMachine miningMachine){
        miningMachine.setId(UUID.randomUUID());
        miningMachineRepository.save(miningMachine);
        return new ResponseEntity(miningMachine,HttpStatus.OK);
    }
    @GetMapping("/miningMachines/{id}/orders")
    public List<Order> getMiningMachineAllOrders(@PathVariable UUID id) {
        Optional<MiningMachine> miningMachine = miningMachineRepository.findById(id);
        return miningMachine.get().getOrderlist();
    }

    @DeleteMapping("/miningMachines/{id}/orders")
    public ResponseEntity<?> deleteAllMiningMachineOrders( @PathVariable UUID id ) {
        Optional<MiningMachine> miningMachine = miningMachineRepository.findById(id);
        List<Order> orders = miningMachine.get().getOrderlist();
        orders.clear();
        return new ResponseEntity( HttpStatus.NO_CONTENT );
    }

   /* @PostMapping("/miningMachines/{id}/orders")
    public ResponseEntity<?> postMiningMachineOrder(@RequestBody Order order, @PathVariable UUID id) {
        MiningMachine miningMachine = miningMachineService.findById(id);
        miningMachine.setId(UUID.randomUUID());
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(miningMachine.getId())
                .toUri();
        miningMachineRepository.save(miningMachine);
         miningMachine.getOrderlist().add(order);
        return ResponseEntity
                .created(returnURI)
                .body( miningMachine.getOrderlist().get(1) );
    }
*/

    @PostMapping("/miningMachines/{id}/orders")
    public ResponseEntity<?> postMiningMachineOrder(@RequestBody Order order, @PathVariable UUID id) {
        MiningMachine miningMachine = miningMachineService.findById(id);
        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(miningMachine.getId())
                .toUri();
        Integer size = miningMachine.getOrderlist().size();
        miningMachineRepository.save(miningMachine);
        miningMachine.getOrderlist().add(order);
        miningMachineService.executeCommand(id, miningMachine.getOrderlist().get(size));
        return ResponseEntity
                .created(returnURI)
                .body( order );
    }







}


