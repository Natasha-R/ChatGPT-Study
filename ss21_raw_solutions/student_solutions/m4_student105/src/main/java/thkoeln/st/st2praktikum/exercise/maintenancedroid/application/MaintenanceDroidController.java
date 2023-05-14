package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.maintenancedroid.domain.MaintenanceDroidRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/maintenanceDroids")
public class MaintenanceDroidController {

    @Autowired
    MaintenanceDroidService droidService;

    @Autowired
    MaintenanceDroidRepository droidRepo;

    @GetMapping
    public List<MaintenanceDroid> getAllDroids() {
        return droidRepo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceDroid addDroid(@RequestBody MaintenanceDroid droid) {
        return droidRepo.findById(droidService.addMaintenanceDroid(droid.getName())).orElseThrow(RuntimeException::new);
    }

    @GetMapping("/{maintenanceDroid-id}")
    public MaintenanceDroid getDroidById(@PathVariable("maintenanceDroid-id") UUID id) {
        return droidRepo.findById(id).orElseThrow(RuntimeException::new);
    }

    @DeleteMapping("/{maintenanceDroid-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDroidById(@PathVariable("maintenanceDroid-id") UUID id) {
        droidRepo.deleteById(id);
    }

    @PatchMapping("/{maintenanceDroid-id}")
    public MaintenanceDroid changeName(@PathVariable("maintenanceDroid-id") UUID id, @RequestBody MaintenanceDroid newDroid) {
        MaintenanceDroid droid = droidRepo.findById(id).orElseThrow(RuntimeException::new);
        droid.setName(newDroid.getName());
        droid = droidRepo.save(droid);
        return droid;
    }

    @PostMapping("/{maintenanceDroid-id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Order giveOrder(@PathVariable("maintenanceDroid-id") UUID id, @RequestBody Order order) {
        droidService.executeCommand(id, order);
        return order;
    }

    @GetMapping("{maintenanceDroid-id}/orders")
    public List<Order> listOrdersOfDroid(@PathVariable("maintenanceDroid-id") UUID id) {
        MaintenanceDroid droid = droidRepo.findById(id).orElseThrow(RuntimeException::new);
        return droid.getOrders();
    }

    @DeleteMapping("{maintenanceDroid-id}/orders")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderHistory(@PathVariable("maintenanceDroid-id") UUID id) {
        MaintenanceDroid droid = droidRepo.findById(id).orElseThrow(RuntimeException::new);
        droid.setOrders(new ArrayList<Order>());
    }
}
