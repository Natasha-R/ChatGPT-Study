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

    public void moveDroid(Order order,SpaceShipDeck deck){

        int steps=order.getNumberOfSteps();
        OrderType direction=order.getOrderType();




        switch (direction){
            case NORTH:
                for(int step=0;step<steps;step++){
                    if(deck.field[coordY][coordX].wallDown || deck.field[coordY+1][coordX].droid == true ){
                        break;
                    }
                    deck.field[coordY][coordX].droid = false;
                    coordY = coordY +1;
                    deck.field[coordY][coordX].droid = true;
                }
                break;

            case EAST:
                for(int step=0;step<steps;step++){
                    if(deck.field[coordY][coordX].wallRight || deck.field[coordY][coordX + 1].droid){
                        break;
                    }
                    deck.field[coordY][coordX].droid = false;

                    coordX = coordX + 1;
                    deck.field[coordY][coordX].droid = true;
                }
                break;

            case SOUTH:
                for(int step=0;step<steps;step++){
                    if(deck.field[coordY][coordX].wallUp || deck.field[coordY - 1][coordX].droid){
                        break;
                    }
                    deck.field[coordY][coordX].droid = false;
                    coordY = coordY - 1;
                    deck.field[coordY][coordX].droid = true;
                }
                break;

            case WEST:
                for(int step=0;step<steps;step++){
                    if(deck.field[coordY][coordX].wallLeft || deck.field[coordY][coordX - 1].droid){
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

