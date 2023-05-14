package thkoeln.st.st2praktikum.exercise;

import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;

import java.util.UUID;

public class Robots {

    UUID robotID;
    String name;
    int positionX = 0;
    int positionY = 0;
    UUID positionZ = new UUID(0,0); // define the "vertical" position on a map of overlapping level

    public Robots(UUID robotID, String name) {

        this.robotID = robotID;
        this.name = name;

    }

    public void spawn(UUID spawnLevel){
        this.positionZ = spawnLevel;
    }

    public void move (String direction, String amount, Spaces level, Robots currentRobot) {

        int intAmount = Integer.parseInt(amount);

        switch(direction) {

            case "no":  for(int i = 0; i < intAmount; i++) { // try to move for each step
                            if(!(level.singleField[positionX][positionY].borderTop)) { // check if current field as a border in moving direction
                                if (!(level.singleField[positionX][positionY+1].isOccupied)) { // check if "destination" field is occupied
                                    level.singleField[positionX][positionY].isOccupied = false;
                                    positionY = positionY + 1; // if nothing is blocking. move robot 1 step
                                    level.singleField[positionX][positionY].isOccupied = true;
                                }
                                else {break;}
                            } else {break;}
                        }
                        break;

            case "ea":  for(int i = 0; i < intAmount; i++) { // try to move for each step
                            if(!(level.singleField[positionX][positionY].borderRight)) { // check if current field as a border in moving direction
                                if(!(level.singleField[positionX+1][positionY].isOccupied)) { // check if "destination" field is occupied
                                    level.singleField[positionX][positionY].isOccupied = false;
                                    positionX =  positionX + 1; // if nothing is blocking. move robot 1 step
                                    level.singleField[positionX][positionY].isOccupied = true;
                                }
                            }
                        }
                        break;

            case "so":  for(int i = 0; i < intAmount; i++) { // try to move for each step

                            if(!(level.singleField[positionX][positionY].borderBottom)) { // check if current field as a border in moving direction

                                if(!(level.singleField[positionX][positionY-1].isOccupied)) { // check if "destination" field is occupied
                                    level.singleField[positionX][positionY].isOccupied = false;
                                    positionY -= 1; // if nothing is blocking. move robot 1 step
                                    level.singleField[positionX][positionY].isOccupied = true;
                                }

                            }
                        }
                        break;

            case "we":  for(int i = 0; i < intAmount; i++) { // try to move for each step

                            if(!(level.singleField[positionX][positionY].borderLeft)) { // check if current field as a border in moving direction

                                if (!(level.singleField[positionX-1][positionY].isOccupied)) { // check if "destination" field is occupied
                                    level.singleField[positionX][positionY].isOccupied = false;
                                    positionX -= 1; // if nothing is blocking. move robot 1 step
                                    level.singleField[positionX][positionY].isOccupied = true;
                                }
                            }
                        }
                        break;
        }
    }
}
