package thkoeln.st.st2praktikum.exercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repository.ObstacleRepository;


@Service
public class ObstacleService {

    @Autowired
    private ObstacleRepository obstacleRepository;

    public ObstacleRepository getObstacleRepository() {
        return this.obstacleRepository;
    }
}
