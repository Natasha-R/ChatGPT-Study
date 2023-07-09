package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;

public class Exercise0 implements Walkable {

    public Exercise0() {
        this.setIllegalMoves();
    }

    public ArrayList<Move> illegalMoves = new ArrayList<>();
    private int positionX = 0;
    private int positionY = 2;

    @Override
    public String walk(String walkCommandString) {
        WalkCommand walkCommand = new WalkCommand(walkCommandString);
        makeMoves(walkCommand);
        return "(" + this.positionX + "," + this.positionY + ")";
    }

    private void makeMoves(WalkCommand walkCommand) {
        for (int i = 0; i < walkCommand.steps; i++) {
            Move currentMove = new Move(this.positionX, this.positionY, walkCommand.direction);
            if(illegalMoves.contains(currentMove)) {
                break;
            }

            switch (walkCommand.direction) {
                case EAST:
                    if(positionX == 11) {
                        break;
                    }
                    positionX++;
                    System.out.println("Right");
                    break;
                case WEST:
                    if(positionX == 0) {
                        break;
                    }
                    positionX--;
                    System.out.println("Left");
                    break;
                case NORTH:
                    if(positionY == 8) {
                        break;
                    }
                    positionY++;
                    break;
                case SOUTH:
                    if(positionY == 0) {
                        break;
                    }
                    positionY--;
                    System.out.println("Down");
                    break;
            }
        }
    }

    private void setIllegalMoves() {
        illegalMoves.add(new Move(4, 4, Direction.NORTH));
        illegalMoves.add(new Move(5, 4, Direction.NORTH));
        illegalMoves.add(new Move(6, 4, Direction.NORTH));

        illegalMoves.add(new Move(4, 5, Direction.SOUTH));
        illegalMoves.add(new Move(5, 5, Direction.SOUTH));
        illegalMoves.add(new Move(6, 5, Direction.SOUTH));


        illegalMoves.add(new Move(2, 0, Direction.EAST));
        illegalMoves.add(new Move(2, 1, Direction.EAST));
        illegalMoves.add(new Move(2, 2, Direction.EAST));

        illegalMoves.add(new Move(3, 0, Direction.WEST));
        illegalMoves.add(new Move(3, 1, Direction.WEST));
        illegalMoves.add(new Move(3, 3, Direction.WEST));


        illegalMoves.add(new Move(4, 0, Direction.EAST));
        illegalMoves.add(new Move(4, 1, Direction.EAST));
        illegalMoves.add(new Move(4, 2, Direction.EAST));
        illegalMoves.add(new Move(4, 3, Direction.EAST));

        illegalMoves.add(new Move(5, 0, Direction.WEST));
        illegalMoves.add(new Move(5, 1, Direction.WEST));
        illegalMoves.add(new Move(5, 2, Direction.WEST));
        illegalMoves.add(new Move(5, 3, Direction.WEST));


        illegalMoves.add(new Move(6, 5, Direction.EAST));
        illegalMoves.add(new Move(6, 6, Direction.EAST));
        illegalMoves.add(new Move(6, 7, Direction.EAST));
        illegalMoves.add(new Move(6, 8, Direction.EAST));

        illegalMoves.add(new Move(7, 5, Direction.WEST));
        illegalMoves.add(new Move(7, 6, Direction.WEST));
        illegalMoves.add(new Move(7, 7, Direction.WEST));
        illegalMoves.add(new Move(7, 8, Direction.WEST));
    }
}
