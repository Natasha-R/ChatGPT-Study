package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@RepositoryRestController
public class MiningMachineController {

    private final MiningMachineService miningMachineService;
    private final MiningMachineDtoMapper miningMachineDtoMapper = new MiningMachineDtoMapper();

    @Autowired
    public MiningMachineController(MiningMachineService miningMachineService) {
        this.miningMachineService = miningMachineService;
    }


    @GetMapping("/miningMachines")
    @ResponseBody
    public Iterable<MiningMachineDto> getAllMiningMachines(){
        ArrayList<MiningMachineDto> allMiningMachineDtos = new ArrayList<>();

        for(MiningMachine miningmachine : miningMachineService.miningMachineRepository.findAll()){
            allMiningMachineDtos.add(miningMachineDtoMapper.mapToDto(miningmachine));
        }
        return allMiningMachineDtos;
    }

    @PostMapping("/miningMachines")
    public ResponseEntity<MiningMachineDto> createMiningMachine(@RequestBody MiningMachineDto miningMachineDto){
        UUID miningMachineId = miningMachineService.addMiningMachine(miningMachineDto.getName());
        Optional<MiningMachine> miningMachine = miningMachineService.getMiningMachineRepository().findById(miningMachineId);
        URI returnURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(miningMachine.get().getId()).toUri();
        if(miningMachine.isEmpty()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else{
            return ResponseEntity
                    .created(returnURI)
                    .body(miningMachineDto);
        }
    }

    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDto> getOneMiningMachine(@PathVariable UUID id){
        Optional<MiningMachine> found = miningMachineService.getMiningMachineRepository().findById(id);
        if(found.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<>(miningMachineDtoMapper.mapToDto(found.get()), HttpStatus.OK);
        }
    }

    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<?> deleteOneMiningMachine(@PathVariable UUID id){
        miningMachineService.getMiningMachineRepository().deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDto> patchMiningMachine(@PathVariable UUID id, @RequestBody MiningMachineDto miningMachineDto){
        Boolean success = miningMachineService.changeName(miningMachineDto.getName(), id);
        if(success){
            return new ResponseEntity<>(miningMachineDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/miningMachines/{id}/orders")
    public ResponseEntity<Order> postOrderToMiningMachine(@PathVariable UUID id, @RequestBody Order order){
        Boolean result = miningMachineService.executeCommand(id, order);
        if(result){
            return new ResponseEntity<>(order,HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/miningMachines/{id}/orders")
    public ResponseEntity<List<Order>> getOrders(@PathVariable UUID id){
        Optional<MiningMachine> miningMachine = miningMachineService.getMiningMachineRepository().findById(id);
        if(miningMachine.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            return new ResponseEntity<>(miningMachine.get().getListOfOrders(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/miningMachines/{id}/orders")
    public ResponseEntity deleteAllOrders(@PathVariable UUID id){
        Optional<MiningMachine> miningMachine = miningMachineService.getMiningMachineRepository().findById(id);
        if(miningMachine.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            miningMachine.get().setListOfOrders(new ArrayList<Order>());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
