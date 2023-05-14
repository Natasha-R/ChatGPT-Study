package thkoeln.st.st2praktikum.exercise;
import java.util.UUID;

public class MiningMachine {
    private String name;
    private UUID uUID;
    private Field field;
    private Vector2D currentPlace;

    public MiningMachine(String pName){
        uUID= UUID.randomUUID();
        this.name=pName;
        this.field=null;
    }

    public Vector2D getCurrentPlace(){
        return currentPlace;
    }

    public void changeCurrentPlace(Vector2D pVector){ this.currentPlace=pVector; }

    public UUID getUUID(){
        return uUID;
    }

    public boolean hasField(){
        return field != null;
    }

    public void changeField(Field pField){
        this.field=pField;
    }

    public Field getField(){
        return field;
    }
}
