package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {
    private int x = 0;
    private int y = 2;

    @Override
    public String moveTo(String moveCommandString) {
        char[] searchNum = moveCommandString.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(char i : searchNum){
            if(Character.isDigit(i)){
                stringBuilder.append(i);
            }
        }

        int move = 0;
        if(stringBuilder == null) throw new RuntimeException();
        else move = Integer.parseInt(stringBuilder.toString());

        if(moveCommandString.contains("no")){
            int y1 = y + move;

            if(y1 >= 9) y1 = 8;
            else if((x >= 4 && x <= 6) && y1 >= 5 && y < 5) y1 = 4;

            y = y1;
        }
        else if(moveCommandString.contains("ea")){
            int x1 = x + move;

            if(x1 >= 12) x1 = 11;
            else if(y >= 0 && y <= 2){
                if(x1 >= 3 && x < 3) x1 = 2;
                else if(x1 >= 5 && (x == 3 || x == 4)) x1 = 4;
            }
            else if(y == 3 && x1 >= 5) x1 = 4;
            else if((y >= 5 && y <= 8) && x1 >= 7 && x < 7) x1 = 6;

            x = x1;
        }
        else if(moveCommandString.contains("so")){
            int y2 = y - move;

            if(y2 < 0) y2 = 0;
            else if((x >=4 && x <= 6) && y2 < 5 && y >=5) y2 = 5;

            y = y2;
        }
        else if(moveCommandString.contains("we")){
            int x2 = x - move;

            if(x2 < 0) x2 = 0;
            else if(y >= 0 && y <= 2){
                if(x2 < 5 && x >= 5) x2 = 5;
                else if(x2 < 3 && (x == 3 || x == 4)) x2 = 3;
            }
            else if(y == 3 && x2 < 5) x2 = 5;
            else if((y >= 5 && y <= 8) && x2 < 7 && x >= 7) x2 = 7;

            x = x2;
        }

        return "("+x+","+y+")";
    }
}
