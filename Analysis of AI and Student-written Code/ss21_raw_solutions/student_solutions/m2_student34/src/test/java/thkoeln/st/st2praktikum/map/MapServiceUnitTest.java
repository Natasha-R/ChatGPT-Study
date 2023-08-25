package thkoeln.st.st2praktikum.map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class MapServiceUnitTest {

    private SpaceShipDeckRepository spaceShipDeckRepository;
    private ConnectionRepository connectionRepository;
    private ObstacleRectangleObstacleFactory obstacleRectangleObstacleFactory;
    private CoordinateConnectionFactory coordinateConnectionFactory;
    private MapService testSubject;

    @BeforeEach
    public void setup() {
        this.spaceShipDeckRepository = mock(SpaceShipDeckRepository.class);
        this.connectionRepository = mock(ConnectionRepository.class);
        this.obstacleRectangleObstacleFactory = mock(ObstacleRectangleObstacleFactory.class);
        this.coordinateConnectionFactory = mock(CoordinateConnectionFactory.class);
        this.testSubject = new MapService(this.spaceShipDeckRepository, this.connectionRepository, this.obstacleRectangleObstacleFactory,
                this.coordinateConnectionFactory);
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
