package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class E2Tests {
    UUID mm1;
    UUID mm2;
    UUID field;
    MiningMachineService service;

    @BeforeEach
    public void initWorld(){
        service = new MiningMachineService();
        field = service.addField(10,10);
        mm1 = service.addMiningMachine("foo");
        mm2 = service.addMiningMachine("bar");
        service.executeCommand(mm1,new Order(OrderType.ENTER, field));
        service.executeCommand(mm1,new Order(OrderType.EAST, 1));
        service.executeCommand(mm2,new Order(OrderType.ENTER, field));
    }

    private void testPoint(){
        var act = service.getPoint(mm2);
        var exp = new Point(0,0);
        assertEquals(exp, act);
    }

    @Test
    public void testBlockingMiningMachine(){
        var o = new Order(OrderType.EAST, 2);
        service.executeCommand(mm2, o);
        testPoint();
    }

    @Test
    public void testMovingOOB(){
        var o = new Order(OrderType.SOUTH, 2);
        service.executeCommand(mm2, o);
        testPoint();
    }

    @Test
    public void testTraversingInvalid(){
        var o = new Order(OrderType.TRANSPORT, field);
        var p1 =new Point(0,0);
        var p2 =new Point(1,0);
        service.addConnection(field,p1,field,p2);
        assertEquals(false, service.executeCommand(mm2,o));
        testPoint();
    }
}
