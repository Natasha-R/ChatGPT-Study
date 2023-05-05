package thkoeln.st.st2praktikum.exercise;


import java.util.*;


public class MiningMachineService {

    Map<UUID, Field> FieldList = new HashMap<>();
    Map<UUID, miningMachine> MachineList = new HashMap<>();



    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {


        UUID tmp = UUID.randomUUID();
        Field field_a = new Field();
        field_a.id = tmp;
        field_a.height = height;
        field_a.width = width;
        field_a.CONNECTION = new ArrayList<>();
        field_a.HORIZONTAL = new ArrayList<>();
        field_a.VERTICAL = new ArrayList<>();
        FieldList.put(tmp, field_a);

        return tmp;

    }

    /**
     * This method adds a barrier to a given field.
     * @param fieldId the ID of the field the barrier shall be placed on
     * @param barrierString the end points of the barrier, encoded as a String, eg. "(2,3)-(10,3)" for a barrier that spans from (2,3) to (10,3)
     */
    public void addBarrier(UUID fieldId, String barrierString) {
        Field field = FieldList.get(fieldId);

        String[] barrier_split = barrierString.split("-"); // [0] = (2,3); [1] = (10,3)
        String from = barrier_split[0]; // (2,3)

        String to = barrier_split[1]; // (10,3)

        int x_from = Integer.parseInt(from.split(",")[0].replace("(", ""));
        int x_to = Integer.parseInt(to.split(",")[0].replace("(", ""));

        int y_from = Integer.parseInt(from.split(",")[1].replace(")", ""));
        int y_to = Integer.parseInt(to.split(",")[1].replace(")", ""));

        if (x_from == x_to) {
            for(int i = y_from; i < y_to; i++) {
                Barrier barrier_a = new Barrier();
                barrier_a.Coordinates = "(" + x_from + "," + i + ")";
                field.HORIZONTAL.add(barrier_a);
            }
        } else {
            for(int i = x_from; i < x_to; i++) {
                Barrier barrier_a = new Barrier();
                barrier_a.Coordinates = "(" + i + "," + y_from + ")";
                field.VERTICAL.add(barrier_a);
            }
        }

        FieldList.put(fieldId,field);

    }

    /**
     * This method adds a traversable connection between two fields. Connections only work in one direction.
     * @param sourceFieldId the ID of the field where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationFieldId the ID of the field where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceFieldId, String sourceCoordinate, UUID destinationFieldId, String destinationCoordinate) {

        Field sourceField = FieldList.get(sourceFieldId);

        Connection con = new Connection();

        con.Connection_ID = UUID.randomUUID();
        con.sourceFieldID =  sourceFieldId;
        con.destinationFieldId = destinationFieldId;
        con.x_source = Integer.parseInt(sourceCoordinate.split(",")[0].replace("(" , ""));
        con.y_source = Integer.parseInt(sourceCoordinate.split(",")[1].replace(")",""));
        con.x_destination = Integer.parseInt(destinationCoordinate.split(",")[0].replace("(", ""));
        con.y_destination = Integer.parseInt(destinationCoordinate.split(",")[1].replace(")", ""));


        sourceField.CONNECTION.add(con);
        FieldList.put(sourceFieldId, sourceField);


        return con.Connection_ID;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {

    miningMachine machine = new miningMachine();
    machine.Name = name;
    machine.field = null;
    machine.miningMachine_ID = UUID.randomUUID();
    machine.x = 0;
    machine.y = 0;

    MachineList.put(machine.miningMachine_ID, machine);


    return machine.miningMachine_ID;
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on squares with a connection to another field
     * "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a barrier or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        miningMachine machine = MachineList.get(miningMachineId);
        String[] command_split = commandString.split(",");
        String command = command_split[0].replace("[", "");
        String id = command_split[1].replace("]", "");
        Field field = machine.field;

        System.out.println(commandString);
        switch (command) {
            case "no":
                norden(field.VERTICAL, Integer.parseInt(id), machine);
                break;
            case "so":
                sueden(field.VERTICAL, Integer.parseInt(id), machine);
                break;
            case "ea":
                osten(field.HORIZONTAL, Integer.parseInt(id), machine);
                break;
            case "we":
                westen(field.HORIZONTAL, Integer.parseInt(id), machine);
                break;
            case "tr": {
                UUID destinationFieldId = UUID.fromString(id);

                for (Connection connection : field.CONNECTION) {
                    if (machine.x == connection.x_source && machine.y == connection.y_source && connection.destinationFieldId.equals(destinationFieldId)) {
                        machine.field = FieldList.get(connection.destinationFieldId);
                        machine.x = connection.x_destination;
                        machine.y = connection.y_destination;
                        MachineList.put(miningMachineId, machine);
                        return true;
                    }
                }
                return false;
            }
            case "en": {
                UUID destinationFieldId = UUID.fromString(id);

                for (Map.Entry<UUID, miningMachine> entry : MachineList.entrySet()) {
                    if (entry.getValue().field != null) {
                        if (entry.getValue().field.id.equals(destinationFieldId)) {
                            if (entry.getValue().x == 0 && entry.getValue().y == 0) {
                                return false;
                            }
                        }
                    }
                }

                machine.x = 0;
                machine.y = 0;
                machine.field = FieldList.get(destinationFieldId);
                MachineList.put(miningMachineId, machine);
                break;
            }
        }
        return true;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId) {
        return MachineList.get(miningMachineId).field.id;
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        miningMachine machine = MachineList.get(miningMachineId);
        int x = machine.x;
        int y = machine.y;
        return "(" + x + "," + y + ")";
    }

    private void westen(List<Barrier> HORIZONTAL, int anzahl, miningMachine machine) {
        /*
        Potentielle Wände raussuchen
         */
        List<Barrier> tmp = new ArrayList<>();
        for (int i = 0; i < HORIZONTAL.size(); i++) {
            Barrier temporaer = HORIZONTAL.get(i); // -> (0,3)
            String[] abc = temporaer.Coordinates.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[1].replace(")", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"

            if (Integer.parseInt(def) == machine.y && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[0].replace("(", "")) <= machine.x && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (machine.x - anzahl) < Integer.parseInt(abc[0].replace("(", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp.add(temporaer); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            machine.x = machine.x - anzahl;
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp.get(0).Coordinates.split(",")[0].replace("(", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp.size(); i++) {
                if (max < Integer.parseInt(tmp.get(i).Coordinates.split(",")[0].replace("(", ""))) {
                    max = Integer.parseInt(tmp.get(i).Coordinates.split(",")[0].replace("(", ""));
                }
            }
            machine.x = max; // x = Der Koordinate der Wand setzen (Da man da stoppt)
        }
        if (machine.x < 0) {
            machine.x = 0;
        }
        MachineList.put(machine.miningMachine_ID, machine);
    }

    private void osten(List<Barrier> HORIZONTAL, int anzahl, miningMachine machine) {
        /*
        Potentielle Wände raussuchen
         */
        List<Barrier> tmp = new ArrayList<>();
        for (int i = 0; i < HORIZONTAL.size(); i++) {
            Barrier temporaer = HORIZONTAL.get(i); // -> (0,3)
            String[] abc = temporaer.Coordinates.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[1].replace(")", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"

            if (Integer.parseInt(def) == machine.y && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[0].replace("(", "")) > machine.x && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (machine.x + anzahl) >= Integer.parseInt(abc[0].replace("(", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp.add(temporaer); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            machine.x = machine.x + anzahl;
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp.get(0).Coordinates.split(",")[0].replace("(", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp.size(); i++) {
                if (max > Integer.parseInt(tmp.get(i).Coordinates.split(",")[0].replace("(", ""))) {
                    max = Integer.parseInt(tmp.get(i).Coordinates.split(",")[0].replace("(", ""));
                }
            }
            machine.x = max - 1; // x = Der Koordinate der Wand setzen (Da man da stoppt)
        }
        if (machine.x > machine.field.width - 1) {
            machine.x = machine.field.width - 1;
        }
        MachineList.put(machine.miningMachine_ID, machine);
    }


    private void sueden(List<Barrier> VERTIKAL, int anzahl, miningMachine machine) {
        List<Barrier> tmp = new ArrayList<>();
        for (int i = 0; i < VERTIKAL.size(); i++) {
            Barrier temporaer = VERTIKAL.get(i); // -> (0,3)
            String[] abc = temporaer.Coordinates.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[0].replace("(", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"

            if (Integer.parseInt(def) == machine.x && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[1].replace(")", "")) <= machine.y && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (machine.y - anzahl) < Integer.parseInt(abc[1].replace(")", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp.add(temporaer); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            machine.y = machine.y - anzahl;
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp.get(1).Coordinates.split(",")[1].replace(")", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp.size(); i++) {
                if (max > Integer.parseInt(tmp.get(i).Coordinates.split(",")[1].replace(")", ""))) {
                    max = Integer.parseInt(tmp.get(i).Coordinates.split(",")[1].replace(")", ""));
                }
            }
            machine.y = max; // x = Der Koordinate der Wand setzen (Da man da stoppt)
        }
        if (machine.y < 0) {
            machine.y = 0;
        }

        MachineList.put(machine.miningMachine_ID, machine);
    }

    private void norden(List<Barrier> VERTIKAL, int anzahl, miningMachine machine) {
        List<Barrier> tmp = new ArrayList<>();

        for (int i = 0; i < VERTIKAL.size(); i++) {
            Barrier temporaer = VERTIKAL.get(i); // -> (0,3)
            String[] abc = temporaer.Coordinates.split(","); // -> String bei ',' aufteilen -> ["(0", "3)"]
            String def = abc[0].replace("(", ""); // -> Index 1 aus Array und Klammer entfernen -> "3"
            if (Integer.parseInt(def) == machine.x && // schauen ob potentielle wand selbe Höhe ist
                    Integer.parseInt(abc[1].replace(")", "")) > machine.y && // schauen ob x von potentieller Wand kleiner gleich als aktuelles x ist
                    (machine.y + anzahl) >= Integer.parseInt(abc[1].replace(")", ""))) {  // schauen ob neue position größer als x von potentieller Wand ist
                tmp.add(temporaer); //Falls Bedingungen zutreffen, füge die Wand der Temporären Liste hinzu
            }
        }
        if (tmp.size() == 0) { // falls keine Wände gefunden wurden -> Neue position kann ohne Probleme gesetzt werden
            machine.y = machine.y + anzahl;
        } else {
            // Liste mit den Potentiellen Wänden durchgehen und die größte X Koordinate davon finden
            int max = Integer.parseInt(tmp.get(0).Coordinates.split(",")[1].replace(")", "")); // Beispiel -> ("0,3") -> 3
            for (int i = 0; i < tmp.size(); i++) {
                if (max > Integer.parseInt(tmp.get(i).Coordinates.split(",")[1].replace(")", ""))) {
                    max = Integer.parseInt(tmp.get(i).Coordinates.split(",")[1].replace(")", ""));
                }
            }
            machine.y = max - 1; // x = Der Koordinate der Wand setzen (Da man da stoppt)
        }
        if (machine.y > machine.field.height - 1) {
            machine.y = machine.field.height - 1;
        }
        MachineList.put(machine.miningMachine_ID, machine);
    }
}
