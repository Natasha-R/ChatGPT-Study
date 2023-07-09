package  thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;
import thkoeln.st.st2praktikum.AbstractEntity;
import static thkoeln.st.st2praktikum.exercise.Space.*;

import java.util.UUID;

@Getter
@Setter
public class MaintenanceDroid extends AbstractEntity implements Moveable, Commandable {

    private String name;
    protected Point coordinate = new Point(0,0);

    private SpaceShipDeck currentSpaceShipDeck;
    private  UUID currentSpaceShipDeckId;

    protected boolean executeCommand(UUID maintenanceDroid, String command){
        if (command.charAt(2)=='n'){
            return initialise(maintenanceDroid,command);
        }
        else if (command.charAt(1)=='t'){
            return transport(maintenanceDroid,command);
        }
        else {
            return movement(maintenanceDroid,command);
        }
    }

    public boolean movement(UUID maintenanceDroid, String commandString
    ){
        String command = commandString.substring(1, 3);
        Integer numberOfSteps = Integer.parseInt(String.valueOf(commandString.charAt(4)));
        if (command.equals("no") || command.equals("so")){
            maintenanceDroids.get(maintenanceDroid).moveY(numberOfSteps,command);
        }
        else if (command.equals("we") || command.equals("ea")){
            maintenanceDroids.get(maintenanceDroid).moveX(numberOfSteps,command);
        }
        return false;
    }

    public Integer getPositionOfAnotherDroidInTheWay(String command){
        Integer positionOfOtherDroid = null;
        for (int i = 0; i < getCurrentSpaceShipDeck().maintenanceDroids.size(); i++){
            if(getCurrentSpaceShipDeck().maintenanceDroids.get(i).coordinate.getX() == coordinate.getX()){
                if((command.equals("no") && coordinate.getY() < getCurrentSpaceShipDeck().maintenanceDroids.get(i).coordinate.getY() ||
                        command.equals("so") && coordinate.getY() > getCurrentSpaceShipDeck().maintenanceDroids.get(i).coordinate.getY())){
                    positionOfOtherDroid = getCurrentSpaceShipDeck().maintenanceDroids.get(i).coordinate.getY();
                }
            }
            else if(getCurrentSpaceShipDeck().maintenanceDroids.get(i).coordinate.getY() == coordinate.getY()){
                if((command.equals("ea")  && coordinate.getX() < getCurrentSpaceShipDeck().maintenanceDroids.get(i).coordinate.getX()) ||
                        (command.equals("we") && coordinate.getX() > getCurrentSpaceShipDeck().maintenanceDroids.get(i).coordinate.getX())){
                    positionOfOtherDroid = getCurrentSpaceShipDeck().maintenanceDroids.get(i).coordinate.getX();
                }
            }
        }
        return positionOfOtherDroid;
    }

    public Integer moveDroidWithoutObstruction( Integer droidPosition, Integer positionOfOtherDroid, String command){
        if (command.equals("no") || command.equals("ea")){
            if(positionOfOtherDroid != null && droidPosition >= positionOfOtherDroid)  return positionOfOtherDroid - 1;
        }else if(command.equals("so") || command.equals("we") ){
            if(positionOfOtherDroid != null && droidPosition <= positionOfOtherDroid)  return positionOfOtherDroid + 1;
        }
        return droidPosition;
    }

    public Integer moveDroidOnXWithObstruction( Integer droidPosition, Integer positionOfOtherDroid, String command){
        for (int i = 0; i < currentSpaceShipDeck.barriers.size(); i++){
            droidPosition = moveDroidWithoutObstruction(droidPosition,positionOfOtherDroid,command);

            if(currentSpaceShipDeck.barriers.get(i).getStartX() == currentSpaceShipDeck.barriers.get(i).getEndX()) {
                if(coordinate.getY() >= currentSpaceShipDeck.barriers.get(i).getStartY() &&
                        coordinate.getY() < currentSpaceShipDeck.barriers.get(i).getEndX() && positionOfOtherDroid == null){
                    if(command.equals("ea") && droidPosition >= currentSpaceShipDeck.barriers.get(i).getStartX() &&
                            coordinate.getX() < currentSpaceShipDeck.barriers.get(i).getStartX()){
                        droidPosition = currentSpaceShipDeck.barriers.get(i).getStartX() - 1;
                    }
                    else if(command.equals("we") && droidPosition <= currentSpaceShipDeck.barriers.get(i).getStartX() &&
                            coordinate.getX() >= currentSpaceShipDeck.barriers.get(i).getStartX()){
                        droidPosition = currentSpaceShipDeck.barriers.get(i).getStartX() + 1;
                    }
                }
            }
        }
        return droidPosition;
    }

    public Integer moveDroidYWithObstruction(Integer droidPosition, Integer positionOfOtherDroid, String command){
        for (int i = 0; i < currentSpaceShipDeck.barriers.size(); i++){
            droidPosition = moveDroidWithoutObstruction(droidPosition,positionOfOtherDroid,command);
            // check if there is a wall,that impending passage
            if(currentSpaceShipDeck.barriers.get(i).getStartY().equals(currentSpaceShipDeck.barriers.get(i).getEndY())) {
                if(coordinate.getX() < currentSpaceShipDeck.barriers.get(i).getEndX() &&
                        coordinate.getX() >= currentSpaceShipDeck.barriers.get(i).getStartX() && positionOfOtherDroid == null){
                    if(command.equals("no") && droidPosition >= currentSpaceShipDeck.barriers.get(i).getStartY() &&
                            coordinate.getY() < currentSpaceShipDeck.barriers.get(i).getStartY()){
                        droidPosition = currentSpaceShipDeck.barriers.get(i).getStartY() - 1;
                    }
                    else if(command.equals("so") && droidPosition <= currentSpaceShipDeck.barriers.get(i).getStartY() &&
                            coordinate.getY() >= currentSpaceShipDeck.barriers.get(i).getStartY()){
                        droidPosition = currentSpaceShipDeck.barriers.get(i).getStartY() + 1;
                    }
                }
            }
        }
        return droidPosition;

    }

    public Integer moveDroidOutOfBounds(Integer droidPosition, String command){
        if (command.equals("no") && (droidPosition >= currentSpaceShipDeck.getHeight())){
            droidPosition = currentSpaceShipDeck.getHeight() - 1;
        }else if((command.equals("so") || command.equals("we")) && droidPosition < 0){
            droidPosition = 0;
        }else if(command.equals("ea") && droidPosition >= currentSpaceShipDeck.getWidth()){
            droidPosition = currentSpaceShipDeck.getWidth() - 1;
        }
        return droidPosition;
    }

    @Override
    public boolean transport(UUID maintenanceDroidId, String command) {
        UUID deckId = UUID.fromString(command.substring(command.indexOf(',') + 1, command.length() - 1));

        if (decks.get(maintenanceDroids.get(maintenanceDroidId).getCurrentSpaceShipDeck().getId()).connection(deckId).getSourceCoordinate().getX().equals(maintenanceDroids.get(maintenanceDroidId).coordinate.getX()) &&
                decks.get(maintenanceDroids.get(maintenanceDroidId).getCurrentSpaceShipDeck().getId()).connection(deckId).getSourceCoordinate().getY().equals(maintenanceDroids.get(maintenanceDroidId).coordinate.getY())) {

            int x = decks.get(maintenanceDroids.get(maintenanceDroidId).getCurrentSpaceShipDeck().getId()).connection(deckId).getDestinationCoordinate().getX();
            int y = decks.get(maintenanceDroids.get(maintenanceDroidId).getCurrentSpaceShipDeck().getId()).connection(deckId).getDestinationCoordinate().getY();

            maintenanceDroids.get(maintenanceDroidId).coordinate.setX(x);
            maintenanceDroids.get(maintenanceDroidId).coordinate.setY(y);

            decks.get(maintenanceDroids.get(maintenanceDroidId).getCurrentSpaceShipDeck().getId()).maintenanceDroids.remove(maintenanceDroids.get(maintenanceDroidId));
            maintenanceDroids.get(maintenanceDroidId).setCurrentSpaceShipDeckId(deckId);
            maintenanceDroids.get(maintenanceDroidId).setCurrentSpaceShipDeck(decks.get(deckId));
            return true;
        }
        return false;
    }

    @Override
    public void moveX(Integer steps, String command) {
        if(command.equals("we"))  steps *= -1;
        Integer xCoordinateOfDroid = coordinate.getX() + steps;
        xCoordinateOfDroid = moveDroidOutOfBounds(xCoordinateOfDroid, command);

        Integer xCoordinateOfOtherDroid = getPositionOfAnotherDroidInTheWay(command);

        xCoordinateOfDroid = moveDroidOnXWithObstruction(xCoordinateOfDroid, xCoordinateOfOtherDroid, command);

        coordinate.setX(xCoordinateOfDroid);
        coordinate.setY(coordinate.getY());

    }

    @Override
    public void moveY(Integer steps, String command) {
        if(command.equals("so"))  steps *= -1;
        Integer yCoordinateOfDroid = coordinate.getY() + steps;
        yCoordinateOfDroid = moveDroidOutOfBounds(yCoordinateOfDroid,command);

        Integer yCoordinateOfOtherDroid = getPositionOfAnotherDroidInTheWay(command);

        yCoordinateOfDroid = moveDroidYWithObstruction(yCoordinateOfDroid, yCoordinateOfOtherDroid, command);

        coordinate.setX(coordinate.getX());
        coordinate.setY(yCoordinateOfDroid);

    }
}