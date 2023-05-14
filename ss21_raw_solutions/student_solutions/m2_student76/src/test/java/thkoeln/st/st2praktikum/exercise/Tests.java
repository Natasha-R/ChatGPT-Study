package thkoeln.st.st2praktikum.exercise;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests  {

    MaintenanceDroidService service = new MaintenanceDroidService();

    protected UUID spaceDeck1;
    protected UUID spaceDeck2;
    protected UUID spaceDeck3;

    protected UUID maintenanceDroid1;
    protected UUID maintenanceDroid2;

    protected UUID connection1;

    public void executeCommands(MaintenanceDroidService service, UUID maintenanceDroid, Command[] Commands){
        for(Command command: Commands){
            service.executeCommand(maintenanceDroid,command);
        }
    }


    @BeforeEach
    public void implement(){
        spaceDeck1= service.addSpaceShipDeck(8,6);
        spaceDeck2= service.addSpaceShipDeck(5,5);
        spaceDeck3= service.addSpaceShipDeck(6,8);

        maintenanceDroid1= service.addMaintenanceDroid("R2-D2");
        maintenanceDroid2= service.addMaintenanceDroid("C-3PO");

        connection1= service.addConnection(spaceDeck1,new Point("(4,0)"),spaceDeck2,new Point("(1,1)"));

    }


    @Test
    public void connectionOutOfBoundTest(){
        implement();
        assertThrows(RuntimeException.class, () -> service.addConnection(spaceDeck1, new Point(3,3),spaceDeck2, new Point(1,5)));
        assertThrows(RuntimeException.class, () -> service.addConnection(spaceDeck3, new Point(2,1),spaceDeck1, new Point(6,1)));
        assertThrows(RuntimeException.class, () -> service.addConnection(spaceDeck3, new Point(0,2),spaceDeck2, new Point(3,5)));
        assertThrows(RuntimeException.class, () -> service.addConnection(spaceDeck2, new Point(1,1),spaceDeck2, new Point(3,5)));
    }

    @Test
    public void barrierOutOfBoundTest(){

        implement();
        assertThrows(RuntimeException.class, ()-> service.addBarrier(spaceDeck1,new Barrier("(2,4)-(3,6)")));
        assertThrows(RuntimeException.class, ()-> service.addBarrier(spaceDeck2,new Barrier("(3,1)-(4,3)")));
        assertThrows(RuntimeException.class, ()-> service.addBarrier(spaceDeck2,new Barrier("(2,1)-(5,3)")));

    }

    @Test
    public void moveOutOfBound(){
        MaintenanceDroidService service = new MaintenanceDroidService();

        assertThrows(RuntimeException.class, ()-> executeCommands(service,maintenanceDroid1,new Command[]{
                new Command("[en,"+spaceDeck3+"]"),
                new Command("[so,6"),
                new Command("[we,1")
        }));

        assertThrows(RuntimeException.class, ()-> executeCommands(service,maintenanceDroid2,new Command[]{
                new Command("[en,"+spaceDeck1+"]"),
                new Command("[no,2"),
                new Command("[we,3"),
                new Command("[so,1"),
                new Command("[ea,4")
        }));


        assertThrows(RuntimeException.class, ()-> executeCommands(service,maintenanceDroid1,new Command[]{
                new Command("[en,"+spaceDeck2+"]"),
                new Command("[so,2"),
                new Command("[we,1"),
                new Command("[so,3")
        }));


    }

}
