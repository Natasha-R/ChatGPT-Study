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
    private TaskCommandFactory taskCommandFactory;
    private MaintenanceDroidService testSubject;

    @BeforeEach
    public void setup() {
        this.maintenanceDroidRepository =
                mock(MaintenanceDroidRepository.class);
        this.taskCommandFactory = mock(TaskCommandFactory.class);
        this.testSubject =
                new MaintenanceDroidService(this.maintenanceDroidRepository,
                        this.taskCommandFactory);
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
}
