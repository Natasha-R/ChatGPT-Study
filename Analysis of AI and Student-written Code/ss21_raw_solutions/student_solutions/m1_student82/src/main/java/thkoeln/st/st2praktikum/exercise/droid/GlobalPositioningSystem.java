package thkoeln.st.st2praktikum.exercise.droid;

import thkoeln.st.st2praktikum.exercise.CoordinateService;
import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;
import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.exceptions.NotSpawnedYetException;
import thkoeln.st.st2praktikum.exercise.exceptions.OutOfFieldException;
import thkoeln.st.st2praktikum.exercise.field.Field;
import thkoeln.st.st2praktikum.exercise.map.Cloud;
import thkoeln.st.st2praktikum.exercise.wall.Walkable;

public interface GlobalPositioningSystem {
    Field getPosition() throws NotSpawnedYetException;
    Decision canGoForward(Direction dir, Cloud world) throws NotSpawnedYetException, NoFieldException;

    static boolean wallInDirection(Direction dir, Droid droid, Cloud world){
        try {
            Coordinate[] maybeWall = CoordinateService.createMaybeWall(droid.getPosition(), dir, world.getDeckList().get(droid.getPosition().getDeckId()));
            return Walkable.containsMaybeWall(maybeWall[0], maybeWall[1],world.getDeckList().get(droid.getPosition().getDeckId()).getWallList());
        } catch (NotSpawnedYetException e) {
            e.printStackTrace();
        } catch (OutOfFieldException e) {
            return true;
        }
        throw new IllegalStateException();
    }

    static boolean droidInDirection(Direction dir, Droid droid, Cloud world) throws NotSpawnedYetException, NoFieldException {
        switch (dir) {
            case NO:
                return world.getDeckList().get(droid.getPosition().getDeckId()).getFieldFromCoordinate(new Coordinate(droid.getPosition().getCoordinate().getXAxes(), droid.getPosition().getCoordinate().getYAxes() + 1)).isBlocked();
            case SO:
                return world.getDeckList().get(droid.getPosition().getDeckId()).getFieldFromCoordinate(new Coordinate(droid.getPosition().getCoordinate().getXAxes(), droid.getPosition().getCoordinate().getYAxes() - 1)).isBlocked();
            case EA:
                return world.getDeckList().get(droid.getPosition().getDeckId()).getFieldFromCoordinate(new Coordinate(droid.getPosition().getCoordinate().getXAxes() + 1, droid.getPosition().getCoordinate().getYAxes())).isBlocked();
            case WE:
                return world.getDeckList().get(droid.getPosition().getDeckId()).getFieldFromCoordinate(new Coordinate(droid.getPosition().getCoordinate().getXAxes() - 1, droid.getPosition().getCoordinate().getYAxes())).isBlocked();
            default:
                throw new IllegalArgumentException(dir.toString());
        }

    }
}
