package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    int startX = 1;
    int startY = 7;

    @Override
    public String moveTo(String moveCommandString) {

        int steps = moveCommandString.charAt(4) - '0';
        String richtung = moveCommandString.substring(1,3);
        int marker = 0;

        switch(richtung){
            case "no":
                if (startY <= 2)
                    marker = 1;
                startY = startY + steps;
                if (marker == 1 && startX == 3 && startY > 2)
                    startY = 2;
                if (marker == 1 && startX == 4 && startY > 2)
                    startY = 2;
                break;
            case "ea":
                if (startX <= 2 && startY >= 3) {
                    startX = startX + steps;
                    if (startX >= 3)
                        startX = 2;
                    break;
                }
                if (startX <= 4 && startY <= 1) {
                    startX = startX + steps;
                    if (startX >= 5)
                        startX = 4;
                    break;
                }
                if (startX <= 5 && startY <= 3) {
                    startX = startX + steps;
                    if (startX >= 6)
                        startX = 5;
                    break;
                }
                startX = startX + steps;
                break;
            case "so":
                if (startY >= 3)
                    marker = 1;
                startY = startY - steps;
                if (marker == 1 && startX == 3 && startY < 3)
                    startY = 3;
                if (marker == 1 && startX == 4 && startY < 3)
                    startY = 3;
                if (startY < 0)
                    startY = 0;
                break;
            case "we":
                if (startX >= 6 && startY <= 3) {
                    startX = startX - steps;
                    if (startX <= 5)
                        startX = 6;
                    break;
                }
                if (startX >= 5 && startY <= 1) {
                    startX = startX - steps;
                    if (startX <= 4)
                        startX = 5;
                    break;
                }
                if (startX >= 3 && startY >= 3) {
                    startX = startX - steps;
                    if (startX <= 2)
                        startX = 3;
                    break;
                }
                startX = startX - steps;
                break;
        }
        return "("+startX+","+startY+")";
    }
}
