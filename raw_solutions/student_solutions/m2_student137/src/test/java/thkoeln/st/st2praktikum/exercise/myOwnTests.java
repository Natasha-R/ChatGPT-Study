package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

public class myOwnTests extends MovementTests {

    // Traversing a connection must not be possible in case the destination is occupied
    @Test
    public  void destinationOccupied() {

        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeOrders(service, cleaningDevice1, new Order[]{
                new Order("[en," + space2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + space3 + "]")
        });

        executeOrders(service, cleaningDevice2, new Order[]{
                new Order("[en," + space2 + "]"),
                new Order("[ea,1]"),
                new Order("[tr," + space3 + "]")
        });

        assertPosition(service, cleaningDevice1, space3, 2, 2);
        assertPosition(service, cleaningDevice2, space2, 1, 0);
    }

    // Using the TR-command must not be possible if there is no outgoing connection on the current position
    @Test
    public  void transportWithoutPortal() {

        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeOrders(service, cleaningDevice1, new Order[]{
                new Order("[en," + space2 + "]"),
                new Order("[no,1]"),
                new Order("[tr," + space3 + "]")
        });

        assertPosition(service, cleaningDevice1, space2, 0, 1);
    }

    // Hitting another cleaningDevice during a Move-Command has to interrupt the current movement
    @Test
    public  void stopMoveOnBumpInOther() {

        CleaningDeviceService service = new CleaningDeviceService();
        createWorld(service);

        executeOrders(service, cleaningDevice1, new Order[]{
                new Order("[en," + space1 + "]"),
                new Order("[no,2]"),
        });

        executeOrders(service, cleaningDevice2, new Order[]{
                new Order("[en," + space1 + "]"),
                new Order("[no,4]"),
        });

        assertPosition(service, cleaningDevice1, space1, 0, 2);
        assertPosition(service, cleaningDevice2, space1, 0, 1);
    }

}