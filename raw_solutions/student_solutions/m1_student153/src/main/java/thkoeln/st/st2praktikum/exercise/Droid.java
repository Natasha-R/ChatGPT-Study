package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class Droid {

    String name;
    UUID droidID;
    int coordX=0;
    int coordY=0;
    UUID coordZ = new UUID(0,0);

    public Droid (String name, UUID uuid){
        this.name=name;
        this.droidID=uuid;

    }

    public void spawnDroid(UUID mapID, Square [][] field){

        coordZ=mapID;
        field [0][0].droid=true;
    }

    public void moveDroid(String direction, String stepString,SpaceShipDeck deck){

        int steps= Integer.parseInt(stepString);

        switch (direction){
            case "no":
                for(int step=0;step<steps;step++){
                   if(deck.field[coordY][coordX].wallDown== true || deck.field[coordY+1][coordX].droid == true ){
                       break;
                   }
                    deck.field[coordY][coordX].droid = false;
                   coordY = coordY +1;
                    deck.field[coordY][coordX].droid = true;
                }
                break;

            case "ea":
                for(int step=0;step<steps;step++){
                    if(deck.field[coordY][coordX].wallRight== true || deck.field[coordY][coordX+1].droid == true ){
                        break;
                    }
                    deck.field[coordY][coordX].droid = false;

                    coordX = coordX + 1;
                    deck.field[coordY][coordX].droid = true;
                }
                break;

            case "so":
                for(int step=0;step<steps;step++){
                    if(deck.field[coordY][coordX].wallUp == true || deck.field[coordY-1][coordX].droid == true ){
                        break;
                    }
                    deck.field[coordY][coordX].droid = false;
                    coordY = coordY - 1;
                    deck.field[coordY][coordX].droid = true;
                }
                break;

            case "we":
                for(int step=0;step<steps;step++){
                    if(deck.field[coordY][coordX].wallLeft == true || deck.field[coordY][coordX-1].droid == true ){
                        break;
                    }
                    deck.field[coordY][coordX].droid = false;
                    coordX = coordX- 1;
                    deck.field[coordY][coordX].droid = true;
                }
                break;
        }
    }

}
