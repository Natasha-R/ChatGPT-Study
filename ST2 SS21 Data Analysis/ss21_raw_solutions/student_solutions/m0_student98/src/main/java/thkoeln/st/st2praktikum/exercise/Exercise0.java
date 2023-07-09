package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    private Position CurrentPos;
    private Matrix Coordinates;

    public Exercise0() {
        this.CurrentPos = new Position(8, 3);
        this.Coordinates = Matrix.createMatrix();
    }

    @Override
    public String moveTo(String moveCommandString) {
        AbstractMove Move = null;
        CommandParser Command = CommandParser.StringToCommand(moveCommandString);

//        System.out.println(CurrentPos);

        switch (Command.getOrientation()) {
            case NO: Move = new MoveNorth();    break;
            case EA: Move = new MoveEast();     break;
            case SO: Move = new MoveSouth();    break;
            case WE: Move = new MoveWest();     break;
        }
        CurrentPos = Move.moveUntilBlocked(Coordinates, CurrentPos, Command.getStepLength());

//        System.out.println(moveCommandString);
//        System.out.println(CurrentPos.toString());
//        System.out.println("");
        return CurrentPos.toString();
    }
}
