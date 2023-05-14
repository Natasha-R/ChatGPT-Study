package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class E2OwnTests {

    MiningMachineService service;
    UUID field1;
    UUID field2;

    @BeforeEach
    void setupWorld(){
        service = new MiningMachineService();
        field1 = service.addField(5,5);
        field2 = service.addField(5,5);
    }

    @Test
    void traversingConnectionWithOccupiedDestinationNotPossible(){
        service.addConnection(
                field1,
                new Coordinate(0,0),
                field2,
                new Coordinate(0,0)
        );

        UUID miningMachine = service.addMiningMachine("TheMiner");
        UUID occupyingMachine = service.addMiningMachine("TheOccupier");

        service.executeCommand(miningMachine,new Command("[en," + field1 + "]"));
        service.executeCommand(occupyingMachine,new Command("[en," + field2 + "]"));

        assertFalse(service.executeCommand(miningMachine,new Command("[tr,"+ field2 + "]")));
        service.executeCommand(occupyingMachine,new Command("[no," + 1 + "]"));
        assertTrue(service.executeCommand(miningMachine,new Command("[tr,"+ field2 + "]")));
    }

    @Test
    void placingWallOutOfBoundNotPossible(){
        assertThrows(RuntimeException.class, () ->
                service.addWall(field1,new Wall("(0,6)-(0,8)")
                )
        );
    }

    @Test
    void placingConnectionOutOfBoundNotPossible(){

        assertThrows(RuntimeException.class, () ->
                service.addConnection(
                        field1,
                        new Coordinate(7,11),
                        field2,
                        new Coordinate(2,1)
                )
        );
        assertThrows(RuntimeException.class, () ->
                service.addConnection(
                        field1,
                        new Coordinate(2,4),
                        field2,
                        new Coordinate(3,8)
                )
        );

        assertDoesNotThrow( () ->
                service.addConnection(
                    field1,
                    new Coordinate(2,2),
                    field2,
                    new Coordinate(2,2)
                )
        );
    }

}
