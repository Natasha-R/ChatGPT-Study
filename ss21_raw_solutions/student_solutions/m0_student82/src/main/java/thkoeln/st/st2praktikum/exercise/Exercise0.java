package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {


    int px = 5;
    int py = 3;
    String[] mO = {"(1,6)","(2,6)","(3,6)","(4,6)","(5,6)","(3,3)","(4,3)","(5,3)","(6,3)","(7,3)","(8,3)"};
    String[] mU = {"(1,5)","(2,5)","(3,5)","(4,5)","(5,5)","(3,2)","(4,2)","(5,2)","(6,2)","(7,2)","(8,2)"};
    String[] mL = {"(5,7)","(5,6)","(5,5)","(5,4)","(5,3)","(5,2)","(3,2)","(3,1)"};
    String[] mR = {"(6,7)","(6,6)","(6,5)","(6,4)","(6,3)","(6,2)","(4,2)","(4,1)"};



    public String getDirection(String moveCommandString) {
        return  moveCommandString.substring(1,3);

    }

    public int getPower(String moveCommandString) {
        int i = moveCommandString.length();
        return  Integer.parseInt(moveCommandString.substring(4,i-1));

    }

    @Override
    public String move(String moveCommandString) {
        System.out.println(moveCommandString);

        int power = getPower(moveCommandString);
        String direction = getDirection(moveCommandString);

        for(int i = 0; i<power; i++){
            if(py == 0 && direction.equals("so")) {direction = "";}
            if(py == 7 && direction.equals("no")) {direction = "";}
            if(px == 0 && direction.equals("we")) {direction = "";}
            if(px == 11 && direction.equals("ea")) {direction = "";}

            for (String s : mU) {
                if (direction.equals("so") && s.equals("("+px+","+(py-1)+")")){
                    direction = "";
                    break;
                }
            }


            for (String s : mO) {
                if (direction.equals("no") && s.equals("("+px+","+(py+1)+")")){
                    direction = "";
                    break;
                }
            }


            for (String s : mL) {
                if (direction.equals("we") && s.equals("("+(px-1)+","+py+")")) {
                    direction = "";
                    break;
                }
            }

            for (String s : mR) {
                if (direction.equals("ea") && s.equals("("+(px+1)+","+py+")")){
                    direction = "";
                    break;
                }
            }


            switch(direction){
                case "no":
                    py = py +1;
                    break;
                case "ea":
                    px = px +1;
                    break;
                case "so":
                    py = py -1;
                    break;
                case "we":
                    px = px -1;
                    break;
                default:

                    break;
            }
        }


        System.out.println(px);
        System.out.println(py);
        return "("+px+","+py+")";
    }

    
}
