package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;


@Getter
@Setter
@AllArgsConstructor

public class MiningMachine {
    private Integer miningPosX;
    private Integer miningPosY;
    private MiningField myMiningField;

    // #################################################################################################################
    // ################################################### Bewegung  ###################################################
    // #################################################################################################################
    public String moveMiningMachine(String command) {
        Pair<Direction,Integer> move = analyse(command);
        Integer newPosition;

        // ################################################### Norden  #################################################
        if (move.getFirst() == Direction.NORTH) {
            // 1) Eine vorläufige Postion wird gesetzt (Entweder Ende des Feldes oder die miningPosY zuzüglich der Bewegung
            newPosition = Math.min(miningPosY + move.getSecond(), myMiningField.getY_size() - 1);

            /*
            2) Alle Wände checken:
            Wenn eine horizontale Wand und Wand im Norden und auf gleicher X-Koordinate wie die Maschine
            und die vorläufige Postion ist weiter weg, dann wird eine neue Position gesetzt
            (Y-Koordinate, da Norden)
            */

            for (Walls walls : myMiningField.getWalls()) {
                if (walls.getFirstY() == walls.getLastY() && walls.getFirstY() > miningPosY && walls.getFirstX() <= miningPosX && walls.getLastX() > miningPosX && walls.getFirstY() <= newPosition)
                    newPosition = walls.getFirstY()-1;
            }
            miningPosY = newPosition;
        }

        // ################################################### Süden  ##################################################
        //gleiche Vorgehensweise wie im Norden
        if (move.getFirst() == Direction.SOUTH) {
            newPosition = Math.max(miningPosY - move.getSecond(), 0);

            for (Walls walls : myMiningField.getWalls()) {
                if (walls.getFirstY() == walls.getLastY() && walls.getFirstY() <= miningPosY && walls.getFirstX() <= miningPosX && walls.getLastX() > miningPosX && walls.getFirstY() > newPosition)
                    newPosition = walls.getFirstY();
            }
            miningPosY = newPosition;
        }

        // ################################################### Westen  ##################################################
        if (move.getFirst() == Direction.WEST) {

            newPosition = Math.max(miningPosX - move.getSecond(), 0);

            /*
            2) Alle Wände checken:
            Wenn eine vertikale Wand und Wand im Westen und auf gleicher Y-Koordinate wie die Maschine
            und die vorläufige Postion ist weiter weg, dann wird eine neue Position gesetzt
            (X-Koordinate, da Westen)
            */

            for (Walls walls : myMiningField.getWalls()) {
                if (walls.getFirstX() == walls.getLastX() && walls.getFirstX() <= miningPosX && walls.getFirstY() <= miningPosY && walls.getLastY() > miningPosY && walls.getFirstX() > newPosition)
                    newPosition = walls.getFirstX();
            }
            miningPosX = newPosition;
        }

        // ################################################### Osten  ##################################################
        //gleiche Vorgehensweise wie im Westen
        if (move.getFirst() == Direction.EAST) {
            newPosition = Math.min(miningPosX + move.getSecond(), myMiningField.getX_size() - 1);


            for (Walls walls : myMiningField.getWalls()) {
                if (walls.getFirstX() == walls.getLastX() && walls.getFirstX() > miningPosX && walls.getFirstY() <= miningPosY && walls.getLastY() > miningPosY && walls.getFirstX() <= newPosition)
                    newPosition = walls.getFirstX()-1;
            }
            miningPosX = newPosition;
        }

        System.out.println("Position: ("+miningPosX+","+miningPosY+")");
        return Position(miningPosX,miningPosY);
    }



    // ################################################## Analyse ######################################################
    static public Pair<Direction,Integer> analyse(String command){
        String direction = command.substring(command.indexOf("[")+1,command.indexOf(","));
        Integer steps = Integer.parseInt(command.substring(command.indexOf(",")+1,command.indexOf("]")));

        if (direction.equals("no")) return Pair.of(Direction.NORTH,steps);
        if (direction.equals("so")) return Pair.of(Direction.SOUTH,steps);
        if (direction.equals("ea")) return Pair.of(Direction.EAST,steps);
        if (direction.equals("we")) return Pair.of(Direction.WEST,steps);

        throw new IllegalArgumentException("Illegal command: "+command);
    }

    static public String Position(Integer posX, Integer posY){
        return "(" + posX + ","+ posY + ")";
    }


}
