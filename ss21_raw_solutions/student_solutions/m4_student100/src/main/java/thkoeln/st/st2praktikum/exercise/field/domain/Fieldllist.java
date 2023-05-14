package thkoeln.st.st2praktikum.exercise.field.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.AddobjectInterface;
import thkoeln.st.st2praktikum.exercise.field.domain.Field;

import java.util.ArrayList;
import java.util.List;

public class Fieldllist implements AddobjectInterface {

    private List<Field> fieldList = new ArrayList<Field>();

    public List<Field> getFieldList() {
        return fieldList;
    }

    @Override
    public void add(Object field) {
        fieldList.add((Field) field);

    }
}
