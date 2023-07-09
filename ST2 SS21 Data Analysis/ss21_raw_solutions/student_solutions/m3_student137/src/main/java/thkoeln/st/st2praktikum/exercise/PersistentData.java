package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class PersistentData {

    ArrayList<Spaces> level = new ArrayList<>();
    ArrayList<Portals> portal = new ArrayList<>();
    ArrayList<Robots> robot = new ArrayList<>();
    ArrayList<TransTech> transtech = new ArrayList<>();

    public Spaces getLevel(UUID findLevel) {
        int position = 0;

        for(int counter = 0; counter < level.size(); counter++) {
            if(level.get(counter).id.compareTo(findLevel) == 0) {
                position = counter;
                break;
            }
        }
        return level.get(position);
    }

    public Robots getRobot(UUID robotID) {
        int position = 0;

        for(int counter = 0; counter < robot.size(); counter++) {
            if(robot.get(counter).robotID.compareTo(robotID) == 0) {
                position = counter;
                break;
            }
        }
        return robot.get(position);

    }

    public Portals getPortal(UUID sourceID) {
        int position = 0;

        for(int counter = 0; counter < portal.size(); counter++) {
            if(portal.get(counter).SourceLvl.compareTo(sourceID) == 0) {
                position = counter;
                break;
            }
        }
        return portal.get(position);
    }

    public void storeNewLevel(Spaces newLevel) {
        level.add(newLevel);
    }

    public void storeNewTransTech(TransTech newTransTech) {
        transtech.add(newTransTech);
    }

    public boolean moveRobot(UUID robotID, Order myOrder) {

        boolean success = true;
        Spaces nextLevel;
        Spaces currentLevel;
        UUID destinationUUID;

        // get the involved robot weather for movement or entering/transport
        Robots neededRobot = getRobot(robotID);

        // check if the provided ordertype is enter or transport
        if(myOrder.getOrderType() == OrderType.ENTER || myOrder.getOrderType() == OrderType.TRANSPORT) {

            switch (myOrder.getOrderType()) {

                case ENTER:
                    currentLevel = getLevel(myOrder.getGridId()); // set destination level from provided order
                    if(currentLevel.singleField[0][0].isOccupied){ // if the field we want to move is occupied return false
                        success = false;
                    }
                    else {
                        neededRobot.spawn(currentLevel.id); // else spawn robot and set current field to occupied
                        currentLevel.singleField[0][0].isOccupied = true;
                    }
                    break;

                case TRANSPORT:
                    nextLevel = getLevel(myOrder.getGridId());
                    currentLevel = getLevel(neededRobot.positionZ);

                    if(currentLevel.singleField[neededRobot.positionX][neededRobot.positionY].isPortal) { // check if field is even a portal
                        Portals neededPortal = getPortal(currentLevel.id);

                        if(currentLevel.singleField[neededRobot.positionX][neededRobot.positionY].destination.compareTo(myOrder.getGridId()) == 0) { // check if destination aligns with robots destinatio
                            if(nextLevel.singleField[neededPortal.DestinationCoordX][neededPortal.DestinationCoordY].isOccupied){ // check if destination field is not blocked
                                success = false; // if the field of the portal destination is already occupied set success to false;
                            }
                            else {
                                currentLevel.singleField[neededRobot.positionX][neededRobot.positionY].isOccupied = false; // set field we are leving to not occupied again
                                neededRobot.positionX = neededPortal.DestinationCoordX; // move robot
                                neededRobot.positionY = neededPortal.DestinationCoordY;
                                neededRobot.positionZ = nextLevel.id; // change is level to new level
                                nextLevel.singleField[neededRobot.positionX][neededRobot.positionY].isOccupied = true; // set field we are entering to occupied
                            }
                        }
                        else {
                            success = false; // if the destination of the portal is not the same as the destination of the robot, set success to false
                        }
                    }
                    else {
                        success = false; // if the field the robot is standing on is not even a portal, set success to false imidiately
                    }
                    break;

                default:  throw  new NumberFormatException();
            }

        } else
            // if order type is a movement direction call robot move function with according values
            if (myOrder.getOrderType() == OrderType.NORTH || myOrder.getOrderType() == OrderType.EAST || myOrder.getOrderType() == OrderType.SOUTH || myOrder.getOrderType() == OrderType.WEST) {

                neededRobot.move(myOrder.getOrderType(), myOrder.getNumberOfSteps(), getLevel(neededRobot.positionZ), neededRobot);
            }
        return success;
    }

    public Vector2D getRobotPos (UUID robot) {

        // get current robot object
        Robots myRobot = getRobot(robot);

        // generate a vector string from his current position and send it into the vetor function to get the current position as a vector back
        String currentPos = "(" + myRobot.positionX + "," + myRobot.positionY + ")";
        return new Vector2D(currentPos);

    }
}
