package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise0 implements Walkable {

        final int xCoordinateSize = 12;
        final int yCoordinateSize = 9;

        int currentXPosition = 1;
        int currentYPosition = 7;

        Room r;
        Room [][] field = new Room[xCoordinateSize][yCoordinateSize];

        public void createRoom() {
            for (int i = 0; i < xCoordinateSize; i++) {
                for (int j = 0; j < yCoordinateSize; j++) {
                    field[i][j] = new Room(i, j);
                }
            }
        }


    public void determineNeighbor(){

        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field[i].length; j++){

                System.out.printf("%d,%d\n",i,j);
                if(j+1 < field[i].length && field[i][j+1] != null) {
                    field[i][j].setNorth(field[i][j + 1]);
                }
                if(i+1 < field.length && field[i+1][j] != null){
                    field[i][j].setEast(field[i+1][j]);
                }
                if(j-1 >= 0 ){
                    field[i][j].setSouth(field[i][j-1]);
                }

                if(i-1 >= 0 && i-1 < field[i].length && field[i-1][j] != null){
                    field[i][j].setWest(field[i-1][j]);
                }
            }
        }
    }

        public void verticallyWalls(){

            //1.wall
            for(int i = 3; i <= 8; i++ ){
                //from east
                field[2][i].setEast(null);
                // from west
                field[3][i].setWest(null);
            }

            //2.wall
            for(int i = 0; i <= 1; i++){
                //from east
                field[4][i].setEast(null);
                //from west
                field[5][i].setWest(null);
            }

            //3.wall
            for(int i = 0; i <= 3; i++){
                //from east
                field[5][i].setEast(null);
                //from west
                field[7][i].setWest(null);
            }
        }

        public void horizontallyWalls(){
                //4.wall
                //from north
                field[3][2].setNorth(null);
                field[4][2].setNorth(null);

                //from south
                field[4][4].setSouth(null);


        }

    public Exercise0(){
        createRoom();
        determineNeighbor();
        verticallyWalls();
        horizontallyWalls();
        r = field[currentXPosition][currentYPosition];
    }

    //@Override
        public String walkTo(String walkCommandString) {

            String [] coordinates = walkCommandString
                    .substring(1,walkCommandString.length()-1)
                    .split(",");

            String direction = coordinates[0];
            int numberOfSteps = Integer.parseInt(coordinates[1]);


            switch (direction) {
                case "no": r = r.moveNorth(numberOfSteps);
                    break;
                case "so": r = r.moveSouth(numberOfSteps);
                    break;
                case "ea": r = r.moveEast(numberOfSteps);
                    break;
                case "we": r = r.moveWest(numberOfSteps);
                    break;
            }

            String xCoordinate = String.valueOf(r.xPosition);
            String yCoordinate = String.valueOf(r.yPosition);
            return "(" + xCoordinate + "," + yCoordinate + ")";
        }


        public static void main(String[]args){

            Exercise0 r = new Exercise0();

//            System.out.println(r.walkTo("[ea,3]"));
//            System.out.println(r.walkTo("[we,2]"));
//            System.out.println(r.walkTo("[ea,4]"));
//            System.out.println(r.walkTo("[so,5]"));

            //System.out.println(r.walkTo("[so,5]"));
            //System.out.println(r.walkTo("[ea,1]")); // expected position (4,0)

            //HiddenTest1
            System.out.println(r.walkTo("[so,5]"));
            System.out.println(r.walkTo("[ea,4]"));
            System.out.println(r.walkTo("[no,1]"));
            System.out.println(r.walkTo("[we,3]"));


//            System.out.println(r.walkTo("[so,5]"));
//            System.out.println(r.walkTo("[ea,5]"));
//            System.out.println(r.walkTo("[so,4]"));
//            System.out.println(r.walkTo("[no,5]"));



        }
    }

