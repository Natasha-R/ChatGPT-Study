package thkoeln.st.st2praktikum.exercise.services;

import thkoeln.st.st2praktikum.exercise.entyties.Space;
import thkoeln.st.st2praktikum.exercise.repository.SpaseRepository;



public class SpaceService implements SpaseRepository {
    @Override
    public Space addSpace(Integer height, Integer width) {
        return  new Space(height,width);
    }
}