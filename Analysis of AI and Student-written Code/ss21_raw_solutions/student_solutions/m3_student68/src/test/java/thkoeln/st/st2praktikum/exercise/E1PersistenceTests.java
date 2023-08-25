package thkoeln.st.st2praktikum.exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class E1PersistenceTests extends MovementTests {

//    private TidyUpRobotService service;
//    private RoomRepository roomRepository;
//
//    @Autowired
//    public E1PersistenceTests(WebApplicationContext appContext, TidyUpRobotService service, RoomRepository roomRepository) {
//        super(appContext);
//
//        this.service = service;
//        this.roomRepository = roomRepository;
//    }
//
//    @Test
//    @Transactional
//    public void newRoomPersistenceTest() {
//        UUID newRoomId = service.addRoom(4,4);
//        Room newRoom = roomRepository.findById(newRoomId).orElseThrow(() -> new EntityNotFoundException(newRoomId.toString()));
//
//        assertEquals(newRoom.getId(), newRoomId);
//    }

//    @Test
//    @Transactional
//    public void newRoomWithObstaclePersistenceTest() {
//        UUID newRoomId = service.addRoom(4,4);
//        Obstacle obstacle = new Obstacle("(1,2)-(1,4)");
//        service.addObstacle(newRoomId, obstacle);
//
//        Room room = roomRepository.findById(newRoomId).orElseThrow(() -> new EntityNotFoundException(newRoomId.toString()));
//        assertSame(room.getObstacles().get(0).getId(), obstacle.getId());
//    }
// }