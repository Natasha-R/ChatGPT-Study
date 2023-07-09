package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class MiningMachine {
    @Id
    private UUID id;
    private UUID fieldId;
    private String name;
    private Coordinate coordinate;
    @ElementCollection(targetClass = Order.class)
    private List<Order> orders;

    public MiningMachine(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.fieldId = null;
        this.coordinate = new Coordinate(0, 0);
        orders = new ArrayList<>();
    }

    public MiningMachine(UUID id, Coordinate coordinate, UUID fieldId, String name) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.fieldId = fieldId;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setFieldId(UUID fieldId) {
        this.fieldId = fieldId;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public UUID getFieldId() {
        return fieldId;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }
}
