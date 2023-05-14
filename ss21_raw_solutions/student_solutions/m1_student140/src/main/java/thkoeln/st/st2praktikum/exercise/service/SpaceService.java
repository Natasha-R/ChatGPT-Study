package thkoeln.st.st2praktikum.exercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repository.SpaceRepository;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;


    public SpaceRepository getSpaceRepository() {
        return this.spaceRepository;
    }



}
