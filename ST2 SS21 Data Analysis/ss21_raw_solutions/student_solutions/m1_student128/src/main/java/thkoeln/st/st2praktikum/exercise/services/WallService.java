package thkoeln.st.st2praktikum.exercise.services;
import thkoeln.st.st2praktikum.exercise.Entity.Point;
import thkoeln.st.st2praktikum.exercise.Entity.Wall;
import thkoeln.st.st2praktikum.exercise.repos.WallRepo;

import java.util.UUID;

public class WallService implements WallRepo {

    @Override
    public Wall addWall(UUID FieldId, String wallString) {

        // convert wallString to points

        String[]pointsData=wallString.split("-");
        String[]startData=pointsData[0].replace("(","")
                .replace(")","").split(",");
        String[]endData=pointsData[1].replace("(","")
                .replace(")","").split(",");

        Point start=new  Point(Integer.parseInt(startData[0]),Integer.parseInt(startData[1]));
        Point end=new    Point(Integer.parseInt(endData[0]),Integer.parseInt(endData[1]));

        return new Wall(FieldId,start,end);


    }
}
