package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.IComponent;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.field.application.FieldRepository;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
public class MiningMachine implements IComponent {
    @Id
    private UUID uuid = UUID.randomUUID();
    @Getter
    private String name;

    @Getter
    @ElementCollection
    private List<Order> orderHistory = new ArrayList<>();

    @OneToOne
    private Field field;

    @Embedded
    private Point position = new Point(0, 0);

    public MiningMachine() {
        this("");
    }

    public MiningMachine(String name) {
        this.name = name;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setName(String newName) {this.name = newName; }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setPosition(Point position) {
        this.position = new Point(position.getX(), position.getY());
    }

    public Field getField() {
        return field;
    }
    public UUID getFieldId() {
        return field.getUUID();
    }

    public Point getPosition() {
        return this.position;
    }
    public Point getPoint() { return this.position; }

    public void deleteAllOrders() {
        orderHistory = new ArrayList<>();
    }

    public Boolean executeOrder(Order order, TransportTechnologyService transportTechnologyService, FieldRepository fieldRepository) {
        boolean success = false;
        orderHistory.add(order);
        switch (order.getOrderType()){
            case NORTH:
                success = moveInSteps(order.getNumberOfSteps(), 1, 0, 1);
                break;
            case EAST:
                success = moveInSteps(order.getNumberOfSteps(), 1, 1, 0);
                break;
            case SOUTH:
                success = moveInSteps(order.getNumberOfSteps(), -1, 0, -1);
                break;
            case WEST:
                success = moveInSteps(order.getNumberOfSteps(), -1, -1, 0);
                break;
            case TRANSPORT:
                Connection connectionToUse = transportTechnologyService.getConnection(getField().getUUID(), order.getGridId(), getPosition());
                if (connectionToUse == null) success = false;
                else success = useConnection(connectionToUse);
                break;
            case ENTER:
                success = setMiningMachineOnField(order.getGridId(), fieldRepository);
                break;
            default:

        }
        return success;
    }

    private boolean useConnection(Connection connection) {
        setField((Field) connection.getDestinationField());
        Point newPosition = new Point(connection.getDestinationPosition().getX(), connection.getDestinationPosition().getY());
        setPosition(newPosition);
        connection.getSourceField().removeMachine(this);
        connection.getDestinationField().addMachine(this);
        return true;
    }

    private boolean setMiningMachineOnField(UUID fieldID, FieldRepository fieldRepository) {
        Optional<Field> fieldOptional = fieldRepository.findById(fieldID);
        if (fieldOptional.isEmpty()) { return false; }
        Field field = fieldOptional.get();
        if (field.addMachine(this)) {
            setField(field);
            setPosition(new Point(0, 0));
        } else { return false; }
        return true;
    }

    private boolean moveInSteps(int stepLength, int toAdd, int stepToX, int stepToY) {
        if (this.field == null) return false;
        Boolean changeXVariable = true;
        if (stepToY != 0) { changeXVariable = false; }
        if (stepLength < 0) {toAdd = toAdd * -1; }
        for (int i = 0; i < Math.abs(stepLength); i++) {
            if (this.position.getX() + stepToX >= 0 && this.position.getY() + stepToY >= 0 &&
                    field.isStepClear(this.position, new Point(this.position.getX() + stepToX, this.position.getY() + stepToY))) {
                if (changeXVariable)
                    this.position = new Point(this.position.getX()+toAdd, this.position.getY());
                else
                    this.position = new Point(this.position.getX(), this.position.getY()+toAdd);
            } else {
                return false;
            }
        }
        return true;
    }
}
