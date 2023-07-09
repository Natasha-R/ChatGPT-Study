package thkoeln.st.st2praktikum.exercise.services;

import thkoeln.st.st2praktikum.exercise.entyties.Point;
import thkoeln.st.st2praktikum.exercise.entyties.Obstacle;
import thkoeln.st.st2praktikum.exercise.repository.ObstacleRepository;

import java.util.UUID;

public class ObstacleService implements ObstacleRepository {

    @Override
    public Obstacle addObstacle(UUID spaceId, String ObstacleString) {

        // convert obstacleString to points

        String[]pointsData=ObstacleString.split("-");
 String[]startData=pointsData[0].replace("(","")
                .replace(")","").split(",");
 String[]endData=pointsData[1].replace("(","")
                .replace(")","").split(",");

        Point start=new  Point(Integer.parseInt(startData[0]),Integer.parseInt(startData[1]));
        Point end=new    Point(Integer.parseInt(endData[0]),Integer.parseInt(endData[1]));

        return new Obstacle(spaceId,start,end);


    }
}
