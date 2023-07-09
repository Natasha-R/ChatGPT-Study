//package thkoeln.st.st2praktikum.exercise.environment.position;
//
//import thkoeln.st.st2praktikum.exercise.environment.Space;
//import thkoeln.st.st2praktikum.exercise.exception.ParseException;
//import thkoeln.st.st2praktikum.exercise.exception.PositionOutOfSpaceException;
//
//public class EnvironmentPositionFactory {
//
//    private Space space;
//
//    public EnvironmentPositionFactory(Space space) {
//        this.space = space;
//    }
//
//    public EnvironmentPosition of(int x, int y) {
//        return build(Position.of(x, y));
//    }
//
//    public EnvironmentPosition of(Position position) {
//        return build(position);
//    }
//
//    public EnvironmentPosition of(String string) throws ParseException {
//        return build(Position.of(string));
//    }
//
//    private EnvironmentPosition build(Position position) {
//        if (!space.isOnSpace(position)) throw new PositionOutOfSpaceException(position, space);
//        return new EnvironmentPosition(space, position);
//    }
//}
