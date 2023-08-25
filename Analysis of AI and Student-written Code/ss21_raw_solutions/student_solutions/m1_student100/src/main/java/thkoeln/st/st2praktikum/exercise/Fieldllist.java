package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

public class Fieldllist implements AddobjectInterface {

    List<Field> fieldList = new ArrayList<Field>();

    @Override
    public void add(Object field) {
        fieldList.add((Field) field);

    }
}
