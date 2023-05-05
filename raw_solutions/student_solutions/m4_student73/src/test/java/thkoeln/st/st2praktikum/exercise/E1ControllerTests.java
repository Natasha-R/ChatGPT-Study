package thkoeln.st.st2praktikum.exercise;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import thkoeln.st.springtestlib.core.Attribute;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.application.TidyUpRobotService;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.transportsystem.application.TransportSystemService;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class E1ControllerTests extends MovementTests {

    private static Attribute[] ROBOT_REST_ATTRIBUTES = new Attribute[]{new Attribute("name")};

    private static Attribute[] COMMAND_REST_ATTRIBUTES = new Attribute[]{new Attribute("taskType"),
            new Attribute("numberOfSteps"),
            new Attribute("gridId")};

    private TidyUpRobotService tidyUpRobotService;
    private RoomService roomService;
    private TransportSystemService transportSystemService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @Autowired
    public E1ControllerTests(WebApplicationContext appContext,
                             MockMvc mockMvc,
                             ObjectMapper objectMapper,
                             TidyUpRobotService tidyUpRobotService,
                             RoomService roomService,
                             TransportSystemService transportSystemService) {
        super(appContext);

        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.tidyUpRobotService = tidyUpRobotService;
        this.roomService = roomService;
        this.transportSystemService = transportSystemService;
    }

    @BeforeEach
    public void init() {
        createWorld(tidyUpRobotService, roomService, transportSystemService);
    }

    @Test
    @Transactional
    public void getTidyUpRobotTest() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(
                        get("/tidyUpRobots" + "/" + tidyUpRobot1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        objectValidator.validateResultActions(new TidyUpRobotMock(ROBOT_NAME_1), resultActions, ROBOT_REST_ATTRIBUTES, "");
    }

    @Test
    @Transactional
    public void getAllTidyUpRobotsTest() throws Exception {
        List<TidyUpRobotMock> tidyUpRobots = new ArrayList<>();
        tidyUpRobots.add(new TidyUpRobotMock(ROBOT_NAME_1));
        tidyUpRobots.add(new TidyUpRobotMock(ROBOT_NAME_2));

        ResultActions resultActions = mockMvc
                .perform(
                        get("/tidyUpRobots")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int i = 0; i < 2; i++) {
            objectValidator.validateResultActions(tidyUpRobots.get(i), resultActions, ROBOT_REST_ATTRIBUTES,"[" + i + "]");
        }
    }

    @Test
    @Transactional
    public void postTidyUpRobotTest() throws Exception {
        TidyUpRobotMock tidyUpRobot = new TidyUpRobotMock("Naty");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/tidyUpRobots")
                                .content(objectMapper.writeValueAsString(tidyUpRobot))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(tidyUpRobot, resultActions, ROBOT_REST_ATTRIBUTES, "");

        assertEquals(3, getTidyUpRobotRepository().count());
    }

    @Test
    @Transactional
    public void deleteTidyUpRobotsTest() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(
                        delete("/tidyUpRobots/" + tidyUpRobot1))
                .andExpect(status().isNoContent());

        assertFalse(getTidyUpRobotRepository().findById(tidyUpRobot1).isPresent());
    }

    @Test
    @Transactional
    public void patchTidyUpRobotTest() throws Exception {
        TidyUpRobotMock tidyUpRobot = new TidyUpRobotMock("Naty");

        ResultActions resultActions = mockMvc
                .perform(
                        patch("/tidyUpRobots/" + tidyUpRobot1)
                                .content(objectMapper.writeValueAsString(tidyUpRobot))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        objectValidator.validateResultActions(tidyUpRobot, resultActions, ROBOT_REST_ATTRIBUTES, "");
    }

    @Test
    @Transactional
    public void postTidyUpRobotEntryTaskTest() throws Exception {
        Task entryTask = Task.fromString("[en," + room1 + "]");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/tidyUpRobots/" + tidyUpRobot1 + "/tasks")
                                .content(objectMapper.writeValueAsString(entryTask))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(entryTask, resultActions, COMMAND_REST_ATTRIBUTES, "");

        assertPosition(tidyUpRobot1, room1, 0, 0);
    }

    @Test
    @Transactional
    public void postTidyUpRobotMoveTaskTest() throws Exception {
        tidyUpRobotService.executeCommand(tidyUpRobot1, Task.fromString("[en," + room1 + "]"));

        Task moveTask =  Task.fromString("[no,3]");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/tidyUpRobots/" + tidyUpRobot1 + "/tasks")
                                .content(objectMapper.writeValueAsString(moveTask))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(moveTask, resultActions, COMMAND_REST_ATTRIBUTES, "");

        assertPosition(tidyUpRobot1, room1, 0, 2);
    }

    @Test
    @Transactional
    public void getAllTidyUpRobotTasksTest() throws Exception {
        Task[] tasks = new Task[]{
                Task.fromString("[en," + room1 + "]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[no,4]")
        };
        executeTasks(tidyUpRobotService, tidyUpRobot1, tasks);

        ResultActions resultActions = mockMvc
                .perform(
                        get("/tidyUpRobots/" + tidyUpRobot1 + "/tasks")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int i = 0; i < tasks.length; i++) {
            objectValidator.validateResultActions(tasks[i], resultActions, COMMAND_REST_ATTRIBUTES,"[" + i + "]");
        }
    }

    @Test
    @Transactional
    public void deleteAllTidyUpRobotTasksTest() throws Exception {
        Task[] tasks = new Task[]{
                Task.fromString("[en," + room1 + "]"),
                Task.fromString("[ea,1]"),
                Task.fromString("[no,4]")
        };
        executeTasks(tidyUpRobotService, tidyUpRobot1, tasks);

        ResultActions resultActions = mockMvc
                .perform(
                        delete("/tidyUpRobots/" + tidyUpRobot1 + "/tasks"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void noRestLevel3Test() throws Exception {
        MockHttpServletResponse getResponse = mockMvc
                .perform(
                        get("/tidyUpRobots" + "/" + tidyUpRobot1)
                                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertTrue(
                getResponse.getStatus() == HttpStatus.NOT_FOUND.value()
                        || !getResponse.getContentAsString().contains("_links"));
    }
}
