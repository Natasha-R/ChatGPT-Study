package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class MachineDirections {

    private Map<UUID, MiningMachine> MachineList = new HashMap<>();

    public void west(List<Barrier> HORIZONTAL, int anzahl, MiningMachine machine) {
            /*
            Potentielle Wände raussuchen
             */
        List<String> tmp = new ArrayList<>();
        for (Barrier barrier : HORIZONTAL) {
            for (int i = barrier.getStart().getY(); i < barrier.getEnd().getY(); i++) {
                tmp.add("(" + barrier.getStart().getX() + "," + i + ")");
            }
        }
        List<String> tmp2 = new ArrayList<>();
        for (String s : tmp) {
            String[] abc = s.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[1].replace(")", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"

            if (Integer.parseInt(def) == machine.getCoordinate().getY() && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[0].replace("(", "")) > machine.getCoordinate().getX() && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (machine.getCoordinate().getX() - anzahl) < Integer.parseInt(abc[0].replace("(", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp2.add(s);
            }
        }
        if (tmp2.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            if(machine.getCoordinate().getX() - anzahl >= 0)
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(machine.getCoordinate().getX() - anzahl, machine.getCoordinate().getY()), machine.getField());
        else
                machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(0, machine.getCoordinate().getY()), machine.getField());
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp2.get(0).split(",")[0].replace("(", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp2.size(); i++) {
                if (max < Integer.parseInt(tmp2.get(i).split(",")[0].replace("(", ""))) {
                    max = Integer.parseInt(tmp2.get(i).split(",")[0].replace("(", ""));
                }
            }
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(max, machine.getCoordinate().getY()), machine.getField());

        }
        if (machine.getCoordinate().getX() < 0) {
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(0, machine.getCoordinate().getY()), machine.getField());
        }
        MachineList.put(machine.getMiningMachine_ID(), machine);
    }

    public void east(List<Barrier> HORIZONTAL, int anzahl, MiningMachine machine) {
        /*
        Potentielle Wände raussuchen
         */
        List<String> tmp = new ArrayList<>();
        for (Barrier barrier : HORIZONTAL) {
            for (int i = barrier.getStart().getY(); i < barrier.getEnd().getY(); i++) {
                tmp.add("(" + barrier.getStart().getX() + "," + i + ")");
            }
        }

        List<String> tmp2 = new ArrayList<>();

        for (String s : tmp) {
            String[] abc = s.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[1].replace(")", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"

            if (Integer.parseInt(def) == machine.getCoordinate().getY() && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[0].replace("(", "")) > machine.getCoordinate().getX() && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (machine.getCoordinate().getX() + anzahl) > Integer.parseInt(abc[0].replace("(", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp2.add(s); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp2.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(machine.getCoordinate().getX() + anzahl, machine.getCoordinate().getY()), machine.getField());
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp2.get(0).split(",")[0].replace("(", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp2.size(); i++) {
                if (max > Integer.parseInt(tmp2.get(i).split(",")[0].replace("(", ""))) {
                    max = Integer.parseInt(tmp2.get(i).split(",")[0].replace("(", ""));
                }
            }
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(max - 1, machine.getCoordinate().getY()), machine.getField());
        }
        if (machine.getCoordinate().getX() > machine.getField().getWidth() - 1) {
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(machine.getField().getWidth() - 1, machine.getCoordinate().getY()), machine.getField());
        }
        MachineList.put(machine.getMiningMachine_ID(), machine);
    }


    public void south(List<Barrier> VERTIKAL, int anzahl, MiningMachine machine) {

        List<String> tmp = new ArrayList<>();
        for (Barrier barrier : VERTIKAL) {
            for (int i = barrier.getStart().getX(); i < barrier.getEnd().getX(); i++) {
                tmp.add("(" + i + "," + barrier.getStart().getY() + ")");
            }
        }

        List<String> tmp2 = new ArrayList<>();

        for (String s : tmp) {
            String[] abc = s.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[0].replace("(", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"

            if (Integer.parseInt(def) == machine.getCoordinate().getX() && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[1].replace(")", "")) < machine.getCoordinate().getY() && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (machine.getCoordinate().getY() - anzahl) < Integer.parseInt(abc[1].replace(")", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp2.add(s); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp2.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            if(machine.getCoordinate().getY() - anzahl >= 0)
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(machine.getCoordinate().getX(), machine.getCoordinate().getY() - anzahl), machine.getField());
        else
                machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(machine.getCoordinate().getX(), 0), machine.getField());
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp2.get(0).split(",")[1].replace(")", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp2.size(); i++) {
                if (max > Integer.parseInt(tmp2.get(i).split(",")[1].replace(")", ""))) {
                    max = Integer.parseInt(tmp2.get(i).split(",")[1].replace(")", ""));
                }
            }
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(machine.getCoordinate().getX(), max), machine.getField());

        }
        if (machine.getCoordinate().getY() < 0) {
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(machine.getCoordinate().getX(), 0), machine.getField());
        }

        MachineList.put(machine.getMiningMachine_ID(), machine);
    }

    public void north(List<Barrier> VERTIKAL, int anzahl, MiningMachine machine) {
        List<String> tmp = new ArrayList<>();
        for (Barrier barrier : VERTIKAL) {
            for (int i = barrier.getStart().getX(); i < barrier.getEnd().getX(); i++) {
                tmp.add("(" + i + "," + barrier.getStart().getY() + ")");
            }
        }

        List<String> tmp2 = new ArrayList<>();

        for (String s : tmp) {
            String[] abc = s.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[0].replace("(", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"
            if (Integer.parseInt(def) == machine.getCoordinate().getX() && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[1].replace(")", "")) > machine.getCoordinate().getY() && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (machine.getCoordinate().getY() + anzahl) > Integer.parseInt(abc[1].replace(")", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp2.add(s); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp2.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(machine.getCoordinate().getX(), machine.getCoordinate().getY() + anzahl), machine.getField());
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp2.get(0).split(",")[1].replace(")", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp2.size(); i++) {
                if (max > Integer.parseInt(tmp2.get(i).split(",")[1].replace(")", ""))) {
                    max = Integer.parseInt(tmp2.get(i).split(",")[1].replace(")", ""));
                }
            }
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(machine.getCoordinate().getX(), max - 1), machine.getField());
        }
        if (machine.getCoordinate().getY() > machine.getField().getHeight() - 1) {
            machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(machine.getCoordinate().getX(), machine.getField().getHeight() - 1), machine.getField());
        }
        MachineList.put(machine.getMiningMachine_ID(), machine);
    }

    public Map<UUID, MiningMachine> getMachineList() {
        return MachineList;
    }

    public Boolean transfer(MiningMachine machine, UUID destinationFieldId, Field field, Map<UUID, Field> fieldList) {
        for (Connection connection : field.getCONNECTION()) {
            if (machine.getCoordinate().getX().equals(connection.getSourceCoordinate().getX()) && machine.getCoordinate().getY().equals(connection.getSourceCoordinate().getY()) && connection.getDestinationFieldId().equals(destinationFieldId)) {

                machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(connection.getDestCoordinate().getX(), connection.getDestCoordinate().getY()), fieldList.get(connection.getDestinationFieldId()));
                MachineList.put(machine.getMiningMachine_ID(), machine);
                return true;
            }
        }
        return false;
    }

    public Boolean entrance(MiningMachine machine, UUID destinationFieldId, Map<UUID, Field> fieldList) {
        for (Map.Entry<UUID, MiningMachine> entry : MachineList.entrySet()) {
            if (entry.getValue().getField() != null) {
                if (entry.getValue().getField().getId().equals(destinationFieldId)) {
                    if (entry.getValue().getCoordinate().getX() == 0 && entry.getValue().getCoordinate().getY() == 0) {
                        return false;
                    }
                }
            }
        }

        machine = new MiningMachine(machine.getMiningMachine_ID(), machine.getName(), new Coordinate(0, 0), fieldList.get(destinationFieldId));

        MachineList.put(machine.getMiningMachine_ID(), machine);
        return true;
    }
}
