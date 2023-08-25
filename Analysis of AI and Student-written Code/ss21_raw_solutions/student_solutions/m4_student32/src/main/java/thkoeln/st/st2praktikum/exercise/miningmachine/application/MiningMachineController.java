package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;


@RestController
public class MiningMachineController {

    private MiningMachineService miningMachineService;
    private MiningMachineDtoMapper miningMachineDtoMapper = new MiningMachineDtoMapper();

    @Autowired
    public MiningMachineController(MiningMachineService miningMachineService) {
        this.miningMachineService = miningMachineService;
    }
    //1. Get all mining machines	/miningMachines	GET
    @GetMapping("/miningMachines")
    public Iterable<MiningMachineDto> getAllMiningMachines() {
        Iterable<MiningMachine> allMiningMachines = miningMachineService.findAllMm();
        List<MiningMachineDto> allDtos = new ArrayList<>();
        for ( MiningMachine miningMachine : allMiningMachines ) {
            allDtos.add(miningMachineDtoMapper.mapToDto(miningMachine));
        }
        return allDtos;
    }

    //2. Create a new mining machine	/miningMachines	POST
    @PostMapping("/miningMachines")
    public ResponseEntity<MiningMachineDto> createNewMiningMachine(@RequestBody MiningMachineDto miningMachineDto){
        MiningMachine miningMachine = miningMachineService.addMiningMachineFromDto(miningMachineDto);

        URI returnURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{miningMachine-id}")
                .buildAndExpand( miningMachine.getMiningMachineId() )
                .toUri();
        MiningMachineDto createdMiningMachineDto = miningMachineDtoMapper.mapToDto( miningMachine );
        return ResponseEntity
                .created(returnURI)
                .body( createdMiningMachineDto );
    }

    //3. Get a specific mining machine by ID	/miningMachines/{miningMachine-id}	GET
    @GetMapping("/miningMachines/{miningMachine-id}")
    public ResponseEntity<MiningMachineDto> getMiningMachine(@PathVariable("miningMachine-id") UUID id) {
        MiningMachine miningMachine = miningMachineService.findMmById(id);
        MiningMachineDto createdMiningMachineDto = miningMachineDtoMapper.mapToDto(miningMachine);
        return new ResponseEntity(createdMiningMachineDto, OK);
    }

    //4. Delete a specific mining machine	/miningMachines/{miningMachine-id}	DELETE
    @DeleteMapping("/miningMachines/{miningMachine-id}")
    public ResponseEntity<?> deleteMiningMachine(@PathVariable("miningMachine-id") UUID id){
        miningMachineService.deleteMmById(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

    //5. Change the name of a specific mining machine	/miningMachines/{miningMachine-id}	PATCH
    @PatchMapping("/miningMachines/{miningMachine-id}")
    public ResponseEntity<?> changeMiningMachinesName (@RequestBody  MiningMachine miningMachineDto, @PathVariable("miningMachine-id") UUID id){
         MiningMachine  miningMachine = miningMachineService.findMmById(id);
         miningMachine.setName( miningMachineDto.getName());
         MiningMachineDto createdMiningMachineDto = miningMachineDtoMapper.mapToDto( miningMachine);
        return new ResponseEntity(createdMiningMachineDto, OK );
    }



    //6. Give a specific mining machine an order	/miningMachines/{miningMachine-id}/orders	POST
    @PostMapping("/miningMachines/{miningMachine-id}/orders")
    public ResponseEntity<Order> addOrderToMiningMachine(@RequestBody Order order, @PathVariable("miningMachine-id") UUID id){
        miningMachineService.executeCommand(id, order);
        return new ResponseEntity(order, CREATED );
    }


    //7. List all the orders a specific mining machine has received so far	/miningMachines/{miningMachine-id}/orders	GET
    @GetMapping("/miningMachines/{miningMachine-id}/orders")
    public Iterable<Order> getAllOrders(@PathVariable("miningMachine-id") UUID id){
        return miningMachineService.findMmById(id).getOrders();
    }

    //8. Delete the order history of a specific mining machine	/miningMachines/{miningMachine-id}/orders	DELETE
    @DeleteMapping("/miningMachines/{miningMachine-id}/orders")
    public ResponseEntity<?> deleteAllOrders(@PathVariable("miningMachine-id") UUID id){
        miningMachineService.findMmById(id).setOrders(new ArrayList<Order>());
        return new ResponseEntity<>(NO_CONTENT);
    }
}

