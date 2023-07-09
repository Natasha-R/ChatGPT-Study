package thkoeln.st.st2praktikum.exercise.miningmachine.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class MiningMachine
{
    @Embedded
    public static final Vector2D zeroCoordinate = new Vector2D(0, 0);

    @Id
    private UUID uuid;

    private boolean initialised;


    @ElementCollection
    private List<Order> orderHistory = new ArrayList<>();


    @Setter
    private String name;

    @Setter
    @OneToOne
    private Field field;

    @Setter
    @Embedded
    private Vector2D coordinate;

    public MiningMachine(String name)
    {
        uuid = UUID.randomUUID();

        initialised = false;


        this.name = name;

        field = null;
        coordinate = new Vector2D(zeroCoordinate.getX(), zeroCoordinate.getY());
    }

    public boolean isInitialised()
    {
        return initialised;
    }
    public void setInitialised(boolean initialised)
    {
        this.initialised = initialised;
    }

    public UUID getFieldId()
    {
        return field.getUuid();
    }

    public Vector2D getVector2D()
    {
        return coordinate;
    }

    public UUID getUuid()
    {
        return uuid;
    }


    public List<Order> getOrderHistory()
    {
        return orderHistory;
    }

    public void addToOrderHistory(Order order)
    {
        orderHistory.add(order);
    }

    public void clearOrderHistory()
    {
        orderHistory = new ArrayList<Order>();
    }
}