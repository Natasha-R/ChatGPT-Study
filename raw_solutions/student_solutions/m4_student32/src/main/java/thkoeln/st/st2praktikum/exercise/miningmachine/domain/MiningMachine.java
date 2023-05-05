package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MiningMachine {

    @Id
    private UUID miningMachineId = UUID.randomUUID();
    private String name;
    private Coordinate coordinate;
    private UUID fieldId;
    @ElementCollection
    private List<Order> orders = new ArrayList<Order>();


    public MiningMachine(UUID miningMachineId, String name) {
        this.miningMachineId = miningMachineId;
        this.name = name;
    }

    public MiningMachine(String name, Coordinate coordinate, UUID fieldId) {
        this.name = name;
        this.coordinate = coordinate;
        this.fieldId = fieldId;
    }

    public MiningMachine(String name, Coordinate coordinate, UUID fieldId, List<Order> orders) {
        this.name = name;
        this.coordinate = coordinate;
        this.fieldId = fieldId;
        this.orders = orders;
    }

    public MiningMachine(String name) {
        this.name = name;
        this.orders = new ArrayList<Order>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

    public void moveNorth() {
        coordinate.setY(coordinate.getY()+1);
    }

    public void moveEast() {
        coordinate.setX(coordinate.getX()+1);
    }

    public void moveSouth() {
        coordinate.setY(coordinate.getY()-1);
    }

    public void moveWest() {
        coordinate.setX(coordinate.getX()-1);
    }
}
