package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.*;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;

import java.net.URI;
import java.util.*;

@RestController
public class MiningMachineController {

    @Autowired
    MiningMachineService miningMachineService;

    @Autowired
    FieldService fieldService;

    @Autowired
    TransportTechnologyService transportTechnologyService;

    ModelMapper modelMapper = new ModelMapper();
    MiningMachineDtoMapper miningMachineDtoMapper = new MiningMachineDtoMapper();
    OrderDtoMapper orderDtoMapper = new OrderDtoMapper();

    @GetMapping("/miningMachines")
    public Iterable<MiningMachineDto> getAllMiningMachines() {
        Iterable<MiningMachine> allMachinesIterable = miningMachineService.miningMachineRepository.findAll();
        List<MiningMachineDto> allMachines = new ArrayList<>();
        for ( MiningMachine machine : allMachinesIterable) {
            allMachines.add(miningMachineDtoMapper.mapToDto(machine));
        }
        return allMachines;
    }

    @GetMapping("/miningMachines/{id}/orders")
    public Iterable<OrderDto> getAllOrdersOfMiningMachine(@PathVariable("id") UUID id) {
        Optional<MiningMachine> machine = miningMachineService.miningMachineRepository.findById(id);
        List<Order> orders = machine.get().getOrderHistory();
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order: orders) {
            orderDtos.add(orderDtoMapper.mapToDto(order));
        }
        return orderDtos;
    }

    @DeleteMapping("/miningMachines/{id}/orders")
    public ResponseEntity deleteAllOrdersOfMiningMachines(@PathVariable("id")  UUID id) {
        Optional<MiningMachine> machine = miningMachineService.miningMachineRepository.findById(id);
        machine.get().deleteAllOrders();
        miningMachineService.miningMachineRepository.save(machine.get());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/miningMachines/{id}")
    public MiningMachineDto getMiningMachine(@PathVariable("id") UUID id) {
        Optional<MiningMachine> machine = miningMachineService.miningMachineRepository.findById(id);
        MiningMachineDto miningMachineDto = miningMachineDtoMapper.mapToDto(machine.get());
        return miningMachineDto;
    }

    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity deleteMiningMachine(@PathVariable("id") UUID id) {
        miningMachineService.miningMachineRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/miningMachines/{id}/orders")
    public ResponseEntity giveMiningMachineOrder(@PathVariable("id") UUID id, @RequestBody OrderDto orderDto) {
        Optional<MiningMachine> machine = miningMachineService.miningMachineRepository.findById(id);
        Order order = orderDtoMapper.mapToEntity(orderDto);
        OrderDto orderToDto = orderDtoMapper.mapToDto(order);
        machine.get().executeOrder(order, transportTechnologyService, fieldService.getFieldRepository());
        return new ResponseEntity(orderToDto, HttpStatus.CREATED);
    }

    @PostMapping("/miningMachines")
    public ResponseEntity createNewMiningMachine(@RequestBody MiningMachineDto miningMachineDto) {
        MiningMachine miningMachine = miningMachineDtoMapper.mapToEntity(miningMachineDto);
        miningMachineService.miningMachineRepository.save(miningMachine);
        MiningMachineDto miningMachineDtoNew = miningMachineDtoMapper.mapToDto(miningMachine);
        return new ResponseEntity(miningMachineDtoNew, HttpStatus.CREATED);
    }

    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity changeNameOfMiningMachine(@PathVariable("id") UUID id, @RequestBody MiningMachineDto miningMachineDto) {
        Optional<MiningMachine> machine = miningMachineService.miningMachineRepository.findById(id);
        MiningMachine miningMachine = machine.get();
        miningMachine.setName(miningMachineDto.getName());
        MiningMachineDto miningMachineDtoNew = miningMachineDtoMapper.mapToDto(miningMachine);
        return new ResponseEntity(miningMachineDtoNew, HttpStatus.OK);
    }

}
