package thkoeln.st.st2praktikum.exercise.maintenancedroid.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderRepository;

import java.util.List;
import java.util.UUID;

@RepositoryRestController
@RestController
@RequestMapping("/maintenanceDroids")
public class MaintenanceDroidController {

    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;

    @Autowired
    private Order order;

    // 1. Get all maintenance droids
    @GetMapping("")
    public List<MaintenanceDroid> getAllMaintenanceDroids() {
        return maintenanceDroidRepository.findAll();
    }

    // 2. Create a new maintenance droid
    @PostMapping("")
    public MaintenanceDroid createMaintenanceDroid(@RequestBody MaintenanceDroid maintenanceDroid) {
        return maintenanceDroidRepository.save(maintenanceDroid);
    }

    // 3. Get a specific maintenance droid by ID
    @GetMapping("/{id}")
    public MaintenanceDroid getMaintenanceDroidById(@PathVariable("id") long id) {
        return maintenanceDroidRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance droid not found"));
    }

    // 4. Delete a specific maintenance droid
    @DeleteMapping("/{id}")
    public void deleteMaintenanceDroid(@PathVariable("id") long id) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance droid not found"));
        maintenanceDroidRepository.delete(maintenanceDroid);
    }

    // 5. Change the name of a specific maintenance droid
    @PatchMapping("/{id}")
    public MaintenanceDroid updateMaintenanceDroidName(@PathVariable("id") long id, @RequestBody String newName) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance droid not found"));
        maintenanceDroid.setName(newName);
        return maintenanceDroidRepository.save(maintenanceDroid);
    }

    // 6. Give a specific maintenance droid an order
    @PostMapping("/{id}/orders")
    public Order createMaintenanceOrder(@PathVariable("id") long id, @RequestBody Order order) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance droid not found"));
        order.setMaintenanceDroid(maintenanceDroid);
        return order;
    }

    // 7. List all the orders a specific maintenance droid has received so far
    @GetMapping("/{id}/orders")
    public List<Order> getMaintenanceOrdersForDroid(@PathVariable("id") long id) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance droid not found"));
        return OrderRepository.findByMaintenanceDroid(maintenanceDroid);
    }

    // 8. Delete the order history of a specific maintenance droid
    @DeleteMapping("/{id}/orders")
    public void deleteMaintenanceOrdersForDroid(@PathVariable("id") long id) {
        MaintenanceDroid maintenanceDroid = maintenanceDroidRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maintenance droid not found"));
        List<Order> maintenanceOrders = OrderRepository.findByMaintenanceDroid(maintenanceDroid);
        OrderRepository.deleteAll(maintenanceOrders);
    }
}