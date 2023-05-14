package thkoeln.st.st2praktikum.droid;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import thkoeln.st.st2praktikum.map.StartPosition;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class MaintenanceDroidServiceUnitTest {

    private MaintenanceDroidRepository maintenanceDroidRepository;
    private StringCommandFactory stringCommandFactory;
    private MaintenanceDroidService testSubject;

    @BeforeEach
    public void setup() {
        this.maintenanceDroidRepository =
                mock(MaintenanceDroidRepository.class);
        this.stringCommandFactory = mock(StringCommandFactory.class);
        this.testSubject =
                new MaintenanceDroidService(this.maintenanceDroidRepository, this.stringCommandFactory);
    }

    @Test
    public void testAddMaintenanceDroidShouldSaveDroid() {
        //given
        when(this.maintenanceDroidRepository
                .save(argThat(maintenanceDroid ->
                        maintenanceDroid.getName().equals("testName")
                        && maintenanceDroid.getPosition() instanceof StartPosition)))
                .thenAnswer((Answer<MaintenanceDroid>) invocationOnMock -> invocationOnMock
                        .getArgument(0));
        //when
        var result = this.testSubject.addMaintenanceDroid("testName");
        //then
        verify(this.maintenanceDroidRepository, times(1)).save(any());
    }

    @Test
    public void testMoveMaintenanceDroidShouldCallMove() {
        //given
        UUID uuid = UUID.randomUUID();
        var droid = mock(MaintenanceDroid.class);
        when(this.maintenanceDroidRepository.findById(uuid))
                .thenReturn(Optional.of(droid));
        var command = mock(Command.class);
        when(this.stringCommandFactory.createCommand("input", uuid)).thenReturn(command);
        when(droid.move(command)).thenReturn(false);
        //when
        var result = this.testSubject.moveMaintenanceDroid(uuid, "input");
        //then
        Assertions.assertThat(result).isFalse();
        verify(droid, times(1)).move(command);
    }
}
