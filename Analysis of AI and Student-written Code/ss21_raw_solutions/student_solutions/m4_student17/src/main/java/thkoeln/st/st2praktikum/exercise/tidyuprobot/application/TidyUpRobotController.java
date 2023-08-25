package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.web.bind.annotation.*;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.exception.RobotNotFound;
import thkoeln.st.st2praktikum.exercise.exception.RoomNotFound;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

//@RepositoryRestController // WRONG
@RestController
public class TidyUpRobotController
{
    private final TidyUpRobotRepository tidyUpRobotRepository;

    private final static Map<UUID, List<Order>> orderHistroy = new HashMap<>();

    public void orderHistoryAdd(String uudiText, Order order)
    {
        UUID uuid = UUID.fromString(uudiText);

        orderHistoryAdd(uuid, order);
    }

    public void orderHistoryAdd(UUID uudi, Order order)
    {
        List<Order> orderList = getOrderHistory(uudi);

        if(orderList == null)
        {
            orderList = new ArrayList<>();
            orderList.add(order);
            orderHistroy.put(uudi, orderList);
        }
        else
        {
            orderList.add(order);
            orderHistroy.replace(uudi, orderList);
        }
    }

    public List<Order> getOrderHistory(UUID uuid)
    {
       return orderHistroy.get(uuid);
    }

    public void orderHistoryClear(UUID uuid)
    {
        orderHistroy.remove(uuid);
    }

    public TidyUpRobotController(TidyUpRobotRepository repository)
    {
        tidyUpRobotRepository = repository;
    }

    public Vector2D getRobotPosition(final UUID uuid) throws RobotNotFound
    {
        final Optional<TidyUpRobot> robot = tidyUpRobotRepository.findById(uuid);

        return robot.get().getCurrentPosition();
    }

    public TidyUpRobot saveRobot(String robotName)
    {
        TidyUpRobot robot = new TidyUpRobot(robotName);

        saveRobot(robot);

        return robot;
    }
    public TidyUpRobot saveRobot(TidyUpRobot robot)
    {
        tidyUpRobotRepository.save(robot);

        return robot;
    }

    public void deleteRobot(String uuidText)
    {
        TidyUpRobot tidyUpRobot = getRobot(uuidText);

        deleteRobot(tidyUpRobot);
    }

    public void deleteRobot(UUID uuid)
    {
        TidyUpRobot tidyUpRobot = getRobot(uuid);

        deleteRobot(tidyUpRobot);
    }

    public void deleteRobot(TidyUpRobot tidyUpRobot)
    {
        tidyUpRobotRepository.delete(tidyUpRobot);
    }

    public TidyUpRobot getRobot(String roboterUUID)
    {
        UUID uuid = UUID.fromString(roboterUUID);
        Optional<TidyUpRobot> robot = tidyUpRobotRepository.findById(uuid);

        return robot.get();
    }

    public TidyUpRobot getRobot(UUID uuid)
    {
        Optional<TidyUpRobot> robot = tidyUpRobotRepository.findById(uuid);

        return robot.get();
    }





    @GetMapping("/tidyUpRobots")
    @ResponseBody
    public Iterable<TidyUpRobot> getAllRobots(HttpServletResponse response)
    {
        Iterable<TidyUpRobot> robots = tidyUpRobotRepository.findAll();

        response.setStatus(200);

        return robots;
    }

    @PostMapping("/tidyUpRobots")
    @ResponseBody
    public TidyUpRobot addNewRobot(@RequestBody TidyUpRobot tidyUpRobotChange, HttpServletResponse response)
    {
        saveRobot(tidyUpRobotChange.getName());

        response.setStatus(201);

        return tidyUpRobotChange;
    }




    @GetMapping("/tidyUpRobots/{tidyUpRobotsid}")
    @ResponseBody
    public TidyUpRobot getRobot(@PathVariable final String tidyUpRobotsid, HttpServletResponse response) throws RobotNotFound
    {
        return getRobot(tidyUpRobotsid);
    }

    @DeleteMapping("/tidyUpRobots/{tidyUpRobotsid}")
    @ResponseBody
    public void deleteRobot(@PathVariable final String tidyUpRobotsid, HttpServletResponse response)
    {
        deleteRobot(tidyUpRobotsid);

        response.setStatus(204);
    }

    @PatchMapping("/tidyUpRobots/{tidyUpRobotsid}")
    @ResponseBody
    public TidyUpRobot changeRobot(@RequestBody TidyUpRobot tidyUpRobotChange, @PathVariable final String tidyUpRobotsid, HttpServletResponse response )
    {
        TidyUpRobot tidyUpRobot = getRobot(tidyUpRobotsid);

        tidyUpRobot.setName(tidyUpRobotChange.getName());

        saveRobot(tidyUpRobot);

        response.setStatus(200);

        return tidyUpRobot;
    }






    @GetMapping("/tidyUpRobots/{tidyUpRobotsid}/orders")
    @ResponseBody
    public Iterable<Order> getOrders(@PathVariable final String tidyUpRobotsid, HttpServletResponse response)
    {
        final TidyUpRobot robot = getRobot(tidyUpRobotsid);
        final List<Order> orders = getOrderHistory(robot.getUUID());
        final Iterable<Order> ordersw = orders;

        response.setStatus(200);

        if(orders == null)
        {
            return null;
        }
        if(orders.size() <= 0)
        {
            return null;
        }

        return ordersw;
    }

    @DeleteMapping("/tidyUpRobots/{tidyUpRobotsid}/orders")
    @ResponseBody
    public void deleteOrders(@PathVariable final String tidyUpRobotsid, HttpServletResponse response)
    {
        TidyUpRobot robot = getRobot(tidyUpRobotsid);

        orderHistoryClear(robot.getUUID());

        saveRobot(robot);

        response.setStatus(204);
    }

    @PostMapping("/tidyUpRobots/{tidyUpRobotsid}/orders")
    public Order postOrder(@RequestBody Order newOrder, @PathVariable final String tidyUpRobotsid, HttpServletResponse response)
    {
        TidyUpRobot robot = getRobot(tidyUpRobotsid);

        orderHistoryAdd(tidyUpRobotsid, newOrder);

        try
        {
            robot.move(newOrder);
            saveRobot(robot);
        }
        catch (RoomNotFound roomNotFound)
        {
            roomNotFound.printStackTrace();
        }

        response.setStatus(201);

        return newOrder;
    }
}
