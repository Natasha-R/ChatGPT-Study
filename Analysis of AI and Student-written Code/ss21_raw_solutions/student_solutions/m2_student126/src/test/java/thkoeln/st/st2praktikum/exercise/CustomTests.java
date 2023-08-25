package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.UUID;


public class CustomTests {

    MiningMachineService service;

    UUID field1;
    UUID field2;

    @BeforeEach
    public void initializeWorld() {
        service = new MiningMachineService();

        field1 = service.addField(5, 5);
        field2 = service.addField(3, 7);
    }

    @Test
    public void bumpingIntoAnotherMiningMachineInterruptsMovement() {
        UUID miningMachine1 = service.addMiningMachine("Chuck");
        UUID miningMachine2 = service.addMiningMachine("Glenn");
        executeCommands(miningMachine1, new Command[]{
                new Command("[en," + field1 + " ]"),
                new Command("[no,1]"),
                new Command("[ea,1]")
        });
        executeCommands(miningMachine2, new Command[]{
                new Command("[en," + field1 + " ]"),
                new Command("[ea,1]"),
                new Command("[no,3]"),
                new Command("[ea,1]"),
                new Command("[no,1]"),
                new Command("[we,2]"),
                new Command("[no,1]"),
                new Command("[we,1]"),
                new Command("[so,2]"),
                new Command("[we,1]"),
                new Command("[so,1]"),
                new Command("[ea,5]"),
        });

        Assertions.assertEquals(new Coordinate(0, 1), service.getCoordinate(miningMachine2));
    }

    @Test
    public void traversingNotPossibleWhenDestinationIsOccupied() {
        UUID miningMachine1 = service.addMiningMachine("Chuck");
        UUID miningMachine2 = service.addMiningMachine("Glenn");
        UUID miningMachine3 = service.addMiningMachine("Ozzy");

        service.addConnection(field1, new Coordinate(0, 0), field2, new Coordinate(0, 0));
        service.addConnection(field1, new Coordinate(1, 0), field2, new Coordinate(3, 2));

        executeCommands(miningMachine1, new Command[]{
                new Command("[en," + field2 + "]"),
                new Command("[ea, 3]"),
                new Command("[no, 2]"),
        });
        service.executeCommand(miningMachine2, new Command("[en," + field2 + "]"));
        executeCommands(miningMachine3, new Command[]{
                new Command("[en," + field1 + "]"),
                new Command("[tr," + field2 + "]"),
                new Command("[we, 1]"),
                new Command("[tr," + field2 + "]")
        });

        Assertions.assertEquals(field1, service.getMiningMachineFieldId(miningMachine3));
    }

    @Test
    public void placingABarrierOutOfBoundsShouldNotBePossible() {
        Assertions.assertThrows(RuntimeException.class, () ->
                service.addBarrier(field1, new Barrier("(5,5)-(5,7)"))
        );
        Assertions.assertThrows(RuntimeException.class, () ->
                service.addBarrier(field1, new Barrier("(0,0)-(0,7)"))
        );
        Assertions.assertThrows(RuntimeException.class, () ->
                service.addBarrier(field1, new Barrier("(0,0)-(5,0)"))
        );
        Assertions.assertThrows(RuntimeException.class, () ->
                service.addBarrier(field1, new Barrier("(7,3)-(3,3)"))
        );
    }

    private void executeCommands(UUID miningMachine, Command[] Commands) {
        for (Command command : Commands) {
            service.executeCommand(miningMachine, command);
        }
    }
}
