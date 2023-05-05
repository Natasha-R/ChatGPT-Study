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
import thkoeln.st.st2praktikum.exercise.miningmachine.application.MiningMachineService;
import thkoeln.st.st2praktikum.exercise.field.application.FieldService;
import thkoeln.st.st2praktikum.exercise.transporttechnology.application.TransportTechnologyService;
import thkoeln.st.st2praktikum.exercise.core.MovementTests;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;

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

    private static Attribute[] COMMAND_REST_ATTRIBUTES = new Attribute[]{new Attribute("orderType"),
            new Attribute("numberOfSteps"),
            new Attribute("gridId")};

    private MiningMachineService miningMachineService;
    private FieldService fieldService;
    private TransportTechnologyService transportTechnologyService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @Autowired
    public E1ControllerTests(WebApplicationContext appContext,
                             MockMvc mockMvc,
                             ObjectMapper objectMapper,
                             MiningMachineService miningMachineService,
                             FieldService fieldService,
                             TransportTechnologyService transportTechnologyService) {
        super(appContext);

        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.miningMachineService = miningMachineService;
        this.fieldService = fieldService;
        this.transportTechnologyService = transportTechnologyService;
    }

    @BeforeEach
    public void init() {
        createWorld(miningMachineService, fieldService, transportTechnologyService);
    }

    @Test
    @Transactional
    public void getMiningMachineTest() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(
                        get("/miningMachines" + "/" + miningMachine1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        objectValidator.validateResultActions(new MiningMachineMock(ROBOT_NAME_1), resultActions, ROBOT_REST_ATTRIBUTES, "");
    }

    @Test
    @Transactional
    public void getAllMiningMachinesTest() throws Exception {
        List<MiningMachineMock> miningMachines = new ArrayList<>();
        miningMachines.add(new MiningMachineMock(ROBOT_NAME_1));
        miningMachines.add(new MiningMachineMock(ROBOT_NAME_2));

        ResultActions resultActions = mockMvc
                .perform(
                        get("/miningMachines")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int i = 0; i < 2; i++) {
            objectValidator.validateResultActions(miningMachines.get(i), resultActions, ROBOT_REST_ATTRIBUTES,"[" + i + "]");
        }
    }

    @Test
    @Transactional
    public void postMiningMachineTest() throws Exception {
        MiningMachineMock miningMachine = new MiningMachineMock("Naty");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/miningMachines")
                                .content(objectMapper.writeValueAsString(miningMachine))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(miningMachine, resultActions, ROBOT_REST_ATTRIBUTES, "");

        assertEquals(3, getMiningMachineRepository().count());
    }

    @Test
    @Transactional
    public void deleteMiningMachinesTest() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(
                        delete("/miningMachines/" + miningMachine1))
                .andExpect(status().isNoContent());

        assertFalse(getMiningMachineRepository().findById(miningMachine1).isPresent());
    }

    @Test
    @Transactional
    public void patchMiningMachineTest() throws Exception {
        MiningMachineMock miningMachine = new MiningMachineMock("Naty");

        ResultActions resultActions = mockMvc
                .perform(
                        patch("/miningMachines/" + miningMachine1)
                                .content(objectMapper.writeValueAsString(miningMachine))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        objectValidator.validateResultActions(miningMachine, resultActions, ROBOT_REST_ATTRIBUTES, "");
    }

    @Test
    @Transactional
    public void postMiningMachineEntryOrderTest() throws Exception {
        Order entryOrder = Order.fromString("[en," + field1 + "]");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/miningMachines/" + miningMachine1 + "/orders")
                                .content(objectMapper.writeValueAsString(entryOrder))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(entryOrder, resultActions, COMMAND_REST_ATTRIBUTES, "");

        assertPosition(miningMachine1, field1, 0, 0);
    }

    @Test
    @Transactional
    public void postMiningMachineMoveOrderTest() throws Exception {
        miningMachineService.executeCommand(miningMachine1, Order.fromString("[en," + field1 + "]"));

        Order moveOrder =  Order.fromString("[no,3]");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/miningMachines/" + miningMachine1 + "/orders")
                                .content(objectMapper.writeValueAsString(moveOrder))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(moveOrder, resultActions, COMMAND_REST_ATTRIBUTES, "");

        assertPosition(miningMachine1, field1, 0, 2);
    }

    @Test
    @Transactional
    public void getAllMiningMachineOrdersTest() throws Exception {
        Order[] orders = new Order[]{
                Order.fromString("[en," + field1 + "]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[no,4]")
        };
        executeOrders(miningMachineService, miningMachine1, orders);

        ResultActions resultActions = mockMvc
                .perform(
                        get("/miningMachines/" + miningMachine1 + "/orders")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int i = 0; i < orders.length; i++) {
            objectValidator.validateResultActions(orders[i], resultActions, COMMAND_REST_ATTRIBUTES,"[" + i + "]");
        }
    }

    @Test
    @Transactional
    public void deleteAllMiningMachineOrdersTest() throws Exception {
        Order[] orders = new Order[]{
                Order.fromString("[en," + field1 + "]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[no,4]")
        };
        executeOrders(miningMachineService, miningMachine1, orders);

        ResultActions resultActions = mockMvc
                .perform(
                        delete("/miningMachines/" + miningMachine1 + "/orders"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void noRestLevel3Test() throws Exception {
        MockHttpServletResponse getResponse = mockMvc
                .perform(
                        get("/miningMachines" + "/" + miningMachine1)
                                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertTrue(
                getResponse.getStatus() == HttpStatus.NOT_FOUND.value()
                        || !getResponse.getContentAsString().contains("_links"));
    }
}
