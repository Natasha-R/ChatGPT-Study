package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
public class MoveSteps {
    MoveType moveType;
    UUID spaceShipDeckId;
    int placeToMove;


    @Setter
    boolean status = true;


    /**
     *
     * @param commandString format [moveTypes, placeToMove] or [moveTypes, spaceShipDeckId]
     */
    public MoveSteps (String commandString){
        try {
            String[] moves = commandString.replace("[", "").replace("]", "").split(",");
            moveType = moveType.valueOf(moves[0]);
            if(moveType.checkMoveType()){
                placeToMove = Integer.parseInt(moves[1]);
            }
            else spaceShipDeckId = UUID.fromString(moves[1]);
            status = true;
            checkIfFieldExists();
        }
        catch(Exception ex){
            status = false;
        }
    }

    public void checkIfFieldExists(){
        for (Map.Entry<UUID, SpaceShipDeck> entry : MapsStorage.getSpaceShipDeckMap().entrySet()){
            if(spaceShipDeckId == entry.getValue().getUuid()){
                status = true;
                break;
            }
        }
        status = false;
    }

}
