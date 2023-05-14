package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.OrderDto;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidDto;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestController
public class MaintenanceDroidController {
    private final MaintenanceDroidService maintenanceDroidService;
    @Autowired
    public MaintenanceDroidController(MaintenanceDroidService maintenanceDroidService) {
        this.maintenanceDroidService = maintenanceDroidService;
    }



    @RequestMapping(method = RequestMethod.GET, value ="/maintenanceDroids")
    public @ResponseBody Iterable<MaintenanceDroidDto> getAllMaintenanceDroid() {
        Iterable<MaintenanceDroid> found = maintenanceDroidService.finAll();
        List foundDtos = new ArrayList<MaintenanceDroidDto>();
        ModelMapper modelMapper = new ModelMapper();
        for ( MaintenanceDroid sg : found ) {
            foundDtos.add( modelMapper.map( sg, MaintenanceDroidDto.class ) );
        }
        return foundDtos;
    }



    @PostMapping("/maintenanceDroids")
    public ResponseEntity<?> createnewMaintenanceDroid(@RequestBody MaintenanceDroidDto maintenanceDroidDto) {
        try {
            MaintenanceDroid newMaintenanceDroid = new MaintenanceDroid();
            ModelMapper modelMapper=new ModelMapper();
            modelMapper.map( maintenanceDroidDto, newMaintenanceDroid );
            maintenanceDroidService.addMaintenanceDroid( newMaintenanceDroid.getName() );
            URI returnURI = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newMaintenanceDroid.getId())
                    .toUri();
            return ResponseEntity
                    .created(returnURI)
                    .body( maintenanceDroidDto );
        }
        catch( Exception e ) {
            return new ResponseEntity<>( HttpStatus.UNPROCESSABLE_ENTITY );
        }
    }

    @PostMapping("/maintenanceDroids/{maintenanceDroid-id}/orders")
    public ResponseEntity<?> giveMaintenanceDroidOrder(@PathVariable("maintenanceDroid-id") UUID id , @RequestBody OrderDto orderDto) {

            Optional<MaintenanceDroid> found = Optional.ofNullable(maintenanceDroidService.getMaintenanceDroid(id));

            if( found.isEmpty())
                return new ResponseEntity<>( HttpStatus.NOT_FOUND );
            else {
                ModelMapper modelMapper=new ModelMapper();
                Order taskent = modelMapper.map( orderDto, Order.class );
                maintenanceDroidService.executeCommand(id,taskent);
                URI returnURI = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(found.get().getId())
                        .toUri();
                return ResponseEntity
                        .created(returnURI)
                        .body( orderDto );
            }

    }

    @RequestMapping(method = RequestMethod.GET, value ="/maintenanceDroids/{maintenanceDroid-id}")
    public ResponseEntity<MaintenanceDroidDto> getoneMaintenanceDroid(@PathVariable("maintenanceDroid-id") UUID id) {
        Optional<MaintenanceDroid> found = Optional.ofNullable(maintenanceDroidService.getMaintenanceDroid(id));
        if( found.isEmpty() )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            ModelMapper modelMapper= new ModelMapper();
            return new ResponseEntity<MaintenanceDroidDto>(
                    modelMapper.map( found.get(), MaintenanceDroidDto.class ), HttpStatus.OK );
        }
    }

    @RequestMapping(method = RequestMethod.GET, value ="/maintenanceDroids/{maintenanceDroid-id}/orders")
    public  @ResponseBody
    Iterable<OrderDto> getOrderfromMaintenanceDroid(@PathVariable("maintenanceDroid-id") UUID id ) {
        Optional<MaintenanceDroid> found = Optional.ofNullable(maintenanceDroidService.getMaintenanceDroid(id));
        if( found.isEmpty() )
            throw new UnsupportedOperationException();
        else {
            Iterable<Order> foundTasks = found.get().getOrderList();
            List foundTasksDto = new ArrayList<OrderDto>();
            ModelMapper modelMapper= new ModelMapper();

            for ( Order sg : foundTasks ) {
                foundTasksDto.add( modelMapper.map( sg, OrderDto.class ) );
            }
            return foundTasksDto;


        }
    }

    @RequestMapping(method = RequestMethod.PATCH,value = "/maintenanceDroids/{maintenanceDroid-id}")
    public ResponseEntity<MaintenanceDroidDto> updateMaintenanceDroidName( @PathVariable("maintenanceDroid-id") UUID id , @RequestBody MaintenanceDroidDto tidyUpRobotDto) {

        Optional<MaintenanceDroid> found = Optional.ofNullable(maintenanceDroidService.getMaintenanceDroid(id));
        if( found.isEmpty())
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            found.get().setName(tidyUpRobotDto.getName());
            maintenanceDroidService.updateRobots(found.get());
            ModelMapper modelMapper= new ModelMapper();
            return new ResponseEntity<>(
                    modelMapper.map( found.get(), MaintenanceDroidDto.class ), HttpStatus.OK );
        }
    }


    @RequestMapping(method = RequestMethod.DELETE,value = "/maintenanceDroids/{maintenanceDroid-id}/orders")
    public ResponseEntity<OrderDto> deleteOrderfromMaintenanceDroid( @PathVariable("maintenanceDroid-id") UUID id ) {

        Optional<MaintenanceDroid> found = Optional.ofNullable(maintenanceDroidService.getMaintenanceDroid(id));
        if( found.isEmpty())
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        else {
            if (found.get().clearOrderHistory()) {
                ModelMapper modelMapper = new ModelMapper();
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else
                return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
    }
}
