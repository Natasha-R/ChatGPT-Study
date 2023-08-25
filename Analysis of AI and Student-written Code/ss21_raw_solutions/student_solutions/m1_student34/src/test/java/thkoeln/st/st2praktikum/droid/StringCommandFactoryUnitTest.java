package thkoeln.st.st2praktikum.droid;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;
import thkoeln.st.st2praktikum.map.*;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StringCommandFactoryUnitTest {

    private SpaceShipDeckRepository spaceShipDeckRepository;
    private ConnectionRepository connectionRepository;
    private MaintenanceDroidRepository maintenanceDroidRepository;
    private StringCommandFactory testSubject;

    @BeforeEach
    public void setup() {
        this.spaceShipDeckRepository = mock(SpaceShipDeckRepository.class);
        this.connectionRepository = mock(ConnectionRepository.class);
        this.maintenanceDroidRepository =
                mock(MaintenanceDroidRepository.class);
        this.testSubject = new StringCommandFactory(this.spaceShipDeckRepository,
                this.connectionRepository, this.maintenanceDroidRepository);
    }

    @Test
    @DisplayName("[no,3] should be MapCommand(NORTH, 3)")
    public void testNo3ShouldBeNo3() {
        //given
        String input = "[no,3]";
        //when
        var result = testSubject.createCommand(input, UUID.randomUUID());
        //then
        Assertions.assertThat(result)
                .isEqualTo(Command.createMapCommand(Direction.NORTH, 3));
    }

    @Test
    @DisplayName("[ea,4] should be MapCommand(EAST, 4)")
    public void testEa4ShouldBeEA4() {
        //given
        String input = "[ea,4]";
        //when
        var result = testSubject.createCommand(input, UUID.randomUUID());
        //then
        Assertions.assertThat(result)
                .isEqualTo(Command.createMapCommand(Direction.EAST, 4));
    }

    @Test
    @DisplayName("[so,14] should be MapCommand(SOUTH, 14)")
    public void testSo14ShouldBeSo14() {
        //given
        String input = "[so,14]";
        //when
        var result = testSubject.createCommand(input, UUID.randomUUID());
        //then
        Assertions.assertThat(result)
                .isEqualTo(Command.createMapCommand(Direction.SOUTH, 14));
    }

    @Test
    @DisplayName("[we,2] should be MapCommand(WEST, 2)")
    public void testWe2ShouldBeWe2() {
        //given
        String input = "[we,2]";
        //when
        var result = testSubject.createCommand(input, UUID.randomUUID());
        //then
        Assertions.assertThat(result)
                .isEqualTo(Command.createMapCommand(Direction.WEST, 2));
    }

    @Test
    @DisplayName("[en,<uuid>] should be EnterCommand")
    public void testEnShouldBeEnterCommand() {
        //given
        var id = UUID.randomUUID();
        var spaceShipDeck = mock(SpaceShipDeck.class);
        when(spaceShipDeckRepository.findById(id))
                .thenReturn(Optional.of(spaceShipDeck));
        String input = "[en," + id.toString() + "]";
        //when
        var result = testSubject.createCommand(input, UUID.randomUUID());
        //then
        Assertions.assertThat(result)
                .isEqualTo(Command.createEnterCommand(spaceShipDeck));
    }

    @Test
    @DisplayName("[tr,<uuid>] should be ConnectionCommand")
    public void testTrShouldBeConnectionCommand() {
        //given

        var id = UUID.randomUUID();
        var sourceMap = mock(Map.class);
        var targetMap = mock(Map.class);
        when(targetMap.getId()).thenReturn(id);

        var connection = mock(Connection.class);
        when(connection.getTarget())
                .thenReturn(MapPosition.of(targetMap, Vector.of(0, 0)));

        var maintenanceDroid = new MaintenanceDroid(
                MapPosition.of(sourceMap, Vector.of(2, 2)),
                "test");
        when(maintenanceDroidRepository.findById(maintenanceDroid.getId()))
                .thenReturn(Optional.of(maintenanceDroid));
        when(connectionRepository
                .findAllBySource(maintenanceDroid.getPosition()))
                .thenReturn(Collections.singletonList(connection));
        String input = "[tr," + id + "]";
        //when
        var result = testSubject.createCommand(input, maintenanceDroid.getId());
        //then
        Assertions.assertThat(result)
                .isEqualTo(Command.createConnectionCommand(connection));
    }

    @Test
    public void testShouldThrowExceptionForUnknownInput() {
        //given
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject
                .createCommand("[gg,78]", UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
