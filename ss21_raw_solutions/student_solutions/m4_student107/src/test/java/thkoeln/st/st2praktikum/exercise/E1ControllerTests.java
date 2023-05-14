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
import thkoeln.st.st2praktikum.exercise.cleaningdevice.application.CleaningDeviceService;
import thkoeln.st.st2praktikum.exercise.space.application.SpaceService;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;
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

    private CleaningDeviceService cleaningDeviceService;
    private SpaceService spaceService;
    private TransportCategoryService transportCategoryService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @Autowired
    public E1ControllerTests(WebApplicationContext appContext,
                             MockMvc mockMvc,
                             ObjectMapper objectMapper,
                             CleaningDeviceService cleaningDeviceService,
                             SpaceService spaceService,
                             TransportCategoryService transportCategoryService) {
        super(appContext);

        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.cleaningDeviceService = cleaningDeviceService;
        this.spaceService = spaceService;
        this.transportCategoryService = transportCategoryService;
    }

    @BeforeEach
    public void init() {
        createWorld(cleaningDeviceService, spaceService, transportCategoryService);
    }

    @Test
    @Transactional
    public void getCleaningDeviceTest() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(
                        get("/cleaningDevices" + "/" + cleaningDevice1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        objectValidator.validateResultActions(new CleaningDeviceMock(ROBOT_NAME_1), resultActions, ROBOT_REST_ATTRIBUTES, "");
    }

    @Test
    @Transactional
    public void getAllCleaningDevicesTest() throws Exception {
        List<CleaningDeviceMock> cleaningDevices = new ArrayList<>();
        cleaningDevices.add(new CleaningDeviceMock(ROBOT_NAME_1));
        cleaningDevices.add(new CleaningDeviceMock(ROBOT_NAME_2));

        ResultActions resultActions = mockMvc
                .perform(
                        get("/cleaningDevices")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int i = 0; i < 2; i++) {
            objectValidator.validateResultActions(cleaningDevices.get(i), resultActions, ROBOT_REST_ATTRIBUTES,"[" + i + "]");
        }
    }

    @Test
    @Transactional
    public void postCleaningDeviceTest() throws Exception {
        CleaningDeviceMock cleaningDevice = new CleaningDeviceMock("Naty");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/cleaningDevices")
                                .content(objectMapper.writeValueAsString(cleaningDevice))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(cleaningDevice, resultActions, ROBOT_REST_ATTRIBUTES, "");

        assertEquals(3, getCleaningDeviceRepository().count());
    }

    @Test
    @Transactional
    public void deleteCleaningDevicesTest() throws Exception {
        ResultActions resultActions = mockMvc
                .perform(
                        delete("/cleaningDevices/" + cleaningDevice1))
                .andExpect(status().isNoContent());

        assertFalse(getCleaningDeviceRepository().findById(cleaningDevice1).isPresent());
    }

    @Test
    @Transactional
    public void patchCleaningDeviceTest() throws Exception {
        CleaningDeviceMock cleaningDevice = new CleaningDeviceMock("Naty");

        ResultActions resultActions = mockMvc
                .perform(
                        patch("/cleaningDevices/" + cleaningDevice1)
                                .content(objectMapper.writeValueAsString(cleaningDevice))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        objectValidator.validateResultActions(cleaningDevice, resultActions, ROBOT_REST_ATTRIBUTES, "");
    }

    @Test
    @Transactional
    public void postCleaningDeviceEntryOrderTest() throws Exception {
        Order entryOrder = Order.fromString("[en," + space1 + "]");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/cleaningDevices/" + cleaningDevice1 + "/orders")
                                .content(objectMapper.writeValueAsString(entryOrder))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(entryOrder, resultActions, COMMAND_REST_ATTRIBUTES, "");

        assertPosition(cleaningDevice1, space1, 0, 0);
    }

    @Test
    @Transactional
    public void postCleaningDeviceMoveOrderTest() throws Exception {
        cleaningDeviceService.executeCommand(cleaningDevice1, Order.fromString("[en," + space1 + "]"));

        Order moveOrder =  Order.fromString("[no,3]");

        ResultActions resultActions = mockMvc
                .perform(
                        post("/cleaningDevices/" + cleaningDevice1 + "/orders")
                                .content(objectMapper.writeValueAsString(moveOrder))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        objectValidator.validateResultActions(moveOrder, resultActions, COMMAND_REST_ATTRIBUTES, "");

        assertPosition(cleaningDevice1, space1, 0, 2);
    }

    @Test
    @Transactional
    public void getAllCleaningDeviceOrdersTest() throws Exception {
        Order[] orders = new Order[]{
                Order.fromString("[en," + space1 + "]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[no,4]")
        };
        executeOrders(cleaningDeviceService, cleaningDevice1, orders);

        ResultActions resultActions = mockMvc
                .perform(
                        get("/cleaningDevices/" + cleaningDevice1 + "/orders")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        for (int i = 0; i < orders.length; i++) {
            objectValidator.validateResultActions(orders[i], resultActions, COMMAND_REST_ATTRIBUTES,"[" + i + "]");
        }
    }

    @Test
    @Transactional
    public void deleteAllCleaningDeviceOrdersTest() throws Exception {
        Order[] orders = new Order[]{
                Order.fromString("[en," + space1 + "]"),
                Order.fromString("[ea,1]"),
                Order.fromString("[no,4]")
        };
        executeOrders(cleaningDeviceService, cleaningDevice1, orders);

        ResultActions resultActions = mockMvc
                .perform(
                        delete("/cleaningDevices/" + cleaningDevice1 + "/orders"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void noRestLevel3Test() throws Exception {
        MockHttpServletResponse getResponse = mockMvc
                .perform(
                        get("/cleaningDevices" + "/" + cleaningDevice1)
                                .contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertTrue(
                getResponse.getStatus() == HttpStatus.NOT_FOUND.value()
                        || !getResponse.getContentAsString().contains("_links"));
    }
}
