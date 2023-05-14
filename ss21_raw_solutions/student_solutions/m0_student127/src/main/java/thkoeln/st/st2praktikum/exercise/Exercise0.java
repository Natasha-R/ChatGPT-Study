package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.util.Pair;

import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise0 implements Walkable {
    // default barriers and starting position
    private int[] position = new int[] {5, 3};
    List<Barrier> barriers = Barrier.getDefaultBarrierList();

    @Override
    public String walk(String walkCommandString) {
        if (!isValidInput(walkCommandString)) {
            throw new IllegalArgumentException();
        }

        Pair<Direction, Integer> walkCommand = getPairOf(walkCommandString);

        for(int currentStep = 0; currentStep < walkCommand.getSecond(); currentStep++) {
            int[] nextPosition = getNextPosition(walkCommand.getFirst());

            Predicate<Barrier> p = element -> element.isBlocking(position, nextPosition);
            if (barriers.stream().anyMatch(p)) {
                break;
            }

            position = nextPosition;
        }

        return "(" + position[0] + "," + position[1] + ")";
    }

    public Boolean isValidInput(String walkCommandString) {
        Pattern st = Pattern.compile("^\\[((no)|(ea)|(so)|(we)),\\d*\\]\\z$");
        Matcher mt = st.matcher(walkCommandString);
        return mt.matches();
    }

    public Pair<Direction, Integer> getPairOf(String walkCommandString) {
        final StringTokenizer st = new StringTokenizer(walkCommandString, "[],");

        Direction direction = Direction.valueOf(st.nextToken());
        Integer steps = Integer.parseInt(st.nextToken());

        return Pair.of(direction,steps);
    }

    public int[] getNextPosition(Direction direction) {
        switch (direction) {
            case no:
                return new int[] {position[0], position[1]+1};
            case ea:
                return new int[] {position[0]+1, position[1]};
            case so:
                return new int[] {position[0], position[1]-1};
            case we:
                return new int[] {position[0]-1, position[1]};
            default:
                throw new IllegalArgumentException();
        }
    }
}
