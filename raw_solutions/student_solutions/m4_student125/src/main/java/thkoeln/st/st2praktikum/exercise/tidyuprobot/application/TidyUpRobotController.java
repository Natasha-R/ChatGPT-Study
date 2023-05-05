package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotDto;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotDtoMapper;

import java.util.List;
import java.util.UUID;

@RestController
public class TidyUpRobotController
{
    private final TidyUpRobotService tidyUpRobotService;
    private TidyUpRobotDtoMapper tidyUpRobotMapper = new TidyUpRobotDtoMapper();

    @Autowired
    public TidyUpRobotController(TidyUpRobotService tidyUpRobotService)
    {
        this.tidyUpRobotService = tidyUpRobotService;
    }

    @GetMapping("/tidyUpRobots")
    public ResponseEntity<List<TidyUpRobot>> getAllTidyUpRobots()
    {
        Iterable<TidyUpRobot> tidyUpRobots = tidyUpRobotService.getTidyUpRobots();
        return new ResponseEntity(tidyUpRobots, HttpStatus.OK);
    }

    @PostMapping("/tidyUpRobots")
    public ResponseEntity<TidyUpRobotDto> createTidyUpRobot(@RequestBody TidyUpRobot tidyUpRobot)
    {
        TidyUpRobot createdTidyUpRobot = tidyUpRobotService.createTidyUpRobot(tidyUpRobot);
        return new ResponseEntity(createdTidyUpRobot, HttpStatus.CREATED);
    }

    @GetMapping("/tidyUpRobots/{id}")
    public ResponseEntity<TidyUpRobot> getTidyUpRobotById(@PathVariable UUID id)
    {
        TidyUpRobot tidyUpRobot = tidyUpRobotService.getTidyUpRobotById(id);
        return new ResponseEntity(tidyUpRobot, HttpStatus.OK);
    }

    @DeleteMapping("/tidyUpRobots/{id}")
    public ResponseEntity<?> deleteOneTidyUpRobot(@PathVariable UUID id)
    {
        if (tidyUpRobotService.deleteById(id))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/tidyUpRobots/{id}")
    public ResponseEntity changeNameOfTidyUpRobot(@PathVariable UUID id, @RequestBody String name)
    {
        tidyUpRobotService.changeName(id, name);
        return new ResponseEntity(name, HttpStatus.OK);
    }

    @PostMapping("/tidyUpRobots/{id}/orders")
    public ResponseEntity<Order> giveOrderToTidyUpRobot(@PathVariable UUID id, @RequestBody Order order)
    {
        tidyUpRobotService.giveOrder(id, order);
        return new ResponseEntity(order, HttpStatus.CREATED);
    }

    @GetMapping("/tidyUpRobots/{id}/orders")
    public ResponseEntity<List<Order>> listOrders(@PathVariable UUID id)
    {
        return new ResponseEntity(tidyUpRobotService.getOrderList(id), HttpStatus.OK);
    }

    @DeleteMapping("/tidyUpRobots/{id}/orders")
    public ResponseEntity deleteOrders(@PathVariable UUID id)
    {
        tidyUpRobotService.deleteOrderList(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
