package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class TidyUpRobotRepository {
    private List<TidyUpRobot> tidyUpRobots = new ArrayList<>();

    public TidyUpRobot getTidyUpRobot ( UUID tidyUpRobotID ){

        for ( int i = 0 ; i < tidyUpRobots.size() ; i++ ){
            if ( tidyUpRobots.get(i).getTidyUpRobotID() == tidyUpRobotID){
                return tidyUpRobots.get(i);
            }
        }
        throw new NoSuchElementException("There is no TidyUpRobot with this ID");
    }

    public void setTidyUpRobots(List<TidyUpRobot> tidyUpRobots){
        this.tidyUpRobots = tidyUpRobots;
    }

    public List<TidyUpRobot> getTidyUpRobots(){
        return tidyUpRobots;
    }
}
