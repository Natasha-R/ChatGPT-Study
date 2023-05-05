package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.linearAlgebra.Vector;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StringConnectionFactoryUnitTest {

    private SpaceShipDeckRepository spaceShipDeckRepository;
    private StringConnectionFactory testSubject;

    @BeforeEach
    public void setup() {
        this.spaceShipDeckRepository = mock(SpaceShipDeckRepository.class);
        this.testSubject = new StringConnectionFactory(this.spaceShipDeckRepository);
    }

    @Test
    public void testCreateConnectionShouldCreateConnection() {
        //given
        var spaceShipDeckSource = new SpaceShipDeck(10, 10);
        var spaceShipDeckTarget = new SpaceShipDeck(10, 10);
        when(this.spaceShipDeckRepository.findById(spaceShipDeckSource.getId()))
                .thenReturn(Optional.of(spaceShipDeckSource));
        when(this.spaceShipDeckRepository.findById(spaceShipDeckTarget.getId()))
                .thenReturn(Optional.of(spaceShipDeckTarget));
        //when
        var result = this.testSubject.createConnection(spaceShipDeckSource
                .getId(), "(1,0)", spaceShipDeckTarget.getId(), "(5,2)");
        //then
        Assertions.assertThat(result)
                .extracting(Connection::getSource)
                .isEqualTo(MapPosition
                        .of(spaceShipDeckSource, Vector.of(1.5, 0.5)));
        Assertions.assertThat(result)
                .extracting(Connection::getTarget)
                .isEqualTo(MapPosition
                        .of(spaceShipDeckTarget, Vector.of(5.5, 2.5)));
    }

    @Test
    public void testCreateConnectionShouldThrowNoSuchElementExceptionWhenSpaceShipDoesntExist() {
        //given
        var spaceShipDeckSource = new SpaceShipDeck(10, 10);
        var spaceShipDeckTarget = new SpaceShipDeck(10, 10);
        when(this.spaceShipDeckRepository.findById(spaceShipDeckSource.getId()))
                .thenReturn(Optional.of(spaceShipDeckSource));
        //when
        //then
        Assertions.assertThatThrownBy(() -> this.testSubject.createConnection(
                spaceShipDeckSource.getId(),
                "(1,0)",
                spaceShipDeckTarget.getId(),
                "(4,5)"
        ))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testCreateConnectionShouldThrowExceptionWhenSourceOutOfBounds() {
        //given
        var spaceShipDeckSource = mock(SpaceShipDeck.class);
        var spaceShipDeckTarget = mock(SpaceShipDeck.class);
        when(spaceShipDeckSource.getId()).thenReturn(UUID.randomUUID());
        when(spaceShipDeckSource.isInBounds(any())).thenReturn(false);
        when(spaceShipDeckTarget.getId()).thenReturn(UUID.randomUUID());
        when(spaceShipDeckTarget.isInBounds(any())).thenReturn(true);
        when(this.spaceShipDeckRepository.findById(spaceShipDeckSource.getId()))
                .thenReturn(Optional.of(spaceShipDeckSource));
        when(this.spaceShipDeckRepository.findById(spaceShipDeckTarget.getId()))
                .thenReturn(Optional.of(spaceShipDeckTarget));
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.createConnection(
                spaceShipDeckSource.getId(),
                "(1,0)",
                spaceShipDeckTarget.getId(),
                "(4,5)"
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreateConnectionShouldThrowExceptionWhenTargetOutOfBounds() {
        //given
        var spaceShipDeckSource = mock(SpaceShipDeck.class);
        var spaceShipDeckTarget = mock(SpaceShipDeck.class);
        when(spaceShipDeckSource.getId()).thenReturn(UUID.randomUUID());
        when(spaceShipDeckSource.isInBounds(any())).thenReturn(true);
        when(spaceShipDeckTarget.getId()).thenReturn(UUID.randomUUID());
        when(spaceShipDeckTarget.isInBounds(any())).thenReturn(false);
        when(this.spaceShipDeckRepository.findById(spaceShipDeckSource.getId()))
                .thenReturn(Optional.of(spaceShipDeckSource));
        when(this.spaceShipDeckRepository.findById(spaceShipDeckTarget.getId()))
                .thenReturn(Optional.of(spaceShipDeckTarget));
        //when
        //then
        Assertions.assertThatThrownBy(() -> testSubject.createConnection(
                spaceShipDeckSource.getId(),
                "(1,0)",
                spaceShipDeckTarget.getId(),
                "(4,5)"
        )).isInstanceOf(IllegalArgumentException.class);
    }
}
