package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class TidyUpRobot {
    @Id
    @Getter
    private UUID id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @Embedded
    private Point currentPosition;
    @Getter
    @Setter
    private UUID roomId;
    @ElementCollection
    @Getter
    @Setter
    private List<Order> orders = new ArrayList<>();

    public TidyUpRobot(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public Point getPoint() {
        return this.currentPosition;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }
}

