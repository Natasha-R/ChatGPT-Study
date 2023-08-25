package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderDTO;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineDTO;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Transactional
public class MiningMachineController {

    private final MiningMachineService miningMachineService;
    private final MiningMachineRepository miningMachineRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public MiningMachineController(MiningMachineService miningMachineService, MiningMachineRepository miningMachineRepository){
        this.miningMachineService = miningMachineService;
        this.miningMachineRepository = miningMachineRepository;
        for(MiningMachine m: miningMachineRepository.findAll()){
            System.out.println(m.getName());
        }
    }

    /**
     * Get one
     */
    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDTO> getMiningMachine( @PathVariable UUID id ){
        Optional<MiningMachine> found = this.miningMachineRepository.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<MiningMachineDTO>(
              this.modelMapper.map(found.get(), MiningMachineDTO.class), HttpStatus.OK
            );
        }
    }

    /**
     * Get all
     */
    @GetMapping("/miningMachines")
    public Iterable<MiningMachineDTO> getAllMiningMachines() {
        Iterable<MiningMachine> found = this.miningMachineRepository.findAll();
        List foundDTO = new ArrayList<MiningMachineDTO>();
        for(MiningMachine miningMachine: found){
            foundDTO.add(modelMapper.map(miningMachine, MiningMachineDTO.class));
        }
        return foundDTO;
    }

    /**
     * Create New
     */
    @PostMapping("/miningMachines")
    public ResponseEntity<?> postMiningMachine( @RequestBody MiningMachineDTO miningMachineDTO) {
        try{
            UUID miningMachine = this.miningMachineService.addMiningMachine(miningMachineDTO.getName());
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{miningMachine-id}")
                    .buildAndExpand( miningMachine )
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body( miningMachineDTO );
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Delete one
     */
    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDTO> deleteMiningMachines( @PathVariable UUID id ){
        Optional<MiningMachine> found = this.miningMachineRepository.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            this.miningMachineRepository.delete(found.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * Patch one
     */
    @PatchMapping(path = "/miningMachines/{id}", consumes = "application/json")
    public ResponseEntity<MiningMachineDTO> patchMiningMachine( @PathVariable UUID id, @RequestBody MiningMachine patch){
        Optional<MiningMachine> found = this.miningMachineRepository.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            found.get().setFieldId(patch.getFieldId());
            found.get().setPoint(patch.getPoint());
            found.get().setName(patch.getName());
            return new ResponseEntity<MiningMachineDTO>(
                    this.modelMapper.map(found.get(), MiningMachineDTO.class), HttpStatus.OK
            );
        }
    }

    /**
     * Post order to miningmachine
     */
    @PostMapping(path = "/miningMachines/{id}/orders", consumes = "application/json")
    public ResponseEntity<?> postMiningMachineEntryOrderTest( @PathVariable UUID id, @RequestBody OrderDTO order) {
        try{
            Optional<MiningMachine> found = this.miningMachineRepository.findById(id);
            if(found.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                this.miningMachineService.executeCommand(found.get().getMiningmaschineId(), Order.fromOrderDTO(order));
                return new ResponseEntity<OrderDTO>(
                        order, HttpStatus.CREATED
                );
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Get all orders from miningmachine
     */
    @GetMapping(path = "/miningMachines/{id}/orders")
    public Iterable<Order> getAllMiningMachineOrders( @PathVariable UUID id ){
        return this.miningMachineRepository.findById(id).get().getOrders();
    }

    /**
     * Delete all orders from miningmachine
     */
    @DeleteMapping(path = "/miningMachines/{id}/orders")
    public ResponseEntity<OrderDTO> deleteAllMiningMachineOrders( @PathVariable UUID id ){
        Optional<MiningMachine> found = this.miningMachineRepository.findById(id);
        if(found.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            found.get().setOrders(new ArrayList<>());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
