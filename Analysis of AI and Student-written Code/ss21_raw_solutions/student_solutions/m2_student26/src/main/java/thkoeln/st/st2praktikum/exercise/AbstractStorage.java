package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

public abstract class AbstractStorage<A extends AbstractEntity> {

    @Getter
    protected ArrayList<A> list = new ArrayList();




    public A getObject(UUID uuid){
        for (A element: list){
            if(element.getUuid().equals(uuid)){
                return element;
            }
        }
        return null;

    }

    public void addObject(A abstractEntity){
        list.add(abstractEntity);
    }

    public A getObjectByIndex(int index){
        return list.get(index);
    }

    public int size(){
        return list.size();
    }
}
