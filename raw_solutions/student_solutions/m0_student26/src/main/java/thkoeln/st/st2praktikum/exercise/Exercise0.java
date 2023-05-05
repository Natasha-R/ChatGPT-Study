package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    private int x = 5;
    private int y = 3;


    @Override
    public String walk(String walkCommandString) {

        int input_x = 0;
        int input_y = 0;

        //String entwirren

        int i = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length()).replace(']', ' ').trim());
        switch(walkCommandString.substring(1,3)) {
            case "no": input_y = i;break;
            case "ea": input_x = i;break;
            case "so": input_y = i *-1;break;
            case "we": input_x = i *-1;break;
        }


        /*Horizontal Waende
        1-5 / 6
        1-5 / 5
        -----------
        3-8 / 2
        3-8 / 3
        -----------
        min x / 0
        max x / 7
        */

        int temp_x = x + input_x;
        int temp_y = y + input_y;

        if (input_x == 0){
            if(y <= 2 && temp_y >= 3 && temp_x >= 3 && temp_x <= 8){
                temp_y = 2;
            }
            else if(y >= 3 && temp_y <= 2 && temp_x >= 3 && temp_x <= 8){
                temp_y = 3;
            }


            else if(y <= 5 && temp_y >= 6 && temp_x >= 1 && temp_x <= 5){
                temp_y = 5;
            }
            else if(y >= 6 && temp_y <= 5 && temp_x >= 1 && temp_x <= 5){
                temp_y = 6;
            }


            else if (temp_y > 7){
                temp_y = 7;
            }
            else if (temp_y < 0){
                temp_y = 0;
            }
            y = temp_y;
        }


        /*Vertical Waende
        3 / 1-2
        4 / 1-2
        -----------
        5 / 2-7
        6 / 2-7
        -----------
        min 0 / x
        max 11 / x
        */

        else if (input_y == 0){
            if(y <= 2 && temp_y >= 1 && x <= 3 && temp_x >= 4){
                temp_x = 3;
            }
            else if(y <= 2 && temp_y >= 1 && x >= 4 && temp_x <= 3){
                temp_x = 4;
            }



            else if(temp_y <= 7 && temp_y >= 2 && x <= 5 && temp_x >= 6) {
                temp_x = 5;
            }
            else if(temp_y <= 7 && temp_y >= 2 && x >= 6 && temp_x <= 5){
                temp_x = 6;
            }

            else if (temp_x > 11){
                temp_x = 11;
            }
            else if (temp_x < 0){
                temp_x = 0;
            }
            x = temp_x;
        }
        return ("(" +temp_x + "," + temp_y + ")");

    }
}
