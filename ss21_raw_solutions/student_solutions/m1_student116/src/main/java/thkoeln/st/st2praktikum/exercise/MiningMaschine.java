package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class MiningMaschine {
    private String name;
    private UUID uUID;
    private Field field;

    public MiningMaschine(String pName){
        uUID= UUID.randomUUID();
        this.name=pName;
        this.field=null;
    }

    public UUID getUUID(){
        return uUID;
    }

    public boolean hasField(){
        if(field!=null)return true;
        return false;
    }

    public void changeField(Field pField){
        this.field=pField;
    }

    public Field getField(){
        return field;
    }
}
