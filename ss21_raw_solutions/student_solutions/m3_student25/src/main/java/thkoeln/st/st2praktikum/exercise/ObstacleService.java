package thkoeln.st.st2praktikum.exercise;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public interface ObstacleService {
    static Boolean contains(List<Obstacle> obstacles, Obstacle searchObstacle) {
        AtomicReference<Boolean> result = new AtomicReference<>(false);
        obstacles.forEach(obstacle -> {
            if (obstacle.equals(searchObstacle)) {
                result.set(true);
            }
        });
        return result.get();
    }
}
