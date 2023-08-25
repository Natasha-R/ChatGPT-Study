package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;

import java.util.UUID;

@RestController
public class MiningMachineController {
    private  MiningMachineService service;

    @Autowired
    public MiningMachineController(MiningMachineService service){
        this.service = service;
    }

    @GetMapping("/miningMachines")
    public Iterable<MiningMachine> getAllMiningMachines(){
        return service.getMiningMachineRepo().findAll();
    }

    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> getMM(@PathVariable UUID id){
        var mm = service.getMiningMachineRepo().get(id);
        return new ResponseEntity<>(mm, HttpStatus.OK);
    }

    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> patchMM(@PathVariable UUID id, @RequestBody MiningMachine newMM){
        var mm = service.getMiningMachineRepo().get(id);
        mm.setName(newMM.getName());
        mm.setPoint(newMM.getPoint());
        mm.setFieldId(newMM.getFieldId());
        service.getMiningMachineRepo().save(mm);
        return new ResponseEntity<MiningMachine>(mm, HttpStatus.OK);
    }

    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachine> getAllMiningMachines(@PathVariable UUID id){
        var mm = service.getMiningMachineRepo().get(id);
        service.getMiningMachineRepo().delete(mm);
        return new ResponseEntity<MiningMachine>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/miningMachines")
    public ResponseEntity<MiningMachine> postMM(@RequestBody MiningMachine mm){
        var id =service.addMiningMachine(mm.getName());
        return new ResponseEntity<MiningMachine>(mm, HttpStatus.CREATED);
    }

    @GetMapping("/miningMachines/{id}/orders")
    public Iterable<Order> getMMOrder(@PathVariable UUID id ){
        var l = service.getMiningMachineRepo().get(id).getOrders();
        System.out.println(l);
        return l;
    }

    @DeleteMapping("/miningMachines/{id}/orders")
    public ResponseEntity<MiningMachine> deleteMMOrders(@PathVariable UUID id){
        service.getMiningMachineRepo().get(id).getOrders().clear();
        return new ResponseEntity<MiningMachine>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/miningMachines/{id}/orders")
    public ResponseEntity<Order> postMMOrder(@PathVariable UUID id, @RequestBody Order order){
        var t = service.execute(id, order);
        if(t){
            return new ResponseEntity<Order >(order, HttpStatus.CREATED);
        }else{
            return null;
        }
    }
}
