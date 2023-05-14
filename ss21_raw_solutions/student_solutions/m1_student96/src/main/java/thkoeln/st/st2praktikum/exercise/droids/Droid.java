package thkoeln.st.st2praktikum.exercise.droids;

import lombok.Data;
import thkoeln.st.st2praktikum.exercise.control.Controller;
import thkoeln.st.st2praktikum.exercise.map.DeckGraph;
import thkoeln.st.st2praktikum.exercise.map.Node;
import thkoeln.st.st2praktikum.exercise.stringConversion.Converter;

import java.util.UUID;

@Data
public class Droid {
    private UUID droidID;
    private Node position;
    private String name;
    private Converter convert = new Converter();

    public Droid(String name){
        this.setName(name);
        this.setDroidID(UUID.randomUUID());
    }

    public String getCoordinates(){
        return position.getCoordinates().toString();
    }

    public boolean spawnDroid(DeckGraph destinationDeck){
        //System.out.println(destinationDeck.toString());
        Node destinationNode = destinationDeck.getNodes().get("(0,0)");
        if (destinationNode.getIsBlocked()) {
            return false;
        }
        destinationNode.setIsBlocked(true);
        this.setPosition(destinationNode);
        return true;
    }

    public boolean traversConnection(DeckGraph destinationDeck){
        try {
            Node start = getPosition();
            Node destination = getPosition().getConnection();
            if(destination.getDeckID() == destinationDeck.getDeckID() &&
                    !destination.getIsBlocked()){
                destination.setIsBlocked(true);
                setPosition(destination);
                start.setIsBlocked(false);
            }
        } catch (NullPointerException e) {
            System.out.println("Exception:" + e);
            return false;
        }
        return false;

    }

    public boolean move(String commandString, Controller controller){
        String command = convert.toCommand(commandString);
        int steps = 0;
        DeckGraph destinationDeck = new DeckGraph(0,0);
        if (command.equals("en") || command.equals("tr")) {
            System.out.println("Command:" + command);
            destinationDeck = controller.getDeckByID(convert.toUUID(commandString));
            System.out.println(destinationDeck.toString());
        } else {
            steps = convert.toSteps(commandString);
        }
        switch (command) {
            case ("en") :
                return spawnDroid(destinationDeck);
            case ("tr") :
                return traversConnection(destinationDeck);
            case ("no") :
                return stepNorth(steps);
            case ("so") :
                return stepSouth(steps);
            case ("ea") :
                return stepEast(steps);
            case ("we") :
                return stepWest(steps);
            default:  return false;
        }
    }

    public boolean stepNorth(int steps){
        if (steps == 0) {
            return true;
        }
        try {
            if (position.getNorth().getIsBlocked()) {
                return true;
            } else {
                position.getNorth().setIsBlocked(true);
            }
        } catch (NullPointerException e) {
            return true;
        }
        position = position.getNorth();
        position.getSouth().setIsBlocked(false);
        return stepNorth(steps-1);
    }

    public boolean stepSouth(int steps){
        if (steps == 0) {
            return true;
        }
        try{
            if (position.getSouth().getIsBlocked()) {
                return true;
            } else {
                position.getSouth().setIsBlocked(true);
                position = position.getSouth();
                position.getNorth().setIsBlocked(false);
                return stepSouth(steps - 1);
            }
        } catch (NullPointerException e) {
            return true;
        }
    }

    public boolean stepEast(int steps){
        Node start = position;
        if (steps == 0) {
            return true;
        }
        try{
            if (position.getEast().getIsBlocked()) {
                return true;
            }else {
                start.getEast().setIsBlocked(true);
                this.setPosition(start.getEast());
                start.setIsBlocked(false);
                return stepEast(steps-1);
            }
        }
        catch (NullPointerException e) {
            return true;
        }
    }

    public boolean stepWest(int steps){
        if (steps == 0) {
            return true;
        }
        try{
            if (position.getWest().getIsBlocked()) {
                return true;
            } else {
                position.getWest().setIsBlocked(true);
                position = position.getWest();
                position.getEast().setIsBlocked(false);
                return stepWest(steps-1);
            }
        }
        catch (NullPointerException e) {
            return true;
        }
    }

    public String toString() {
        return getName();
    }
}
