package thkoeln.st.st2praktikum.exercise.services;

import thkoeln.st.st2praktikum.exercise.Entity.Field;
import thkoeln.st.st2praktikum.exercise.repos.FieldRepo;



public class FieldService implements FieldRepo {
    @Override
    public Field addField(Integer height, Integer width) {
        return  new Field(height,width);
    }
}
