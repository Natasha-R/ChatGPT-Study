package thkoeln.st.st2praktikum.exercise.field.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;



public class Barrier {

    private Coordinate start;
    private Coordinate end;

    public Barrier() {
    }

    public Barrier(Coordinate start, Coordinate end) {

        if (start.getY().equals(end.getY())) {
            if (start.getX() < end.getX()) {
                this.start = start;
                this.end = end;
            } else {
                this.start = end;
                this.end = start;
            }
        } else if (start.getX().equals(end.getX())) {
            if (start.getY() < end.getY()) {
                this.start = start;
                this.end = end;
            } else if (start.getY() > end.getY()) {
                this.start = end;
                this.end = start;
            }
        }
        if (!start.getX().equals(end.getX()) && !start.getY().equals(end.getY())) {
            throw new RuntimeException("Error! It' a digonal barrier. Please check your input!");
        }
    }
    
    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public static Barrier fromString(String barrierString) {
        if (!barrierString.contains("-")) {
            throw new RuntimeException("Please check your general usage!");
        }

        Barrier barrier = new Barrier();

        String[] temp = barrierString.split("-");

        if (!temp[0].startsWith("(") || !temp[0].contains(",") || !temp[0].endsWith(")")) {
            throw new RuntimeException("Please check your first input");
        }
        String[] firstSplit = temp[0].split(",", 2);
        String[] secondSplit = temp[1].split(",", 2);
        String firstReplace = firstSplit[0].replace("(", "");
        String secondReplace = firstSplit[1].replace(")", "");
        String thirdReplace = secondSplit[0].replace("(", "");
        String fourthReplace = secondSplit[1].replace(")", "");
        try {
            int firstX = Integer.parseInt(firstReplace);
            int firstY = Integer.parseInt(secondReplace);
            int secondX = Integer.parseInt(thirdReplace);
            int secondY = Integer.parseInt(fourthReplace);

            if (firstX < 0 || firstY < 0 || secondX < 0 || secondY < 0) {
                throw new RuntimeException("Error, please check one of your inputs!");
            }

            barrier.start = new Coordinate(firstX, firstY);
            barrier.end = new Coordinate(secondX, secondY);
            if (firstY == (secondY)) {
                if (firstX < secondX) {
                    barrier.start = new Coordinate(firstX, firstY);
                    barrier.end = new Coordinate(secondX, secondY);

                } else {
                    barrier.start = new Coordinate(secondX, secondY);
                    barrier.end = new Coordinate(firstX, firstY);
                }
            } else if (firstX == secondX) {
                if (firstY < secondY) {
                    barrier.start = new Coordinate(firstX, firstY);
                    barrier.end = new Coordinate(secondX, secondY);
                } else {
                    barrier.start = new Coordinate(secondX, secondY);
                    barrier.end = new Coordinate(firstX, firstY);
                }
            }
            if (firstX != secondX && firstY != secondY) {
                throw new RuntimeException("Error! It' a digonal barrier. Please check your input!");
            }

        } catch (NumberFormatException e) {
            throw new RuntimeException("Error, please check one of your inputs!");
        }
        return barrier;
    }
    
}
