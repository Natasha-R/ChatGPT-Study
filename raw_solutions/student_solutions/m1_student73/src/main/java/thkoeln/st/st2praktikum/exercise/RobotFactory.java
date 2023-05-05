package thkoeln.st.st2praktikum.exercise;

public class RobotFactory {
    public TidyUpRobot getRobot(String nameParam) {
        return new TidyUpRobot(nameParam);
    }
}
