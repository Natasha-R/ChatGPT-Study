package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;

import lombok.Data;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class MaintenanceDroidDto {

    Coordinate coordinate;
    private List<Order> orderList=new ArrayList<>();

    private String name;
    private UUID now_in_ShipDeck = UUID.randomUUID();
    private int posx ;
    private int posy ;

    public UUID getnowinShipDeck(){
        return now_in_ShipDeck;
    }

    public String getCoordinateString(){
        return "(" + posx + "," + posy + ")";
    }

    public Coordinate getCoordinate(){
        return new Coordinate(posx,posy);
    }
}
