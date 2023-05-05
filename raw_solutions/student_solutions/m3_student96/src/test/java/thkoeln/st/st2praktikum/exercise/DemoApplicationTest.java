package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.droids.MaintenanceDroidRepository;
import thkoeln.st.st2praktikum.exercise.map.SpaceShipDeckRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DemoApplicationTest {

    MaintenanceDroidService service = new MaintenanceDroidService();

    @Autowired
    public E1MovementTests( WebApplicationContext appContext )

    protected UUID spaceShipDeck1;
    protected UUID spaceShipDeck2;
    protected UUID spaceShipDeck3;

    @Autowired
    private SpaceShipDeckRepository spaceShipDeckRepository;

    @Autowired
    private MaintenanceDroidRepository maintenanceDroidRepository;

    @BeforeEach
    public void setupData() {
        spaceShipDeck1 = service.addSpaceShipDeck(6,6);
        spaceShipDeck2 = service.addSpaceShipDeck(5,5);
        spaceShipDeck3 = service.addSpaceShipDeck(3,3);
    }

    @AfterEach
    public void tearDown() {
        spaceShipDeckRepository.deleteAll();
        maintenanceDroidRepository.deleteAll();
    }

    @Test
    public void contextLoads(){

    }

    @Test
    public void testSpaceShipDeck(){
        assertEquals(spaceShipDeck1, service.getSpaceShipDeckRepository().findById(spaceShipDeck1).get());
    }



}
