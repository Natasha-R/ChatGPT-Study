package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class MiningMachineService {
    private final ArrayList<Field> fields;
    private final ArrayList<MiningMaschine> miningMaschines;
    private final ArrayList<Connections> connections;
    public MiningMachineService(){
        fields =new ArrayList<>();
        miningMaschines =new ArrayList<>();
        connections =new ArrayList<>();
    }
    /**
     * This method creates a new field.
     * @param height the height of the field
     * @param width the width of the field
     * @return the UUID of the created field
     */
    public UUID addField(Integer height, Integer width) {
        Field newField=new Field(height,width);
        fields.add(newField);
        return newField.getUUID();
    }

    /**
     * This method adds a obstacle to a given field.
     * @param fieldId the ID of the field the obstacle shall be placed on
     * @param obstacleString the end points of the obstacle, encoded as a String, eg. "(2,3)-(10,3)" for a obstacle that spans from (2,3) to (10,3)
     */
    public void addObstacle(UUID fieldId, String obstacleString) {
        if(fieldExists(fieldId)){
            int[] koordinatenFuerObstacles=divideObstaclesString(obstacleString);
            Objects.requireNonNull(getField(fieldId)).addObstacles(koordinatenFuerObstacles);
        }
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
        if(fieldExists(sourceFieldId) && fieldExists(destinationFieldId)){
            Connections connection=new Connections(sourceFieldId, divideCoordinate(sourceCoordinate),
                    destinationFieldId, divideCoordinate(destinationCoordinate));
            connections.add(connection);
            return connection.getUUID();
        }
        return null;
    }

    /**
     * This method adds a new mining machine
     * @param name the name of the mining machine
     * @return the UUID of the created mining machine
     */
    public UUID addMiningMachine(String name) {
        MiningMaschine miningMaschine=new MiningMaschine(name);
        miningMaschines.add(miningMaschine);
        return miningMaschine.getUUID();
    }

    /**
     * This method lets the mining machine execute a command.
     * @param miningMachineId the ID of the mining machine
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,field-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on gridcells with a connection to another field
     * "[en,<field-id>]" for setting the initial field where a mining machine is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The mining machine will always spawn at (0,0) of the given field.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the mining machine hits a obstacle or
     *      another mining machine, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID miningMachineId, String commandString) {
        if(miningMaschineExists(miningMachineId)){
            commandString=commandString.replace("[","");
            commandString=commandString.replace("]","");
            String[] uebergangsStringFuerBefehle=commandString.split(",");
            uebergangsStringFuerBefehle[0]=uebergangsStringFuerBefehle[0].toLowerCase();
            if(uebergangsStringFuerBefehle[0].equals("no") || uebergangsStringFuerBefehle[0].equals("so")
                    || uebergangsStringFuerBefehle[0].equals("ea") || uebergangsStringFuerBefehle[0].equals("we")){
                int anzahlAnSchritten=Integer.parseInt(uebergangsStringFuerBefehle[1]);
                walkMaschine(uebergangsStringFuerBefehle[0],miningMachineId, anzahlAnSchritten);
                return true;
            }
            else if(uebergangsStringFuerBefehle[0].equals("tr")){
                UUID fieldID=UUID.fromString(uebergangsStringFuerBefehle[1]);
                return transportMiningMaschine(miningMachineId,fieldID);
            }
            else if(uebergangsStringFuerBefehle[0].equals("en")){
                UUID fieldID=UUID.fromString(uebergangsStringFuerBefehle[1]);
                return spwanMiningMaschine(miningMachineId,fieldID);
            }
        }
        return false;
    }

    /**
     * This method returns the field-ID a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the UUID of the field the mining machine is located on
     */
    public UUID getMiningMachineFieldId(UUID miningMachineId){
        if(miningMaschineExists(miningMachineId)) {
            if (getMiningMaschine(miningMachineId).getField() != null) {
                return getMiningMaschine(miningMachineId).getField().getUUID();
            }
        }
        return null;
    }

    /**
     * This method returns the coordinates a mining machine is standing on
     * @param miningMachineId the ID of the mining machine
     * @return the coordiantes of the mining machine encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID miningMachineId){
        int[] coordinateFromMiningMaschine = Objects.requireNonNull(getField(Objects.requireNonNull(getMiningMaschine(miningMachineId))
                .getField().getUUID())).whereIsTheMiningMaschineStanding(miningMachineId);
        return "("+coordinateFromMiningMaschine[0]+","+coordinateFromMiningMaschine[1]+")";
    }

    private boolean fieldExists(UUID pUUID){
        for(int i = 0; i< fields.size(); i++){
            if(fields.get(i).getUUID().equals(pUUID))return true;
        }
        System.out.print("Field nicht gefunden");
        return false;
    }

    private boolean miningMaschineExists(UUID pUUID){
        for(int i = 0; i< miningMaschines.size(); i++){
            if(miningMaschines.get(i).getUUID().equals(pUUID))return true;
        }
        return false;
    }

    private Field getField(UUID pUUID){
        if(fieldExists(pUUID)) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i).getUUID().equals(pUUID)) return fields.get(i);
            }
        }
        return null;
    }

    private MiningMaschine getMiningMaschine(UUID pUUID){
        for(int i = 0; i< miningMaschines.size(); i++){
            if(miningMaschines.get(i).getUUID().equals(pUUID))return miningMaschines.get(i);
        }
        return null;
    }

    private int[] divideObstaclesString(String pObstaclesString){
        pObstaclesString=pObstaclesString.replace(")","");
        pObstaclesString=pObstaclesString.replace("(","");
        String[] divideObstacleString=pObstaclesString.split("-");
        String[] ersteHaelfteDerKoordinate=divideObstacleString[0].split(",");
        String[] zweiteHaelfteDerKoordinate=divideObstacleString[1].split(",");
        int[] alleZahlenFuerDieKoordinaten=new int[4];
        for(int i=0;i<2;i++){
            alleZahlenFuerDieKoordinaten[i]=Integer.parseInt(ersteHaelfteDerKoordinate[i]);
            alleZahlenFuerDieKoordinaten[i+2]=Integer.parseInt(zweiteHaelfteDerKoordinate[i]);
        }
        return alleZahlenFuerDieKoordinaten;
    }

    private int[] divideCoordinate(String coordinate){
        coordinate=coordinate.replace(")","");
        coordinate=coordinate.replace("(","");
        String[] stringArrayFuerKoordinaten=coordinate.split(",");
        int[] ergebnisArrayFuerKoordinaten=new int[2];
        for(int i=0;i<2;i++){
            ergebnisArrayFuerKoordinaten[i]=Integer.parseInt(stringArrayFuerKoordinaten[i]);
        }
        return ergebnisArrayFuerKoordinaten;
    }


    private boolean transportMiningMaschine(UUID pMiningMachineId, UUID pFieldID){
        if(fieldExists(pFieldID)){
            Field aktuellesfeldDerMiningMaschine=getField(getMiningMachineFieldId(pMiningMachineId));
            if(isAMiningMaschineStandingOnAConnection(pMiningMachineId,aktuellesfeldDerMiningMaschine.getUUID())!=null){
                Connections zielConnection=isAMiningMaschineStandingOnAConnection(pMiningMachineId,aktuellesfeldDerMiningMaschine.getUUID());
                getField(zielConnection.getDestinationFieldId()).
                        addMiningMaschine(getMiningMaschine(pMiningMachineId),zielConnection.getDestinationCoordinate());
                getMiningMaschine(pMiningMachineId).changeField(getField(zielConnection.
                        getDestinationFieldId()));
                assert aktuellesfeldDerMiningMaschine != null;
                aktuellesfeldDerMiningMaschine.loescheMiningMaschine(pMiningMachineId);
                return true;
            }
        }
        return false;
    }


    public Connections isAMiningMaschineStandingOnAConnection(UUID pUUIDVonMiningMaschine, UUID pFieldID){
        if(getField(pFieldID).whereIsTheMiningMaschineStanding(pUUIDVonMiningMaschine)!=null) {
            int[] koordinateFuerDieMiningMaschine = getField(pFieldID).whereIsTheMiningMaschineStanding(pUUIDVonMiningMaschine);
            for (int i = 0; i < connections.size(); i++) {
                if (connections.get(i).getSourceFieldId().equals(pFieldID)) {
                    if (connections.get(i).getSourceCoordinate()[0] == koordinateFuerDieMiningMaschine[0])
                        return connections.get(i);
                }
            }
        }
        return null;
    }

    private boolean spwanMiningMaschine(UUID pMiningMaschineID,UUID pFieldID){
        if(fieldExists(pFieldID)){
            if(!Objects.requireNonNull(getMiningMaschine(pMiningMaschineID)).hasField()){
                if(getField(pFieldID)!=null){
                    if(getField(pFieldID).isSpawnFrei()){
                        getMiningMaschine(pMiningMaschineID).changeField(getField(pFieldID));
                        return getField(pFieldID).addMiningMaschine(getMiningMaschine(pMiningMaschineID));
                    }
                }
            }
        }
        return false;
    }

    private void walkMaschine(String pBefehl, UUID pMiningMaschineID, int pAnzahl){
        if(getMiningMaschine(pMiningMaschineID).hasField()) {
            if (pBefehl.equals("no")) {
                getMiningMaschine(pMiningMaschineID).getField().goNorth(pMiningMaschineID, pAnzahl);
            } else if (pBefehl.equals("so")) {
                getMiningMaschine(pMiningMaschineID).getField().goSouth(pMiningMaschineID, pAnzahl);
            } else if (pBefehl.equals("ea")) {
                getMiningMaschine(pMiningMaschineID).getField().goEast(pMiningMaschineID, pAnzahl);
            } else if (pBefehl.equals("we")) {
                getMiningMaschine(pMiningMaschineID).getField().goWest(pMiningMaschineID, pAnzahl);
            }
        }
    }

}
