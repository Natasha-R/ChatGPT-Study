package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class MapServiceUnitTest {

    private SpaceShipDeckRepository spaceShipDeckRepository;
    private ConnectionRepository connectionRepository;
    private StringRectangleObstacleFactory stringRectangleObstacleFactory;
    private StringConnectionFactory stringConnectionFactory;
    private MapService testSubject;

    @BeforeEach
    public void setup() {
        this.spaceShipDeckRepository = mock(SpaceShipDeckRepository.class);
        this.connectionRepository = mock(ConnectionRepository.class);
        this.stringRectangleObstacleFactory = mock(StringRectangleObstacleFactory.class);
        this.stringConnectionFactory = mock(StringConnectionFactory.class);
        this.testSubject = new MapService(this.spaceShipDeckRepository,
                this.connectionRepository, this.stringRectangleObstacleFactory,
                this.stringConnectionFactory);
    }

    @Test
    public void testAddMapShouldSave() {
        //given
        var expectedSpaceShipDeck = mock(SpaceShipDeck.class);
        when(expectedSpaceShipDeck.getId()).thenReturn(UUID.randomUUID());
        when(this.spaceShipDeckRepository.save(any()))
                .thenReturn(expectedSpaceShipDeck);
        //when
        var result = this.testSubject.addMap(10, 20);
        //then
        verify(this.spaceShipDeckRepository, times(1)).save(any());
        Assertions.assertThat(result).isEqualTo(expectedSpaceShipDeck.getId());
    }
}
