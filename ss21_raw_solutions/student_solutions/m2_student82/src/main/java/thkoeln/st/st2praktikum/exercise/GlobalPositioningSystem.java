package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.exceptions.NoFieldException;
import thkoeln.st.st2praktikum.exercise.exceptions.NotSpawnedYetException;
import thkoeln.st.st2praktikum.exercise.exceptions.OutOfFieldException;

public interface GlobalPositioningSystem {
    Field getPosition() throws NotSpawnedYetException;
    Decision canGoForward(OrderType orderType, Cloud world) throws NotSpawnedYetException, NoFieldException;

    static boolean wallInDirection(OrderType orderType, Droid droid, Cloud world){
        try {
            Coordinate[] maybeWall = CoordinateService.createMaybeWall(droid.getPosition(), orderType, world.getDecks().get(droid.getPosition().getDeckId()));
            return Walkable.containsMaybeWall(maybeWall[0], maybeWall[1],world.getDecks().get(droid.getPosition().getDeckId()).getWallList());
        } catch (NotSpawnedYetException e) {
            e.printStackTrace();
        } catch (OutOfFieldException e) {
            return true;
        }
        throw new IllegalStateException();
    }

    static boolean droidInDirection(OrderType orderType, Droid droid, Cloud world) throws NotSpawnedYetException, NoFieldException {
        switch (orderType) {
            case NORTH:
                return world.getDecks().get(droid.getPosition().getDeckId()).getFieldFromCoordinate(new Coordinate(droid.getPosition().getCoordinate().getX(), droid.getPosition().getCoordinate().getY() + 1)).isBlocked();
            case SOUTH:
                return world.getDecks().get(droid.getPosition().getDeckId()).getFieldFromCoordinate(new Coordinate(droid.getPosition().getCoordinate().getX(), droid.getPosition().getCoordinate().getY() - 1)).isBlocked();
            case EAST:
                return world.getDecks().get(droid.getPosition().getDeckId()).getFieldFromCoordinate(new Coordinate(droid.getPosition().getCoordinate().getX() + 1, droid.getPosition().getCoordinate().getY())).isBlocked();
            case WEST:
                return world.getDecks().get(droid.getPosition().getDeckId()).getFieldFromCoordinate(new Coordinate(droid.getPosition().getCoordinate().getX() - 1, droid.getPosition().getCoordinate().getY())).isBlocked();
            default:
                throw new IllegalArgumentException(orderType.toString());
        }

    }
}
