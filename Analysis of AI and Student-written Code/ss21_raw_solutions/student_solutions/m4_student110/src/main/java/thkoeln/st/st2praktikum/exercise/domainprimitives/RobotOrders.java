package thkoeln.st.st2praktikum.exercise.domainprimitives;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import javax.persistence.*;
import java.util.UUID;


public class RobotOrders {


    @Getter
    @Setter
    private UUID robotsId;

    @Getter
    @Setter
    private Order order;

    public RobotOrders(UUID robotsId, Order order){
        this.robotsId = robotsId;
        this.order = order;
    }

    public RobotOrders(){}


}
