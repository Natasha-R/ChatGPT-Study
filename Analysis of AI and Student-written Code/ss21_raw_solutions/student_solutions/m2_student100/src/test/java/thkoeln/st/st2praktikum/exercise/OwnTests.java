package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import org.junit.jupiter.api.Test;

import java.util.IllegalFormatException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;




public class OwnTests extends MovementTests {

    private MiningMachineService service = new MiningMachineService();


    @BeforeEach

            public  void worldInitialize() {
        
        createWorld(service);
    }




    @Test
    public void moveoutofboundaries() {


        executeOrders(service, miningMachine1, new Order[]{
                new Order("[en," + field1 + "]"),
                new Order("[ea,4]"),
                new Order("[no,10]"),
                new Order("[ea,10]"),
                new Order("[we,10]")
        });

        assertPosition(service, miningMachine1, field1, 0, 0);


    }

    @Test
    public void placingwalloutofbounds() {



        assertThrows(RuntimeException.class, () -> service.addWall(field1,  new Wall("(7,0)-(7,8)")));
        assertThrows(RuntimeException.class, () -> service.addWall(field3,  new Wall("(4,0-4,4)")));
        assertThrows(RuntimeException.class, () -> service.addWall(field2,  new Wall("(6,6-6,9)")));


    }



    @Test
    public void placingconnectionoutofbounds() {


        assertThrows(RuntimeException.class, () -> service.addConnection(
                field1, new Coordinate("(7,1)"), field2, new Coordinate("(3,0)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(
                field1, new Coordinate("(1,0)"), field2, new Coordinate("(8,5)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(
                field2, new Coordinate("(8,1)"), field2, new Coordinate("(3,3)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(
                field2, new Coordinate("(2,1)"), field2, new Coordinate("(8,5)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(
                field3, new Coordinate("(3,6)"), field2, new Coordinate("(1,1)")));
        assertThrows(RuntimeException.class, () -> service.addConnection(
                field3, new Coordinate("(1,1)"), field2, new Coordinate("(8,5)")));




    }
    /*
    @Test
    public void placingconnectionoutofbounds {

    }
*/




}
