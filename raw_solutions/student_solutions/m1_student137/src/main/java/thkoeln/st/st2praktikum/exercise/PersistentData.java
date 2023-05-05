package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class PersistentData {

    ArrayList<Spaces> level = new ArrayList<>();
    ArrayList<Portals> portal = new ArrayList<>();
    ArrayList<Robots> robot = new ArrayList<>();

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
                //System.out.println(portal.get(position).DestinationCoordX + ", " + portal.get(position).DestinationCoordY);
                break;
            }
        }
        return portal.get(position);
    }

    public ArrayList<Integer> getIntWallPositons(String positionString) {

        // split start and end of wall
        String[] mainSplit = positionString.split("-");
        ArrayList<String> totalSplit = new ArrayList<>();

        // split start and end POINT of start and end values
        for(int i= 0; i < mainSplit.length; i++){
            String[] leftSplit = mainSplit[i].split(",");
            totalSplit.add(leftSplit[0]);
            totalSplit.add(leftSplit[1]);
        }
        String[] wallEndFirstStep = totalSplit.get(1).split("\\)");
        String[] wallEndSecondStep = totalSplit.get(3).split("\\)");

        ArrayList<Integer> allPositions = new ArrayList<>();

        allPositions.add(Integer.parseInt(totalSplit.get(0).substring(1)));
        allPositions.add(Integer.parseInt(wallEndFirstStep[0]));
        allPositions.add(Integer.parseInt(totalSplit.get(2).substring(1)));
        allPositions.add(Integer.parseInt(wallEndSecondStep[0]));

        return allPositions;
    }

    public void storeNewLevel(Spaces newLevel) {
        level.add(newLevel);
    }

    public boolean moveRobot(UUID robotID, String command) {

        boolean success = true;
        Spaces nextLevel;
        Spaces currentLevel;
        UUID destinationUUID;

        String Command1 = command.substring(1, 3); // gets the first command without the closing bracket up till the comma
        String Command2 = (command.substring(4, command.length() - 1)); // gets the second command without the closing bracket

        Robots neededRobot = getRobot(robotID);

        switch (Command1) {

            case "en":  destinationUUID = UUID.fromString(Command2);

                        currentLevel = getLevel(destinationUUID);
                        if(currentLevel.singleField[0][0].isOccupied){
                            success = false;
                        }
                        else {
                            neededRobot.spawn(currentLevel.id);
                            currentLevel.singleField[0][0].isOccupied = true;
                        }
                        break;

            case "tr":  destinationUUID = UUID.fromString(Command2);
                        nextLevel = getLevel(destinationUUID);
                        currentLevel = getLevel(neededRobot.positionZ);

                        if(currentLevel.singleField[neededRobot.positionX][neededRobot.positionY].isPortal) { // check if field is even a portal
                            Portals neededPortal = getPortal(currentLevel.id);

                            if(currentLevel.singleField[neededRobot.positionX][neededRobot.positionY].destination.compareTo(destinationUUID) == 0) { // check if destination aligns with robots destinatio
                                if(nextLevel.singleField[neededPortal.DestinationCoordX][neededPortal.DestinationCoordY].isOccupied){ // check if destination field is not blocked
                                    success = false; // if the field of the portal destination is already occupied set success to false;
                                }
                                else {
                                    currentLevel.singleField[neededRobot.positionX][neededRobot.positionY].isOccupied = false;
                                    neededRobot.positionX = neededPortal.DestinationCoordX;
                                    neededRobot.positionY = neededPortal.DestinationCoordY;
                                    neededRobot.positionZ = nextLevel.id;

                                }
                                nextLevel.singleField[neededRobot.positionX][neededRobot.positionY].isOccupied = false;
                            }
                            else {
                                success = false; // if the destination of the portal is not the same as the destination of the robot, set success to false
                            }
                        }
                        else {
                            success = false; // if the field the robot is standing on is not even a portal, set success to false imidiately
                        }
                        break;

            default:    neededRobot.move(Command1, Command2, getLevel(neededRobot.positionZ), neededRobot);
                        break;



        }

        //System.out.println(neededRobot.positionX +", "+ neededRobot.positionY);
        return success;
    }

}
