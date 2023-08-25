package thkoeln.st.st2praktikum.exercise.services;

import thkoeln.st.st2praktikum.exercise.obstacle.ObstacleInterface;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public interface ObstacleService {
    static Boolean contains(ArrayList<ObstacleInterface> obstacleArrayList, ObstacleInterface searchObstacle){
        AtomicReference<Boolean> result = new AtomicReference<>(false);
        obstacleArrayList.forEach(obstacle -> {
            if(obstacle.isEqualObstacle(searchObstacle)){
                result.set(true);
            };
        }); return result.get();
    }
}
