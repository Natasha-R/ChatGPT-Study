package thkoeln.st.st2praktikum.exercise.controllingentity;

import thkoeln.st.st2praktikum.exercise.entitys.MiningMachine;
import thkoeln.st.st2praktikum.exercise.interfaces.Fieldable;
import thkoeln.st.st2praktikum.exercise.interfaces.Machineable;
import thkoeln.st.st2praktikum.exercise.interfaces.NoMoveable;
import thkoeln.st.st2praktikum.exercise.selfdefinedsupport.CoordinatePair;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class MachineControllerCenter {
    public boolean checkMachine(HashMap<UUID, Machineable> collectionOfMiningMachine, CoordinatePair machineCoordinate, UUID fieldId){
        Machineable machine=new MiningMachine();
        Set<UUID> keys = collectionOfMiningMachine.keySet();
        for ( UUID key : keys ) {
            machine=collectionOfMiningMachine.get(key);
            if(machine.getCoordinate().getXCoordinate()!=-1) {
                if (fieldId.equals(machine.getIsAtFieldId())) {
                    if (collectionOfMiningMachine.get(key).getCoordinate().getXCoordinate().equals(machineCoordinate.getXCoordinate()))
                        if (collectionOfMiningMachine.get(key).getCoordinate().getYCoordinate().equals(machineCoordinate.getYCoordinate()))
                            return false;
                }
            }
        }
        return true;
    }
    public boolean checkField(Fieldable field, CoordinatePair machineCoordinate){
       if(machineCoordinate.getYCoordinate()>=0&&machineCoordinate.getXCoordinate()>=0) {
            return field.getHeight() >= machineCoordinate.getYCoordinate() && field.getWidth() >= machineCoordinate.getXCoordinate();
        }else return false;
    }
    public boolean checkWall(HashMap<UUID, NoMoveable> walls, CoordinatePair machinesteps, CoordinatePair machineCoordinate){
        CoordinatePair newPosition=generateNewPosition(machinesteps,machineCoordinate);
        Set<UUID> keys = walls.keySet();
        for (UUID key : keys) {
            if (isRobotNotNextToWall(walls.get(key),machineCoordinate,machinesteps))
                return false;
        }
        return true;
    }
    public boolean isRobotNotNextToWall(NoMoveable wall,CoordinatePair machineCoordinate,CoordinatePair machinesteps){
        if(machinesteps.getXCoordinate().equals(1)){
            if(wall.getSourceX().equals(machineCoordinate.getXCoordinate()+1))
                return !(machineCoordinate.getYCoordinate() <= wall.getSourceY() || machineCoordinate.getYCoordinate() >= wall.getDestinationY());
            return false;
        }else if (machinesteps.getXCoordinate().equals(-1)){
            if (wall.getSourceX().equals(machineCoordinate.getXCoordinate()))
                return !(machineCoordinate.getYCoordinate() <= wall.getSourceY() || machineCoordinate.getYCoordinate() >= wall.getDestinationY());
            return false;
        }else if(machinesteps.getYCoordinate().equals(1)){
            if(wall.getSourceY().equals(machineCoordinate.getYCoordinate()+1))
                return !(machineCoordinate.getXCoordinate() <= wall.getSourceX() || machineCoordinate.getXCoordinate() >= wall.getDestinationX());
            return false;
        }else if(machinesteps.getYCoordinate().equals(-1)) {
            if (wall.getSourceY().equals(machineCoordinate.getYCoordinate()))
                return !(machineCoordinate.getXCoordinate() <= wall.getSourceX() || machineCoordinate.getXCoordinate() >= wall.getDestinationX());
            return false;
        }
        throw new UnsupportedOperationException("Die Maschine darf nur einen Schritt gehen");
    }

    public CoordinatePair generateNewPosition(CoordinatePair machinesteps, CoordinatePair machineCoordinate){
        CoordinatePair newPosition=new CoordinatePair();
        newPosition.setCoordinatePair(machineCoordinate.getXCoordinate() + machinesteps.getXCoordinate(), machineCoordinate.getYCoordinate() + machinesteps.getYCoordinate());
        Integer x=machinesteps.getXCoordinate();
        Integer y=machinesteps.getYCoordinate();
        return newPosition;
    }
    public void moveMachine (CoordinatePair newPosition,UUID fieldId,Machineable machine){
        machine.setCoordinate(newPosition);
        machine.setIsAtFieldId(fieldId);
    }
    public CoordinatePair generateOneStep(CoordinatePair steps){
        CoordinatePair step=new CoordinatePair();
        if(steps.getXCoordinate()<0)
            step.setCoordinatePair(-1,0);
        else if(steps.getXCoordinate()>0)
            step.setCoordinatePair(1,0);
        else if(steps.getYCoordinate()>0)
            step.setCoordinatePair(0,1);
        else if(steps.getYCoordinate()<0)
            step.setCoordinatePair(0,-1);
        return step;
    }
    public CoordinatePair readCommand(String[] command){
        CoordinatePair steps=new CoordinatePair();
        switch (command[0]){
            case "no":{steps.setCoordinatePair(0,Integer.valueOf(command[1]));break;}
            case "so":{steps.setCoordinatePair(0,Integer.valueOf(command[1])*(-1));break;}
            case "ea":{steps.setCoordinatePair(Integer.valueOf(command[1]),0);break;}
            case "we":{steps.setCoordinatePair(Integer.valueOf(command[1])*(-1),0);break;}
            default:{steps.setCoordinatePair(0,0);}
        }return steps;
    }
}
