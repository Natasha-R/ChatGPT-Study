package thkoeln.st.st2praktikum.exercise;



import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class MaintanceDroid implements Moveable, Traversable, Encompassable {

    private String name;
    private UUID nowInShipDeck=UUID.randomUUID();
    int posx=0;
    int posy=0;


    MaintanceDroid(String name){
        this.name=name;
    }


    public UUID getNowInShipDeck(){
        return nowInShipDeck;
    }

    public String getCoordinates(){
        return "("+posx+","+posy+")";
    }

    @Override
    public boolean traverse(UUID ShipDeckID, ShipDeck ShipDeck) {
        for (Map.Entry<UUID, Connection> entry : ShipDeck.getConnectionHashMap().entrySet()) {
            Connection value = entry.getValue();
            if (value.getEntranceShipDeckID().equals(nowInShipDeck)&&value.getEntranceCoordinates().equals(getCoordinates())){
                String[] newCoordinates = value.getExitCoordinates().split((Pattern.quote(",")));
                posx=Integer.parseInt(newCoordinates[0].replaceAll("\\D", ""));
                posy=Integer.parseInt(newCoordinates[newCoordinates.length-1].replaceAll("\\D", ""));
                nowInShipDeck=ShipDeckID;
                return true;
            }
        }
        return false;
    }


    @Override
    public void en(UUID ShipDeckID){

        nowInShipDeck=ShipDeckID;
        posx=0;
        posy=0;
    }


    @Override
    public void moveNorth(int moves, ShipDeck ShipDeck){
        int takenSteps=0;
        if (moves>ShipDeck.getGrid()[0].length-1-posy){
            moves=ShipDeck.getGrid()[0].length-1-posy;
        }

        while (takenSteps < moves) {
            if (ShipDeck.getGrid()[posx][posy + takenSteps] == "Wan1" && ShipDeck.getGrid()[posx][posy + takenSteps + 1] == "Wan2"){
                posy = posy + takenSteps;
                return;
            }

            if (ShipDeck.getGrid()[posx][posy + takenSteps] == "Wan2" && ShipDeck.getGrid()[posx][posy + takenSteps + 1] == "Wan1"){
                posy = posy + takenSteps;
                return;
            } else {
                takenSteps += 1;
            }
        }
        posy = posy + takenSteps;
    }


    @Override
    public void moveEast(int moves, ShipDeck ShipDeck){
        int takenSteps=0;


        if (moves>ShipDeck.getGrid().length-1-posx){
            moves=ShipDeck.getGrid().length-1-posx;
        }
        while (takenSteps < moves ) {
            if (ShipDeck.getGrid()[posx + takenSteps ][posy] == "Wan1"&&ShipDeck.getGrid()[posx + takenSteps + 1][posy] == "Wan2"){
                posx=posx+takenSteps;
                return;
            }
            if (ShipDeck.getGrid()[posx + takenSteps ][posy] == "Wan2"&&ShipDeck.getGrid()[posx + takenSteps + 1][posy] == "Wan1"){
                posx=posx+takenSteps;
                return;
            }
            else{
                takenSteps += 1;
            }
        }
        posx = posx + takenSteps;

    }


    @Override
    public void moveSouth(int moves, ShipDeck ShipDeck){

        int takenSteps=0;

        if (posy-moves<0){
            moves=posy;
        }
        while (takenSteps < moves ) {
            if (ShipDeck.getGrid()[posx][posy - takenSteps ] == "Wan1"&&ShipDeck.getGrid()[posx][posy - takenSteps - 1] == "Wan2"){
                posy = posy - takenSteps;
                return;

            }
            if (ShipDeck.getGrid()[posx][posy - takenSteps ] == "Wan2"&&ShipDeck.getGrid()[posx][posy - takenSteps - 1] == "Wan1"){
                posy = posy - takenSteps;
                return;
            }
            else {
                takenSteps += 1;
            }
        }
        posy = posy - takenSteps;
    }


    @Override
    public void moveWest(int moves, ShipDeck ShipDeck){
        int takenSteps = 0;
        if (posx - moves<0){
            moves = posx;
        }

        while (takenSteps < moves ) {
            if (ShipDeck.getGrid()[posx - takenSteps ][posy] == "Wan1"&&ShipDeck.getGrid()[posx - takenSteps - 1][posy] == "Wan2"){
                posx=posx-takenSteps;
                return;
            }
            if (ShipDeck.getGrid()[posx - takenSteps ][posy] == "Wan2"&&ShipDeck.getGrid()[posx - takenSteps - 1][posy] == "Wan1"){
                posx=posx-takenSteps;
                return;
            }
            else{
                takenSteps += 1;
            }
        }
        posx = posx - takenSteps;

    }
}