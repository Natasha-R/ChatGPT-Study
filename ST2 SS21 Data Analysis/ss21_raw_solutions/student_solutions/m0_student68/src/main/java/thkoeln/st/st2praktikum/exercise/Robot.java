package thkoeln.st.st2praktikum.exercise;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class Robot {
    private Integer robotPosX;
    private Integer robotPosY;
    private Map myMap;


    // #################################################################################################################
    // ################################################### MOVE ROBOT ##################################################
    // #################################################################################################################
    public String moveRobot(String command) {
        Pair<Direction,Integer> move = parseCommand(command);                           // Kommando analysieren in Form von einem Pair  bsp. Pair(NORTH,2)
        Integer stopPoint;
        // =============================================================================================================
        // ========================================== MOVE TO NORTH ====================================================
        // =============================================================================================================
        if (move.getFirst() == Direction.NORTH) {
            // Setzen des vorläufigen StopPoint
            stopPoint = Math.min(robotPosY + move.getSecond(), myMap.getYSize() - 1);  // StopPoint ist MapGrenze ODER robotPosY + Bewegung

            // Alle Barrieren checken
            for (Barrier barrier : myMap.getBarriers()) {
                if (barrier.getYStart() == barrier.getYEnd()                            // Wenn horizontale Barriere
                        && barrier.getYStart() > robotPosY                              // & Barriere liegt im nördlichen Bereich
                        && barrier.getXStart() <= robotPosX                             // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                        && barrier.getXEnd() > robotPosX                                // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                        && barrier.getYStart() <= stopPoint)                            // & Barriere Schränkt Bewegung ein bzw. ist vorläufiger/vorheriger StopPoint ist weiter entfernt
                    stopPoint = barrier.getYStart()-1;                                  // => Setzt neuen StopPoint
                }

            robotPosY = stopPoint;                                                       // Setzen der neuen Y-Position des Roboters
        }

        // =============================================================================================================
        // ========================================== MOVE TO SOUTH ====================================================
        // =============================================================================================================
        if (move.getFirst() == Direction.SOUTH) {
            // Setzen des vorläufigen StopPoint
            stopPoint = Math.max(robotPosY - move.getSecond(), 0);                      // StopPoint ist MapGrenze ODER robotPosY - Bewegung

            // Alle Barrieren checken
            for (Barrier barrier : myMap.getBarriers()) {
                if (barrier.getYStart() == barrier.getYEnd()                            // Wenn horizontale Barriere
                        && barrier.getYStart() <= robotPosY                              // & Barriere liegt im südlichen Bereich
                        && barrier.getXStart() <= robotPosX                             // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                        && barrier.getXEnd() > robotPosX                                // & Barriere liegt auf gleicher X-Koordinate wie der Roboter
                        && barrier.getYStart() > stopPoint)                             // & Barriere Schränkt Bewegung ein bzw. vorläufiger/vorheriger StopPoint ist weiter entfernt
                    stopPoint = barrier.getYStart();                                    // => Setzt neuen StopPoint
                }

            robotPosY = stopPoint;                                                       // Setzen der neuen Y-Position des Roboters
        }

        // =============================================================================================================
        // =========================================== MOVE TO WEST ====================================================
        // =============================================================================================================
        if (move.getFirst() == Direction.WEST) {
            // Setzen des vorläufigen StopPoint
            stopPoint = Math.max(robotPosX - move.getSecond(), 0);                      // StopPoint ist MapGrenze ODER robotPosX - Bewegung

            // Alle Barrieren checken
            for (Barrier barrier : myMap.getBarriers()) {
                if (barrier.getXStart() == barrier.getXEnd()                            // Wenn vertikale Barriere
                        && barrier.getXStart() <= robotPosX                              // & Barriere liegt im östlichen Bereich
                        && barrier.getYStart() <= robotPosY                             // & Barriere liegt auf gleicher Y-Koordinate wie der Roboter
                        && barrier.getYEnd() > robotPosY                                // & Barriere liegt auf gleicher Y-Koordinate wie der Roboter
                        && barrier.getXStart() > stopPoint)                             // & Barriere Schränkt Bewegung ein bzw. vorläufiger/vorheriger StopPoint ist weiter entfernt
                    stopPoint = barrier.getXStart();                                    // => Setzt neuen StopPoint
                }

            robotPosX = stopPoint;                                                      // Setzen der neuen X-Position des Roboters
        }

        // =============================================================================================================
        // =========================================== MOVE TO EAST ====================================================
        // =============================================================================================================
        if (move.getFirst() == Direction.EAST) {
            // Setzen des vorläufigen StopPoint
            stopPoint = Math.min(robotPosX + move.getSecond(), myMap.getXSize() - 1);  // StopPoint ist MapGrenze ODER robotPosX - Bewegung

            // Alle Barrieren checken
            for (Barrier barrier : myMap.getBarriers()) {
                if (barrier.getXStart() == barrier.getXEnd()                            // Wenn vertikale Barriere
                        && barrier.getXStart() > robotPosX                              // & Barriere liegt im östlichen Bereich
                        && barrier.getYStart() <= robotPosY                             // & Barriere liegt auf gleicher Y-Koordinate wie der Roboter
                        && barrier.getYEnd() > robotPosY                                // & Barriere liegt auf gleicher Y-Koordinate wie der Roboter
                        && barrier.getXStart() <= stopPoint)                             // & Barriere Schränkt Bewegung ein bzw. vorläufiger/vorheriger StopPoint ist weiter entfernt
                    stopPoint = barrier.getXStart()-1;                                  // => Setzt neuen StopPoint
                }
            robotPosX = stopPoint;                                                      // Setzen der neuen X-Position des Roboters
        }

        System.out.println("Position: ("+robotPosX+","+robotPosY+")");
        return ParsePosition(robotPosX,robotPosY);
    }



    // #################################################################################################################
    // ################################################## PARSE COMMAND ################################################
    // #################################################################################################################
    static public Pair<Direction,Integer> parseCommand (String command){
        String direction = command.substring(command.indexOf("[")+1,command.indexOf(","));
        Integer cells = Integer.parseInt(command.substring(command.indexOf(",")+1,command.indexOf("]")));
        //System.out.println("Direction: "  + direction + "     cells: " + cells);

        if (direction.equals("no"))
            return Pair.of(Direction.NORTH,cells);

        if (direction.equals("so"))
            return Pair.of(Direction.SOUTH,cells);

        if (direction.equals("ea"))
            return Pair.of(Direction.EAST,cells);

        if (direction.equals("we"))
            return Pair.of(Direction.WEST,cells);

        throw new IllegalArgumentException("Illegal command: "+command);
    }



    // #################################################################################################################
    // ################################################## PARSE POSITION ###############################################
    // #################################################################################################################
    static public String ParsePosition (Integer posX, Integer posY){
        return "(" + posX + ","+ posY + ")";
    }




}
