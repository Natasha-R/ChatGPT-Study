


package thkoeln.st.st2praktikum.exercise;

        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import thkoeln.st.st2praktikum.exercise.core.MovementTests;
        import thkoeln.st.st2praktikum.exercise.exception.BarrierException;
        import thkoeln.st.st2praktikum.exercise.exception.ConnexionException;

        import java.util.UUID;

        import static org.junit.jupiter.api.Assertions.*;


public class MyTest extends MovementTests {

    protected MaintenanceDroidService theServiceAgent;

    @BeforeEach
    public void initVariable(){
        System.out.println("---------Before---------");
        theServiceAgent=new MaintenanceDroidService();
        createWorld(theServiceAgent);
    }
    @Test
    public void connectionOutOfBound(){

        assertThrows(ConnexionException.class, () -> theServiceAgent.addConnection(spaceShipDeck1,new Point(6,3),spaceShipDeck2,new Point(3,2)) );
    }

    @Test
    public void mooveOutOfBound(){

        executeCommands(theServiceAgent, maintenanceDroid1, new Command[]{
                new Command("[en," + spaceShipDeck1 + "]"),
                new Command("[no,3]"),
                new Command("[ea,6]"),
                new Command("[so,5]"),
                new Command("[we,1]"),
                new Command("[no,1]"),
                new Command("[tr," + spaceShipDeck2 + "]")
        });
        assertPosition(theServiceAgent, maintenanceDroid1, spaceShipDeck2, 0, 1);
    }

    @Test
    public void barrierOutOfBound(){
        UUID spaceship5=theServiceAgent.addSpaceShipDeck(5,5);
        assertThrows(BarrierException.class,()->  theServiceAgent.addBarrier(spaceship5,new Barrier( new Point(5,2) , new Point(3,3) )) );

    }
}
