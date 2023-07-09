package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.domainprimitives.*;
import thkoeln.st.st2praktikum.exercise.room.BarrierInRoom;
import thkoeln.st.st2praktikum.exercise.room.BarrierRepository;
import thkoeln.st.st2praktikum.exercise.room.Room;
import thkoeln.st.st2praktikum.exercise.room.RoomRepository;
import thkoeln.st.st2praktikum.exercise.room.application.RoomService;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.TransportCategory;
import thkoeln.st.st2praktikum.exercise.transportcategory.application.TransportCategoryService;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class TidyUpRobotService {

    private RoomService roomService;

    private TransportCategoryService transportCategoryService;

    private Integer durch = 0;

    @Getter
    public List<TidyUpRobot> allTidyUpRobots = new ArrayList<>();
    @Getter
    public List<CurrentCoordinate>allCurrentRobotCoordinates = new ArrayList<>();
    @Getter
    public List<Room> allRooms = new ArrayList<>();
    @Getter
    public List<BarrierInRoom> allRoomBarriers = new ArrayList<>();
    @Getter
    public List<Connection> allConnections = new ArrayList<>();
    @Getter
    public List<TransportCategory> allTransportCategories = new ArrayList<>();
    @Getter
    public List<Order> allOrders = new ArrayList<>();
    @Getter
    public List<RobotOrders> robotOrders = new ArrayList<>();



    @Getter
    public TidyUpRobotRepository tidyUpRobotRepository;
    @Getter
    public RoomRepository roomRepository;
    @Getter
    public BarrierRepository barrierRepository;
    @Getter
    public ConnectionRepository connectionRepository;
    @Getter
    public OrderRepository orderRepository;






    public TidyUpRobotService(){}

    @Autowired
    public TidyUpRobotService(
            TransportCategoryService transportCategoryService,
            RoomService roomService,
            TidyUpRobotRepository tidyUpRobotRepository,
            RoomRepository roomRepository,
            BarrierRepository barrierRepository,
            ConnectionRepository connectionRepository,
            OrderRepository orderRepository){

        this.transportCategoryService = transportCategoryService;
        this.roomService = roomService;
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
        this.barrierRepository = barrierRepository;
        this.connectionRepository = connectionRepository;
        this.orderRepository = orderRepository;

    }








    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newTidyUpRobot = new TidyUpRobot(name);
        allTidyUpRobots.add(newTidyUpRobot);
        tidyUpRobotRepository.save(newTidyUpRobot);


        return newTidyUpRobot.getTidyUpRobotId();
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */


    public Boolean executeCommand(UUID tidyUpRobotId, Order order) {

        allOrders.add(order);

        robotOrders.add(new RobotOrders(tidyUpRobotId,order));

        persistLists();
        persistRepositorys();



        System.out.println(allRooms.size()+ "all Rooms Size");
        System.out.println(transportCategoryService.transportCategories.size()+ "fsa");
        TidyUpRobot robotInAction = getRobotById(tidyUpRobotId);
        Coordinate RobotsCordinate = robotInAction.getCoordinate();
        System.out.println("Koordinate des Roboters"+ RobotsCordinate);

        if (order.isValidOrder(order)){
            if (order.getOrderString().contains("no")||order.getOrderString().contains("so")||order.getOrderString().contains("ea")||order.getOrderString().contains("we")){

                checkIfBarrierContact(tidyUpRobotId,RobotsCordinate,order.getOrderString(),getTidyUpRobotRoomId(tidyUpRobotId));
            }
            else if (order.getOrderString().contains("tr")){
                if (!transportRobot(tidyUpRobotId,RobotsCordinate)) return false;
            }
            else if (order.getOrderString().contains("en")){

                if (!setInitialRoom(UUID.fromString(order.getOrderString().substring(4, order.getOrderString().length()-1)),tidyUpRobotId))return false;
            }
            else throw new UnsupportedOperationException();
        }
        return true;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        for (int i =0; i<allTidyUpRobots.size();i++){
            if (allTidyUpRobots.get(i).getTidyUpRobotId().equals(tidyUpRobotId) ){
                return allTidyUpRobots.get(i).getRoomId();
            }
        }
        return null;
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId){
        throw new UnsupportedOperationException();
    }



    public Coordinate checkIfBarrierContact(UUID robotsId, Coordinate robotsCoordinate, String commandString, UUID roomId){
        Integer moveAmmount = Integer.parseInt(commandString.substring(4,5));

        //Norden
        if ( commandString.contains("no")){
            return moveNorth(robotsId,robotsCoordinate,commandString,roomId,moveAmmount);
        }
        //süden
        else if (commandString.contains("so")) {
            return moveSouth(robotsId,robotsCoordinate,commandString,roomId,moveAmmount);
        }
        //Osten
        else if (commandString.contains("ea")){

            return moveEast(robotsId,robotsCoordinate,commandString,roomId,moveAmmount);

        }
        //Westen
        else if (commandString.contains("we")){

            return moveWest(robotsId,robotsCoordinate,commandString,roomId,moveAmmount);
        }
        return null;
    }



    public Coordinate moveNorth(UUID robotsId,Coordinate currentCoordinate, String commandString, UUID roomId, Integer moveAmmount){
        Integer xPointer = currentCoordinate.getX();
        Integer yPointer = currentCoordinate.getY();
        for (int i = 0; i< allRoomBarriers.size();i++){


            if (Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(3,4))>currentCoordinate.getY() && Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(3,4))<= (currentCoordinate.getY()+moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(1,2)) == currentCoordinate.getX() && allRoomBarriers.get(i).getRoomIdFromBarrier().equals(roomId) ){
                currentCoordinate.setY(Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(3,4))-1);
                tidyUpRobotRepository.save(getRobotById(robotsId));
                return currentCoordinate;
            }
        }
        currentCoordinate.setY(currentCoordinate.getY() + moveAmmount);
        if (currentCoordinate.getY()>=getRoom(roomId).checkRoomBoundariesY(allRooms)){
            currentCoordinate.setY(getRoom(roomId).checkRoomBoundariesY(allRooms));
        }
        if (!checkIfNextCoordinateIsBlocked(currentCoordinate.getX(),currentCoordinate.getY(),roomId,robotsId)){
            currentCoordinate.setX(xPointer);
            currentCoordinate.setY(yPointer);
        }
        tidyUpRobotRepository.save(getRobotById(robotsId));
        return currentCoordinate;
    }



    public Coordinate moveSouth(UUID robotsId,Coordinate currentCoordinate, String commandString, UUID roomId, Integer moveAmmount){
        Integer xPointer = currentCoordinate.getX();
        Integer yPointer = currentCoordinate.getY();
        for (int i = 0; i <allRoomBarriers.size(); i++) {

            if (Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(3,4)) < currentCoordinate.getY() && Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(3,4)) >= (currentCoordinate.getY() - moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(1,2)) == currentCoordinate.getX() && allRoomBarriers.get(i).getRoomIdFromBarrier().equals(roomId)) {
                currentCoordinate.setY(Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(3,4)) + 1);
                tidyUpRobotRepository.save(getRobotById(robotsId));
                return currentCoordinate;
            }
        }

        currentCoordinate.setY(currentCoordinate.getY() - moveAmmount);
        if (currentCoordinate.getY()<0)currentCoordinate.setY(0);
        if (!checkIfNextCoordinateIsBlocked(currentCoordinate.getX(),currentCoordinate.getY(),roomId,robotsId)){
            currentCoordinate.setX(xPointer);
            currentCoordinate.setY(yPointer);
        }
        tidyUpRobotRepository.save(getRobotById(robotsId));
        return currentCoordinate;
    }



    public Coordinate moveEast(UUID robotsId,Coordinate currentCoordinate, String commandString, UUID roomId, Integer moveAmmount){


        Integer xPointer = currentCoordinate.getX();
        Integer yPointer = currentCoordinate.getY();

        for (int i = 0; i<allRoomBarriers.size(); i++){

            if (Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(1,2))> currentCoordinate.getX() && Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(1,2))<= (currentCoordinate.getX()+moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(3,4)) == currentCoordinate.getY() && allRoomBarriers.get(i).getRoomIdFromBarrier().equals(roomId) ) {
                currentCoordinate.setX(Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(1,2))-1);
                tidyUpRobotRepository.save(getRobotById(robotsId));
                return currentCoordinate;
            }
        }

        currentCoordinate.setX(currentCoordinate.getX() + moveAmmount);
        System.out.println(allRooms.size());
        if (currentCoordinate.getX()>=getRoom(roomId).checkRoomBoundariesX(allRooms)){
            currentCoordinate.setX(getRoom(roomId).checkRoomBoundariesX(allRooms));
        }
        if (!checkIfNextCoordinateIsBlocked(currentCoordinate.getX(),currentCoordinate.getY(),roomId,robotsId)){
            currentCoordinate.setX(xPointer);
            currentCoordinate.setY(yPointer);
        }
        tidyUpRobotRepository.save(getRobotById(robotsId));
        return currentCoordinate;
    }

    public Coordinate moveWest(UUID robotsId,Coordinate currentCoordinate, String commandString, UUID roomId, Integer moveAmmount){
        Integer xPointer = currentCoordinate.getX();
        Integer yPointer = currentCoordinate.getY();


        for (int i = 0; i<allRoomBarriers.size(); i++){

            if (Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(1,2))< currentCoordinate.getX() && Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(1,2))>= (currentCoordinate.getX()-moveAmmount) && Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(3,4)) == currentCoordinate.getY() && allRoomBarriers.get(i).getRoomIdFromBarrier().equals(roomId) ) {
                currentCoordinate.setX(Integer.parseInt(allRoomBarriers.get(i).getSolvedBarrierString().substring(1,2)));
                tidyUpRobotRepository.save(getRobotById(robotsId));
                return currentCoordinate;
            }
        }
        currentCoordinate.setX(currentCoordinate.getX() - moveAmmount);
        if (currentCoordinate.getX()<0)currentCoordinate.setX(0);

        if (!checkIfNextCoordinateIsBlocked(currentCoordinate.getX(),currentCoordinate.getY(),roomId,robotsId)){
            currentCoordinate.setX(xPointer);
            currentCoordinate.setY(yPointer);
        }
        tidyUpRobotRepository.save(getRobotById(robotsId));
        return currentCoordinate;
    }
    public Boolean transportRobot(UUID tidyUpRobotId, Coordinate robotsCoordinate) {



        for (int o=0;o<allConnections.size(); o++){

            if (Integer.parseInt(allConnections.get(o).getSourceCoordinatAsString().substring(1,2)) == robotsCoordinate.getX() && Integer.parseInt(allConnections.get(o).getSourceCoordinatAsString().substring(3,4))==robotsCoordinate.getY()){

                System.out.println(getRobotById(tidyUpRobotId).getRoomId()+ "Start raum");
                System.out.println(getRobotById(tidyUpRobotId).getCoordinate().getX() + "Das ist X vorher");
                System.out.println(getRobotById(tidyUpRobotId).getCoordinate().getY() + "Das ist Y vorher");


                for (int i = 0; i < allTidyUpRobots.size(); i++) {
                    if (allTidyUpRobots.get(i).getTidyUpRobotId().equals(tidyUpRobotId)) {
                        allTidyUpRobots.get(i).setRoomId(allConnections.get(o).getDestinationRoomId());


                        allTidyUpRobots.get(i).setCoordinate(new Coordinate(Integer.parseInt(allConnections.get(o).getDestinationCoordinatAsString().substring(1,2)),Integer.parseInt(allConnections.get(o).getDestinationCoordinatAsString().substring(3,4))));





                        tidyUpRobotRepository.save(getRobotById(tidyUpRobotId));


                        System.out.println(getRobotById(tidyUpRobotId).getCoordinate().getX() + "Das ist X DANACH");
                        System.out.println(getRobotById(tidyUpRobotId).getCoordinate().getY() + "Das ist Y DANACH");

                        System.out.println("korrdinate sollte ak werdeb");
                    }
                }


                if (!checkIfNextCoordinateIsBlocked(robotsCoordinate.getX(),robotsCoordinate.getY(),getTidyUpRobotRoomId(tidyUpRobotId),tidyUpRobotId)){
                    throw  new IllegalArgumentException("Transfer to other room not possible because destination is blocked");
                }
                else return true;

            }
        }
        return false;
    }
    public Boolean setInitialRoom(UUID newRoomUuid, UUID tidyUpRobotId){
        TidyUpRobot aktuellerRoboter = getRobotById(tidyUpRobotId);


        for (int i = 0; i < allTidyUpRobots.size(); i++) {

            if (allTidyUpRobots.get(i).getRoomId() != null){
                if (allTidyUpRobots.get(i).getTidyUpRobotId() !=tidyUpRobotId && allTidyUpRobots.get(i).getRoomId().equals(newRoomUuid) && allTidyUpRobots.get(i).getCoordinate().getX()==0 &&  allTidyUpRobots.get(i).getCoordinate().getY()==0  ){

                    tidyUpRobotRepository.save(getRobotById(tidyUpRobotId));
                    return false;
                }

            }


            if (allTidyUpRobots.get(i).getTidyUpRobotId().equals(tidyUpRobotId)) {

                allTidyUpRobots.get(i).setRoomId(newRoomUuid);
                allTidyUpRobots.get(i).setCoordinate(new Coordinate(0,0));
            }







        }
        tidyUpRobotRepository.save(getRobotById(tidyUpRobotId));


        return true;
    }

    public TidyUpRobot getRobotById(UUID robotsID){
        for (int i =0; i<allTidyUpRobots.size(); i++){

            if (allTidyUpRobots.get(i).getTidyUpRobotId().equals(robotsID)){
                return allTidyUpRobots.get(i);
            }
        }
        return null;
    }


    public Boolean checkIfNextCoordinateIsBlocked(Integer x, Integer y , UUID roomID, UUID moveRobot){
        for (int i=0; i<allCurrentRobotCoordinates.size(); i++){
            if (allCurrentRobotCoordinates.get(i).getX().equals(x)&& allCurrentRobotCoordinates.get(i).getY().equals(y)){
                if (getTidyUpRobotRoomId(allCurrentRobotCoordinates.get(i).getRobotId()).equals(roomID) && allCurrentRobotCoordinates.get(i).getRobotId() != moveRobot){
                    return false;
                    //throw  new IllegalArgumentException("Not possible");
                }
            }
        }
        return true;

    }

    public Room getRoom(UUID roomUUID){
        for (int i=0; i<allRooms.size();i++){
            if (allRooms.get(i).getRoomId().equals(roomUUID)){
                return allRooms.get(i);
            }
        }
        return null;
    }

    public void persistLists(){

        if (allRooms.isEmpty()) {


            //Räume
            for (Room room : roomService.rooms) {
                allRooms.add(room);
            }

            //Barrieren
            for (BarrierInRoom barrierInRoom : roomService.barriersInRoom) {
                allRoomBarriers.add(barrierInRoom);
            }

            //Transport Categories
            for (TransportCategory transportCategory : transportCategoryService.transportCategories) {
                allTransportCategories.add(transportCategory);
            }

            //Connections
            for (Connection connection : transportCategoryService.connections) {
                allConnections.add(connection);
            }

        }
    }

    public void persistRepositorys(){
        if (durch == 0){

            System.out.println("Es wird gespeichert");
            //Rooms
            for (Room room : allRooms){
                roomRepository.save(room);
            }

            //TidyUpRobot
            for (TidyUpRobot tidyUpRobot : allTidyUpRobots ){
                tidyUpRobotRepository.save(tidyUpRobot);
            }

            //Connections
            for (Connection connection : allConnections ){
                connectionRepository.save(connection);
            }

            //Orders
            for (Order order : allOrders){
                orderRepository.save(order);
            }
        }
        durch = 1;


    }

    public List<Order> getRobotersOrders(UUID robotId){

        List<Order> returnListWithOrdersFromRobot = new ArrayList<>();

        for (RobotOrders robotOrder : robotOrders){
            if (robotOrder.getRobotsId().equals(robotId)){
                returnListWithOrdersFromRobot.add(robotOrder.getOrder());
            }
        }
        return returnListWithOrdersFromRobot;
    }

    public void deleteRobotersOrders(UUID robotId){
        for (RobotOrders robotOrder : robotOrders){
            if (robotOrder.getRobotsId().equals(robotId)){
                allOrders.remove(robotOrder.getOrder());
                orderRepository.delete(robotOrder.getOrder());
            }
        }


    }





}
