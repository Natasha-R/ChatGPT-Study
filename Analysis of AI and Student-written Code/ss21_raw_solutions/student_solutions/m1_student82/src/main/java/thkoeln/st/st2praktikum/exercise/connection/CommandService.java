package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.CoordinateService;
import thkoeln.st.st2praktikum.exercise.droid.Decision;
import thkoeln.st.st2praktikum.exercise.droid.Direction;
import thkoeln.st.st2praktikum.exercise.droid.DirectionService;
import thkoeln.st.st2praktikum.exercise.droid.Droid;
import thkoeln.st.st2praktikum.exercise.exceptions.NoConnectionException;
import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.exceptions.NotSpawnedYetException;
import thkoeln.st.st2praktikum.exercise.map.Cloud;

import java.util.UUID;

import static thkoeln.st.st2praktikum.exercise.droid.Decision.canGoForward;

public interface CommandService {

    static boolean commandWithUUID(Command command, Droid droid, Cloud world) {
        switch (command.getDir()){
            case TR:
                try {
                   return droid.travel(world);
                } catch (NoFieldException e) {
                    e.printStackTrace();
                    return false;
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                    return false;
                }
            case EN:

                try {
                    return droid.spawn(world, command.getSpaceShipUUId());
                } catch (NoFieldException e) {
                    e.printStackTrace();
                    return false;
                }
            default:
                return false;
        }


    }

    static boolean commandWithPower(Command command, Droid droid, Cloud world){
        for(int i = 0; i < command.getPower(); i++) {
            try {
                if (droid.canGoForward(command.getDir(), world) == Decision.canGoForward) {
                    try {
                        droid.move(command.getDir(), world);
                    } catch (NotSpawnedYetException e) {
                        System.out.println(e.toString());

                    } catch (NoFieldException e) {
                        System.out.println(e.toString());

                    }

                }
            } catch (NotSpawnedYetException e) {
                System.out.println(e.toString());
            } catch (NoFieldException e) {
                System.out.println(e.toString());
            }
        }
        return true;
    }


    static Command stringToCommand(String commandString) throws IllegalArgumentException{
        String newCommandRaw = commandString.substring(1, commandString.length() -1);
        String[] commandParts = newCommandRaw.split(",");
        Direction dir = DirectionService.stringToDirection(commandParts[0]);

        switch (dir){
            case NO:
            case EA:
            case WE:
            case SO:
                return new Command( Integer.parseInt(commandParts[1]), dir);
            case EN:
            case TR:
                return new Command(UUID.fromString(commandParts[1]), dir);
            default:
                throw new IllegalArgumentException(dir.toString());
        }

    }
}
