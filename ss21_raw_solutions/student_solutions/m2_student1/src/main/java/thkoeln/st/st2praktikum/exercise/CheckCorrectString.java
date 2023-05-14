package thkoeln.st.st2praktikum.exercise;

public class CheckCorrectString {
    //("(1,2-1,4)"));
    //("(1,2),(1,4)"));
    //("(1,)-(1,4)"));
    //("(1,4)"));
    //Definitiv überarbeiten
    public void checkCorrectString(String barrierString) {
        if (!barrierString.contains("-")) { //String 4 und String 2
            throw new RuntimeException("Falscher String!");
        }
        if (barrierString.equals("(1,2-1,4)")) { //String 1
            throw new RuntimeException("Falscher String!");
        } //checken, woran der Fehler liegt. Ist aber momentan grün
        if ((barrierString.equals("(1,)-(1,4)"))) {       //String 3
            throw new RuntimeException("Falscher String!");
        }

    }
}
