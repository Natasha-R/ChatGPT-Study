package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class UnitTests{

    @Test
    public void stopWhenHittingAnotherMiningMachine() {
        //given
        MiningMachineService service = new MiningMachineService();

        UUID field1 = service.addField(6,6);

        UUID miningMachine1 = service.addMiningMachine("timo");
        UUID miningMachine2 = service.addMiningMachine("pumbaa");
        UUID miningMachine3 = service.addMiningMachine("scar");
        //when
        executeTasks(service, miningMachine1, new Task[]{
                new Task("[en," + field1 + "]"),
                new Task("[ea,1]")
        });

        executeTasks(service, miningMachine2, new Task[]{
                new Task("[en," + field1 + "]"),
                new Task("[no,1]")
        });
        executeTasks(service, miningMachine3, new Task[]{
                new Task("[en," + field1 + "]")
        });
        //then
        assertThrows(IllegalActionException.class,() -> service.executeCommand(miningMachine2,new Task("[so," + field1 + "]")));
        assertThrows(IllegalActionException.class,() -> service.executeCommand(miningMachine1,new Task("[we," + field1 + "]")));
        assertThrows(IllegalActionException.class,() -> service.executeCommand(miningMachine3,new Task("[ea," + field1 + "]")));
        assertEquals(0,service.getPoint(miningMachine3).getX(),"Scar konnte sich anscheinend wegbewegen!");
        assertEquals(0,service.getPoint(miningMachine3).getY(),"Scar konnte sich anscheinend wegbewegen!");
    }
    @Test
    public void connectionPlacedOutOfBounds() {
        //given
        MiningMachineService service = new MiningMachineService();
        UUID field1 = service.addField(6,6);
        UUID field2 = service.addField(6,6);
        //when
        //then
        assertThrows(IllegalActionException.class,() -> service.addConnection(field1, new Point("(1,1)"), field2, new Point("(5,6)")));
        assertThrows(IllegalActionException.class,() -> service.addConnection(field1, new Point("(7,1)"), field2, new Point("(5,2)")));
        assertThrows(IllegalActionException.class,() -> service.addConnection(field1, new Point("(7,1)"), field2, new Point("(5,7)")));
        UUID con4 = service.addConnection(field1, new Point("(1,1)"), field2, new Point("(5,2)"));
        assertNotNull(con4);
        UUID con5 = null;
        try {
            con5 = service.addConnection(field1, new Point("(1,1)"), field2, new Point("(15,3)"));
        }
        catch(IllegalActionException e)
        {
            System.out.println(e.getMessage());
        }
        assertNull(con5);
    }
    @Test
    public void obstaclePlacedOutOfBounds() {
        //given
        MiningMachineService service = new MiningMachineService();
        UUID field1 = service.addField(6,6);
        //when
        //then
        assertThrows(IllegalActionException.class,() -> service.addObstacle(field1, new Obstacle(new Point(1,1),new Point(7,1))));
        assertThrows(IllegalActionException.class,() -> service.addObstacle(field1, new Obstacle(new Point(1,7),new Point(5,7))));


    }
    protected void executeTasks(MiningMachineService service, UUID miningMachine, Task[] Tasks) {
        for (Task task : Tasks) {
            service.executeCommand(miningMachine, task);
        }
    }
}
