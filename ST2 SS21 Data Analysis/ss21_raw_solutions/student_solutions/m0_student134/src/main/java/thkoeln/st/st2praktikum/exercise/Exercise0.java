package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    /**
     * Initialisierung der Koordinaten des Roboters und
     * den Waenden.
     */

    private String robot_coordinates = "5,3";
    private int robot_x = Integer.parseInt(robot_coordinates.substring(0,1));
    private int robot_y = Integer.parseInt(robot_coordinates.substring(2));

    private String[] wall1_coordinates = {"1,6","2,6","3,6","4,6","5,6","6,6"};
    private String[] wall2_coordinates = {"6,8","6,7","6,6","6,5","6,4","6,3","6,2"};
    private String[] wall3_coordinates = {"3,3","4,3","5,3","6,3","7,3","8,3","9,3"};
    private String[] wall4_coordinates = {"4,1","4,2","4,3"};

    @Override

    /**
     * Methode walk:
     * Der Roboter bewegt sich abhaengig davon ob der Weg frei ist.
     * @param walkCommandString Der Befehl, wie der Roboter sich bewegen soll, wird uebergeben. (Richtung, Schrittanzahl)
     * @return robot_coordinates gibt den aktuellen Standpunkt des Roboters wieder.
     */

    public String walk(String walkCommandString) {


        // Der String Befehl wird aufgeteilt in 2 Strings:
        // Die Richtung "direction" und die Schrittanzahl "steps"
        String direction = walkCommandString.substring(1,3);
        int steps = Integer.parseInt(walkCommandString.substring(4,5));

            switch (direction) {
                case "no": {
                    // For-Schleifen laeufen solange wie es Schritte gibt.
                    for (int i = 0; i < steps; i++) {
                        if (robot_y < 7) {
                            // Methode test wird aufgerufen und die Variable "direction wird uebergeben"
                            if (test(direction) == true) {
                                robot_y++;
                            }
                        }
                    }
                    break;
                }

                case "ea": {
                    for (int i = 0; i < steps; i++) {
                        if (robot_x < 11) {
                            if (test(direction) == true) {
                                robot_x++;
                            }
                        }
                    }
                    break;
                }

                case "so": {
                    for (int i = 0; i < steps; i++) {
                        if (robot_y > 0) {
                            if (test(direction) == true) {
                                robot_y--;
                            }
                        }
                    }
                    break;
                }

                case "we": {
                    for (int i = 0; i < steps; i++) {
                        if (robot_x > 0) {
                            if (test(direction) == true) {
                                robot_x--;
                            }
                        }
                    }
                    break;
                }
            }
        // Konvertiert die Werte vom Typen Integer in die gewuenschte String Syntax um.
        robot_coordinates = "(" + String.valueOf(robot_x) + "," + String.valueOf(robot_y) + ")";
        return robot_coordinates;
    }

    /**
     * Methode no:
     * Prueft ob in der Richtung Norden eine Wand existiert.
     * @param wall die Koordinaten der Wand werden uebergeben.
     * @return boolean 
     */

    public boolean no(String [] wall){
        
        // Zaehler der anfaengt zu zaehlen wenn ein Wandpunkt erreicht wird.
        int wall_counter = 0;

        for (int i = 0; i < wall.length; i++){
            // Prueft ob bei dem naechsten Schritt ein Wandpunkt vorhanden ist.
            if (wall[i].equals(robot_x + "," + (robot_y + 1))){
                wall_counter++;
            }
            if (wall[i].equals((robot_x + 1) + "," + (robot_y + 1))){
                wall_counter++;
            }
        }
        if(wall_counter == 2){
            return false;
        }
        return true;
    }

    /**
     * Methode so:
     * Prueft ob in der Richtung Sueden eine Wand existiert.
     * @param wall die Koordinaten der Wand werden uebergeben.
     * @return
     */
    public boolean so(String [] wall){

        int wall_counter = 0;
        for (int i = 0; i < wall.length; i++){
            if (wall[i].equals(robot_x + "," + (robot_y))){
                wall_counter++;
            }
            if (wall[i].equals((robot_x + 1) + "," + robot_y)){
                wall_counter++;
            }
        }
        if(wall_counter == 2){
            return false;
        }
        return true;
    }

    /**
     * Methode ea:
     * Prueft ob in der Richtung Osten eine Wand existiert.
     * @param wall
     * @return boolean
     */

    public boolean ea(String [] wall){

        int wall_counter = 0;
        String a = (robot_x + 1) + "," + (robot_y + 1);
        String b = (robot_x + 1) + "," + robot_y;
        for (int i = 0; i < wall.length; i++){
            if (wall[i].equals(a)){
                wall_counter++;
            }
            if (wall[i].equals(b)){
                wall_counter++;
            }
        }
        if(wall_counter == 2){
            return false;
        }
        return true;
    }

    /**
     * Methode we:
     * Prueft ob in der Richtung Westen eine Wand existiert.
     * @param wall
     * @return
     */

    public boolean we(String [] wall){

        int wall_counter = 0;
        for (int i = 0; i < wall.length; i++){
            if (wall[i].equals(robot_x + "," + (robot_y + 1))){
                wall_counter++;
            }
            if (wall[i].equals(robot_coordinates)){
                wall_counter++;
            }
        }
        if(wall_counter == 2){
            return false;
        }
        return true;
    }

    /**
     * Methode no_so_ea_we:
     * @param wall
     * @param direction
     * @return
     */

    public boolean no_so_ea_we(String [] wall, String direction) {
        switch (direction) {
            case "no":
                if (no(wall) == false) return false;
                break;
            case "so":
                if (so(wall) == false) return false;
                break;
            case "ea":
                if (ea(wall) == false) return false;
                break;
            case "we":
                if (we(wall) == false) return false;
                break;
        }
        return true;
    }

    /**
     * Methode test:
     * @param direction
     * @return
     */

    public boolean test(String direction){

        if(no_so_ea_we(wall1_coordinates,direction) == false)
            return false;
        if(no_so_ea_we(wall2_coordinates,direction) == false)
            return false;
        if(no_so_ea_we(wall3_coordinates,direction) == false)
            return false;
        if(no_so_ea_we(wall4_coordinates,direction) == false)
            return false;
        return true;
    }
}
