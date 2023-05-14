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
import thkoeln.st.st2praktikum.exercise.maintenancedroid.application.MaintenanceDroidService;
import thkoeln.st.st2praktikum.exercise.spaceshipdeck.application.SpaceShipDeckService;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;

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

    private static Attribute[] COMMAND_REST_ATTRIBUTES = new Attribute[]{new Attribute("commandType"),
            new Attribute("numberOfSteps"),
            new Attribute("gridId")};

    private MaintenanceDroidService maintenanceDroidService;
    private SpaceShipDeckService spaceShipDeckService;
    private TransportTechnologyService transportTechnologyService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @Autowired
    public E1ControllerTests(WebApplicationContext appContext,
                             MockMvc mockMvc,
                             ObjectMapper objectMapper,
                             MaintenanceDroidService maintenanceDroidService,
                             SpaceShipDeckService spaceShipDeckService,
                             TransportTechnologyService transportTechnologyService) {
        super(appContext);

        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.maintenanceDroidService = maintenanceDroidService;
        this.spaceShipDeckService = spaceShipDeckService;
        this.transportTechnologyService = transportTechnologyService;
    }

    @BeforeEach
    public void init() {
        createWorld(maintenanceDroidService, spaceShipDeckService, transportTechnologyService);
    }

    @Test
    @Transactional
    public void getMaintenanceDroidTest() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(
                        get("/maintenanceDroids" + "/" + maintenanceDroid1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        objectValidator.validateResultActions(new MaintenanceDroidMock(ROBOT_NAME_1), resultActions, ROBOT_REST_ATTRIBUTES, "");
    }

    @Test
    @Transactional
    public void getAllMaintenanceDroidsTest() throws Exception {
        List<MaintenanceDroidMock> maintenanceDroids = new ArrayList<>();
        maintenanceDroids.add(new MaintenanceDroidMock(ROBOT_NAME_1));
        maintenanceDroids.add(new MaintenanceDroidMock(ROBOT_NAME_2));

        ResultActions resultActions = mockMvc
                .perform(
                        get("/maintenanceDroids")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int i = 0; i < 2; i++) {
            objectValidator.validateResultActions(maintenanceDroids.get(i), resultActions, ROBOT_REST_ATTRIBUTES,"[" + i + "]");
        }
    }

    @Test
    @Transactional
    public void postMaintenanceDroidTest() throws Exception {
        MaintenanceDroidMock maintenanceDroid = new MaintenanceDroidMock("Naty");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/maintenanceDroids")
                                .content(objectMapper.writeValueAsString(maintenanceDroid))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(maintenanceDroid, resultActions, ROBOT_REST_ATTRIBUTES, "");

        assertEquals(3, getMaintenanceDroidRepository().count());
    }

    @Test
    @Transactional
    public void deleteMaintenanceDroidsTest() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(
                        delete("/maintenanceDroids/" + maintenanceDroid1))
                .andExpect(status().isNoContent());

        assertFalse(getMaintenanceDroidRepository().findById(maintenanceDroid1).isPresent());
    }

    @Test
    @Transactional
    public void patchMaintenanceDroidTest() throws Exception {
        MaintenanceDroidMock maintenanceDroid = new MaintenanceDroidMock("Naty");

        ResultActions resultActions = mockMvc
                .perform(
                        patch("/maintenanceDroids/" + maintenanceDroid1)
                                .content(objectMapper.writeValueAsString(maintenanceDroid))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        objectValidator.validateResultActions(maintenanceDroid, resultActions, ROBOT_REST_ATTRIBUTES, "");
    }

    @Test
    @Transactional
    public void postMaintenanceDroidEntryCommandTest() throws Exception {
        Command entryCommand = Command.fromString("[en," + spaceShipDeck1 + "]");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/maintenanceDroids/" + maintenanceDroid1 + "/commands")
                                .content(objectMapper.writeValueAsString(entryCommand))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(entryCommand, resultActions, COMMAND_REST_ATTRIBUTES, "");

        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 0);
    }

    @Test
    @Transactional
    public void postMaintenanceDroidMoveCommandTest() throws Exception {
        maintenanceDroidService.executeCommand(maintenanceDroid1, Command.fromString("[en," + spaceShipDeck1 + "]"));

        Command moveCommand =  Command.fromString("[no,3]");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/maintenanceDroids/" + maintenanceDroid1 + "/commands")
                                .content(objectMapper.writeValueAsString(moveCommand))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(moveCommand, resultActions, COMMAND_REST_ATTRIBUTES, "");

        assertPosition(maintenanceDroid1, spaceShipDeck1, 0, 2);
    }

    @Test
    @Transactional
    public void getAllMaintenanceDroidCommandsTest() throws Exception {
        Command[] commands = new Command[]{
                Command.fromString("[en," + spaceShipDeck1 + "]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[no,4]")
        };
        executeCommands(maintenanceDroidService, maintenanceDroid1, commands);

        ResultActions resultActions = mockMvc
                .perform(
                        get("/maintenanceDroids/" + maintenanceDroid1 + "/commands")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int i = 0; i < commands.length; i++) {
            objectValidator.validateResultActions(commands[i], resultActions, COMMAND_REST_ATTRIBUTES,"[" + i + "]");
        }
    }

    @Test
    @Transactional
    public void deleteAllMaintenanceDroidCommandsTest() throws Exception {
        Command[] commands = new Command[]{
                Command.fromString("[en," + spaceShipDeck1 + "]"),
                Command.fromString("[ea,1]"),
                Command.fromString("[no,4]")
        };
        executeCommands(maintenanceDroidService, maintenanceDroid1, commands);

        ResultActions resultActions = mockMvc
                .perform(
                        delete("/maintenanceDroids/" + maintenanceDroid1 + "/commands"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void noRestLevel3Test() throws Exception {
        MockHttpServletResponse getResponse = mockMvc
                .perform(
                        get("/maintenanceDroids" + "/" + maintenanceDroid1)
                                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertTrue(
                getResponse.getStatus() == HttpStatus.NOT_FOUND.value()
                        || !getResponse.getContentAsString().contains("_links"));
    }
}
