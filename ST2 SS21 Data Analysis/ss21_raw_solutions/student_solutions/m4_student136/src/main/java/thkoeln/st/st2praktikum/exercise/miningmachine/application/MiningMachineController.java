package thkoeln.st.st2praktikum.exercise.miningmachine.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.field.domain.FieldRepository;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachine;
import thkoeln.st.st2praktikum.exercise.miningmachine.domain.MiningMachineRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@Transactional
public class MiningMachineController
{
    private MiningMachineService miningMachineService;
    private MiningMachineDtoMapper miningMachineDtoMapper;


    private FieldRepository fieldRepository;
    private ConnectionRepository connectionRepository;
    private MiningMachineRepository miningMachineRepository;
    private TransportCategoryRepository transportCategoryRepository;

    @Autowired
    public MiningMachineController(FieldRepository fieldRepository,
                                   ConnectionRepository connectionRepository,
                                   MiningMachineRepository miningMachineRepository,
                                   TransportCategoryRepository transportCategoryRepository,
                                   MiningMachineService miningMachineService)
    {
        this.fieldRepository = fieldRepository;
        this.connectionRepository = connectionRepository;
        this.miningMachineRepository = miningMachineRepository;
        this.transportCategoryRepository = transportCategoryRepository;

        this.miningMachineService = miningMachineService;
        miningMachineDtoMapper = new MiningMachineDtoMapper();
    }

    /*
     * Get all mining machines
     */
    @GetMapping("/miningMachines")
    public Iterable<MiningMachineDto> getAllMiningMachines()
    {
        Iterable<MiningMachine> allMiningMachines = miningMachineService.findAll();
        List<MiningMachineDto> allDtos = new ArrayList<>();

        for(MiningMachine miningMachine : allMiningMachines)
        {
            allDtos.add(miningMachineDtoMapper.mapToDto(miningMachine));
        }
        return allDtos;
    }

    /**
     * Create a new mining machine
     */
    @PostMapping("/miningMachines")
    public ResponseEntity<MiningMachineDto> createNewMiningMachine(@RequestBody MiningMachineDto miningMachineDto)
    {
        try
        {
            // create new so that the ID has been set, then transfer the received DTO
            MiningMachineDto newMiningMachineDto = miningMachineService.addMiningMachineFromDto(miningMachineDto);

            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newMiningMachineDto.getUuid())
                    .toUri();

            return ResponseEntity
                    .created(returnURI)
                    .body(miningMachineDto);
        }
        catch( Exception e )
        {
            // something bad happened
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /*
     * Get a specific mining machine by ID
     */
    @GetMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDto> getOneMiningMachine(@PathVariable UUID id)
    {
        MiningMachine miningMachine = miningMachineService.findById(id);
        MiningMachineDto miningMachineDto = miningMachineDtoMapper.mapToDto(miningMachine);
        return new ResponseEntity<>(miningMachineDto, HttpStatus.OK);
    }

    /*
     * Delete a specific mining machine
     */
    @DeleteMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDto> deleteOneMiningMachine( @PathVariable UUID id )
    {
        miningMachineService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /*
     * Change the name of a specific mining machine
     */
    @PatchMapping("/miningMachines/{id}")
    public ResponseEntity<MiningMachineDto> changeOneMiningMachine(@PathVariable UUID id, @RequestBody MiningMachineDto miningMachineDto)
    {
        try
        {
            // only update (not creating a new entity, unlike put)
            MiningMachine miningMachine = miningMachineRepository.findById(id).get();

            miningMachineService.patchName(id, miningMachineDto.getName());

            MiningMachineDto newMiningMachineDto = miningMachineDtoMapper.mapToDto(miningMachine);

            //return ResponseEntity.ok().body(miningMachineDto);
            return new ResponseEntity<>(newMiningMachineDto, HttpStatus.OK);
        }
        catch(Exception e)
        {
            // something bad happened
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /**
     * Give a specific mining machine an order
     */
    @PostMapping("/miningMachines/{id}/orders")
    public ResponseEntity<Order> createNewMiningMachineOrder(@PathVariable UUID id, @RequestBody Order order)
    {
        try
        {
            //MiningMachine miningMachine = miningMachineRepository.findById(id).get();

            // execute order
            miningMachineService.executeCommand(id, order);

            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(order.toString())
                    .toUri();

            return ResponseEntity
                    .created(returnURI)
                    .body(order);
        }
        catch( Exception e )
        {
            e.printStackTrace();
            // something bad happened
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /*
     * List all the orders a specific mining machine has received so far
     */
    @GetMapping("/miningMachines/{id}/orders")
    public Iterable<Order> createNewMiningMachineOrder(@PathVariable UUID id)
    {
        Iterable<Order> allOrders = miningMachineService.findById(id).getOrderHistory();

        return allOrders;
    }

    /*
     * Delete the order history of a specific mining machine
     */
    @DeleteMapping("/miningMachines/{id}/orders")
    public ResponseEntity<Order> deleteOrderHistory(@PathVariable UUID id)
    {
        miningMachineService.findById(id).clearOrderHistory();

        return new ResponseEntity<Order>(HttpStatus.NO_CONTENT);
    }
}