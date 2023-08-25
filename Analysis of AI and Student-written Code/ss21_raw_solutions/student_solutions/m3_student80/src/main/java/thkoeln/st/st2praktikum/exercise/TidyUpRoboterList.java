package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TidyUpRoboterList implements addStuff {

    private List<TidyUpRobot> tidyUpRobotsList = new ArrayList<TidyUpRobot>();





    @Override
    public void add(Object toAdd) {
        tidyUpRobotsList.add((TidyUpRobot) toAdd);
    }

    public List<TidyUpRobot> getTidyUpRobotsList() {
        return tidyUpRobotsList;
    }
}
