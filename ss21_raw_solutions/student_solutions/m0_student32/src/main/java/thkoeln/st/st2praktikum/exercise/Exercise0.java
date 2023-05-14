package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements GoAble {
    private int xPos = 3;
    private int yPos = 0;
    final private Integer[][] hBorders = {{0,0,11},{3,4,7},{4,1,8},{7,0,11}};
    final private Integer[][] vBorders = {{0,0,7},{3,0,3},{7,0,2},{11,0,7}};




    @Override
    public String go(String goCommandString) {
        String[] commands = goCommandString.substring(1, goCommandString.length()-1).split(",");
        String direction = commands[0];
        int steps = Integer.parseInt(commands[1]);

        switch (direction){
            case "no":
                for (int i = 0; i < steps; i++) {
                    if(!moveNorth()) break;
                    System.out.println("("+xPos+","+yPos+")⬆");
                }
                break;
            case "ea":
                for (int i = 0; i < steps; i++) {
                    if(!moveEast()) break;
                    System.out.println("("+xPos+","+yPos+")➡");
                }
                break;
            case "so":
                for (int i = 0; i < steps; i++) {
                    if(!moveSouth()) break;
                    System.out.println("("+xPos+","+yPos+")⬇");
                }
                break;
            case "we":
                for (int i = 0; i < steps; i++) {
                    if(!moveWest()) break;
                    System.out.println("("+xPos+","+yPos+")⬅");
                }
                break;
            default: return null;
        }

        return "("+xPos+","+yPos+")";
    }

    private boolean moveNorth(){
        for (Integer[] hBorder : hBorders) {
            if (yPos + 1 == hBorder[0]) {
                if (hBorder[1] <= xPos && hBorder[2] >= xPos) {
                    return false;
                }
            }
        }
        yPos++;
        return true;
    }

    private boolean moveSouth(){
        for (Integer[] hBorder : hBorders) {
            if (yPos == hBorder[0]) {
                if (hBorder[1] <= xPos && hBorder[2] >= xPos + 1) {
                    return false;
                }
            }
        }
        yPos--;
        return true;
    }

    private boolean moveEast(){
        for (Integer[] vBorder : vBorders) {
            if (xPos + 1 == vBorder[0]) {
                if (vBorder[1] <= yPos && vBorder[2] >= yPos + 1) {
                    return false;
                }
            }
        }
        xPos++;
        return true;
    }

    private boolean moveWest(){
        for (Integer[] vBorder : vBorders) {
            if (xPos == vBorder[0]) {
                if (vBorder[1] <= yPos && vBorder[2] >= yPos + 1) {
                    return false;
                }
            }
        }
        xPos--;
        return true;
    }
}
