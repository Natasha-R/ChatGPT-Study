package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public class Portals {

    UUID portalID;
    UUID SourceLvl;
    UUID DestinationLvl;
    int SourceCoordX;
    int SourceCoordY;
    int DestinationCoordX;
    int DestinationCoordY;

    public Portals (UUID portalID, UUID SourseLvl, UUID DestinationLvl, String SourceCoords, String DestinationCoords) {

        // single out start coords as int values
        ArrayList<Integer> intStartCoords = PortalCoords(SourceCoords);
        ArrayList<Integer> intDestinationCoords = PortalCoords(DestinationCoords);

        // assign all values to object
        this.portalID = portalID;
        this.SourceLvl = SourseLvl;
        this.DestinationLvl = DestinationLvl;
        this.SourceCoordX = intStartCoords.get(0);
        this.SourceCoordY = intStartCoords.get(1);
        this.DestinationCoordX = intDestinationCoords.get(0);
        this.DestinationCoordY = intDestinationCoords.get(1);
        //System.out.println(portalID);
        //System.out.println("portal intern: " + DestinationCoordX + ", " + DestinationCoordY);

    }

    public ArrayList<Integer> PortalCoords(String coords){

        // split start and end of value
        String[] Split = coords.split(",");
        ArrayList<String> totalSplit = new ArrayList<>();


        totalSplit.add(Split[0]); // (NUMBER
        totalSplit.add(Split[1]); // NUMBER)

        String[] wallEndFirstStep = totalSplit.get(1).split("\\)");

        ArrayList<Integer> allPositions = new ArrayList<>();

        allPositions.add(Integer.parseInt(totalSplit.get(0).substring(1)));
        allPositions.add(Integer.parseInt(wallEndFirstStep[0]));
        return allPositions;
    }

}