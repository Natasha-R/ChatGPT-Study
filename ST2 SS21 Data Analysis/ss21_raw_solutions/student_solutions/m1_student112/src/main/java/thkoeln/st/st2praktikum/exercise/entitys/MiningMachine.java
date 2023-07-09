package thkoeln.st.st2praktikum.exercise.entitys;
import thkoeln.st.st2praktikum.exercise.interfaces.Machineable;
import thkoeln.st.st2praktikum.exercise.selfdefinedsupport.CoordinatePair;

import javax.persistence.Id;
import java.util.UUID;

public class MiningMachine implements Machineable {

    @Id
    protected UUID miningmachineId;
    private UUID isAtFieldId;
    private String name;
    private CoordinatePair coordinate;

    public MiningMachine(){
        miningmachineId = UUID.randomUUID();
    }

    //Setter----------------------------------------------------------------------------------------------------
    @Override
    public void setIsAtFieldId(UUID isAtFieldId) {
        this.isAtFieldId = isAtFieldId;
    }
    @Override
    public void setName(String name){this.name=name;}
    @Override
    public void setCoordinate(CoordinatePair coordinate){this.coordinate=coordinate;}

    //Getter----------------------------------------------------------------------------------------------------
    @Override
    public UUID getIsAtFieldId(){
        if(getCoordinate().getXCoordinate()==-1)
            throw new IllegalArgumentException("Diese Maschiene wurde noch in kein Feld gesetzt");
        return isAtFieldId;}
    @Override
    public UUID getMiningmachineId() {
        return miningmachineId;
    }
    @Override
    public String getName(){return name;}
    @Override
    public CoordinatePair getCoordinate(){
         return coordinate;}

}
