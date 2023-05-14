package thkoeln.st.st2praktikum.exercise;

public class Barrier {

    private Coordinate start;
    private Coordinate end;

    /*
            Barrier barrier2 = new Barrier(new Coordinate(4, 1 ), new Coordinate(2, 1 ));
                        Barrier barrier2 = new Barrier(new Coordinate(10, 1 ), new Coordinate(10, 5 ));
                                                Barrier barrier2 = new Barrier(new Coordinate(10, 1 ), new Coordinate(11, 2 ));
     */
    public Barrier(Coordinate pos1, Coordinate pos2) {

        if (pos1.getY().equals(pos2.getY())) {
            if (pos1.getX() < pos2.getX()) {
                start = pos1;
                end = pos2;
            } else {
                start = pos2;
                end = pos1;
            }
        } else if (pos1.getX().equals(pos2.getX())) {
            if(pos1.getY() < pos2.getY()) {
                start = pos1;
                end = pos2;
            } else if(pos1.getY() > pos2.getY()) {
                start = pos2;
                end = pos1;
            }
        }
        if(!pos1.getX().equals(pos2.getX()) && !pos1.getY().equals(pos2.getY())) {
            throw new RuntimeException("Error! It' a digonal barrier. Please check your input!");
        }

    }

    /**
     * @param barrierString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Barrier(String barrierString) {

        if (!barrierString.contains("-")) {
            throw new RuntimeException("Please check your general usage!");
        }

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

            if(firstX < 0 || firstY < 0 || secondX < 0 || secondY < 0) {
                throw new RuntimeException("Error, please check one of your inputs!");
            }

            start = new Coordinate(firstX, firstY);
            end = new Coordinate(secondX, secondY);
            if (firstY == (secondY)) {
                if (firstX < secondX) {
                    start = new Coordinate(firstX, firstY);
                    end = new Coordinate(secondX, secondY);

                } else {
                    start = new Coordinate(secondX, secondY);
                    end = new Coordinate(firstX, firstY);
                }
            } else if (firstX == secondX) {
                if(firstY < secondY) {
                    start = new Coordinate(firstX, firstY);
                    end = new Coordinate(secondX, secondY);
                } else {
                    start = new Coordinate(secondX, secondY);
                    end = new Coordinate(firstX, firstY);
                }
            }
            if(firstX != secondX && firstY != secondY) {
                throw new RuntimeException("Error! It' a digonal barrier. Please check your input!");
            }

        } catch (NumberFormatException e) {
            throw new RuntimeException("Error, please check one of your inputs!");
        }
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
