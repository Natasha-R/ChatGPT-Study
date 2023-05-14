package thkoeln.st.st2praktikum.exercise.maintenancedroid.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.domainprimitives.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain.ShipDeck;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.TransportTechnology;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class MaintenanceDroid extends AbstractEntity {



    @Embedded
    private Coordinate coordinate;
    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Order> orderList=new ArrayList<>();

    @Getter
    @Setter
    private String name;
    private UUID now_in_ShipDeck = UUID.randomUUID();
    private int posx ;
    private int posy ;


    public MaintenanceDroid(String name) {
        this.name = name;
    }


    public UUID getSpaceShipDeckId() {
        return now_in_ShipDeck;
    }

    public Coordinate getCoordinate(){
        return new Coordinate(posx,posy);
    }

    public boolean clearOrderHistory(){
        orderList = new ArrayList<>();
        return true;
    }



    public boolean preformOrder(Order order , ShipDeck shipDeck){
        if (shipDeck != null && order!=null)
            orderList.add(order);
        switch (order.getOrderType()) {

            case TRANSPORT:
                return traverse(order.getGridId(), shipDeck);

            case NORTH:
                moveNorth(order.getNumberOfSteps(), shipDeck);
                return true;
            case EAST:
                moveEast(order.getNumberOfSteps(), shipDeck);
                return true;
            case SOUTH:
                moveSouth(order.getNumberOfSteps(), shipDeck);
                return true;
            case WEST:
                moveWest(order.getNumberOfSteps(), shipDeck);
                return true;
        }
        return false;
    }

    public boolean traverse(UUID shipDeckID, ShipDeck shipDeck) {

        if (shipDeck.getTransportTechnology()!=null)
            for (TransportTechnology value : shipDeck.getTransportTechnology())
                for (Connection val : value.getConnectionList())
                    if (val.getEntranceShipDeckID().equals(now_in_ShipDeck) && val.getEntranceCoordinates().equals(getCoordinate())) {

                        posx = val.getExitCoordinates().getX();
                        posy = val.getExitCoordinates().getY();
                        now_in_ShipDeck = shipDeckID;
                        return true;
                    }
        return false;
    }


    public void encompass(UUID shipDeckID) {

        now_in_ShipDeck = shipDeckID;
        posx = 0;
        posy = 0;
        orderList.add(new Order(OrderType.ENTER,shipDeckID));

    }


    public void moveNorth(int moves, ShipDeck shipDeck) {
        int takenSteps = 0;
        if (moves > shipDeck.getGrid()[0].length - 1 - posy) {
            moves = shipDeck.getGrid()[0].length - 1 - posy;
        }

        while (takenSteps < moves) {
            if (shipDeck.getGrid()[posx][posy + takenSteps] == "Wan1" && shipDeck.getGrid()[posx][posy + takenSteps + 1] == "Wan2") {
                posy = posy + takenSteps;
                return;
            }


            if (shipDeck.getGrid()[posx][posy + takenSteps] == "Wan2" && shipDeck.getGrid()[posx][posy + takenSteps + 1] == "Wan1") {
                posy = posy + takenSteps;
                return;
            } else {
                takenSteps += 1;
            }
        }
        posy = posy + takenSteps;
    }


    public void moveEast(int moves, ShipDeck room) {
        int takenSteps = 0;


        if (moves > room.getGrid().length - 1 - posx) {
            moves = room.getGrid().length - 1 - posx;
        }
        while (takenSteps < moves) {
            if (room.getGrid()[posx + takenSteps][posy] == "Wan1" && room.getGrid()[posx + takenSteps + 1][posy] == "Wan2") {
                posx = posx + takenSteps;
                return;

            }
            if (room.getGrid()[posx + takenSteps][posy] == "Wan2" && room.getGrid()[posx + takenSteps + 1][posy] == "Wan1") {
                posx = posx + takenSteps;
                return;

            } else {
                takenSteps += 1;
            }
        }
        posx = posx + takenSteps;

    }


    public void moveSouth(int moves, ShipDeck room) {
        int takenSteps = 0;


        if (posy - moves < 0) {
            moves = posy;
        }
        while (takenSteps < moves) {
            if (room.getGrid()[posx][posy - takenSteps] == "Wan1" && room.getGrid()[posx][posy - takenSteps - 1] == "Wan2") {
                posy = posy - takenSteps;
                return;

            }
            if (room.getGrid()[posx][posy - takenSteps] == "Wan2" && room.getGrid()[posx][posy - takenSteps - 1] == "Wan1") {
                posy = posy - takenSteps;
                return;

            } else {
                takenSteps += 1;
            }
        }
        posy = posy - takenSteps;


    }


    public void moveWest(int moves, ShipDeck room) {
        int takenSteps = 0;
        if (posx - moves < 0) {
            moves = posx;
        }

        while (takenSteps < moves) {
            if (room.getGrid()[posx - takenSteps][posy] == "Wan1" && room.getGrid()[posx - takenSteps - 1][posy] == "Wan2") {
                posx = posx - takenSteps;
                return;
            }
            if (room.getGrid()[posx - takenSteps][posy] == "Wan2" && room.getGrid()[posx - takenSteps - 1][posy] == "Wan1") {
                posx = posx - takenSteps;
                return;
            } else {
                takenSteps += 1;
            }
        }
        posx = posx - takenSteps;

    }


    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public UUID getRoomId() {
        return now_in_ShipDeck;
    }

}