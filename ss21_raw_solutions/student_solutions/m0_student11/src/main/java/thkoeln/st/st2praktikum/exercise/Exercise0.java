package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    //check if border is in direction, if not move one step in that direction
    //command is: "[direction, steps]"
    //check if moving out of field, x,y != -1 & x <= 11, y <= 8
    int x = 4;
    int y = 0;
    int [][]borders = {{1,0,3},{0,0,4},{1,1,3},{0,1,4},{1,2,3},{0,2,4},{1,3,3},{0,3,4},{1,4,3},{0,4,4},{1,5,3},{0,5,4},{1,5,1},{1,6,2},{2,5,1},{2,6,2},{3,5,1},{3,6,2},{5,5,1},{5,6,2},{6,6,2},{6,5,1},{6,5,4},{7,5,3},{6,4,4},{7,4,3},{6,3,4},{7,3,3},{6,2,4},{7,2,3},{6,1,4},{7,1,3}};
    @Override
    public String moveTo(String moveCommandString) {
        String inuf = moveCommandString.replace("[","").replace("]","");
        String[] in = inuf.split(",");
        String direction = in[0];
        int steps = Integer.parseInt(in[1]);
        for(int i = 0; i< steps; i++) {
            switch (direction) {
                case "no": {
                    // plus y
                    if(!borderInFront(x,y,"no") && y+1 < 8){
                        y++;
                        break;
                    }
                    else{
                        System.out.println("Border detected");
                        break;
                    }
                }
                case "ea": {
                    // plus x
                    if(!borderInFront(x,y, "ea") && x+1 < 11){
                        x++;
                        break;
                    }
                    else{
                        System.out.println("Border detected");
                        break;
                    }
                }
                case "so": {
                    // minus y
                    if(!borderInFront(x,y, "so") && y-1 >= 0){
                        y--;
                        break;
                    }
                    else{
                        System.out.println("Border detected");
                        break;
                    }
                }
                case "we": {
                    // minus x
                    if(!borderInFront(x,y, "we") && x-1 >= 0){
                        x--;
                        break;
                    }
                    else{
                        System.out.println("Border detected");
                        break;
                    }
                }
                default:
                    System.out.println("unsupported direction");
            }
        }
        //throw new UnsupportedOperationException();
        return "(" + x + "," + y + ")";

    }
    //FALSCH next coordinate, if it crosses a border return true
    //direction heading
    //current coordinate
    //declare inside and outside border
    public boolean borderInFront(int x, int y, String direction){
        //write contains method
        int dir = 0;
        switch(direction) {
            case "no": dir = 1; break;
            case "so": dir = 2; break;
            case "we": dir = 3; break;
            case "ea": dir = 4; break;
        }
        for(int i = 0; i < borders.length; i++){
            if(borders[i][0] == x && borders[i][1] == y && borders[i][2] == dir){
                    return true;
            }
        }
        return false;
    }
}
