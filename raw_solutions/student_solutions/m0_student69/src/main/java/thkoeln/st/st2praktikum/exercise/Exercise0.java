package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Exercise0 implements Moveable {

    List<String> tmp = new ArrayList<>();

    int x = 0;
    int y = 2;

    @Override
    public String move(String moveCommandString) {
        tmp.clear();
        String[] random = moveCommandString.split(",");

        String richtung = random[0].replace("[", "");
        int anzahl = Integer.parseInt(random[1].replace("]", ""));

        List<String> HORIZONTAL = new ArrayList<>(Arrays.asList("(3,0)", "(3,1)", "(3,2)", "(5,0)", "(5,1)", "(5,2)", "(5,3)",
                "(7,5)", "(7,6)", "(7,7)", "(7,8)"));
        List<String> VERTIKAL = new ArrayList<>(Arrays.asList("(4,5)", "(5,5)", "(6,5)"));

        switch (richtung) {
            case "no":
                norden(VERTIKAL, anzahl);
                break;
            case "so":
                süden(VERTIKAL, anzahl);
                break;
            case "ea":
                osten(HORIZONTAL, anzahl);
                break;
            case "we":
                westen(HORIZONTAL, anzahl);
                break;
        }
        System.out.println(moveCommandString);
        System.out.println("(" + x + "," + y + ")");
        return "(" + x + "," + y + ")";
    }

    private void westen(List<String> HORIZONTAL, int anzahl) {
        /*
        Potentielle Wände raussuchen
         */
        for (int i = 0; i < HORIZONTAL.size(); i++) {
            String temporaer = HORIZONTAL.get(i); // -> (0,3)
            String[] abc = temporaer.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[1].replace(")", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"

            if (Integer.parseInt(def) == y && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[0].replace("(", "")) <= x && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (x - anzahl) < Integer.parseInt(abc[0].replace("(", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp.add(temporaer); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            x = x - anzahl;
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp.get(0).split(",")[0].replace("(", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp.size(); i++) {
                if (max < Integer.parseInt(tmp.get(i).split(",")[0].replace("(", ""))) {
                    max = Integer.parseInt(tmp.get(i).split(",")[0].replace("(", ""));
                }
            }
            x = max; // x = Der Koordinate der Wand setzen (Da man da stoppt)
        }
        if (x < 0) {
            x = 0;
        }
    }

    private void osten(List<String> HORIZONTAL, int anzahl) {
        /*
        Potentielle Wände raussuchen
         */
        for (int i = 0; i < HORIZONTAL.size(); i++) {
            String temporaer = HORIZONTAL.get(i); // -> (0,3)
            String[] abc = temporaer.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[1].replace(")", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"

            if (Integer.parseInt(def) == y && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[0].replace("(", "")) > x && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (x + anzahl) >= Integer.parseInt(abc[0].replace("(", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp.add(temporaer); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            x = x + anzahl;
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp.get(0).split(",")[0].replace("(", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp.size(); i++) {
                if (max > Integer.parseInt(tmp.get(i).split(",")[0].replace("(", ""))) {
                    max = Integer.parseInt(tmp.get(i).split(",")[0].replace("(", ""));
                }
            }
            x = max - 1; // x = Der Koordinate der Wand setzen (Da man da stoppt)
        }
        if (x > 12 - 1) {
            x = 12 - 1;
        }
    }


    private void süden(List<String> VERTIKAL, int anzahl) {
        for (int i = 0; i < VERTIKAL.size(); i++) {
            String temporaer = VERTIKAL.get(i); // -> (0,3)
            String[] abc = temporaer.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[0].replace("(", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"

            if (Integer.parseInt(def) == x && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[1].replace(")", "")) <= y && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (y - anzahl) < Integer.parseInt(abc[1].replace(")", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp.add(temporaer); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            y = y - anzahl;
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp.get(1).split(",")[1].replace(")", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp.size(); i++) {
                if (max > Integer.parseInt(tmp.get(i).split(",")[1].replace(")", ""))) {
                    max = Integer.parseInt(tmp.get(i).split(",")[1].replace(")", ""));
                }
            }
            y = max; // x = Der Koordinate der Wand setzen (Da man da stoppt)
        }
        if (y < 0) {
            y = 0;
        }
        System.out.println(y);

    }

    private void norden(List<String> VERTIKAL, int anzahl) {


        for (int i = 0; i < VERTIKAL.size(); i++) {
            String temporaer = VERTIKAL.get(i); // -> (0,3)
            String[] abc = temporaer.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[0].replace("(", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"

            if (Integer.parseInt(def) == x && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[1].replace(")", "")) > y && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (y + anzahl) >= Integer.parseInt(abc[1].replace(")", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp.add(temporaer); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            y = y + anzahl;
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp.get(1).split(",")[1].replace(")", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp.size(); i++) {
                if (max > Integer.parseInt(tmp.get(i).split(",")[1].replace(")", ""))) {
                    max = Integer.parseInt(tmp.get(i).split(",")[1].replace(")", ""));
                }
            }
            y = max - 1; // x = Der Koordinate der Wand setzen (Da man da stoppt)
        }
        if (y > 9 - 1) {
            y = 9 - 1;
        }

    }
}
