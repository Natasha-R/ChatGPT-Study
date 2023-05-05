package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;
import static thkoeln.st.st2praktikum.exercise.Zone.*;

public class TidyUpRobot extends AbstractEntity implements Moveable, Transportable{

    private String name;
    protected Pair coordinate = new Pair(0,0);

    private Room currentRoom;
    private UUID currentRoomUUID;

    protected boolean executeCommand(UUID tidyUpRobot, String command){
        if (command.charAt(2)=='n'){
            return initialise(tidyUpRobot,command);
        }
        else if (command.charAt(1)=='t'){
            return tarnsport(tidyUpRobot,command);
        }
        else {
            return movement(tidyUpRobot,command);
        }
    }

    public boolean movement(UUID tidyUpRobot, String commandString) {
        String command = commandString.substring(1, 3);
        Integer numberOfSteps = Integer.parseInt(String.valueOf(commandString.charAt(4)));
        if (command.equals("no") || command.equals("so")){
            tidyUpRobots.get(tidyUpRobot).moveOnYAxis(numberOfSteps,command);
        }
        else if (command.equals("we") || command.equals("ea")){
            tidyUpRobots.get(tidyUpRobot).moveOnXAxis(numberOfSteps,command);
        }
        return false;
    }

    //Is there another robot in the same path?
    public Integer getValueOfAnotherRobotInSamePath(String command){
        Integer valueOfAnotherRobot = null;
        for (int i = 0; i < getCurrentRoom().tidyUpRobots.size(); i++){
            if(getCurrentRoom().tidyUpRobots.get(i).coordinate.getX() == coordinate.getX()){
                if((command.equals("no") && coordinate.getY() < getCurrentRoom().tidyUpRobots.get(i).coordinate.getY() ||
                        command.equals("so") && coordinate.getY() > getCurrentRoom().tidyUpRobots.get(i).coordinate.getY())){
                    valueOfAnotherRobot = getCurrentRoom().tidyUpRobots.get(i).coordinate.getY();
                }
            }
            else if(getCurrentRoom().tidyUpRobots.get(i).coordinate.getY() == coordinate.getY()){
                if((command.equals("ea")  && coordinate.getX() < getCurrentRoom().tidyUpRobots.get(i).coordinate.getX()) ||
                        (command.equals("we") && coordinate.getX() > getCurrentRoom().tidyUpRobots.get(i).coordinate.getX())){
                    valueOfAnotherRobot = getCurrentRoom().tidyUpRobots.get(i).coordinate.getX();
                }
            }
        }
        return valueOfAnotherRobot;
    }

    /*When there is wall,that not impeding passage*/
    public Integer moveRobotWithoutObstruktiveWall(Integer valueOfRobot, Integer valueOfAnotherRobot, String command){
        if (command.equals("no") || command.equals("ea")){
            if(valueOfAnotherRobot != null && valueOfRobot >= valueOfAnotherRobot)  return valueOfAnotherRobot - 1;
        }else if(command.equals("so") || command.equals("we") ){
            if(valueOfAnotherRobot != null && valueOfRobot <= valueOfAnotherRobot)  return valueOfAnotherRobot + 1;
        }
        return valueOfRobot;
    }

    /*When there is wall,that impeding passage*/
    public Integer moveRobotOnYAxisWithObstruktiveWall(Integer valueOfRobot, Integer valueOfAnotherRobot, String command){
        for (int i = 0; i < currentRoom.walls.size(); i++){
            valueOfRobot = moveRobotWithoutObstruktiveWall(valueOfRobot,valueOfAnotherRobot,command);
            // check if there is a wall,that impending passage
            if(currentRoom.walls.get(i).getStartValueOfY().equals(currentRoom.walls.get(i).getEndValueOfY())) {
                if(coordinate.getX() < currentRoom.walls.get(i).getEndValueOfX() &&
                        coordinate.getX() >= currentRoom.walls.get(i).getStartValueOfX() && valueOfAnotherRobot == null){
                    if(command.equals("no") && valueOfRobot >= currentRoom.walls.get(i).getStartValueOfY() &&
                            coordinate.getY() < currentRoom.walls.get(i).getStartValueOfY()){
                        valueOfRobot = currentRoom.walls.get(i).getStartValueOfY() - 1;
                    }
                    else if(command.equals("so") && valueOfRobot <= currentRoom.walls.get(i).getStartValueOfY() &&
                            coordinate.getY() >= currentRoom.walls.get(i).getStartValueOfY()){
                        valueOfRobot = currentRoom.walls.get(i).getStartValueOfY() + 1;
                    }
                }
            }
        }
        return valueOfRobot;
    }

    public Integer moveRobotOnXAxisWithObstruktiveWall(Integer valueOfRobot, Integer valueOfAnotherRobot, String command){
        for (int i = 0; i < currentRoom.walls.size(); i++){
            valueOfRobot = moveRobotWithoutObstruktiveWall(valueOfRobot,valueOfAnotherRobot,command);

            if(currentRoom.walls.get(i).getStartValueOfX() == currentRoom.walls.get(i).getEndValueOfX()) {
                if(coordinate.getY() >= currentRoom.walls.get(i).getStartValueOfY() &&
                        coordinate.getY() < currentRoom.walls.get(i).getEndValueOfX() && valueOfAnotherRobot == null){
                    if(command.equals("ea") && valueOfRobot >= currentRoom.walls.get(i).getStartValueOfX() &&
                            coordinate.getX() < currentRoom.walls.get(i).getStartValueOfX()){
                        valueOfRobot = currentRoom.walls.get(i).getStartValueOfX() - 1;
                    }
                    else if(command.equals("we") && valueOfRobot <= currentRoom.walls.get(i).getStartValueOfX() &&
                            coordinate.getX() >= currentRoom.walls.get(i).getStartValueOfX()){
                        valueOfRobot = currentRoom.walls.get(i).getStartValueOfX() + 1;
                    }
                }
            }
        }
        return valueOfRobot;
    }

    public Integer moveRobotWithOutOfBounds(Integer valueOfRobot, String command){
        if (command.equals("no") && (valueOfRobot >= currentRoom.getHeight())){
            valueOfRobot = currentRoom.getHeight() - 1;
        }else if((command.equals("so") || command.equals("we")) && valueOfRobot < 0){
            valueOfRobot = 0;
        }else if(command.equals("ea") && valueOfRobot >= currentRoom.getWidth()){
            valueOfRobot = currentRoom.getWidth() - 1;
        }
        return valueOfRobot;
    }

    public void moveOnYAxis(Integer numberOfStep,String command){
        if(command.equals("so"))  numberOfStep *= -1;
        Integer yValueOfRobot = coordinate.getY() + numberOfStep;
        yValueOfRobot = moveRobotWithOutOfBounds(yValueOfRobot,command);

        //Is there another Robot in same path?
        Integer yValueOfAnotherRobot = getValueOfAnotherRobotInSamePath(command);

        yValueOfRobot = moveRobotOnYAxisWithObstruktiveWall(yValueOfRobot, yValueOfAnotherRobot, command);

        coordinate.setX(coordinate.getX());
        coordinate.setY(yValueOfRobot);
    }

    public void moveOnXAxis(Integer numberOfStep,String command){
        if(command.equals("we"))  numberOfStep *= -1;
        Integer xValueOfRobot = coordinate.getX() + numberOfStep;
        xValueOfRobot = moveRobotWithOutOfBounds(xValueOfRobot,command);

        Integer xValueOfAnotherRobot = getValueOfAnotherRobotInSamePath(command);

        xValueOfRobot = moveRobotOnXAxisWithObstruktiveWall(xValueOfRobot, xValueOfAnotherRobot, command);

        coordinate.setX(xValueOfRobot);
        coordinate.setY(coordinate.getY());
    }

    @Override
    public boolean tarnsport(UUID tidyUpRobot, String order) {
        UUID roomID = UUID.fromString(order.substring(order.indexOf(',') + 1, order.length() - 1));

        //check if Tidyuprobot's coordinate same as the connection's source coordinate
        if (rooms.get(tidyUpRobots.get(tidyUpRobot).getCurrentRoom().id).connection(roomID).getSourceCoordinate().getX().equals(tidyUpRobots.get(tidyUpRobot).coordinate.getX()) &&
                rooms.get(tidyUpRobots.get(tidyUpRobot).getCurrentRoom().id).connection(roomID).getSourceCoordinate().getY().equals(tidyUpRobots.get(tidyUpRobot).coordinate.getY())) {

            int x = rooms.get(tidyUpRobots.get(tidyUpRobot).getCurrentRoom().id).connection(roomID).getDestinationCoordinate().getX();
            int y = rooms.get(tidyUpRobots.get(tidyUpRobot).getCurrentRoom().id).connection(roomID).getDestinationCoordinate().getY();

            tidyUpRobots.get(tidyUpRobot).coordinate.setX(x);
            tidyUpRobots.get(tidyUpRobot).coordinate.setY(y);

            //remove the robot from Source Room and add it in Destination Room
            rooms.get(tidyUpRobots.get(tidyUpRobot).getCurrentRoom().id).tidyUpRobots.remove(tidyUpRobots.get(tidyUpRobot));
            tidyUpRobots.get(tidyUpRobot).setCurrentRoomUUID(roomID);
            tidyUpRobots.get(tidyUpRobot).setCurrentRoom(rooms.get(roomID));
            return true;
        }
        return false;
    }

    public String getName() { return name;}
    public void setName(String name){ this.name = name; }

    public Room getCurrentRoom(){ return currentRoom; }
    public void  setCurrentRoom(Room currentRoom){ this.currentRoom = currentRoom; }
    public UUID getCurrentRoomUUID(){ return currentRoomUUID; }
    public void setCurrentRoomUUID(UUID currentRoomUUID){ this.currentRoomUUID = currentRoomUUID; }
}