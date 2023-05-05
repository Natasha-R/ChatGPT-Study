package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    int x_position = 1;
    int y_position = 7;
    int[][] map = new int[12][9];

    public Exercise0(){
        map[3][8] = 1;
        map[3][7] = 1;
        map[3][6] = 1;
        map[3][5] = 1;
        map[3][4] = 1;
        map[3][3] = 3;
        map[4][3] = 2;
        map[5][0] = 1;
        map[5][1] = 1;
        map[6][0] = 1;
        map[6][1] = 1;
        map[6][2] = 1;
        map[6][3] = 1;
    }
    @Override
    public String goTo(String goCommandString) {
        int steps = 0;
        int new_y = y_position;
        int new_x = x_position;
        if(goCommandString.length()==6)
            steps = Character.getNumericValue(goCommandString.charAt(4));
        else if(goCommandString.length()==7)
            steps = Character.getNumericValue(goCommandString.charAt(4)) * 10 + Character.getNumericValue(goCommandString.charAt(5));
        else
            throw new IllegalArgumentException("Wrong command.");

        if(goCommandString.charAt(1)=='s'){
            new_y = y_position-steps;
            for(int i = 0; i <= steps;i++){
                if(y_position-i < 0){
                    new_y = 0;
                    break;
                }
                else if(map[x_position][y_position-i] == 2 || map[x_position][y_position-i] == 3) {
                    new_y = y_position - i;
                    break;
                }
            }
        }
        else if(goCommandString.charAt(1) == 'n'){
            new_y = y_position+steps;
            for(int i = 0; i <= steps;i++){

                if(y_position+i > 8){
                    new_y = 8;
                    break;
                }
                else if(map[x_position][y_position+i] == 2 || map[x_position][y_position+i] == 3) {
                    new_y = y_position + i - 1;
                    break;
                }
            }
        }
        else if(goCommandString.charAt(1)=='e'){
            new_x = x_position+steps;
            for(int i = 0; i <= steps;i++){
                if(x_position+i > 11){
                    new_x = 11;
                    break;
                }
                else if(map[x_position+i][y_position] == 1 || map[x_position+i][y_position] == 3) {
                    new_x = x_position + i - 1;
                    break;
                }

            }
        }
        else if(goCommandString.charAt(1) == 'w'){
            new_x = x_position-steps;
            for(int i = 0; i <= steps;i++){
                if(x_position-i < 0){
                    new_x = 0;
                    break;
                }
                else if(map[x_position-i][y_position] == 1 || map[x_position-i][y_position] == 3) {
                    new_x = x_position - i;
                    break;
                }
            }
        }
        else
            throw new IllegalArgumentException("Wrong direction.");

        x_position = new_x;
        y_position = new_y;
        return "("+x_position+","+y_position+")";
    }
}

