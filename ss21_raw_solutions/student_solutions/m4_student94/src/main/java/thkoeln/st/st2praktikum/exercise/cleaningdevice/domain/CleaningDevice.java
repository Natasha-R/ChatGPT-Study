package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class CleaningDevice {
    @Id
    protected UUID cleaningDeviceId;

   private String name;

    private UUID spaceId;

    @ManyToOne
    public Space deviceSpace;

    @Embedded
    private Order order;

    @Embedded
    private Coordinate coordinate;

    @ElementCollection(targetClass = Order.class)
    private List<Order> orders = new ArrayList<>();

    public void  addOrder(Order order){
        orders.add(order);

    }
    public CleaningDevice(String name) {
        this.name = name;
        this.cleaningDeviceId = UUID.randomUUID();
    }
}
