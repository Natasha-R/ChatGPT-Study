package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class Exercise0 implements Walkable {

    private  Wall [] wallsvertical = {new Wall(5, 6, 2), new Wall(5, 6, 3), new Wall(5, 6, 4), new Wall(5, 6, 5), new Wall(5, 6, 6), new Wall(5, 6, 7), new Wall(3, 4, 1), new Wall(3, 4, 2)};// Hier werden die Positionen der senkrechten Mauern gespeichert

    private  Wall []wallshorizontal = {new Wall(2,3,3), new Wall(2,3,4), new Wall(2,3,5), new Wall(2,3,6), new Wall(2,3,7), new Wall(2,3,8), new Wall(5,6,1), new Wall(5,6,2), new Wall(5,6,3), new Wall(5,6,4), new Wall(5,6,5) }; // Hier werden die Positionen der waagerechten Mauern gespeichert
    private int x_koordinate = 5;
    private int y_koordinate = 3;
    private int limit = 0;
    @Override
    public String walkTo(String walkCommandString) {
          // So wird die Mauer hinzugefügt und zum Vergleich gespeichert
        String pointOfTheCompas = "" + walkCommandString.charAt(1) + walkCommandString.charAt(2);
        String [] devidedString = walkCommandString.split(",");
        String []numberOfStepsAndSquareBracket  = devidedString[1].split("]");
        int numberOfSteps = Integer.parseInt(numberOfStepsAndSquareBracket [0]);
        System.out.println(numberOfSteps);
        boolean isWallhere = false;
        String actuallyPosition = "";//aktuelle Position der Turtle

        switch(pointOfTheCompas){
            case "no":{
                limit = y_koordinate + numberOfSteps;
                int y = 0;
                for( y = y_koordinate; y <= limit; y++){
                    for(Wall wall : wallshorizontal){
                        if(y == wall.befor && x_koordinate == wall.posWall){
                            isWallhere = true;
                            break;
                        }
                    }
                    actuallyPosition = "("+ x_koordinate +","+ y + ")";
                    y_koordinate = y;
                    if(isWallhere || y >= 7){
                        isWallhere = false;
                        break; //Kommt eine Mauer in die Quere oder bin ich am obersten Rand? Dann bleibe ich stehen
                    }
                }

            }
            break;
            case "we":{
                limit = (x_koordinate - numberOfSteps);
                int x = 0;
                for(x = x_koordinate; x >= limit; x--) {
                    for (Wall wall : wallsvertical) {
                        if (x == wall.behind && y_koordinate == wall.posWall) {
                            isWallhere = true;
                            break;
                        }
                    }
                    actuallyPosition = "(" + x + "," + y_koordinate + ")";
                    x_koordinate = x;
                    if (isWallhere || x <= 0) {
                        isWallhere = false;
                        break;
                    }
                }

            }
            break;
            case "so":{
                limit = (y_koordinate - numberOfSteps);
                int y = 0;
                for( y = y_koordinate; y >= limit; y--){
                    for(Wall wall : wallshorizontal){
                        if(y == wall.behind && x_koordinate == wall.posWall){
                            isWallhere = true;
                            break;
                        }
                    }
                    actuallyPosition = "("+ x_koordinate +","+ y + ")";
                    y_koordinate = y;
                    if(isWallhere || y <= 0){
                        isWallhere = false;
                        break;
                    }
                }
            }
            break;
            case "ea":{
                limit = (x_koordinate + numberOfSteps);
                int x = 0;
                for( x = x_koordinate; x <= limit; x++){
                    for(Wall wall : wallsvertical){
                        if(x == wall.befor && y_koordinate == wall.posWall){
                            isWallhere = true;
                            break;
                        }
                    }
                    actuallyPosition = "("+ x +","+ y_koordinate + ")";
                    x_koordinate = x;
                    if(isWallhere || x >= 11){
                        isWallhere = false;
                        break; //Kommt eine Mauer in die Quere oder bin ich am obersten Rand? Dann bleibe ich stehen
                    }
                }

            }
            break;
            default:
                throw new IllegalStateException("Bitte geben sie eine Himmelsrichtung an" + pointOfTheCompas);
        }


        return actuallyPosition ;
    }

}
