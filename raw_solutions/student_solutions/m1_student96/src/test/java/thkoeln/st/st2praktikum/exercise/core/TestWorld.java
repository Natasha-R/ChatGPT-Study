package thkoeln.st.st2praktikum.exercise.core;

import lombok.Data;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroidService;

import java.util.UUID;

@Data
public abstract class TestWorld {

    protected UUID spaceShipDeck0;
    protected UUID spaceShipDeck4;

    protected UUID maintenanceDroid0;

    protected UUID connection1;
    protected UUID connection2;



    protected void createWorld(MaintenanceDroidService service) {
        spaceShipDeck0 = service.addSpaceShipDeck(2,4);
        spaceShipDeck4 = service.addSpaceShipDeck(5,2);

        connection1 = service.addConnection(spaceShipDeck0,"(0,0)", spaceShipDeck4, "(1,0)");



        maintenanceDroid0=service.addMaintenanceDroid("arvin");


    }



}
