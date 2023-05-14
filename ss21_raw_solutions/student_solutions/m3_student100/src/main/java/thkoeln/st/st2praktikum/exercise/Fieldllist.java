package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;

class Fieldllist implements AddobjectInterface {

    private List<Field> fieldList = new ArrayList<Field>();

    public List<Field> getFieldList() {
        return fieldList;
    }

    @Override
    public void add(Object field) {
        fieldList.add((Field) field);

    }
}
