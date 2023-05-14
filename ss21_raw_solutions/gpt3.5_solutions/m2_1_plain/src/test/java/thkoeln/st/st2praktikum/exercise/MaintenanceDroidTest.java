package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

class MaintenanceDroidTest {

    private MaintenanceDroid maintenanceDroid1;
    private MaintenanceDroid maintenanceDroid2;
    private Map<UUID, SpaceShipDeck> spaceShipDecks;
    private MaintenanceDroid maintenanceDroid;
    int deckWidth = 10;
    int deckHeight = 10;

    @BeforeEach
    void setUp() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        maintenanceDroid1 = new MaintenanceDroid(id1, "Droid1");
        maintenanceDroid2 = new MaintenanceDroid(id2, "Droid2");

        UUID spaceShipDeckId = UUID.randomUUID();
        SpaceShipDeck spaceShipDeck = new SpaceShipDeck(spaceShipDeckId, deckWidth, deckHeight);
        spaceShipDecks = new HashMap<>();
        spaceShipDecks.put(spaceShipDeckId, spaceShipDeck);

        UUID droidId = UUID.randomUUID();
        String droidName = "R2-D2";
        maintenanceDroid = new MaintenanceDroid(droidId, droidName);
    }

    @Test
    void testMoveInterruptedByAnotherMaintenanceDroid() {
        // Set the coordinates of both droids to be on the same spot
        maintenanceDroid1.move("no1");
        maintenanceDroid2.move("so1");

        // Verify that moving maintenanceDroid1 to the north is interrupted by maintenanceDroid2
        Assertions.assertDoesNotThrow(() -> maintenanceDroid1.move("no1"));
        Assertions.assertEquals(0, maintenanceDroid1.getCurrentX());
        Assertions.assertEquals(1, maintenanceDroid1.getCurrentY());
    }

    @Test
    void testMoveOutOfBounds() {
        // Move droid to an out-of-bounds position
        maintenanceDroid.move("no10000");

        // Assert that the droid did not move and is still at the initial position
        Assertions.assertEquals(0, maintenanceDroid.getCurrentX());
        Assertions.assertEquals(0, maintenanceDroid.getCurrentY());
    }

    @Test
    public void testPlacingConnectionOutOfBounds() {
        UUID id = UUID.randomUUID();
        UUID sourceSpaceShipDeckId = UUID.randomUUID();
        String sourceCoordinate = "A1";
        UUID destinationSpaceShipDeckId = UUID.randomUUID();
        String destinationCoordinate = "Z99";

        // Creating a connection with out of bounds coordinates should throw an IllegalArgumentException
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Connection(id, sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
        });
    }
}