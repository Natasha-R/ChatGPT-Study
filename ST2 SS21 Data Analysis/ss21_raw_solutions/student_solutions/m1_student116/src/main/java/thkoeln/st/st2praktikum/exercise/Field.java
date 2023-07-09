package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Field {
    private final UUID uUID;
    private final MiningMaschine[][] miningMaschines;
    private final Obstacles obstacles;

    public Field(int pHeight,int pWidth){
        miningMaschines=new MiningMaschine[pHeight][pWidth];
        uUID=UUID.randomUUID();
        obstacles=new Obstacles(pHeight,pWidth);
    }

    public void addObstacles(int[] koordinatenString){
        if(koordinatenString[0]==koordinatenString[2]){
            obstacles.addObstacleLineX(koordinatenString);
        }
        else{
            obstacles.addObstacleLineY(koordinatenString);
        }
    }

    public boolean addMiningMaschine(MiningMaschine pMiningMaschine){
        if(miningMaschines[0][0]==null){
            miningMaschines[0][0]=pMiningMaschine;
            return true;
        }
        return false;
    }

    public void addMiningMaschine(MiningMaschine pMiningMaschine, int[] pKoordinaten){
        if(miningMaschines[pKoordinaten[0]][pKoordinaten[1]]==null){
            miningMaschines[pKoordinaten[0]][pKoordinaten[1]]=pMiningMaschine;
        }
    }

    public UUID getUUID(){
        return uUID;
    }

    public int[] whereIsTheMiningMaschineStanding(UUID pUUIDVonMiningMaschine){
        int[] koordinateFuerDieMiningMaschine=new int[2];
        for(int i=0;i<miningMaschines.length;i++){
            for (int j=0;j<miningMaschines[0].length;j++){
                if(miningMaschines[i][j]!=null) {
                    if (miningMaschines[i][j].getUUID().equals(pUUIDVonMiningMaschine)) {
                        koordinateFuerDieMiningMaschine[0] = i;
                        koordinateFuerDieMiningMaschine[1] = j;
                        return koordinateFuerDieMiningMaschine;
                    }
                }
            }
        }
        return null;
    }

    public int whereIsTheMaschineX(UUID pUUIDVonMiningMaschine){
        int[] koordinateVonMiningMaschine=whereIsTheMiningMaschineStanding(pUUIDVonMiningMaschine);
        return koordinateVonMiningMaschine[0];
    }

    public int whereIsTheMaschineY(UUID pUUIDVonMiningMaschine){
        int[] koordinateVonMiningMaschine=whereIsTheMiningMaschineStanding(pUUIDVonMiningMaschine);
        return koordinateVonMiningMaschine[1];
    }

    public void loescheMiningMaschine(UUID pUUIDVonDerZuLoeschendenMiningMaschine){
        for(int i=0;i<miningMaschines.length;i++){
            for (int j=0;j<miningMaschines[0].length;j++){
                if(miningMaschines[i][j]!=null){
                    if(miningMaschines[i][j].getUUID().equals(pUUIDVonDerZuLoeschendenMiningMaschine)) {
                        miningMaschines[i][j] = null;
                }
                }
            }
        }
    }

    public void goNorth(UUID pMiningMaschineID,int pAnzahl){
        if(pAnzahl>0) {
            if (whereIsTheMaschineY(pMiningMaschineID) < miningMaschines[0].length - 1) {
                if (!obstacles.isBlockedNorth(whereIsTheMaschineX(pMiningMaschineID), whereIsTheMaschineY(pMiningMaschineID))) {
                    if (!checkIfAMiningCartIsNorth(whereIsTheMaschineX(pMiningMaschineID), whereIsTheMaschineY(pMiningMaschineID))) {
                        int[] aktuellePositionMiningMaschine=whereIsTheMiningMaschineStanding(pMiningMaschineID);
                        miningMaschines[aktuellePositionMiningMaschine[0]][aktuellePositionMiningMaschine[1] + 1] = miningMaschines[aktuellePositionMiningMaschine[0]][aktuellePositionMiningMaschine[1]];
                        miningMaschines[aktuellePositionMiningMaschine[0]][aktuellePositionMiningMaschine[1]] = null;
                        goNorth(pMiningMaschineID, pAnzahl - 1);
                    }
                }
            }
        }
    }

    public void goSouth(UUID pMiningMaschineID,int pAnzahl){
        if(pAnzahl>0){
            if(whereIsTheMaschineY(pMiningMaschineID)>0) {
                if (!obstacles.isBlockedSouth(whereIsTheMaschineX(pMiningMaschineID), whereIsTheMaschineY(pMiningMaschineID))) {
                    if (!checkIfAMiningCartIsSouth(whereIsTheMaschineX(pMiningMaschineID), whereIsTheMaschineY(pMiningMaschineID))) {
                        int[] aktuellePositionMiningMaschine=whereIsTheMiningMaschineStanding(pMiningMaschineID);
                        miningMaschines[aktuellePositionMiningMaschine[0]][aktuellePositionMiningMaschine[1] - 1] = miningMaschines[aktuellePositionMiningMaschine[0]][aktuellePositionMiningMaschine[1]];
                        miningMaschines[aktuellePositionMiningMaschine[0]][aktuellePositionMiningMaschine[1]] = null;
                        goSouth(pMiningMaschineID, pAnzahl - 1);
                 }
                }
            }
        }
    }

    public void goEast(UUID pMiningMaschineID,int pAnzahl){
        if(pAnzahl>0) {
            if (whereIsTheMaschineX(pMiningMaschineID) < miningMaschines.length - 1) {
                if (!obstacles.isBlockedEast(whereIsTheMaschineX(pMiningMaschineID), whereIsTheMaschineY(pMiningMaschineID))) {
                    if (!checkIfAMiningCartIsEast(whereIsTheMaschineX(pMiningMaschineID), whereIsTheMaschineY(pMiningMaschineID))) {
                        int[] aktuellePositionMiningMaschine=whereIsTheMiningMaschineStanding(pMiningMaschineID);
                        miningMaschines[aktuellePositionMiningMaschine[0]+1][aktuellePositionMiningMaschine[1]] = miningMaschines[aktuellePositionMiningMaschine[0]][aktuellePositionMiningMaschine[1]];
                        miningMaschines[aktuellePositionMiningMaschine[0]][aktuellePositionMiningMaschine[1]] = null;
                        goEast(pMiningMaschineID, pAnzahl - 1);
                    }
                }
            }
        }
    }

    public void goWest(UUID pMiningMaschineID,int pAnzahl){
        if(pAnzahl>0) {
            if (whereIsTheMaschineX(pMiningMaschineID) > 0) {
                if (!obstacles.isBlockedWest(whereIsTheMaschineX(pMiningMaschineID), whereIsTheMaschineY(pMiningMaschineID))) {
                    if (!checkIfAMiningCartIsWest(whereIsTheMaschineX(pMiningMaschineID), whereIsTheMaschineY(pMiningMaschineID))) {
                        int[] aktuellePositionMiningMaschine=whereIsTheMiningMaschineStanding(pMiningMaschineID);
                        miningMaschines[aktuellePositionMiningMaschine[0]-1][aktuellePositionMiningMaschine[1]] = miningMaschines[aktuellePositionMiningMaschine[0]][aktuellePositionMiningMaschine[1]];
                        miningMaschines[aktuellePositionMiningMaschine[0]][aktuellePositionMiningMaschine[1]] = null;
                        goWest(pMiningMaschineID, pAnzahl - 1);
                    }
                }
            }
        }
    }

    private boolean checkIfAMiningCartIsNorth(int x,int y){
        if(miningMaschines[0].length>y+1) {
            return miningMaschines[x][y + 1] != null;
        }
        return false;
    }

    private boolean checkIfAMiningCartIsSouth(int x,int y){
        if(0<y-1) {
            return miningMaschines[x][y - 1] != null;
        }
        return false;
    }

    private boolean checkIfAMiningCartIsEast(int x,int y){
        if(miningMaschines.length>x+1) {
            return miningMaschines[x + 1][y] != null;
        }
        return false;
    }

    private boolean checkIfAMiningCartIsWest(int x,int y){
        if(0<x-1) {
            return miningMaschines[x - 1][y] != null;
        }
        return false;
    }

    public boolean isSpawnFrei(){
        return miningMaschines[0][0] == null;
    }
}
