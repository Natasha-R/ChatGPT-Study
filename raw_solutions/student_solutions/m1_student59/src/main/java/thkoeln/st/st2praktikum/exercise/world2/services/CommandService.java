package thkoeln.st.st2praktikum.exercise.world2.services;

import thkoeln.st.st2praktikum.exercise.world2.Cloud;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.IllegalDecisionException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.IllegalDirectionException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.MiningmaschineNotSpawnedException;
import thkoeln.st.st2praktikum.exercise.world2.exceptions.NoSquareFoundException;
import thkoeln.st.st2praktikum.exercise.world2.miningMaschine.Miningmaschine;
import thkoeln.st.st2praktikum.exercise.world2.types.Command;
import thkoeln.st.st2praktikum.exercise.world2.types.Decision;

import java.util.UUID;

public interface CommandService {

    static Command stringToCommand(String commandString) throws IllegalArgumentException {
        String commandString_raw = commandString.substring(1, commandString.length() - 1);
        String[] commandString_array = commandString_raw.split(",");
        switch (commandString_array[0]){
            case "en":
            case "tr":
                return new Command(DirectionService.stringToDirection(commandString_array[0]), UUID.fromString(commandString_array[1]));
            case "no":
            case "we":
            case "so":
            case "ea":
                return new Command(DirectionService.stringToDirection(commandString_array[0]),Integer.parseInt(commandString_array[1]));
            default:
                throw new IllegalArgumentException(commandString_array[0]);
        }
    }

    static boolean commandWithUUID(Command command, Miningmaschine miningMaschine, Cloud world) throws IllegalDirectionException {
        final UUID fieldId = command.getUUID();
        switch (command.getDirection()) {
            case EN:
                try{
                    return miningMaschine.spawnMiningMaschine(fieldId, CoordinateService.getDefaultSpawn(),true, world);
                }catch (IllegalStateException illegalStateException){
                    System.out.println(illegalStateException);
                }
                break;
            case TR:
                try{
                    return miningMaschine.tunnelMininngMaschine(world);
                }catch (IllegalStateException illegalStateException){
                    System.out.println(illegalStateException);
                }
                break;
            default:
                throw new IllegalDirectionException(command.getDirection().toString());
        }
        throw new IllegalStateException("commandWithUUID");
    }


    static Boolean commandWithPower(Command command, Miningmaschine miningMaschine, Cloud world) throws IllegalDecisionException {

        int power = command.getPower();

        for(int i=0;i<power;i++){
            System.out.println("----------------");
            System.out.print("Name: ");
            System.out.println(miningMaschine.getName());
            System.out.print("Akutelle Position: ");
            try{
                System.out.println(miningMaschine.getPosition().toString());
            }catch (MiningmaschineNotSpawnedException miningmaschineNotSpawnedException){
                System.out.println(miningmaschineNotSpawnedException);
            }
            System.out.print("direction: ");
            System.out.println(command.getDirection());
            System.out.print("power: ");
            System.out.println(power);
            //Check Border in Direction
            // -> Border yes no
            Decision decision = miningMaschine.canGoForward(command.getDirection(), world);
            System.out.println(decision);
            switch (decision) {
                case MAKEABLE:
                    final boolean result = miningMaschine.drive(command.getDirection(), world);
                    System.out.println("result: "+result);
                    break;
                case NOWAY:
                    break;
                default:
                    throw new IllegalDecisionException(decision.toString());
            }
        }
        world.getMiningmaschineHashMap().put(miningMaschine.getId(), miningMaschine);
        return true;

    }
}
