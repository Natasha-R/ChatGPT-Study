package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    int startY = 7;
    int startX = 11;
    @Override
    public String goTo(String goCommandString) {
        // throw new UnsupportedOperationException();

        String direction = goCommandString.substring(1,3); // substring the incoming value for direction
        int amount = Integer.parseInt(goCommandString.substring(4,5)); // substring incoming value for moving amount

        // define raster with bounds
        int  [][] lab = new int [8][12]; // define array size
        // define the blocked directions per field
        int  [] WEarray = {1, 5, 8, 9, 11, 12};
        int  [] NOarray = {2, 5, 6, 9, 10, 12};
        int  [] EAarray = {3, 6, 7, 9, 10, 11};
        int  [] SOarray = {4, 7, 8, 10, 11, 12};
        // fill entire array with 0
        for(int y = 0; y <8; y++) {
            for(int x = 0; x < 12; x++){
                lab[y][x] = 0;
            }
        }
        // fill outer barrier
        for(int x = 1; x <11; x++) {
            lab[0][x] = 4;
        }
        for(int x = 1; x <11; x++) {
            lab[7][x] = 2;
        }
        for(int y = 1; y <7; y++) {
            lab[y][0] = 1;
        }
        for(int y = 1; y <7; y++) {
            lab[y][11] = 3;
        }
        // fill outer corner barrier
        lab[0][0] = 8;
        lab[0][11] = 7;
        lab[7][0] = 5;
        lab[7][11] = 6;

        // define specific barriers inside the raster
        for(int x = 5; x <11; x++) {
            lab[6][x] = 4;
        }
        for(int x = 6; x <11; x++) {
            lab[5][x] = 2;
        }
        for(int y = 2; y <5; y++) {
            lab[y][6] = 1;
        }
        for(int y = 2; y <4; y++) {
            lab[y][5] = 3;
        }
        lab[5][5] = 12;
        lab[4][5] = 6;
        lab[5][4] = 3;
        lab[6][11] = 7;
        lab[5][11] = 6;

        // switch case to move depending on direction
        // first checking current field value to decide if movement in direction is possible.
        // moving one field in direction and checking for next movement
        // movement aborts on hitting any barrier, stays in current position and continues with direction change for another movement test
        switch (direction) {

            case "we" : outerloop:
                        for (int m = 0; m < amount; m++) { // itterate in single steps for every move
                            for (int i : WEarray) { // check for every step if movement in direction is possible
                                if (lab[startY][startX] == i) { // if movement is not possible in desired direction, abort action
                                    break outerloop; // abort outer loop
                                }
                            }
                            startX = startX - 1; // if movement is possible, continue by one value (in this case negative x) and repeat loop
                        }
                        break;

            case "no" : outerloop:
                        for (int m = 0; m < amount; m++) { // see documentation for "we" movement
                            for (int i : NOarray) {
                                if (lab[startY][startX] == i) {
                                    System.out.println();
                                    break outerloop;
                                }
                            }
                            startY = startY + 1;
                        }
                            break;

            case "ea" : outerloop:
                        for (int m = 0; m < amount; m++) { // see documentation for "we" movement
                            for (int i : EAarray) {
                                if (lab[startY][startX] == i) {
                                    break outerloop;
                                }
                            }
                            startX = startX + 1;
                        }
                            break;

            case "so" : outerloop:
                        for (int m = 0; m < amount; m++) { // see documentation for "we" movement
                            for (int i : SOarray) {
                                if (lab[startY][startX] == i) {
                                    break outerloop;
                                }
                            }
                            startY = startY - 1;
                        }
                            break;
        }

        //test output of outta bounds
/*
        for(int y = 7; y>=0; y--) {
            for(int x = 0; x < 12; x++){
                System.out.print(lab[y][x] + "  ");
            }
            System.out.println();
        }
        System.out.println();

 */

        return "(" + startX + ","  + startY + ")";
    }
}
