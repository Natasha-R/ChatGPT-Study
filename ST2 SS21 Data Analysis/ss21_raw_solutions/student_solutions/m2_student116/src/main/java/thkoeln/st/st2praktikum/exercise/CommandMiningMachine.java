package thkoeln.st.st2praktikum.exercise;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.UUID;

public class CommandMiningMachine {
    private final ArrayList<MiningMachine> miningMachines;
    private final ArrayList<Field> fields;

    public CommandMiningMachine(){
        miningMachines=new ArrayList<>();
        fields=new ArrayList<>();
    }

    public void addMiningMachine(MiningMachine pMiningMachine){
        if(pMiningMachine!=null){
            miningMachines.add(pMiningMachine);
        }
    }

    public void addField(Field pField){
        if (pField!=null){
            fields.add(pField);
        }
    }

    public boolean fieldExists(UUID pUUID) {
        for (Field field : fields) {
            if (field.getUUID().equals(pUUID)) return true;
        }
        return false;
    }


    public Field getField(UUID pFieldID){
        for (Field field : fields) {
            if (field.getUUID().equals(pFieldID)) return field;
        }
        return null;
    }

    public boolean addMiningMachinetoField(MiningMachine pMiningMachine, UUID pFieldID, Vector2D pVector) {
        if (isFree(pVector, pFieldID) && pMiningMachine.getField()!=null) {
            if(getField(pFieldID)!=null) {
                getMiningMachine(pMiningMachine.getUUID()).changeField(getField(pFieldID));
                getMiningMachine(pMiningMachine.getUUID()).changeCurrentPlace(pVector);
                return true;
            }
        }
        return false;
    }

    public void go(UUID pMiningMachine, Integer pAnzahl, CommandType pCommand) {
        if (pAnzahl > 0 && withinTheLimits(pMiningMachine, pCommand)) {
            if (!isBlockedByMiningMachine(pCommand, pMiningMachine)) {
                if(!getMiningMachine(pMiningMachine).getField().isBlocked(getMiningMachine(pMiningMachine).getCurrentPlace(),pCommand, getRightAddedIntForBlocked(pCommand, getMiningMachine(pMiningMachine).getCurrentPlace()))) {
                    getMiningMachine(pMiningMachine).changeCurrentPlace(getRightAddedIntForBlocked(pCommand, getMiningMachine(pMiningMachine).getCurrentPlace()));
                    go(pMiningMachine, pAnzahl - 1, pCommand);
                }
            }
        }
    }

    private boolean withinTheLimits(UUID pMiningmachine, CommandType pCommand) {
        if(getFieldOfAMiningMachine(pMiningmachine).getDynamics()!=null && getMiningMachine(pMiningmachine).getCurrentPlace()!=null) {
            if (CommandType.NORTH.equals(pCommand)) {
                return getFieldOfAMiningMachine(pMiningmachine).getDynamics().getY()-1 > getMiningMachine(pMiningmachine).getCurrentPlace().getY();
            } else if (CommandType.SOUTH.equals(pCommand)) {
                return 0 < getMiningMachine(pMiningmachine).getCurrentPlace().getY();
            } else if (CommandType.WEST.equals(pCommand)) {
                return 0 < getMiningMachine(pMiningmachine).getCurrentPlace().getX();
            } else if (CommandType.EAST.equals(pCommand)) {
                return getFieldOfAMiningMachine(pMiningmachine).getDynamics().getX()-1 > getMiningMachine(pMiningmachine).getCurrentPlace().getX();
            }
        }
        return false;
    }

    private Field getFieldOfAMiningMachine(UUID pMiningMachine) {
        return getMiningMachine(pMiningMachine).getField();
    }

    public MiningMachine getMiningMachine(UUID pMiningMachine) {
        for (MiningMachine miningMachine : miningMachines) {
            if (miningMachine.getUUID() == pMiningMachine) return miningMachine;
        }
        return null;
    }

    private boolean isBlockedByMiningMachine(CommandType pCommandType, UUID pMiningMachine) {
        if (withinTheLimits(pMiningMachine, pCommandType)) {
            for (int i = 0; i < miningMachines.size()-1; i++) {
                if (miningMachines.get(i).getCurrentPlace().equals(getRightAddedIntForBlocked(pCommandType, getMiningMachine(pMiningMachine).getCurrentPlace()))) {
                    return true;
                }
            }
        }
        return false;
    }

    private Vector2D getRightAddedIntForBlocked(CommandType pCommand, Vector2D pVector) {
        if (pCommand.equals(CommandType.NORTH)) {
            return new Vector2D(pVector.getX(), pVector.getY() + 1);
        } else if (pCommand.equals(CommandType.SOUTH)) {
            return new Vector2D(pVector.getX(), pVector.getY() - 1);
        } else if (pCommand.equals(CommandType.WEST)) {
            return new Vector2D(pVector.getX() - 1, pVector.getY());
        } else if (pCommand.equals(CommandType.EAST)) {
            return new Vector2D(pVector.getX() + 1, pVector.getY());
        }
        throw new RuntimeException();
    }

    public boolean isFree(Vector2D pVector, UUID pFieldID) {
        for (MiningMachine miningMachine : miningMachines) {
            if (miningMachine.getField() != null && miningMachine.getCurrentPlace() != null) {
                if (miningMachine.getCurrentPlace().equals(pVector) && getField(pFieldID) == miningMachine.getField()) {
                    return false;
                }
            }
        }
        return true;
    }
}