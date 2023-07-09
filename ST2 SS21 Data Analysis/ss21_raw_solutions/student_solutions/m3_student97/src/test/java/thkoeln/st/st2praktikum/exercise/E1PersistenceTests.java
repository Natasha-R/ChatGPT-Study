package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1PersistenceTests extends MovementTests {

    private TidyUpRobotService service;
    private RoomRepository roomRepository;
    private TransportCategoryRepository transportCategoryRepository;

    @Autowired
    public E1PersistenceTests(WebApplicationContext appContext, TidyUpRobotService service, RoomRepository roomRepository, TransportCategoryRepository transportCategoryRepository) {
        super(appContext);

        this.service = service;
        this.roomRepository = roomRepository;
        this.transportCategoryRepository = transportCategoryRepository;
    }

    @Test
    @Transactional
    public void newRoomPersistenceTest() {
        UUID newRoomId = service.addRoom(4,4);
        Room newRoom = roomRepository.findById(newRoomId).orElseThrow(() -> new EntityNotFoundException(newRoomId.toString()));

        assertEquals(newRoom.getId(), newRoomId);
    }

    @Test
    @Transactional
    public void newRoomWithObstaclePersistenceTest() {
        UUID newRoomId = service.addRoom(4,4);
        Barrier barrier = new Barrier("(1,2)-(1,4)");
        service.addBarrier(newRoomId, barrier);

        Room room = roomRepository.findById(newRoomId).orElseThrow(() -> new EntityNotFoundException(newRoomId.toString()));
        assertTrue(room.getBarriers().get(0).equals(barrier));
    }

    @Test
    @Transactional
    public void newRoomsWithConnectionTest() {
        String transportCategoryName = "Autobahn";
        UUID transportCategoryId = service.addTransportCategory(transportCategoryName);


        UUID roomId1 = service.addRoom(4,4);
        UUID roomId2 = service.addRoom(4,4);
        Point point1 = new Point(1,1);
        Point point2 = new Point(2,2);

        UUID connectionID = service.addConnection(transportCategoryId, roomId1, point1, roomId2, point2);

        TransportCategory transportCategory = transportCategoryRepository.findById(transportCategoryId).get();

        assertSame(transportCategory.getType(), transportCategoryName);
    }
}
