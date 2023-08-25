package thkoeln.st.st2praktikum.exercise.domainprimitives;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import javax.persistence.Embeddable;
import java.util.UUID;


public class Order {

    private OrderType orderType;
    private Integer numberOfSteps;
    private UUID gridId;

    
    protected Order() {
        
    }

    public Order(OrderType orderType, Integer numberOfSteps) {
        this.orderType = orderType;
        this.numberOfSteps = numberOfSteps;
        
    }

    public Order(OrderType orderType, UUID gridId) {
        this.orderType = orderType;
        this.gridId = gridId;
        
    }
    
    public OrderType getOrderType() {
        return orderType;
    }

    public Integer getNumberOfSteps() {
        return numberOfSteps;
    }

    public UUID getGridId() {
        return gridId;
    }

    public static Order fromString(String orderString ) {
        throw new UnsupportedOperationException();
    }
    
}
